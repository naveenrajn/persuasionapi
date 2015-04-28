package osu.ceti.persuasionapi.core.operations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.data.access.RuleComparatorDAO;
import osu.ceti.persuasionapi.data.model.RuleComparator;

@Component
public class RuleComparatorOperations {
	
	@Autowired RuleComparatorDAO ruleComparatorDAO;
	
	public List<String> getAllRuleComparators() throws DatabaseException {
		List<RuleComparator> ruleComparators = ruleComparatorDAO.getAllRuleComparators();
		
		List<String> ruleComparisonIDs = new ArrayList<String>();
		for(RuleComparator ruleComparator : ruleComparators) {
			ruleComparisonIDs.add(ruleComparator.getComparatorId());
		}
		return ruleComparisonIDs;
	}
	
}
