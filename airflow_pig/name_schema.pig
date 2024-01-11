%DECLARE movie_path $input_dir3/result1.tsv
%DECLARE name_path $input_dir4/name.basics.tsv
%DECLARE output_path $output_dir6

--LOAD FILES
movie = LOAD '$movie_path' USING org.apache.pig.piggybank.storage.CSVExcelStorage('\t',
    'NO_MULTILINE','NOCHANGE')
    as (person_id:chararray, 
        actor:int,
        director:int);
DESCRIBE movie;
name = LOAD '$name_path' USING org.apache.pig.piggybank.storage.CSVExcelStorage('\t',
    'NO_MULTILINE','NOCHANGE', 'SKIP_INPUT_HEADER')
    as (person_id:chararray, 
        primaryName:chararray, 
        birthYear:int,
        deathYear:int,
        primaryProfession:chararray,
        knownForTitles:chararray);
DESCRIBE name;

--TOKENZE FLATTEN profession
name_projected = FOREACH name
    GENERATE person_id as person_id,
    primaryName as primaryName,
    TOKENIZE(primaryProfession) as professions;
DESCRIBE name_projected;
define SetIntersect datafu.pig.sets.SetIntersect();

name_flattened = FOREACH name_projected
    GENERATE person_id as person_id,
    primaryName as primaryName,
    FLATTEN(professions) as profession;
DESCRIBE name_flattened;

--FILTER
name_director = FILTER name_flattened BY (profession == 'director');
DESCRIBE name_director;

name_actor = FILTER name_flattened BY (profession == 'actor') OR (profession == 'actress');
DESCRIBE name_actor;

--JOIN
name_actor_movieCount = JOIN name_actor BY person_id, movie BY person_id;
DESCRIBE name_actor_movieCount;

name_director_movieCount = JOIN name_director BY person_id, movie BY person_id;
DESCRIBE name_director_movieCount;

--SIMPLE JOINED
actor_movie_simple = FOREACH name_actor_movieCount GENERATE
    name_actor::primaryName as primaryName,
    name_actor::profession as role,
    movie::actor as movies;
DESCRIBE actor_movie_simple;

director_movie_simple = FOREACH name_director_movieCount GENERATE
    name_director::primaryName as primaryName,
    name_director::profession as role,
    movie::director as movies;
DESCRIBE director_movie_simple;

--ORDER LIMIT
/*
actor_movie_ordered = ORDER actor_movie_simple BY movies DESC;
DESCRIBE actor_movie_ordered;

director_movie_ordered = ORDER director_movie_simple BY movies DESC;
DESCRIBE director_movie_ordered;

result_actor = LIMIT actor_movie_ordered 3;
result_director = LIMIT director_movie_ordered 3;
*/

result_actor = LIMIT(ORDER actor_movie_simple BY movies DESC) 3;
DESCRIBE result_actor;

result_director =  LIMIT (ORDER director_movie_simple BY movies DESC) 3;
DESCRIBE result_director;

-- UNION
final_result = UNION result_actor, result_director;
DESCRIBE final_result;

--STORE
STORE final_result INTO '$output_path' USING JsonStorage();