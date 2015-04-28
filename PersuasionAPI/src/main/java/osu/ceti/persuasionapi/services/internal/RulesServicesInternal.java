package osu.ceti.persuasionapi.services.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.Constants;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.core.operations.RuleActionOperations;
import osu.ceti.persuasionapi.core.operations.RuleComparatorOperations;
import osu.ceti.persuasionapi.core.operations.RuleComparisonOperations;
import osu.ceti.persuasionapi.core.operations.RuleOperations;
import osu.ceti.persuasionapi.core.operations.RuleQueueMappingOperations;
import osu.ceti.persuasionapi.core.operations.UserSocialFeedOperations;
import osu.ceti.persuasionapi.data.model.Activity;
import osu.ceti.persuasionapi.data.model.Badge;
import osu.ceti.persuasionapi.data.model.RuleAction;
import osu.ceti.persuasionapi.data.model.RuleComparator;
import osu.ceti.persuasionapi.data.model.RuleComparison;
import osu.ceti.persuasionapi.data.model.RuleQueueMapping;
import osu.ceti.persuasionapi.data.model.UserAttribute;
import osu.ceti.persuasionapi.services.internal.wrappers.Rule;
import osu.ceti.persuasionapi.services.internal.wrappers.RuleComparisonsWrapper;

@Service
@Transactional
public class RulesServicesInternal {
	
	@Autowired RuleOperations ruleOperations;
	@Autowired RuleComparatorOperations ruleComparatorOperations;
	@Autowired RuleComparisonOperations ruleComparisonOperations;
	@Autowired RuleQueueMappingOperations ruleQueueMappingOperations;
	@Autowired RuleActionOperations ruleActionOperations;
	@Autowired UserSocialFeedOperations userSocialFeedOperations;

	public RulesServicesInternal() {
		// TODO Auto-generated constructor stub
	}

	public List<Rule> getAllRules() throws DatabaseException {
		List<osu.ceti.persuasionapi.data.model.Rule> rules = ruleOperations.getAllTopLevelRules();
		return lazyLoadAndMapRules(rules);
	}
	
	public List<Rule> getAllTopLevelRules() throws DatabaseException {
		List<osu.ceti.persuasionapi.data.model.Rule> rulesList = ruleOperations.getAllTopLevelRules();

		List<Rule> mappedRulesList = new ArrayList<Rule>();
		for(osu.ceti.persuasionapi.data.model.Rule rule : rulesList) {
			mappedRulesList.add(lazyLoadAndMapSingleRule(rule));
		}
		return mappedRulesList;
	}
	
	public List<Rule> getAllChildRules(Integer ruleId) throws PersuasionAPIException {
		osu.ceti.persuasionapi.data.model.Rule rule = ruleOperations.getRuleByRuleId(ruleId);
		
		if(rule == null) throw new PersuasionAPIException("1001", "Failed to retrieve child rules"
				+ ". The requested rule " + ruleId + " not found");
		
		Set<osu.ceti.persuasionapi.data.model.Rule> childRulesList = rule.getChildRules();
		List<Rule> mappedChildRulesList = new ArrayList<Rule>();
		for(osu.ceti.persuasionapi.data.model.Rule childRule : childRulesList) {
			mappedChildRulesList.add(lazyLoadAndMapSingleRule(childRule));
		}
		return mappedChildRulesList;
	}
	
	private List<Rule> lazyLoadAndMapRules(List<osu.ceti.persuasionapi.data.model.Rule> rules) {
		List<Rule> mappedRules = new ArrayList<Rule>();
		for(osu.ceti.persuasionapi.data.model.Rule rule : rules) {
			Rule mappedRule = lazyLoadAndMapSingleRule(rule);
			mappedRules.add(mappedRule);
		}
		return mappedRules;
	}

