package osu.ceti.persuasionapi.core.helpers;

public class Constants {

	//Constants used for fields in the internal JMS messages
	public static final String USER_ID = "user_id";
	public static final String ACTIVITY_NAME = "activity_name";
	public static final String TIMESTAMP = "timestamp";
	public static final String EMAIL_SUBJECT = "subject";
	public static final String EMAIL_BODY = "body";
	
	//Constants used by badge rule comparison logic
	//This should map to the ID column persuasion_api.rule_comparators
	public static final String COUNT_GREATER_THAN = "COUNT_GREATER_THAN";
	public static final String COUNT_GREATER_THAN_EQUAL = "COUNT_GREATER_THAN_EQUAL";
	public static final String COUNT_LESS_THAN = "COUNT_LESS_THAN";
	public static final String COUNT_LESS_THAN_EQUAL = "COUNT_LESS_THAN_EQUAL";
	public static final String VALUE_EQUAL_TO = "VALUE_EQUAL_TO";
	public static final String VALUE_IN = "VALUE_IN";
	
}
