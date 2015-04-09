package osu.ceti.persuasionapi.async;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.ComparisonVauleReplacer;
import osu.ceti.persuasionapi.core.helpers.Constants;
import osu.ceti.persuasionapi.core.helpers.JMSMessageSender;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.core.operations.UserSocialFeedOperations;
import osu.ceti.persuasionapi.data.model.ActivityLog;
import osu.ceti.persuasionapi.data.model.Badge;
import osu.ceti.persuasionapi.data.model.Rule;
import osu.ceti.persuasionapi.data.model.RuleAction;
import osu.ceti.persuasionapi.data.model.RuleComparison;
import osu.ceti.persuasionapi.data.model.RuleQueueMapping;

@Component
public class RuleActionProcessor {
	
	private static final Log log = LogFactory.getLog(RuleActionProcessor.class);
	
	@Autowired JMSMessageSender jmsMessageSender;
	@Autowired JmsTemplate jmsTemplate;
	
	@Autowired UserSocialFeedOperations userSocialFeedOperations;
	@Autowired ComparisonVauleReplacer comparisonValueProvider;
	
	final String emailNotificationsQueueName = "osu.ceti.persuasionapi.rulenotifications.email";

	/**
	 * Performs the configured rule actions - badges and notifications
	 * @param userId
	 * @param rule
	 * @param badgesToBeAssigned
	 * @param activityLogs
	 * @param userAttributes
	 * @param atleastOneChildRulePassed
	 * @throws PersuasionAPIException 
	 */
	public void performRuleAction(String userId, Rule rule, Map<String, Badge> badgesToBeAssigned, 
			Map<String, ActivityLog> activityLogs, Map<String, String> userAttributes, 
			boolean atleastOneChildRulePassed) throws PersuasionAPIException {
		
		RuleAction action = rule.getRuleAction();
		if(action == null) return; //Nothing to be done
		
		checkAndQueueBadgeAssignment(action.getBadge(), badgesToBeAssigned);

		if(action.getNotifyAlways() || atleastOneChildRulePassed == false) {
			//Send notifications only if all child rules fail or when is set to notify always
			postNotificationsToJMS(userId, rule, activityLogs, userAttributes);
			postEmailNotification(userId, action.getEmailSubject(), 
					action.getEmailMsg(), activityLogs, userAttributes);
			postSocialNotification(userId, rule, action.getSocialUpdate(),
					activityLogs, userAttributes);
		}
	}
	
	/**
	 * Checks the user's current badge assignments and queues up appropriate badges,
	 * avoiding duplicates under same badge class
	 * @param badge
	 * @param badgesToBeAssigned
	 */
	private void checkAndQueueBadgeAssignment(Badge badge, Map<String, Badge> badgesToBeAssigned) {
		if(badge != null) {
			if(badgesToBeAssigned.containsKey(badge.getBadgeClass())) {
				int currentBadgeLevel = badgesToBeAssigned.get(badge.getBadgeClass()).getBadgeLevel();
				if(badge.getBadgeLevel() > currentBadgeLevel) {
					badgesToBeAssigned.put(badge.getBadgeClass(), badge);
				}
			} else {
				badgesToBeAssigned.put(badge.getBadgeClass(), badge);
			}
		} //else nothing to be done
	}
	
	/**
	 * Posts notifications to the email queue
	 * @param userId
	 * @param emailSubject
	 * @param emailMessage
	 * @param userAttributes 
	 * @param activityLogs 
	 */
	private void postEmailNotification(String userId, String emailSubject, String emailMessage, Map<String, ActivityLog> activityLogs, Map<String, String> userAttributes) {
		
		if(StringHelper.isEmpty(userId) ||
				StringHelper.isEmpty(emailSubject) ||
				StringHelper.isEmpty(emailMessage)) {
			return;
		}
		
		Map<String, String> messageMap = new HashMap<String, String>();
		messageMap.put(Constants.USER_ID, userId);
		
		StringBuffer processedEmailSubject = new StringBuffer(emailSubject);
		boolean replacementResult = comparisonValueProvider.replaceAllValues(
				processedEmailSubject, activityLogs, userAttributes);
		if(!replacementResult) {
			log.error("Error building email notification for user " + userId
					+ ". Not all replacement values are available to build "
					+ "the notification text");
			log.error("Erroneous notification: " + processedEmailSubject);
			return;
		}
		messageMap.put(Constants.EMAIL_SUBJECT, processedEmailSubject.toString());
		
		StringBuffer processedEmailMessage = new StringBuffer(emailMessage);
		replacementResult = comparisonValueProvider.replaceAllValues(
				processedEmailMessage, activityLogs, userAttributes);
		if(!replacementResult) {
			log.error("Error building email notification for user " + userId
					+ ". Not all replacement values are available to build "
					+ "the notification text");
			log.error("Erroneous notification: " + processedEmailMessage);
			return;
		}
		messageMap.put(Constants.EMAIL_BODY, processedEmailMessage.toString());
		
		jmsMessageSender.sendJMSMessage(jmsTemplate, 
				emailNotificationsQueueName, messageMap);
	}
	
