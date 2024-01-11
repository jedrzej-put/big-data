import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

public class ActorCounter extends Configured implements Tool {

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new ActorCounter(), args);
        System.exit(res);
    }

    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(getConf(), "ActorCounter");
        job.setJarByClass(this.getClass());
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.setMapperClass(ActorCounterMapper.class);
        job.setReducerClass(ActorCounterReducer.class);
        job.setCombinerClass(ActorCounterCombiner.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static class ActorCounterMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

        private final Text movieId = new Text();
        private String actorCategory;
        private final IntWritable one = new IntWritable(1);
        private final IntWritable zero = new IntWritable(0);

        public void map(LongWritable offset, Text lineText, Context context) {
            try {
                if (offset.get() != 0) {
                    String line = lineText.toString();
                    int i = 0;
                    for (String word : line
                            .split("\\t")) {
                        if (word.isEmpty()) {
                            continue;
                        }
                        if (i == 0) {
                            movieId.set(word);
                        }
                        if (i == 3) {
                            actorCategory = word;
                        }
                        i++;
                    }
                    if (actorCategory.equals("actor") || actorCategory.equals("actress") || actorCategory.equals("self")) {
                        context.write(movieId, one);
                    } else {
                        context.write(movieId, zero);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class ActorCounterReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        private final IntWritable resultValue = new IntWritable();
        int count;

        @Override
        public void reduce(Text movieId, Iterable<IntWritable> counts,
                           Context context) throws IOException, InterruptedException {
            count = 0;

            for (IntWritable val : counts) {
                count += val.get();
            }
            resultValue.set(count);
            context.write(movieId, resultValue);
        }
    }

    public static class ActorCounterCombiner extends Reducer<Text, IntWritable, Text, IntWritable> {

        IntWritable total = new IntWritable();

        @Override
        public void reduce(Text movieId, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

            int tmpCount = 0;

            for (IntWritable val : values) {
                tmpCount += val.get();
            }
            total.set(tmpCount);
            context.write(movieId, total);
        }
    }
}