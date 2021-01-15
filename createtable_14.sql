DROP DATABASE IF EXISTS `moviedb`;
CREATE DATABASE `moviedb`; 
USE `moviedb`;

CREATE TABLE employees(
	email 					VARCHAR(50)		DEFAULT '',
    password				VARCHAR(20)		NOT NULL 	DEFAULT '',
    fullname				VARCHAR(100)    DEFAULT '',
    PRIMARY KEY (email)
);

CREATE TABLE movies (
	id						INT				NOT NULL    AUTO_INCREMENT,
	title					VARCHAR(100)	NOT NULL    DEFAULT '',
	year					INT				NOT NULL,
    director				VARCHAR(100)	NOT NULL	DEFAULT '',
    banner_url				VARCHAR(200)	DEFAULT '',
    trailer_url				VARCHAR(200)	DEFAULT '',
	PRIMARY KEY (id)
);

CREATE TABLE stars (
	id						INT				NOT NULL    AUTO_INCREMENT,
	first_name				VARCHAR(50)		NOT NULL	DEFAULT '',
	last_name				VARCHAR(50)		NOT NULL	DEFAULT '',
    dob						DATE,
    photo_url				VARCHAR(200)	DEFAULT '',
	PRIMARY KEY (id)
);

CREATE TABLE stars_in_movies (
	star_id					INT				NOT NULL,
	movie_id				INT				NOT NULL,
	FOREIGN KEY (star_id) REFERENCES stars(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
);

CREATE TABLE genres (
	id						INT				NOT NULL AUTO_INCREMENT,
	name					VARCHAR(32)		NOT NULL	DEFAULT '',
    PRIMARY KEY (id)
);

CREATE TABLE genres_in_movies (
	genre_id				INT				NOT NULL,
	movie_id				INT				NOT NULL,
	FOREIGN KEY (genre_id) REFERENCES genres(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
);

CREATE TABLE creditcards (
	id						VARCHAR(20)		NOT NULL	DEFAULT '',
	first_name				VARCHAR(50)		NOT NULL	DEFAULT '',
	last_name				VARCHAR(50)		NOT NULL	DEFAULT '',
    expiration				DATE			NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE customers (
	id						INT				NOT NULL    AUTO_INCREMENT,
	first_name				VARCHAR(50)		NOT NULL	DEFAULT '',
	last_name				VARCHAR(50)		NOT NULL	DEFAULT '',
    cc_id					VARCHAR(20)		NOT NULL	DEFAULT '',
    address					VARCHAR(200),
    email					VARCHAR(50),
    password				VARCHAR(20),
	PRIMARY KEY (id),
	FOREIGN KEY (cc_id) REFERENCES creditcards(id)
);

CREATE TABLE sales (
	id						INT				NOT NULL    AUTO_INCREMENT,
	customer_id				INT				NOT NULL,
    movie_id				INT 			NOT NULL,	
    sale_date				DATE 			NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE,
	FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE
);

