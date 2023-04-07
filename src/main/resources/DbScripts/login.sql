DROP TABLE IF EXISTS auth_mgmt.user_role;
DROP TABLE IF EXISTS auth_mgmt.user;

CREATE TABLE auth_mgmt.user (
    id int unsigned NOT NULL AUTO_INCREMENT,
    user_name varchar(30) NOT NULL,
    user_pass varchar(255) NOT NULL,
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