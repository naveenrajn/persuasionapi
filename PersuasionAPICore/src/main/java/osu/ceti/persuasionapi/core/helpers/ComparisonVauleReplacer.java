package osu.ceti.persuasionapi.core.helpers;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.data.model.ActivityLog;

@Component
public class ComparisonVauleReplacer {

	private final String ACTIVITY_VALUE_FORMAT = "@@(.+?)#value@@";
	private final String ACTIVITY_COUNT_FORMAT = "@@(.+?)#count@@";
	private final String ATTRIBUTE_FORMAT = "@@(.+?)#@@";
	
	private final String GENERIC_FORMAT = "@@.+?@@";
	
	/**
	 * Processes the comparisonString and replaces it with appropriate attribute/activity values
	 * @param comparisonString
	 * @param activityLogs
	 * @param userAttributes
	 * @return
	 */
	public String getValue(String comparisonString, 
			Map<String, ActivityLog> activityLogs,
			Map<String, String> userAttributes) {
		if(comparisonString.matches(ATTRIBUTE_FORMAT)) {
			Matcher matcher = Pattern.compile(ATTRIBUTE_FORMAT).matcher(comparisonString);
			matcher.find();
			String attributeName = matcher.group(1);
			
			if(!userAttributes.containsKey(attributeName)) return null;
			return userAttributes.get(attributeName);
		} else if(comparisonString.matches(ACTIVITY_COUNT_FORMAT)) {
			Matcher matcher = 
					Pattern.compile(ACTIVITY_COUNT_FORMAT).matcher(comparisonString);
			matcher.find();
			String activityName = matcher.group(1);
			
			if(!activityLogs.containsKey(activityName)) return null;
			return String.valueOf(activityLogs.get(activityName).getCount());
		} else if(comparisonString.matches(ACTIVITY_VALUE_FORMAT)) {
			Matcher matcher = 
					Pattern.compile(ACTIVITY_VALUE_FORMAT).matcher(comparisonString);
			matcher.find();
			String activityName = matcher.group(1);
			
			if(!activityLogs.containsKey(activityName)) return null;
			return activityLogs.get(activityName).getValue();
		} else {
			return comparisonString;
		}
	}
	
	/**
	 * Processes the given text and replaces it with appropriate attribute/activity values
	 * @param text
	 * @param activityLogs
	 * @param userAttributes
	 * @return
	 */
	public boolean replaceAllValues(StringBuffer text,
			Map<String, ActivityLog> activityLogs,
			Map<String, String> userAttributes) {
		try {
		boolean allReplacementsFound = true;
		
		StringBuffer operatingString = new StringBuffer(text);
		Matcher matcher = Pattern.compile(GENERIC_FORMAT).matcher(operatingString);
		
		int offset = 0;
		while(matcher.find()) {
			String stringToBeRaplaced = matcher.group();
			String replacementString = getValue(stringToBeRaplaced, activityLogs, userAttributes);
			if(replacementString == null) {
				allReplacementsFound = false;
				text.replace(matcher.start()-offset, matcher.end()-offset, "null");
			} else {
				text.replace(matcher.start()-offset, matcher.end()-offset, replacementString);
				offset += (stringToBeRaplaced.length() - replacementString.length());
			}
		}
		
		return allReplacementsFound;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
