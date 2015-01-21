package osu.ceti.persuasionapi.core.operations;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.data.access.UserBadgeMappingsDAO;
import osu.ceti.persuasionapi.data.model.Badges;
import osu.ceti.persuasionapi.data.model.UserBadgeMappings;
import osu.ceti.persuasionapi.data.model.UserBadgeMappingsId;
import osu.ceti.persuasionapi.data.model.Users;

@Component
public class UserBadgeOperations {
	
	private static final Log log = LogFactory.getLog(UserBadgeOperations.class);

	@Autowired UserBadgeMappingsDAO userBadgeDAO;
	
	/**
	 * Retrieve all current badges for user with the given userId
	 * @param userId
	 * @return list of user badges
	 * @throws DatabaseException 
	 */
	public List<UserBadgeMappings> getAllBadgesForUser(String userId) throws DatabaseException {
		return userBadgeDAO.getAllBadgeMappingsForUser(userId);
	}
	
	/**
	 * Retrieve the current badge level for a given user for a given badge class
	 * @param userId
	 * @param badgeClass
	 * @return UserBadgeMapping handle, if found a badge assignment
	 * @throws DatabaseException 
	 */
	public UserBadgeMappings getSingleClassBadgeForUser(String userId, String badgeClass) 
			throws DatabaseException {
		
		UserBadgeMappingsId userBadgeMappingsId = new UserBadgeMappingsId();
		userBadgeMappingsId.setUsers(new Users(userId));
		userBadgeMappingsId.setBadgeClass(badgeClass);
		
		return userBadgeDAO.findById(userBadgeMappingsId);
	}
	
	/**
	 * Assigns the specified badge to the user with the given userId
	 * @param userId
	 * @param badge
	 * @throws DatabaseException
	 */
	public void assignBadgeForUser(String userId, Badges badge) throws DatabaseException {
		UserBadgeMappingsId searchCriteriaId = new UserBadgeMappingsId();
		searchCriteriaId.setUsers(new Users(userId));
		searchCriteriaId.setBadgeClass(badge.getBadgeClass());
		System.out.println("Badge class: " + badge.getBadgeClass());
		
		UserBadgeMappings userBadgeMappings = new UserBadgeMappings();
		try {
			System.out.println("Searching for user badge mappings");
			userBadgeMappings = userBadgeDAO.findById(searchCriteriaId);
			if(userBadgeMappings == null) {
				System.out.println("Null. Creating new object");
				userBadgeMappings = new UserBadgeMappings();
				userBadgeMappings.setId(searchCriteriaId);
			}
			System.out.println("Existing badge assignment found for user for this badge class - add user Id and badge class");
			System.out.println("After print");
		} catch(Exception e) {
			System.out.println("User badge mappings not found");
			userBadgeMappings.setId(searchCriteriaId);
		}
		System.out.println("User badge mappings after search/create" + userBadgeMappings);
		userBadgeMappings.setBadges(badge);
		userBadgeMappings.setProcessedTime(new Date());
		
		userBadgeDAO.merge(userBadgeMappings);
		
	}
}