	private Rule lazyLoadAndMapSingleRule(osu.ceti.persuasionapi.data.model.Rule rule) {
		Rule mappedRule = new Rule();
		mappedRule.setRuleId(String.valueOf(rule.getRuleId()));
		if(rule.getParentRule() != null)
			mappedRule.setParentRuleId(String.valueOf(rule.getParentRule().getRuleId()));
		mappedRule.setRuleName(rule.getRuleName());
		mappedRule.setRuleDesc(rule.getRuleDesc());
		if(rule.getRuleAction()!=null) {
			RuleAction ruleAction = rule.getRuleAction();
			if(ruleAction.getBadge()!=null) {
				Badge badge = ruleAction.getBadge();
				mappedRule.setBadgeId(String.valueOf(badge.getBadgeId()));
				mappedRule.setBadgeClass(badge.getBadgeClass());
				mappedRule.setBadgeName(badge.getBadgeName());
				mappedRule.setBadgeDescription(badge.getBadgeDesc());
			}
			System.out.println("email subject: " + ruleAction.getEmailSubject());
			if(StringHelper.isNotEmpty(ruleAction.getEmailSubject())) {
				mappedRule.setEmailSubject(ruleAction.getEmailSubject());
			} else {
				mappedRule.setEmailSubject("");
			}
			if(StringHelper.isNotEmpty(ruleAction.getEmailMsg())) {
				mappedRule.setEmailMsg(ruleAction.getEmailMsg());
			} else {
				mappedRule.setEmailMsg("");
			}
			if(StringHelper.isNotEmpty(ruleAction.getSocialUpdate())) {
				mappedRule.setSocialUpdate(ruleAction.getSocialUpdate());
			} else {
				mappedRule.setSocialUpdate("");
			}
			if(ruleAction.getNotifyAlways()!=null) {
				mappedRule.setNotifyAlways(ruleAction.getNotifyAlways());
			} else {
				mappedRule.setNotifyAlways(false);
			}
		}
		
		Hibernate.initialize(rule.getRuleQueueMappings());
		List<String> ruleQueues = new ArrayList<String>();
		if(rule.getRuleQueueMappings() != null) {
			Set<RuleQueueMapping> ruleQueueMappings = 
					(Set<RuleQueueMapping>) rule.getRuleQueueMappings();
			for(RuleQueueMapping ruleQueueMapping : ruleQueueMappings) {
				ruleQueues.add(ruleQueueMapping.getId().getQueueName());
			}
		}
		mappedRule.setRuleQueueMappings(ruleQueues);
		
		Hibernate.initialize(rule.getRuleComparisons());
		List<RuleComparisonsWrapper> mappedRuleComparisons = new ArrayList<RuleComparisonsWrapper>();
		if(rule.getRuleComparisons() != null) {
			Set<RuleComparison> ruleComparisons = rule.getRuleComparisons();
			for(RuleComparison comparison : ruleComparisons) {
				RuleComparisonsWrapper mappedComparison = new RuleComparisonsWrapper();
				mappedComparison.setRuleComparisonId(String.valueOf(comparison.getRuleCompId()));
				
				String featureType = comparison.getFeatureType();
				String activityOrAttributeName = "";
				if(featureType.equalsIgnoreCase("ACTIVITY")) {
					activityOrAttributeName = comparison.getActivity().getActivityName();
					mappedComparison.setFeatureType("ACTIVITY");
				} else if(featureType.equalsIgnoreCase("ATTRIBUTE")) {
					activityOrAttributeName = comparison.getUserAttribute().getAttributeName();
					mappedComparison.setFeatureType("ATTRIBUTE");
				}
				mappedComparison.setActivityOrAttributeName(activityOrAttributeName);
				
				mappedComparison.setRuleComparator(comparison.getRuleComparator().getComparatorId());
				mappedComparison.setValue(comparison.getValue());
				mappedRuleComparisons.add(mappedComparison);
			}
		}
		mappedRule.setRuleComparisons(mappedRuleComparisons);
		
		return mappedRule;
	}

	public List<String> getAllRuleComparators() throws DatabaseException {
		return ruleComparatorOperations.getAllRuleComparators();
	}

	public Rule getRuleById(Integer ruleId) throws DatabaseException {
		osu.ceti.persuasionapi.data.model.Rule rule = ruleOperations.getRuleByRuleId(ruleId);
		return lazyLoadAndMapSingleRule(rule);
	}

	public Integer createOrUpdateRule(Rule ruleToBeEdited) throws PersuasionAPIException {
		
		osu.ceti.persuasionapi.data.model.Rule persistantRule = null;
		if(StringHelper.isNotEmpty(ruleToBeEdited.getRuleId())) {
			try {
				Integer ruleId = Integer.parseInt(ruleToBeEdited.getRuleId());
				persistantRule = ruleOperations.getRuleByRuleId(ruleId);
			} catch(NumberFormatException e) {
				//no action. allow the ruleId to be null
			}
			if(persistantRule == null) throw new PersuasionAPIException("1001",
					"The requested rule not found. Something might have changed. "
					+ "Please refresh the page and try again");
		} else {
			persistantRule = new osu.ceti.persuasionapi.data.model.Rule();
		}
		
		Integer badgeId = null;
		try {
			if(StringHelper.isNotEmpty(ruleToBeEdited.getBadgeId()))
				badgeId = Integer.parseInt(ruleToBeEdited.getBadgeId());
		} catch (NumberFormatException e) {
			throw new PersuasionAPIException("1001", "Invalid badge assignment set for rule. Something might have changed. Please refresh the page and try again");
		}
		
		osu.ceti.persuasionapi.data.model.Rule parentRule = null;
		System.out.println("Parent rule ID: " + ruleToBeEdited.getParentRuleId());
		if(StringHelper.isNotEmpty(ruleToBeEdited.getParentRuleId())) {
			try {
				Integer parentRuleId = Integer.parseInt(ruleToBeEdited.getParentRuleId());
				parentRule = ruleOperations.lookupRule(parentRuleId);
			} catch(NumberFormatException e) {}
			if(parentRule == null) throw new PersuasionAPIException("1001", "Invalid parent rule. Something might have changed. Please refresh the page and try again");
		}

		//Update rule name and description
		persistantRule.setRuleName(ruleToBeEdited.getRuleName());
		persistantRule.setRuleDesc(ruleToBeEdited.getRuleDesc());
		persistantRule.setParentRule(parentRule);
		persistantRule = ruleOperations.persistRule(persistantRule);
		
		//Update rule comparisons first
		List<RuleComparison> ruleComparisons = mapRuleComparisons(ruleToBeEdited.getRuleComparisons());
		ruleComparisonOperations.updateComparisonsForRule(persistantRule, ruleComparisons);
		
		//Update rule actions - update custom JMS queues
		System.out.println("Rule queue mapping size: " + ruleToBeEdited.getRuleQueueMappings().size());
		ruleQueueMappingOperations.updateCustomJMSQueuesForRule(
				persistantRule, ruleToBeEdited.getRuleQueueMappings());

		ruleActionOperations.updateRuleAction(persistantRule, badgeId,
				ruleToBeEdited.getEmailSubject(), ruleToBeEdited.getEmailMsg(),
				ruleToBeEdited.getSocialUpdate(), ruleToBeEdited.isNotifyAlways());
		
		return persistantRule.getRuleId();
	}

