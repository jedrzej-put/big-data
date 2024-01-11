import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession, Dataset}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming._
import scala.io.Source
import collection.immutable.Map
import java.nio.charset.CodingErrorAction
import scala.io.{Codec, Source}


private case class Event(
                          date: String,
                          film_id: Int,
                          film_title: String,
                          user_id: Int,
                          rate: Int
                        )
object Main {

  def main(args: Array[String]): Unit = {
    if (args.length < 8) {
      System.err.println("Usage: java -jar NPD.jar <topic> <server> <moviesFilePath> <netflixTrigger> <anomalyTrigger> <anomalyWindow> <anomalyTresh1> <anomalyTresh2>")
      System.exit(1)
    }
    // prevents from wrong coding (solution for my errors)
    implicit val codec: Codec = Codec("UTF-8")
    codec.onMalformedInput(CodingErrorAction.REPLACE)
    codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

    // Arguments from command
    val Array(topic, server, moviesFilePath, netflixTrigger, anomalyTrigger, anomalyWindow, anomalyTresh1, anomalyTresh2) = args

    // Reading csv file and aggregating film id with title
    val moviesLines = Source.fromFile(moviesFilePath).getLines().map(_.split(",").toSeq).toSeq
    val moviesTitle = moviesLines.map(x => x.head -> x(2)).toMap

    // Create local SparkSession
    val spark = SparkSession
      .builder
      .appName("Netflix")
      .getOrCreate()

    // To not show so much warnings in spark
    spark.sparkContext.setLogLevel("WARN")
    // To show some schemas if default size is too small
    spark.conf.set("spark.sql.debug.maxToStringFields", 100)
    spark.conf.set("spark.sql.streaming.checkpointLocation", "checkpoint")

    import spark.implicits._

    // Create DataFrame representing the stream of input lines from subscribing to kafka topic
    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", server)
      .option("subscribe", topic)
      .load()

    // Subscribe to 1 topic, without headers
    val data = df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
      .as[(String, String)]



    // add data from kafka source to dataframe and class Event
    val netflixToEvent = data.
      map(_._2.split(","))
      .map(x => Event(
        date = x(0),
        film_id = x(1).toInt,
        film_title = moviesTitle.getOrElse(x(1), "none"),
        user_id = x(2).toInt,
        rate = x(3).toInt
      ))
      .withColumn("ts", to_timestamp($"date", "yyyy-MM-dd"))



    // Some operations, aggregations and timestamps, processing events
    val netflix = netflixToEvent
      .withColumn("month", to_timestamp(date_format($"ts", "yyyy-MM-'01'"), "yyyy-MM-dd"))
      .withWatermark("month", "1 month")
      .groupBy( $"month", $"film_id", $"film_title")
      .agg(
        count("rate").as("number_of_rates"),
        sum("rate").as("sum_of_rates"),
        approx_count_distinct("user_id").as("number_of_unique_users_rates")
      )
      .withColumn("month", date_format($"month", "yyyy-MM"))
      .withColumn("film_id_month", concat(col("film_id"),lit(':'), col("month")))
      .select("film_id_month", "month","film_id", "film_title", "number_of_rates", "sum_of_rates", "number_of_unique_users_rates")

    val netflixStream = netflix
      .writeStream
      .format("csv")
      .option("checkpointLocation", "/tmp/spark-streaming-checkpoint") // Replace with a suitable checkpoint location
      .outputMode("append")
      .trigger(Trigger.ProcessingTime(netflixTrigger)) // Specify the desired trigger interval
      .start("stat/")

//    val netflixStream = netflix
//      .writeStream
//      .format("console")
//      .trigger(Trigger.ProcessingTime(10000))
//      .start()


        // Some operations, aggregations and timestamps, windows, processing events to look for anomaly
        val anomalies = netflixToEvent
          .withWatermark("ts", "1 day")
          .groupBy(window($"ts", anomalyWindow), $"film_id") //tumbling window
          .agg(
            count("rate").as("interest"),
            avg("rate").as("avg_interest"),
          )
          .withColumn("anomaly",$"interest" >= anomalyTresh1 && $"avg_interest" >= anomalyTresh2)
          .withColumn("start", date_format( $"window"("start"), "yyyy-MM-dd"))
          .withColumn("end", date_format( $"window"("end"), "yyyy-MM-dd"))
          .withColumn("film_id_start_end", concat(col("film_id"),lit(':'), col("start"),lit(':'), col("end")))
          .select("film_id_start_end", "start", "end", "film_id", "interest", "avg_interest", "anomaly")
          .where("anomaly == true")


    val anomaliesStream = anomalies
      .writeStream
      .format("csv")
      .option("checkpointLocation", "/tmp/spark-streaming-checkpoint") // Replace with a suitable checkpoint location
      .outputMode("append")
      .trigger(Trigger.ProcessingTime(anomalyTrigger)) // Specify the desired trigger interval
      .start("anom/")



    netflixStream.awaitTermination()
    anomaliesStream.awaitTermination()

  }
}
