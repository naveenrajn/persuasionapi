use persuasion_api;

INSERT INTO `user` VALUES ('1'),('2'),('3'),('4'),('naveen');

INSERT INTO `user_attribute` VALUES ('location',NULL),('user_type',NULL);

INSERT INTO `badge` VALUES (1,'reviewer',1,'reviewer_1','Reviewer Level 1',NULL,NULL,NULL,NULL),(2,'reviewer',2,'reviewer_2','Reviewer Level 2',NULL,NULL,NULL,NULL),(3,'reviewer',3,'reviewer_3','Reviewer Level 3',NULL,NULL,NULL,NULL);

INSERT INTO `rule` VALUES (1,'reviewer1','Award Reviewer 1',NULL),(2,'reviewer2','Award Reviewer 2',1),(3,'reviewer3','Award Reviewer 3',2),(4,'reviewer4','Award Reviewer 4',3);

INSERT INTO `activity` VALUES ('review_posted','The user posted a review'),('review_upvoted',NULL);

INSERT INTO `rule_comparator` VALUES ('COUNT_GREATER_THAN','Comparator to check if count is greater than the comparison value'),('COUNT_GREATER_THAN_EQUAL','Comparator to check if count is greater than or equal to the comparison value'),('COUNT_LESS_THAN','Comparator to check if count is less than the comparison value'),('COUNT_LESS_THAN_EQUAL','Comparator to check if count is less than or equal the comparison value'),('VALUE_EQUAL_TO','Comparator to check if value is equal to the comparison value'),('VALUE_IN','Comparator to check if value is in the list of comma separated comparison values');

INSERT INTO `rule_comparison` VALUES (1,1,'ACTIVITY','review_posted',NULL,'COUNT_GREATER_THAN','5'),(2,2,'ACTIVITY','review_posted',NULL,'COUNT_GREATER_THAN','10'),(3,3,'ACTIVITY','review_posted',NULL,'COUNT_GREATER_THAN','15');

INSERT INTO `rule_action` VALUES (1,1,NULL,NULL,NULL,0),(2,2,'You did it!!','Thank you for posting review on @@review_posted#value@@. You are now a Level 2 reviewer. This is our way of thanking you for posting @@review_posted#count@@ reviews. Keep this coming!!','Level 2 reviewer for @@location#@@ area',0),(3,3,NULL,NULL,NULL,0);

INSERT INTO `rule_queue_mapping` VALUES (1,'osu.ceti.persuasionapi.reviewer1');

INSERT INTO `activity_log` VALUES ('1','review_posted',10,'21','2015-02-24 21:14:39'),('3','review_posted',15,'21','2015-02-26 01:26:59'),('4','review_posted',2,'21','2015-02-26 01:28:22'),('naveen','review_posted',7,'21','2015-02-26 03:24:32');

INSERT INTO `user_attribute_value` VALUES ('1','location','WA','2015-02-21 20:04:40'),('1','user_type','reviewer','2015-02-21 20:29:20'),('2','user_type','user1','2015-02-21 17:02:58');

INSERT INTO `user_badge_mapping` VALUES ('1',1,'reviewer','2015-02-24 20:28:01'),('3',2,'reviewer','2015-02-26 01:26:59'),('naveen',1,'reviewer','2015-02-26 03:24:32');
