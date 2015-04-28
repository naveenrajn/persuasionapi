package osu.ceti.persuasionapi.services.internal;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.core.operations.BadgeOperations;
import osu.ceti.persuasionapi.core.operations.BadgeRuleOperations;
import osu.ceti.persuasionapi.core.operations.UserBadgeOperations;
import osu.ceti.persuasionapi.core.operations.UserSocialFeedOperations;
import osu.ceti.persuasionapi.data.model.Badge;
import osu.ceti.persuasionapi.services.internal.wrappers.BadgeResponse;

@Service
@Transactional
public class BadgeServicesInternal {
	
	private static final Logger log = Logger.getLogger(BadgeServicesInternal.class);
	
	@Autowired BadgeOperations badgeOperations;
	@Autowired UserBadgeOperations userBadgeOperations;
	@Autowired BadgeRuleOperations badgeRuleOperations;
	@Autowired UserSocialFeedOperations userSocialFeedOperations;

	public BadgeServicesInternal() {
		// TODO Auto-generated constructor stub
	}
	
	public Map<String, List<Badge>> getAllBadgesGroupedByBadgeClass() throws PersuasionAPIException {
		List<Badge> badges = badgeOperations.getAllBadges();
		
		Map<String, List<Badge>> badgesGroupedByClass = new HashMap<String, List<Badge>>();
		for(Badge badge : badges) {
			String badgeClass = badge.getBadgeClass();
			if(!badgesGroupedByClass.containsKey(badgeClass)) {
				badgesGroupedByClass.put(badgeClass, new LinkedList<Badge>());
			}
			badgesGroupedByClass.get(badgeClass).add(badge);
		}
		return badgesGroupedByClass;
	}
	
	public Map<String, List<BadgeResponse>> getAllBadgesGroupedByBadgeClass2() throws PersuasionAPIException {
		List<Badge> badges = badgeOperations.getAllBadges();
		
		Map<String, List<BadgeResponse>> badgesGroupedByClass = new HashMap<String, List<BadgeResponse>>();
		for(Badge badge : badges) {
			String badgeClass = badge.getBadgeClass();
			if(!badgesGroupedByClass.containsKey(badgeClass)) {
				badgesGroupedByClass.put(badgeClass, new LinkedList<BadgeResponse>());
			}
			BadgeResponse badgeResponse = new BadgeResponse();
			badgeResponse.setBadgeId(String.valueOf(badge.getBadgeId()));
			badgeResponse.setBadgeName(badge.getBadgeName());
			badgeResponse.setBadgeDesc(badge.getBadgeDesc());
			badgeResponse.setBadgeClass(badge.getBadgeClass());
			badgeResponse.setBadgeLevel(String.valueOf(badge.getBadgeLevel()));
			badgeResponse.setEmailSubject(badge.getEmailSubject());
			badgeResponse.setEmailMsg(badge.getEmailMsg());
			badgeResponse.setPublicRecognition(badge.getPublicRecognition());
			badgesGroupedByClass.get(badgeClass).add(badgeResponse);
		}
		return badgesGroupedByClass;
	}
	
	public void updateBadgeClass(String oldClassName, String newClassName) throws PersuasionAPIException {
		boolean newBadgeAlreadyExists = 
				badgeOperations.checkIfBadgeClassExists(newClassName);
		if(newBadgeAlreadyExists) throw new PersuasionAPIException("1001", 
				"The specified badge class already in use. Please choose a different one");
		badgeOperations.updateBadgeClass(oldClassName, newClassName);
		userBadgeOperations.updateBadgeClass(oldClassName, newClassName);
	}

	public Badge createOrEditBadge(Badge newBadge, boolean isNewBadgeClass) throws PersuasionAPIException {
		
		if(isNewBadgeClass) {
			boolean badgeClassExists = 
					badgeOperations.checkIfBadgeClassExists(newBadge.getBadgeClass());
			if(badgeClassExists) {
				log.error("Found an existing badge class: " + newBadge.getBadgeClass()
						+ " requested for creation. Skipping creating new badge. Throwing error");
				throw new PersuasionAPIException("1001", "The requested badge class already exists"
						+ ". Please enter a different badge class"
						+ " or use the add option to the existing class");
			}
		}
		
		if(newBadge.getBadgeId()!=null) {//editing existing badge
			Badge existing = badgeOperations.lookupBadge(newBadge.getBadgeId());
			if(existing == null) {
				log.error("No badge found with Id: " + newBadge.getBadgeId()
						+ ". Skipping editing badge. Throwing error");
				throw new PersuasionAPIException("1001", "No such badge found for editing"
						+ ". Please refresh the page and try again");
			}
			
			if(existing.getBadgeLevel() != newBadge.getBadgeLevel()) {
				checkAndErrorOnExistingBadgeForClass(newBadge.getBadgeClass(), newBadge.getBadgeLevel());
			}
			return badgeOperations.editBadge(existing, newBadge);
		} else {//creating new badge
			checkAndErrorOnExistingBadgeForClass(newBadge.getBadgeClass(), newBadge.getBadgeLevel());;
			return badgeOperations.createNewBadge(newBadge);
		}
	}

	private void checkAndErrorOnExistingBadgeForClass(String badgeClass,
			int badgeLevel) throws PersuasionAPIException {
		Badge existing = 
				badgeOperations.lookupBadge(badgeClass, badgeLevel);
		if(existing != null) {
			log.error("Found an existing badge for class: " + badgeClass
					+ " and level: " + badgeLevel
					+ ". Skipping creating a new badge. Throwing error");
			throw new PersuasionAPIException("1001", "A badge for level " + badgeClass
					+ " already exists for class " + badgeLevel
					+ ". Please choose a different level");
		}
	}

	public void deleteBadge(Integer badgeId) throws PersuasionAPIException {
		Badge existingBadge = badgeOperations.lookupBadge(badgeId);
		if(existingBadge == null) {
			log.error("No badge found with Id: " + badgeId
					+ ". Skipping deleting badge. Throwing error");
			throw new PersuasionAPIException("1001", "No such badge found for deleting"
					+ ". Please refresh the page and try again");
		}
		badgeRuleOperations.deleteAllBadgeMappings(badgeId);
		userBadgeOperations.removeAllAssignmentsForBadge(badgeId);
		userSocialFeedOperations.removeAllNotificationsForBadge(badgeId);
		badgeOperations.deleteBadge(existingBadge);
	}

}
