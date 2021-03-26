CREATE DATABASE wildlife_tracker;
\c wildlife_tracker;
CREATE TABLE animals (id serial PRIMARY KEY, name varchar,age varchar, healthy varchar,endangered varchar );
CREATE TABLE sightings (id serial PRIMARY KEY, location varchar ,ranger varchar);
CREATE DATABASE wildlife_tracker_test WITH TEMPLATE wildlife_tracker;