	private List<RuleComparison> mapRuleComparisons(
			List<RuleComparisonsWrapper> ruleComparisons) {
		List<RuleComparison> mappedRuleComparisonList = new ArrayList<RuleComparison>();
		
		for(RuleComparisonsWrapper ruleComparisonWrapper : ruleComparisons) {
			RuleComparison mappedRuleComparison = new RuleComparison();
			
			Integer ruleComparisonId = null;
			try {
				if(ruleComparisonWrapper.getRuleComparisonId() != null)
				ruleComparisonId = Integer.parseInt(ruleComparisonWrapper.getRuleComparisonId());
			} catch(NumberFormatException e) {
			}
			mappedRuleComparison.setRuleCompId(ruleComparisonId);
			
			String featureType = ruleComparisonWrapper.getFeatureType();
			mappedRuleComparison.setFeatureType(featureType);
			
			if(featureType.equalsIgnoreCase("ACTIVITY")) {
				String activityName = ruleComparisonWrapper.getActivityOrAttributeName();
				mappedRuleComparison.setActivity(new Activity(activityName));
			} else if(featureType.equalsIgnoreCase("ATTRIBUTE")) {
				String attributeName = ruleComparisonWrapper.getActivityOrAttributeName();
				mappedRuleComparison.setUserAttribute(new UserAttribute(attributeName));
			}
			
			String ruleComparatorID = ruleComparisonWrapper.getRuleComparator();
			mappedRuleComparison.setRuleComparator(new RuleComparator(ruleComparatorID, ""));
			
			mappedRuleComparison.setValue(ruleComparisonWrapper.getValue());
			mappedRuleComparisonList.add(mappedRuleComparison);
		}
		
		return mappedRuleComparisonList;
	}

	public void deleteRule(Integer ruleId) throws PersuasionAPIException {
		osu.ceti.persuasionapi.data.model.Rule rule = ruleOperations.getRuleByRuleId(ruleId);
		
		if(rule == null) throw new PersuasionAPIException("1001", "The requested rule not found. "
				+ "Something might have changed. Please refresh the page and try again");
		
		deleteSingleRule(rule);
	}
	
	private void deleteSingleRule(osu.ceti.persuasionapi.data.model.Rule rule) throws DatabaseException {
		Set<osu.ceti.persuasionapi.data.model.Rule> childRules = rule.getChildRules();
		for(osu.ceti.persuasionapi.data.model.Rule childRule : childRules) {
			deleteSingleRule(childRule);
		}
		
		Integer ruleId = rule.getRuleId();
		ruleQueueMappingOperations.deleteAllQueueMappingsForRule(ruleId);
		ruleActionOperations.deleteAllActionsForRule(ruleId);
		ruleComparisonOperations.deleteAllComparisonsForRule(ruleId);
		userSocialFeedOperations.removeAllNotificationsForRule(ruleId);
		ruleOperations.deleteRule(rule);
	}

	public void updateParentRule(Integer ruleId, Integer parentRuleId) throws PersuasionAPIException {
		osu.ceti.persuasionapi.data.model.Rule rule = ruleOperations.getRuleByRuleId(ruleId);
		osu.ceti.persuasionapi.data.model.Rule parentRule = ruleOperations.getRuleByRuleId(parentRuleId);
		
		if(rule == null || parentRule == null) {
			throw new PersuasionAPIException("1001", "Something seems to have changed"
					+ ". Please refresh the page and try again");
		}
		
		rule.setParentRule(parentRule);
		ruleOperations.persistRule(rule);
	}

}
