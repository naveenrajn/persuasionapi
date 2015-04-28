package osu.ceti.persuasionapi.core.operations;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.data.access.BadgeDAO;
import osu.ceti.persuasionapi.data.model.Badge;

@Component
public class BadgeOperations {
	
	private static final Logger log = Logger.getLogger(BadgeOperations.class);
	
	@Autowired BadgeDAO badgeDAO;

	public Badge lookupBadge(Integer badgeId) throws DatabaseException {
		return badgeDAO.findById(badgeId);
	}
	
	public Badge lookupBadge(String badgeClass, Integer badgeLevel) throws DatabaseException {
		return badgeDAO.findByClassAndLevel(badgeClass, badgeLevel);
	}
	
	public List<Badge> getAllBadges() throws DatabaseException {
		return badgeDAO.getAllBadges();
	}

	public void updateBadgeClass(String oldClassName, String newClassName) throws DatabaseException {
		badgeDAO.updateBadgeClass(oldClassName, newClassName);
	}

	public Badge createNewBadge(Badge newBadge) throws DatabaseException {
		badgeDAO.merge(newBadge);
		return lookupBadge(newBadge.getBadgeClass(), newBadge.getBadgeLevel());
	}

	public boolean checkIfBadgeClassExists(String badgeClass) throws DatabaseException {
		long badgeCount = badgeDAO.lookupBadgeCountForClass(badgeClass);
		if(badgeCount >0) return true;
		return false;
	}

	public Badge editBadge(Badge existing, Badge modifiedBadge) throws DatabaseException {
		existing.setBadgeName(modifiedBadge.getBadgeName());
		existing.setBadgeDesc(modifiedBadge.getBadgeDesc());
		existing.setEmailSubject(modifiedBadge.getEmailSubject());
		existing.setEmailMsg(modifiedBadge.getEmailMsg());
		existing.setPublicRecognition(modifiedBadge.getPublicRecognition());
		badgeDAO.merge(existing);
		return existing;
	}

	public void deleteBadge(Badge badge) throws DatabaseException {
		badgeDAO.delete(badge);
	}
	
}
