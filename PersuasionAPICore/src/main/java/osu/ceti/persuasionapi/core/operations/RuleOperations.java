package osu.ceti.persuasionapi.core.operations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.data.access.RuleDAO;
import osu.ceti.persuasionapi.data.model.Rule;

@Component
public class RuleOperations {
	
	@Autowired RuleDAO ruleDAO;

	public Rule lookupRule(Integer ruleId) throws DatabaseException {
		return ruleDAO.findById(ruleId);
	}

	public List<Rule> getAllTopLevelRules() throws DatabaseException {
		return ruleDAO.getAllTopLevelRules();
	}

	public Rule getRuleByRuleId(Integer ruleId) throws DatabaseException {
		return ruleDAO.findById(ruleId);
	}

	public Rule persistRule(Rule rule) throws DatabaseException {
		return ruleDAO.merge(rule);
	}

	public void deleteRule(osu.ceti.persuasionapi.data.model.Rule rule) {
		ruleDAO.delete(rule);
	}

}
