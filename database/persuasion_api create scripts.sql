drop database persuasion_api;
create database persuasion_api;
use persuasion_api;

DROP TABLE IF EXISTS user;
CREATE TABLE user (
    user_id VARCHAR(255),
    PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS badge;
CREATE TABLE badge (
    badge_id INTEGER NOT NULL AUTO_INCREMENT,
    badge_class VARCHAR(50) NOT NULL,
    badge_level INTEGER NOT NULL,
    badge_name VARCHAR(50) NOT NULL,
    badge_desc VARCHAR(255),
    image MEDIUMBLOB,
    email_subject VARCHAR(255),
    email_msg TEXT,
    public_recognition TEXT,
    PRIMARY KEY (badge_id),
    CONSTRAINT UNIQUE (badge_class , badge_level)
);

DROP TABLE IF EXISTS user_badge_mapping;
CREATE TABLE user_badge_mapping (
    user_id VARCHAR(255),
    badge_id INTEGER NOT NULL,
    badge_class VARCHAR(50) NOT NULL,
    processed_time TIMESTAMP NOT NULL,
    PRIMARY KEY (user_id , badge_class),
    CONSTRAINT FOREIGN KEY (user_id) REFERENCES user (user_id),
    CONSTRAINT FOREIGN KEY (badge_id) REFERENCES badge (badge_id)
);

DROP TABLE IF EXISTS rule;
CREATE TABLE rule (
    rule_id INTEGER NOT NULL AUTO_INCREMENT,
    rule_name VARCHAR(50) NOT NULL,
    rule_desc VARCHAR(255),
    parent_rule INTEGER,
    PRIMARY KEY (rule_id)
);
ALTER TABLE rule ADD CONSTRAINT FOREIGN KEY(parent_rule) REFERENCES rule(rule_id);

DROP TABLE IF EXISTS rule_comparator;
CREATE TABLE rule_comparator (
    comparator_id VARCHAR(30),
    description VARCHAR(255) NOT NULL,
    PRIMARY KEY (comparator_id)
);

DROP TABLE IF EXISTS activity;
CREATE TABLE activity (
    activity_name VARCHAR(255),
    activity_desc VARCHAR(255),
    PRIMARY KEY (activity_name)
);

DROP TABLE IF EXISTS activity_log;
CREATE TABLE activity_log (
    user_id VARCHAR(255),
    activity_name VARCHAR(255),
    count INTEGER NOT NULL DEFAULT 1,
    value TEXT,
    log_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id , activity_name),
    CONSTRAINT FOREIGN KEY (user_id) REFERENCES user (user_id),
    CONSTRAINT FOREIGN KEY (activity_name) REFERENCES activity (activity_name)
);

DROP TABLE IF EXISTS user_attribute;
CREATE TABLE user_attribute (
    attribute_name VARCHAR(255),
    attribute_desc VARCHAR(255),
    PRIMARY KEY (attribute_name)
);

DROP TABLE IF EXISTS user_attribute_value;
CREATE TABLE user_attribute_value (
    user_id VARCHAR(255),
    attribute_name VARCHAR(255),
    value TEXT NOT NULL,
    log_time TIMESTAMP NOT NULL,
    PRIMARY KEY (user_id , attribute_name),
    CONSTRAINT FOREIGN KEY (user_id) REFERENCES user (user_id),
    CONSTRAINT FOREIGN KEY (attribute_name) REFERENCES user_attribute (attribute_name)
);

DROP TABLE IF EXISTS rule_action;
CREATE TABLE rule_action (
    rule_id INTEGER NOT NULL,
    badge_id INTEGER,
    email_subject VARCHAR(255),
    email_msg TEXT,
    social_update TEXT,
    notify_always BOOL,
    CONSTRAINT PRIMARY KEY (rule_id),
    CONSTRAINT FOREIGN KEY (rule_id) REFERENCES rule (rule_id),
    CONSTRAINT FOREIGN KEY (badge_id) REFERENCES badge (badge_id)
);

DROP TABLE IF EXISTS rule_queue_mapping;
CREATE TABLE rule_queue_mapping (
	rule_id INTEGER NOT NULL,
    queue_name VARCHAR(255) NOT NULL,
    CONSTRAINT PRIMARY KEY (rule_id, queue_name),
	CONSTRAINT FOREIGN KEY (rule_id) REFERENCES rule (rule_id)
);

DROP TABLE IF EXISTS rule_comparison;
CREATE TABLE rule_comparison (
    rule_comp_id INTEGER NOT NULL AUTO_INCREMENT,
    rule_id INTEGER,
    feature_type VARCHAR(30) NOT NULL,
    activity_name VARCHAR(255),
    attribute_name VARCHAR(255),
    comparator VARCHAR(30) NOT NULL,
    value VARCHAR(255) NOT NULL,
    PRIMARY KEY (rule_comp_id),
    CONSTRAINT UNIQUE (rule_id , activity_name , attribute_name),
    CONSTRAINT FOREIGN KEY (rule_id) REFERENCES rule (rule_id),
    CONSTRAINT FOREIGN KEY (activity_name) REFERENCES activity (activity_name),
    CONSTRAINT FOREIGN KEY (attribute_name) REFERENCES user_attribute (attribute_name),
    CONSTRAINT FOREIGN KEY (comparator) REFERENCES rule_comparator (comparator_id)
);

DROP TABLE IF EXISTS user_social_notification;
CREATE TABLE user_social_notification (
	notification_id INTEGER NOT NULL AUTO_INCREMENT,
	user_id VARCHAR(255) NOT NULL,
    rule_id INTEGER,
    badge_id INTEGER,
    notification_text TEXT NOT NULL,
    posted_time TIMESTAMP NOT NULL,
    CONSTRAINT PRIMARY KEY(notification_id),
    CONSTRAINT UNIQUE (user_id , rule_id, badge_id),
    CONSTRAINT FOREIGN KEY (user_id) REFERENCES user (user_id),
    CONSTRAINT FOREIGN KEY (rule_id) REFERENCES rule (rule_id),
    CONSTRAINT FOREIGN KEY (badge_id) REFERENCES badge (badge_id)
);