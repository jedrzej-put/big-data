#!/bin/sh

# Argumenty: <topic> <server> <moviesFilePath> <netflixTrigger> <anomalyTrigger> <anomalyWindow> <anomalyTresh1> <anomalyTresh2>"

# usage: ./engine.sh <server/cluster>
# for example localhost:9092

echo "usage: ./engine.sh <server/cluster>"

CLUSTER_NAME=$(/usr/share/google/get_metadata_value attributes/dataproc-cluster-name)

spark-submit \
--packages org.apache.spark:spark-sql-kafka-0-10_2.12:3.1.2 \
--driver-memory 8g \
--class Main --master local[*] target/scala-2.12/big-data_2.12-0.1.0.jar \
movie-rates \
${CLUSTER_NAME}-w-0:9092 \
movie_titles/movie_titles.csv \
"10 seconds" \
"10 seconds" \
"30 days" \
100 \
4
