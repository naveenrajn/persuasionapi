drop database persuasion_api;
create database persuasion_api;
use persuasion_api;

DROP TABLE IF EXISTS users;
CREATE TABLE users(user_id VARCHAR(255), user_type VARCHAR(50),
					PRIMARY KEY(user_id));

DROP TABLE IF EXISTS badges;
CREATE TABLE badges(badge_id INTEGER NOT NULL AUTO_INCREMENT, badge_class VARCHAR(50) NOT NULL, 
					badge_level INTEGER NOT NULL, badge_name VARCHAR(50) NOT NULL, badge_desc VARCHAR(255), 
                    image MEDIUMBLOB, email_msg TEXT, public_recognition TEXT,
                    PRIMARY KEY(badge_id), CONSTRAINT UNIQUE(badge_class, badge_level));

DROP TABLE IF EXISTS user_badge_mappings;
CREATE TABLE user_badge_mappings(user_id VARCHAR(255), badge_id INTEGER NOT NULL, badge_class VARCHAR(50) NOT NULL, processed_time TIMESTAMP NOT NULL,
									PRIMARY KEY(user_id, badge_class),
                                    CONSTRAINT FOREIGN KEY(user_id) REFERENCES users(user_id),
                                    CONSTRAINT FOREIGN KEY(badge_id) REFERENCES badges(badge_id));

DROP TABLE IF EXISTS badge_rule_mappings;
CREATE TABLE badge_rule_mappings(rule_id INTEGER NOT NULL AUTO_INCREMENT, rule_name VARCHAR(50) NOT NULL, 
									rule_desc VARCHAR(255), badge_id INTEGER, rule_order INTEGER NOT NULL,
                                    PRIMARY KEY(rule_id),
                                    CONSTRAINT UNIQUE(badge_id, rule_order),
                                    CONSTRAINT FOREIGN KEY(badge_id) REFERENCES badges(badge_id));

DROP TABLE IF EXISTS rule_comparators;
CREATE TABLE rule_comparators(comparator_id VARCHAR(30), description VARCHAR(255) NOT NULL,
								PRIMARY KEY(comparator_id));

DROP TABLE IF EXISTS badge_rules;
CREATE TABLE badge_rules(rule_comp_id INTEGER NOT NULL AUTO_INCREMENT, rule_id INTEGER, activity_id INTEGER, 
							comparator VARCHAR(30), value VARCHAR(255) NOT NULL, comparison_group INTEGER NOT NULL,
                            PRIMARY KEY(rule_comp_id),
                            CONSTRAINT UNIQUE(rule_id, activity_id, comparison_group),
                            CONSTRAINT FOREIGN KEY(rule_id) REFERENCES badge_rule_mappings(rule_id),
                            CONSTRAINT FOREIGN KEY(comparator) REFERENCES rule_comparators(comparator_id));

DROP TABLE IF EXISTS activities;
CREATE TABLE activities(activity_id INTEGER NOT NULL AUTO_INCREMENT, activity_name VARCHAR(30) NOT NULL, 
						activity_desc VARCHAR(255),
						PRIMARY KEY(activity_id), CONSTRAINT UNIQUE(activity_name));

DROP TABLE IF EXISTS activity_log;
CREATE TABLE activity_log(user_id VARCHAR(255), activity_id INTEGER, count INTEGER NOT NULL DEFAULT 1, value VARCHAR(255) NOT NULL,
							log_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            PRIMARY KEY(user_id, activity_id),
                            CONSTRAINT FOREIGN KEY(user_id) REFERENCES users(user_id),
                            CONSTRAINT FOREIGN KEY(activity_id) REFERENCES activities(activity_id));