
-- ************************************** levels

CREATE TABLE levels
(
 levelno varchar(45) NOT NULL ,
 level   varchar(45) NOT NULL ,

PRIMARY KEY (levelno)
);




-- ************************************** subjects

CREATE TABLE subjects
(
 subjectno varchar(45) NOT NULL ,
 subject   varchar(45) NOT NULL ,

PRIMARY KEY (subjectno)
);


-- ************************************** teachers

CREATE TABLE teachers
(
 teacherno   varchar(45) NOT NULL ,
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
 teachersubjectno varchar(45) NOT NULL ,
 teacherno        varchar(45) NOT NULL ,
 subjectno        varchar(45) NOT NULL ,

PRIMARY KEY (teachersubjectno),
 FOREIGN KEY  (teacherno) REFERENCES teachers (teacherno),
 FOREIGN KEY  (subjectno) REFERENCES subjects (subjectno)
);





-- ************************************** students

CREATE TABLE students
(
 studentno   varchar(45) NOT NULL ,
 name        varchar(45) NOT NULL ,
 email       varchar(45) NOT NULL ,
 password    varchar(45) NOT NULL ,
 activatekey varchar(45) ,
 activated   varchar(45) ,
 levelno     varchar(45) NOT NULL ,

PRIMARY KEY (studentno),
  FOREIGN KEY  (levelno) REFERENCES levels (levelno)
);





-- ************************************** studentsubjects

CREATE TABLE studentsubjects
(
 studentsubjectno varchar(45) NOT NULL ,
 studentno        varchar(45) NOT NULL ,
 subjectno        varchar(45) NOT NULL ,

PRIMARY KEY (studentsubjectno),
 FOREIGN KEY  (studentno) REFERENCES students (studentno),
 FOREIGN KEY  (subjectno) REFERENCES subjects (subjectno)
);





-- ************************************** quizs

CREATE TABLE quizs
(
 quizno    varchar(45) NOT NULL ,
 title     varchar(100) NOT NULL ,
 time      decimal NOT NULL ,
 fullscore decimal NOT NULL ,
 levelno   varchar(45) NOT NULL ,
 subjectno varchar(45) NOT NULL ,
 status varchar(45),

PRIMARY KEY (quizno),
  FOREIGN KEY  (levelno) REFERENCES levels (levelno),
 FOREIGN KEY  (subjectno) REFERENCES subjects (subjectno)
);




-- ************************************** questions

CREATE TABLE questions
(
 questionno varchar(45) NOT NULL ,
 question   varchar(100) NOT NULL ,
 ans1       varchar(45) ,
 ans2       varchar(45) ,
 ans3       varchar(45) ,
 ans4       varchar(45) ,
 correctans varchar(45) ,
 quizno     varchar(45) NOT NULL ,

PRIMARY KEY (questionno),  FOREIGN KEY  (quizno) REFERENCES quizs (quizno)
);



-- ************************************** historys

CREATE TABLE historys
(
 historyno varchar(45) NOT NULL ,
 score     decimal NOT NULL ,
 date      date NOT NULL ,
 quizno    varchar(45) NOT NULL ,
 studentno varchar(45) NOT NULL ,

PRIMARY KEY (historyno),  FOREIGN KEY  (quizno) REFERENCES quizs (quizno),
 FOREIGN KEY  (studentno) REFERENCES students (studentno)
);

ALTER TABLE quizs
ADD COLUMN  teacherno   varchar(45);

ALTER TABLE quizs
ADD FOREIGN KEY (teacherno) REFERENCES teachers(teacherno);


INSERT INTO MOBIC.LEVELS (LEVELNO, "LEVEL") 
	VALUES ('1', 'Grade 1');
INSERT INTO MOBIC.LEVELS (LEVELNO, "LEVEL") 
	VALUES ('2', 'Grade 2');
INSERT INTO MOBIC.LEVELS (LEVELNO, "LEVEL") 
	VALUES ('3', 'Grade 3');
INSERT INTO MOBIC.LEVELS (LEVELNO, "LEVEL") 
	VALUES ('4', 'Grade 4');
INSERT INTO MOBIC.LEVELS (LEVELNO, "LEVEL") 
	VALUES ('5', 'Grade 5');
INSERT INTO MOBIC.LEVELS (LEVELNO, "LEVEL") 
	VALUES ('6', 'Grade 6');



INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES ('101', 'Math 1');
INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES ('102', 'Math 2');
INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES ('201', 'Science 1');
INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES ('202', 'Science 2');
INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES ('301', 'Art 1');
INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES ('302', 'Art 2');
INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES ('103', 'Math 3');

INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES ('203', 'Science 3');

INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES ('303', 'Art 3');
INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES ('104', 'Math 4');

INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES ('204', 'Science 4');

INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES ('304', 'Art 4');
INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES ('105', 'Math 5');

INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES ('205', 'Science 5');

INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES ('305', 'Art 5');
INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES ('106', 'Math 6');

INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES ('206', 'Science 6');

INSERT INTO MOBIC.SUBJECTS (SUBJECTNO, SUBJECT) 
	VALUES ('306', 'Art 6');



INSERT INTO MOBIC.STUDENTS (STUDENTNO, "NAME", EMAIL, PASSWORD, ACTIVATEKEY, ACTIVATED, LEVELNO) 
	VALUES ('61130500011', 'John Carter', 'nuannirun@gmail.com', '12345', '25331', 'activated', '1');

INSERT INTO MOBIC.TEACHERS (TEACHERNO, "NAME", EMAIL, PASSWORD, ACTIVATEKEY, ACTIVATED) 
	VALUES ('1234567890', 'Marl willson', 'jaturong.3410@mail.kmutt.ac.th', '1234567890', '34237', 'activated');



INSERT INTO MOBIC.QUIZS (QUIZNO, TITLE, "TIME", FULLSCORE, LEVELNO, SUBJECTNO, STATUS,TEACHERNO) 
	VALUES ('1', 'Find the answer of Math', 10, 10, '1', '101', 'on','1234567890');
INSERT INTO MOBIC.QUIZS (QUIZNO, TITLE, "TIME", FULLSCORE, LEVELNO, SUBJECTNO, STATUS,TEACHERNO) 
	VALUES ('2', 'Find the answer of Sci', 20, 20, '1', '201', 'on','1234567890');



INSERT INTO MOBIC.QUESTIONS (QUESTIONNO, QUESTION, ANS1, ANS2, ANS3, ANS4, CORRECTANS, QUIZNO) 
	VALUES ('1', '2 + 1 = ?', '1', '2', '3', '3', '3', '1');
INSERT INTO MOBIC.QUESTIONS (QUESTIONNO, QUESTION, ANS1, ANS2, ANS3, ANS4, CORRECTANS, QUIZNO) 
	VALUES ('2', '100 + 32 = ?', '132', '45', '234', '7897', '132', '1');
INSERT INTO MOBIC.QUESTIONS (QUESTIONNO, QUESTION, ANS1, ANS2, ANS3, ANS4, CORRECTANS, QUIZNO) 
	VALUES ('3', 'How many minutes does the light shine from the sun to the earth?', '2', '12', '20', '8', '8', '2');
INSERT INTO MOBIC.QUESTIONS (QUESTIONNO, QUESTION, ANS1, ANS2, ANS3, ANS4, CORRECTANS, QUIZNO) 
	VALUES ('4', 'Sound caused by what?', 'wind', 'Vibration of the object', 'soil', 'sky', 'Vibration of the object', '2');


INSERT INTO MOBIC.HISTORYS (HISTORYNO, SCORE, "DATE", QUIZNO, STUDENTNO) 
	VALUES ('1', 20, '2019-11-19', '3', '61130500011');
INSERT INTO MOBIC.HISTORYS (HISTORYNO, SCORE, "DATE", QUIZNO, STUDENTNO) 
	VALUES ('2', 10, '2019-11-19', '2', '61130500011');


INSERT INTO MOBIC.QUIZS (QUIZNO, TITLE, "TIME", FULLSCORE, LEVELNO, SUBJECTNO, STATUS, TEACHERNO) 
	VALUES ('3', 'Calculus', 10, 20, '4', '201', 'on', '1122334455');

INSERT INTO MOBIC.TEACHERS (TEACHERNO, "NAME", EMAIL, PASSWORD, ACTIVATEKEY, ACTIVATED) 
	VALUES ('1122334455', 'Sanit Nidnhoy', 'sanit@gmail.com', '1122334455', '12345', 'activeted');


