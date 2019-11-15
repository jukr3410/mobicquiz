
-- ************************************** levels

CREATE TABLE levels
(
 levelno int NOT NULL ,
 level   varchar(45) NOT NULL ,

PRIMARY KEY (levelno)
);




-- ************************************** subjects

CREATE TABLE subjects
(
 subjectno int NOT NULL ,
 subject   varchar(45) NOT NULL ,

PRIMARY KEY (subjectno)
);


-- ************************************** teachers

CREATE TABLE teachers
(
 teacherno   int NOT NULL ,
 name        varchar(45) NOT NULL ,
 email       varchar(45) NOT NULL ,
 password    varchar(45) NOT NULL ,
 activatekey varchar(45) ,
 activated   varchar(45) ,

PRIMARY KEY (teacherno)
);



-- ************************************** teachersubjects

CREATE TABLE teachersubjects
(
 teachersubjectno int NOT NULL ,
 teacherno        int NOT NULL ,
 subjectno        int NOT NULL ,

PRIMARY KEY (teachersubjectno),
 FOREIGN KEY  (teacherno) REFERENCES teachers (teacherno),
 FOREIGN KEY  (subjectno) REFERENCES subjects (subjectno)
);





-- ************************************** students

CREATE TABLE students
(
 studentno   int NOT NULL ,
 name        varchar(45) NOT NULL ,
 email       varchar(45) NOT NULL ,
 password    varchar(45) NOT NULL ,
 activatekey varchar(45) ,
 activated   varchar(45) ,
 levelno     int NOT NULL ,

PRIMARY KEY (studentno),
  FOREIGN KEY  (levelno) REFERENCES levels (levelno)
);





-- ************************************** studentsubjects

CREATE TABLE studentsubjects
(
 studentsubjectno int NOT NULL ,
 studentno        int NOT NULL ,
 subjectno        int NOT NULL ,

PRIMARY KEY (studentsubjectno),
 FOREIGN KEY  (studentno) REFERENCES students (studentno),
 FOREIGN KEY  (subjectno) REFERENCES subjects (subjectno)
);





-- ************************************** quizs

CREATE TABLE quizs
(
 quizno    int NOT NULL ,
 title     varchar(45) NOT NULL ,
 time      decimal NOT NULL ,
 fullscore decimal NOT NULL ,
 levelno   int NOT NULL ,
 subjectno int NOT NULL ,
 status varchar(45),

PRIMARY KEY (quizno),
  FOREIGN KEY  (levelno) REFERENCES levels (levelno),
 FOREIGN KEY  (subjectno) REFERENCES subjects (subjectno)
);




-- ************************************** questions

CREATE TABLE questions
(
 questionno int NOT NULL ,
 question   varchar(45) NOT NULL ,
 ans1       varchar(45) ,
 ans2       varchar(45) ,
 ans3       varchar(45) ,
 ans4       varchar(45) ,
 correctans varchar(45) ,
 quizno     int NOT NULL ,

PRIMARY KEY (questionno),  FOREIGN KEY  (quizno) REFERENCES quizs (quizno)
);



-- ************************************** historys

CREATE TABLE historys
(
 historyno int NOT NULL ,
 score     decimal NOT NULL ,
 date      date NOT NULL ,
 quizno    int NOT NULL ,
 studentno int NOT NULL ,

PRIMARY KEY (historyno),  FOREIGN KEY  (quizno) REFERENCES quizs (quizno),
 FOREIGN KEY  (studentno) REFERENCES students (studentno)
);




INSERT INTO MOBIC.LEVELS (LEVELNO, "LEVEL") 
	VALUES (1, 'Grade 1');
INSERT INTO MOBIC.LEVELS (LEVELNO, "LEVEL") 
	VALUES (2, 'Grade 2');
INSERT INTO MOBIC.LEVELS (LEVELNO, "LEVEL") 
	VALUES (3, 'Grade 3');
INSERT INTO MOBIC.LEVELS (LEVELNO, "LEVEL") 
	VALUES (4, 'Grade 4');
INSERT INTO MOBIC.LEVELS (LEVELNO, "LEVEL") 
	VALUES (5, 'Grade 5');
INSERT INTO MOBIC.LEVELS (LEVELNO, "LEVEL") 
	VALUES (6, 'Grade 6');



INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES (101, 'Math');
INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES (201, 'Science');
INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES (301, 'Art');





