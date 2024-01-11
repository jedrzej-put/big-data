import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;

import java.io.*;

public class ActorDirector implements WritableComparable<ActorDirector> {

    IntWritable actor;
    IntWritable director;

    public ActorDirector() {
        set(new IntWritable(0), new IntWritable(0));
    }

    public ActorDirector(Integer actor, Integer director) {
        set(new IntWritable(actor), new IntWritable(director));
    }

    public void set(IntWritable actor, IntWritable director) {
        this.actor = actor;
        this.director = director;
    }



    public IntWritable getActor() {
        return actor;
    }

    public IntWritable getDirector() {
        return director;
    }

    public void addActorDirector(ActorDirector actorDirector) {
        set(new IntWritable(this.actor.get() + actorDirector.getActor().get()),
                new IntWritable(this.director.get() + actorDirector.getDirector().get()));
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {

        actor.write(dataOutput);
        director.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {

        actor.readFields(dataInput);
        director.readFields(dataInput);
    }

    @Override
    public int compareTo(ActorDirector actorDirector) {

        // compares the first of the two values
        int comparison = actor.compareTo(actorDirector.actor);

        // if they're not equal, return the value of compareTo between the "sum" value
        if (comparison != 0) {
            return comparison;
        }

        // else return the value of compareTo between the "count" value
        return director.compareTo(actorDirector.director);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActorDirector actorDirector = (ActorDirector) o;

        return actor.equals(actorDirector.actor) && director.equals(actorDirector.director);
    }

    @Override
    public int hashCode() {
        int result = actor.hashCode();
        result = 31 * result + director.hashCode();
        return result;
    }
}