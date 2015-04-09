package osu.ceti.persuasionapi.async;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.async.cache.DataCache;
import osu.ceti.persuasionapi.async.cache.DataCacheOperations;
import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.operations.ActivityLogOperations;
import osu.ceti.persuasionapi.core.operations.UserAttributeOperations;
import osu.ceti.persuasionapi.core.operations.UserBadgeOperations;
import osu.ceti.persuasionapi.data.model.ActivityLog;
import osu.ceti.persuasionapi.data.model.Badge;
import osu.ceti.persuasionapi.data.model.Rule;

@Component
public class BadgeRuleProcessor {

	private static final Log log = LogFactory.getLog(BadgeRuleProcessor.class);

	@Autowired DataCacheOperations dataCacheOperations;
	@Autowired RuleComparisonProcessor ruleComparisonProcessor;
	@Autowired RuleActionProcessor ruleActionProcessor;
	
	@Autowired ActivityLogOperations activityLogDAO;
	@Autowired UserBadgeOperations userBadgeOperations;
	@Autowired UserAttributeOperations userAttributeOperations;
	
	/**
	 * Runs the user activities/attributes through all the rules and performs necessary actions
	 * @param userId
	 * @throws PersuasionAPIException
	 */
	public void processRulesForUser(String userId) throws PersuasionAPIException {

		log.debug("Processing rules for user Id: " + userId);
		
		//TODO: If Hibernate cache does not work, resort to checkOrPopulateBadgeRules() and maintain cache
		//dataCacheOperations.checkOrPopulateBadgeRules();
		try {
			dataCacheOperations.populateOrRefreshBadgeRules();
		} catch(Exception e) {
			e.printStackTrace();
		}

		Map<String, ActivityLog> activityLogs = 
				activityLogDAO.getAllActivityLogsForUser(userId);
		Map<String, String> userAttributes = 
				userAttributeOperations.getAllAttributesForUser(userId);

		Queue<Rule> rulesToBeProcessed = new LinkedList<Rule>();
		rulesToBeProcessed.addAll(DataCache.rules);

		Map<String, Badge> badgesToBeAssigned = new HashMap<String, Badge>();
		
		for(Rule rule : rulesToBeProcessed) {
			processRule(userId, rule, activityLogs, userAttributes, badgesToBeAssigned);
		}
		
		processBadgeAssignments(userId, badgesToBeAssigned);
	}
	
	/**
	 * Processes the rule and triggers child rules/rule actions
	 * @param userId
	 * @param rule
	 * @param activityLogs
	 * @param userAttributes
	 * @param badgesToBeAssigned
	 * @return true/false indicating if the rule passed
	 * @throws PersuasionAPIException 
	 */
	private boolean processRule(String userId, Rule rule, Map<String, ActivityLog> activityLogs,
			Map<String, String> userAttributes, Map<String, Badge> badgesToBeAssigned) 
			throws PersuasionAPIException {
		boolean rulePassed = 
				ruleComparisonProcessor.processRuleComparisons(rule, activityLogs, userAttributes);
		if(rulePassed) {
			boolean atleastOneChildRulePassed = processSubrules(userId, rule, activityLogs, 
					userAttributes, badgesToBeAssigned);
			ruleActionProcessor.performRuleAction(userId, rule, badgesToBeAssigned, 
					activityLogs, userAttributes, atleastOneChildRulePassed);
		}
		return rulePassed;
	}
	
	/**
	 * Processes child rules of a user
	 * @param userId
	 * @param rule
	 * @param activityLogs
	 * @param userAttributes
	 * @param badgesToBeAssigned
	 * @return true/false indicating if at least one child rule passed
	 * @throws PersuasionAPIException 
	 */
	private boolean processSubrules(String userId, Rule rule, 
			Map<String, ActivityLog> activityLogs, Map<String, String> userAttributes, 
			Map<String, Badge> badgesToBeAssigned) throws PersuasionAPIException {

		boolean atleastOneChildRulePassed = false;

		@SuppressWarnings("unchecked")
		Set<Rule> childRules = rule.getChildRules();
		
		for(Rule childRule : childRules) {
			boolean result = processRule(userId, childRule, activityLogs, userAttributes, 
					badgesToBeAssigned);
			if(result) atleastOneChildRulePassed = true;
		}

		return atleastOneChildRulePassed;
	}
	
	/**
	 * Assigns the badges identified by rule processing to the user
	 * @param userId
	 * @param badgesToBeAssigned
	 * @throws PersuasionAPIException 
	 */
	//TODO: Add badge removal logic. Is this required? Yes it is
	private void processBadgeAssignments(String userId,
			Map<String, Badge> badgesToBeAssigned) throws PersuasionAPIException {
		if(!badgesToBeAssigned.isEmpty()) {
			for(String badgeClass : badgesToBeAssigned.keySet()) {
				Badge badge = badgesToBeAssigned.get(badgeClass);
				userBadgeOperations.assignBadgeForUser(userId, badge);
			}
		}
		//TODO: Identify and remove obsolete badges
	}

}
