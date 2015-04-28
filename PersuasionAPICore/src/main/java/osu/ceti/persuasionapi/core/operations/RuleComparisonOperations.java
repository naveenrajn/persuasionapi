package osu.ceti.persuasionapi.core.operations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.access.RuleComparisonDAO;
import osu.ceti.persuasionapi.data.model.Activity;
import osu.ceti.persuasionapi.data.model.Rule;
import osu.ceti.persuasionapi.data.model.RuleComparator;
import osu.ceti.persuasionapi.data.model.RuleComparison;
import osu.ceti.persuasionapi.data.model.UserAttribute;

@Component
public class RuleComparisonOperations {
	
	@Autowired RuleComparisonDAO ruleComparisonDAO;
	@Autowired ActivityOperations activityOperations;
	@Autowired UserAttributeOperations userAttributeOperations;
	@Autowired RuleComparatorOperations ruleComparatorOperations;

	public void updateComparisonsForRule(Rule rule, List<RuleComparison> ruleComparisons)
			throws PersuasionAPIException {
		List<Integer> listOfComparisonsForRule = new ArrayList<Integer>();
		
		for(RuleComparison ruleComparison : ruleComparisons) {
			
			RuleComparison comparisonToBePersisted = new RuleComparison();
			
			if(ruleComparison.getRuleCompId() != null) {
				Integer ruleCompId = ruleComparison.getRuleCompId();
				comparisonToBePersisted = ruleComparisonDAO.findById(ruleCompId);
				if(comparisonToBePersisted == null) continue;
			}
			
			comparisonToBePersisted.setRule(rule);
			
			comparisonToBePersisted.setFeatureType(ruleComparison.getFeatureType());
			
			if(ruleComparison.getActivity()!=null) {
				String activityName = ruleComparison.getActivity().getActivityName();
				Activity activity = activityOperations.findOrCreateActivity(activityName);
				comparisonToBePersisted.setActivity(activity);
			} else if (ruleComparison.getUserAttribute() != null) {
				String attributeName = ruleComparison.getUserAttribute().getAttributeName();
				UserAttribute userAttribute = userAttributeOperations.findOrCreateUserAttribute(
						attributeName, ruleComparison.getUserAttribute().getAttributeDesc());
				comparisonToBePersisted.setUserAttribute(userAttribute);
			} else {
				continue;
			}
			
			if(ruleComparison.getRuleComparator()!=null && 
					StringHelper.isNotEmpty(ruleComparison.getRuleComparator().getComparatorId())) {
				RuleComparator ruleComparator = new RuleComparator();
				ruleComparator.setComparatorId(ruleComparison.getRuleComparator().getComparatorId());
				comparisonToBePersisted.setRuleComparator(ruleComparator);
			} else {
				continue;
			}
			
			comparisonToBePersisted.setValue(ruleComparison.getValue());
			RuleComparison persistedObject = ruleComparisonDAO.merge(comparisonToBePersisted);
			
			listOfComparisonsForRule.add(persistedObject.getRuleCompId());
		}
		
		ruleComparisonDAO.removeAllOtherComparisonsForRule(rule.getRuleId(), listOfComparisonsForRule);
	}

	public void deleteAllComparisonsForRule(Integer ruleId) throws DatabaseException {
		List<Integer> validRuleComparisonIds = new ArrayList<Integer>();
		ruleComparisonDAO.removeAllOtherComparisonsForRule(ruleId, validRuleComparisonIds);
	}
	
}
