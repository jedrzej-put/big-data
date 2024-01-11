import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

public class AvgSizeStations extends Configured implements Tool {

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new AvgSizeStations(), args);
        System.exit(res);
    }

    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(getConf(), "AvgSizeStations");
        job.setJarByClass(this.getClass());
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //TODO: set mapper and reducer class
        job.setMapperClass(AvgSizeStationMapper.class);
        job.setCombinerClass(AvgSizeStationCombiner.class);
        job.setReducerClass(AvgSizeStationReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(ActorDirector.class);
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static class AvgSizeStationMapper extends Mapper<LongWritable, Text, Text, ActorDirector> {

        private final Text person_nconst = new Text();
        private final ActorDirector actorDirector = new ActorDirector();


        public void map(LongWritable offset, Text lineText, Context context) {
            try {
                if (offset.get() != 0) {
                    String line = lineText.toString();
                    int i = 0;
                    for (String word : line
                            .split("\\t")) {
                        if (i == 2) {
                            person_nconst.set(word);
                        }
                        if (i == 3) {
                            if(word.equals("actor") || word.equals("actress") || word.equals("self")){
                                actorDirector.set(new IntWritable(1), new IntWritable(0));
                            }else if(word.equals("director")){
                                actorDirector.set(new IntWritable(0), new IntWritable(1));
                            }else{
                                actorDirector.set(new IntWritable(0), new IntWritable(0));
                            }
                        }
                        i++;
                    }
                    //DONE: write intermediate pair to the context
                    context.write(person_nconst, actorDirector);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class AvgSizeStationReducer extends Reducer<Text, ActorDirector, Text, Text> {

        private final ActorDirector resultValue = new ActorDirector();
        ActorDirector sumActorDirector = new ActorDirector(0, 0);
        @Override
        public void reduce(Text key, Iterable<ActorDirector> values,
                           Context context) throws IOException, InterruptedException {
            sumActorDirector.set(new IntWritable(0), new IntWritable(0));
            for (ActorDirector val : values) {
                sumActorDirector.addActorDirector(val);
            }
            resultValue.set(sumActorDirector.getActor(), sumActorDirector.getDirector());
            //DONE: write result pair to the context
            Text resultTextValue = new Text(resultValue.getActor() + "\t" + resultValue.getDirector());
            context.write(key, resultTextValue);
        }
    }

    public static class AvgSizeStationCombiner extends Reducer<Text, ActorDirector, Text, ActorDirector> {

        private final ActorDirector actorDirector = new ActorDirector(0, 0);

        @Override
        public void reduce(Text key, Iterable<ActorDirector> values, Context context) throws IOException, InterruptedException {

            actorDirector.set(new IntWritable(0), new IntWritable(0));

            for (ActorDirector val : values) {
                actorDirector.addActorDirector(val);
            }
            context.write(key, actorDirector);
        }
    }

}