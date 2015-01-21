package osu.ceti.persuasionapi.async;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.async.cache.DataCache;
import osu.ceti.persuasionapi.async.cache.DataCacheOperations;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.Constants;
import osu.ceti.persuasionapi.core.operations.ActivityLogOperations;
import osu.ceti.persuasionapi.core.operations.UserBadgeOperations;
import osu.ceti.persuasionapi.data.model.ActivityLog;
import osu.ceti.persuasionapi.data.model.BadgeRuleMappings;
import osu.ceti.persuasionapi.data.model.BadgeRules;

@Component
public class BadgeRuleProcessor {
	
	private static final Log log = LogFactory.getLog(BadgeRuleProcessor.class);
	
	@Autowired DataCacheOperations dataCacheOperations;
	@Autowired ActivityLogOperations activityLogDAO;
	@Autowired UserBadgeOperations userBadgeOperations;

	/**
	 * Runs all user activities through the badge rules and assigns appropriate badges
	 * @param userId
	 * @throws PersuasionAPIException
	 */
	//TODO: Add badge removal logic. Is this required?
	public void processRulesForUser(String userId) throws PersuasionAPIException {
		
		log.debug("Processing rules for user Id: " + userId);
		
		//TODO: If hibernate cache does not work, resort to checkOrPopulateBadgeRules() and maintain cache
		//dataCacheOperations.checkOrPopulateBadgeRules();
		dataCacheOperations.populateOrRefreshBadgeRules();
		
		Map<Integer, ActivityLog> activityLogs = activityLogDAO.getAllActivityLogsForUser(userId);
		
		for(String badgeClass : DataCache.badgeRuleMappings.keySet()) {
			Map<Integer, BadgeRuleMappings> badgeRuleMappings = DataCache.badgeRuleMappings.get(badgeClass);
			System.out.println("Processing Badge Class: " + badgeClass);
			for(int ruleOrder : badgeRuleMappings.keySet()) {
				Map<Integer, List<BadgeRules>> badgeRulesGroupedByGroupId = DataCache.badgeRules.get(badgeRuleMappings.get(ruleOrder));
				boolean rulePassed = true;
				for(int groupId : badgeRulesGroupedByGroupId.keySet()) {
					rulePassed = true; //Start processing the current comparison group for the rule
					for(BadgeRules badgeRule : badgeRulesGroupedByGroupId.get(groupId)) {
						log.debug("Processing rule with comparison ID " + badgeRule.getRuleCompId() + " comparator " + badgeRule.getRuleComparators().getComparatorId());
						if(!activityLogs.containsKey(badgeRule.getActivityId())) {
							rulePassed = false;
							break;
						}
						ActivityLog activityLog = activityLogs.get(badgeRule.getActivityId());
						log.debug("Comparing count: " + activityLog.getCount() 
								+ ", activity value:" + activityLog.getValue()
								+ " to rule value:" + badgeRule.getValue()
								+ "using comparator: " + badgeRule.getRuleComparators().getComparatorId());
						if(!valuePassesComparisonCriteria(badgeRule.getRuleComparators().getComparatorId(), badgeRule.getValue(), activityLog.getCount(), activityLog.getValue())) {
							rulePassed = false;
							break;
						}
					}
					if(rulePassed) {
						log.debug("Rule Passed. Skipping processing other comparison groups in the rule and proceed to assign the badge to the user");
						break;
					}
				}
				if(rulePassed) {
					log.info("Rules passed. Assigning the badge " + badgeRuleMappings.get(ruleOrder).getBadges().getBadgeId() + " to the user " + userId);
					userBadgeOperations.assignBadgeForUser(userId, badgeRuleMappings.get(ruleOrder).getBadges());
					log.debug("Skipping processing of any lower order badge rules in this badge class: " + badgeClass);
					break;
				}
			}
		}
		
	}
	
	/**
	 * Compares activity value/count to rule value according to the specified comparator
	 * @param comparatorId
	 * @param comparisonValue
	 * @param count
	 * @param value
	 * @return
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
			throw new RuntimeException("Comparison method not supported");
		}
	}
}
