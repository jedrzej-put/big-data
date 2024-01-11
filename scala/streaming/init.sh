CLUSTER_NAME=$(/usr/share/google/get_metadata_value attributes/dataproc-cluster-name)

kafka-topics.sh --bootstrap-server ${CLUSTER_NAME}-w-0:9092 --create \     --replication-factor 2 --partitions 3 --topic movie-rates

kafka-topics.sh --bootstrap-server ${CLUSTER_NAME}-w-0:9092 --list

mkdir movie_titles
cd movie_titles
hadoop fs -copyToLocal gs://$1/movie_titles.csv
cd ..

mkdir movie_rates
cd movie_rates
hadoop fs -copyToLocal gs://$1/netflix-prize-data/*
cd ..

export CLUSTER_NAME