	/**
	 * Logs social feed notifications
	 * @param userId
	 * @param rule 
	 * @param notificationText
	 * @param userAttributes 
	 * @param activityLogs 
	 * @throws PersuasionAPIException
	 */
	private void postSocialNotification(String userId, Rule rule, 
			String notificationText, Map<String, ActivityLog> activityLogs, 
			Map<String, String> userAttributes) throws PersuasionAPIException {
		if(StringHelper.isEmpty(userId) || StringHelper.isEmpty(notificationText)) {
			return;
		}
		
		StringBuffer processedNotificationText = new StringBuffer(notificationText);
		boolean replacementResult = comparisonValueProvider.replaceAllValues(
				processedNotificationText, activityLogs, userAttributes);
		if(!replacementResult) {
			log.error("Error building social feed notifications for user " + userId
					+ " for rule " + rule.getRuleId() + ". Not all replacement values"
					+ "are available to build the notification text");
			log.error("Erroneous notification: " + processedNotificationText);
			return;
		}
		
		userSocialFeedOperations.logSocialNotification(userId, rule.getRuleId(),
				null, processedNotificationText.toString());
	}

	/**
	 * Posts notifications to the configured queues
	 * @param userId
	 * @param rule
	 * @param activityLogs
	 * @param userAttributes
	 */
	private void postNotificationsToJMS(String userId, Rule rule,
			Map<String, ActivityLog> activityLogs, Map<String, String> userAttributes) {
		
		@SuppressWarnings("unchecked")
		Set<RuleQueueMapping> ruleQueueMappings = rule.getRuleQueueMappings();

		for(RuleQueueMapping mapping : ruleQueueMappings) {
			String queueName = mapping.getId().getQueueName();
			Map<String, String> messageMap = new HashMap<String, String>();
			messageMap.put(Constants.USER_ID, userId);
			messageMap.putAll(prepareMessageMap(rule, activityLogs, userAttributes));
			jmsMessageSender.sendJMSMessage(jmsTemplate, queueName, messageMap);
		}
		
	}

	/**
	 * Prepares the contents of the message to be posted for JMS notifications
	 * i.e. all activities/attributes involved in the rule processing(both self and up the chain)
	 * @param rule
	 * @param activityLogs
	 * @param userAttributes
	 * @return
	 */
	private Map<String, String> prepareMessageMap(Rule rule, Map<String, ActivityLog> activityLogs,
			Map<String, String> userAttributes) {
		Map<String, String> returnMap = new HashMap<String, String>();
		
		@SuppressWarnings("unchecked")
		Set<RuleComparison> ruleComparisons = rule.getRuleComparisons();
		
		for(RuleComparison comparison : ruleComparisons) {
			if(comparison.getFeatureType().equalsIgnoreCase("ACTIVITY")) {
				String activityName = comparison.getActivity().getActivityName();
				returnMap.put(activityName, activityLogs.get(activityName).getValue());
			} else {
				String attributeName = comparison.getUserAttribute().getAttributeName();
				returnMap.put(attributeName, userAttributes.get(attributeName));
			}
		}
		if(rule.getParentRule()  != null) {
			returnMap.putAll(prepareMessageMap(rule.getParentRule(), activityLogs, userAttributes));
		}
		return returnMap;
	}
	
}
