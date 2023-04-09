DROP TABLE IF EXISTS auth_mgmt.user_role;
DROP TABLE IF EXISTS auth_mgmt.user;

CREATE TABLE auth_mgmt.user (
    id int unsigned NOT NULL AUTO_INCREMENT,
    FIRST_NAME varchar(30) NOT NULL,
    LAST_NAME varchar(30) NOT NULL,
    user_name varchar(30) NOT NULL,
    user_pass varchar(255) NOT NULL,
    EMAIL varchar(30) NOT NULL,
    PHONE varchar(30) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE auth_mgmt.user_role (
    id int unsigned NOT NULL AUTO_INCREMENT,
    role varchar(15) NOT NULL,
    user_id int unsigned NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);

commit;