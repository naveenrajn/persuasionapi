package osu.ceti.persuasionapi.core.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.data.access.RuleActionDAO;
import osu.ceti.persuasionapi.data.model.Badge;
import osu.ceti.persuasionapi.data.model.RuleAction;

@Component
public class RuleActionOperations {

	@Autowired RuleActionDAO ruleActionDAO;

	public void updateRuleAction(
			osu.ceti.persuasionapi.data.model.Rule rule,
			Integer badgeId, String emailSubject, String emailMsg,
			String socialUpdate, boolean notifyAlways) throws DatabaseException {
		
		RuleAction ruleAction = new RuleAction();
		ruleAction.setRuleId(rule.getRuleId());
		
		if(badgeId != null) {
			Badge badge = new Badge();
			badge.setBadgeId(badgeId);
			ruleAction.setBadge(badge);
		}
		
		ruleAction.setEmailSubject(emailSubject);
		ruleAction.setEmailMsg(emailMsg);
		ruleAction.setSocialUpdate(socialUpdate);
		ruleAction.setNotifyAlways(notifyAlways);
		
		ruleActionDAO.merge(ruleAction);
	}

	public void deleteAllActionsForRule(Integer ruleId) throws DatabaseException {
		ruleActionDAO.deleteAllActionsForRule(ruleId);
	}

}
