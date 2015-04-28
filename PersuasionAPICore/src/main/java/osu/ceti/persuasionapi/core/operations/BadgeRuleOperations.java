package osu.ceti.persuasionapi.core.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.data.access.RuleActionDAO;

@Component
public class BadgeRuleOperations {

	@Autowired RuleActionDAO ruleActionDAO;
	
	public void deleteAllBadgeMappings(Integer badgeId) throws DatabaseException {
		ruleActionDAO.nullifyAllAssignmentsForBadge(badgeId);
	}

}
