add JAR /usr/lib/hive/lib/hive-hcatalog-core.jar;

DROP TABLE IF EXISTS mapreduce_output;
DROP TABLE IF EXISTS hive_input;
DROP TABLE IF EXISTS json_output;

create table mapreduce_output(movie_id string, actor_number int) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' STORED AS TEXTFILE;

load data inpath '${hiveconf:input_dir3}' into table mapreduce_output;

CREATE TABLE hive_input(
    tconst STRING,
    titleType STRING,
    primaryTitle STRING,
    originalTitle STRING,
    isAdult INT,
    startYear STRING,
    endYear STRING,
    runtimeMinutes STRING,
    genres STRING)
ROW FORMAT delimited fields terminated BY '\t' tblproperties("skip.header.line.count"="1");

load data inpath '${hiveconf:input_dir4}' into table hive_input;

CREATE EXTERNAL TABLE json_output(
    genre STRING,
    actors INT,
    movies INT
)
ROW FORMAT SERDE 
'org.apache.hive.hcatalog.data.JsonSerDe'
LOCATION '${hiveconf:output_dir6}';

WITH hive_movies_only AS (SELECT tconst AS movie_id, genres FROM hive_input WHERE titleType = "movie"),
joined_cte AS (SELECT m.movie_id AS movie_id, m.actor_number AS actor_number, h.genres AS genres FROM mapreduce_output m INNER JOIN hive_movies_only h ON m.movie_id = h.movie_id),
genres_split AS (SELECT movie_id, actor_number, genre FROM joined_cte lateral view explode(split(genres, ",")) genres AS genre),
results AS (SELECT genre, COUNT(movie_id) as movies, SUM(actor_number) as actors FROM genres_split GROUP BY genre ORDER BY actors DESC LIMIT 3)
INSERT INTO json_output SELECT * FROM results;
