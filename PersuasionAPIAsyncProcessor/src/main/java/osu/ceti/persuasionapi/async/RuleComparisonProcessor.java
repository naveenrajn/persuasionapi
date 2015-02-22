package osu.ceti.persuasionapi.async;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.core.helpers.Constants;
import osu.ceti.persuasionapi.data.model.Activity;
import osu.ceti.persuasionapi.data.model.ActivityLog;
import osu.ceti.persuasionapi.data.model.Rule;
import osu.ceti.persuasionapi.data.model.RuleComparison;
import osu.ceti.persuasionapi.data.model.UserAttribute;

@Component
public class RuleComparisonProcessor {

	/**
	 * Compare activity/attribute values as specified by the comparison rules for the rule
	 * @param rule
	 * @param activityLogs
	 * @param userAttributes
	 * @return true if all comparisons passes; false otherwise
	 */
	public boolean processRuleComparisons(Rule rule, Map<String, ActivityLog> activityLogs,
			Map<String, String> userAttributes) {
		@SuppressWarnings("unchecked")
		Set<RuleComparison> ruleComparisons = rule.getRuleComparisons();
		
		boolean rulePassed = true;
		if(ruleComparisons.size() == 0 ) rulePassed = false;
		for(RuleComparison comparison : ruleComparisons) {
			Integer count = null;
			String value = null;
			//TODO: Modify these comparisons to use constants and use StringHelper
			if(comparison.getFeatureType().equalsIgnoreCase("ACTIVITY")) {
				Activity activity = comparison.getActivity();
				if(activity == null) {
					rulePassed = false;
					break;
				}
				ActivityLog log = activityLogs.get(activity.getActivityName());
				count = log.getCount();
				value = log.getValue();
			} else if(comparison.getFeatureType().equalsIgnoreCase("ATTRIBUTE")) {
				UserAttribute attribute = comparison.getUserAttribute();
				if(attribute == null) {
					rulePassed = false;
					break;
				}
				value = userAttributes.get(attribute.getAttributeName());
			}
			if(!valuePassesComparisonCriteria(comparison.getRuleComparator().getComparatorId(),
					comparison.getValue(), count, value)) {
				rulePassed = false;
				break;
			}
		}
		return rulePassed;
	}
	
	/**
	 * Compares individual activity/attribute to rule value according to the specified comparator
	 * @param comparatorId
	 * @param comparisonValue
	 * @param count
	 * @param value
	 * @return true if comparison succeeeds; false otherwise
	 */
	private boolean valuePassesComparisonCriteria(String comparatorId, 
			String comparisonValue, int count, String value) {
		//TODO: Handle exceptions appropriately
		if(comparatorId.equalsIgnoreCase(Constants.COUNT_GREATER_THAN)) {
			return count>Integer.parseInt(comparisonValue);
		} else if(comparatorId.equalsIgnoreCase(Constants.COUNT_GREATER_THAN_EQUAL)) {
			return count>=Integer.parseInt(comparisonValue);
		} else if(comparatorId.equalsIgnoreCase(Constants.VALUE_EQUAL_TO)) {
			return value.equalsIgnoreCase(comparisonValue);
		} else {
			//TODO: Handle exception appropriately
			throw new RuntimeException("Comparison method not supported");
		}
	}
	
}
