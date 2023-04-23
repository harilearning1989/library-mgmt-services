CREATE TABLE LIBRARY_MGMT.STUDENT (
    ID int NOT NULL AUTO_INCREMENT,
    STUDENT_ID int,
    STUDENT_NAME varchar(255),
    FATHER_NAME varchar(255),
    GENDER varchar(10),
    MOBILE long,
    CATEGORY varchar(10),
    EMAIL varchar(255),
    PRIMARY KEY (ID,STUDENT_ID)
);

ALTER TABLE library_mgmt.STUDENT ADD CONSTRAINT STUDENT_ID_UC UNIQUE (STUDENT_ID);
ALTER TABLE library_mgmt.STUDENT ADD CONSTRAINT email_uc UNIQUE (email);

update library_mgmt.STUDENT set email = concat(replace(STUDENT_NAME,' ', '.'),'@gmail.com') where email is null;
update library_mgmt.STUDENT set email = replace(STUDENT_NAME,' ', '.') where email is not null;
update library_mgmt.STUDENT set email = concat(email, '@gmail.com');

DELETE STD1 FROM library_mgmt.STUDENT AS STD1
INNER JOIN library_mgmt.STUDENT AS STD2
WHERE STD1.id < STD2.id AND STD1.STUDENT_NAME = STD2.STUDENT_NAME;

UPDATE a SET a.column1 = b.column2 FROM myTable a
INNER JOIN myTable b on a.myID = b.myID
--================================================
CREATE TABLE LIBRARY_MGMT.BOOKS (
    ID int NOT NULL AUTO_INCREMENT,
    BOOK_NAME varchar(255) DEFAULT NULL,
    SUBJECT varchar(255) DEFAULT NULL,
    ISBN int NOT NULL,
    PRICE int DEFAULT NULL,
    BOOK_QUANTITY int DEFAULT NULL,
    AVAIL_BOOKS int DEFAULT 0,
    AUTHORS varchar(500) DEFAULT NULL,
    CREATED_DATE datetime DEFAULT NULL,
    PUBLISHED_DATE varchar(255) DEFAULT NULL,
    SHORT_DESCRIPTION varchar(510) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    LONG_DESCRIPTION varchar(510) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    PRIMARY KEY (ID,ISBN)
);
--====================================================
CREATE TABLE LIBRARY_MGMT.ISSUED_BOOKS (
    ID int NOT NULL AUTO_INCREMENT,
    STUDENT_ID int DEFAULT NULL,
    STUDENT_NAME varchar(20) NOT NULL,
    ISBN varchar(20) NOT NULL,
    ISSUED_DATE DATETIME NOT NULL,
    SUBJECT varchar(80) NOT NULL,
    BOOK_NAME varchar(80) NOT NULL,
    AUTHORS varchar(500) NOT NULL,
    PRICE int DEFAULT NULL,
    PRIMARY KEY (ID)
);
--===========================================
CREATE TABLE LIBRARY_MGMT.RETURNED_BOOKS (
    rid int NOT NULL AUTO_INCREMENT,
    BOOK_ID int NOT NULL,
    STUDENT_ID int NOT NULL,
    return_date date NOT NULL,
    fine int NOT NULL DEFAULT '0',
    PRIMARY KEY (rid),
    KEY RETURNED_BOOKS_STUDENT_ID_FK (STUDENT_ID),
    KEY RETURNED_BOOKS_BOOK_ID_FK (BOOK_ID),
    CONSTRAINT RETURNED_BOOKS_BOOK_ID_FK FOREIGN KEY (BOOK_ID) REFERENCES BOOKS (id),
    CONSTRAINT RETURNED_BOOKS_STUDENT_ID_FK FOREIGN KEY (STUDENT_ID) REFERENCES STUDENT (id)
);
--===========================================
CREATE TABLE LIBRARY_MGMT.CONTACTS (
    ID int(11) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    NAME varchar(30) DEFAULT NULL,
    EMAIL varchar(50) DEFAULT NULL,
    PHONE varchar(20) DEFAULT NULL,
    MESSAGE text,
    CREATED_AT DATETIME NOT NULL
);