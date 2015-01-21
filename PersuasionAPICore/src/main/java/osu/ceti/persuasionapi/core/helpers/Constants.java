package osu.ceti.persuasionapi.core.helpers;

public class Constants {

	//Constants used for fields in the internal JMS messages
	public static final String USER_ID = "user_id";
	public static final String ACTIVITY_ID = "activity_id";
	public static final String TIMESTAMP = "timestamp";
	
	//Constants used by badge rule comparison logic
	//This should map to the ID column persuasion_api.rule_comparators
	public static final String VALUE_GREATER_THAN = "VALUE_GREATER_THAN";
	public static final String VALUE_GREATER_THAN_EQUAL = "VALUE_GREATER_THAN_EQUAL";
	public static final String COUNT_GREATER_THAN = "COUNT_GREATER_THAN";
	public static final String COUNT_GREATER_THAN_EQUAL = "COUNT_GREATER_THAN_EQUAL";
	public static final String VALUE_EQUAL_TO = "VALUE_EQUAL_TO";
	
}
