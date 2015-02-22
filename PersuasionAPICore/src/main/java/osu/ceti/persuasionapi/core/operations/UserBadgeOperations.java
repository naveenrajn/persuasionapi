package osu.ceti.persuasionapi.core.operations;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.data.access.UserBadgeMappingDAO;
import osu.ceti.persuasionapi.data.model.Badge;
import osu.ceti.persuasionapi.data.model.User;
import osu.ceti.persuasionapi.data.model.UserBadgeMapping;
import osu.ceti.persuasionapi.data.model.UserBadgeMappingId;

@Component
public class UserBadgeOperations {
	
	private static final Log log = LogFactory.getLog(UserBadgeOperations.class);

	@Autowired UserBadgeMappingDAO userBadgeDAO;
	
	/**
	 * Retrieve all current badges for user with the given userId
	 * @param userId
	 * @return list of user badges
	 * @throws DatabaseException 
	 */
	public List<UserBadgeMapping> getAllBadgesForUser(String userId) throws DatabaseException {
		return userBadgeDAO.getAllBadgeMappingsForUser(userId);
	}
	
	/**
	 * Retrieve the current badge level for a given user for a given badge class
	 * @param userId
	 * @param badgeClass
	 * @return UserBadgeMapping handle, if found a badge assignment
	 * @throws DatabaseException 
	 */
	public UserBadgeMapping getSingleClassBadgeForUser(String userId, String badgeClass) 
			throws DatabaseException {
		
		UserBadgeMappingId userBadgeMappingId = new UserBadgeMappingId();
		userBadgeMappingId.setUser(new User(userId));
		userBadgeMappingId.setBadgeClass(badgeClass);
		
		return userBadgeDAO.findById(userBadgeMappingId);
	}
	
	/**
	 * Assigns the specified badge to the user with the given userId
	 * @param userId
	 * @param badge
	 * @throws DatabaseException
	 */
	public void assignBadgeForUser(String userId, Badge badge) throws DatabaseException {
		UserBadgeMappingId searchCriteriaId = new UserBadgeMappingId();
		searchCriteriaId.setUser(new User(userId));
		searchCriteriaId.setBadgeClass(badge.getBadgeClass());
		
		UserBadgeMapping userBadgeMapping = new UserBadgeMapping();
		try {
			userBadgeMapping = userBadgeDAO.findById(searchCriteriaId);
			if(userBadgeMapping == null) {
				userBadgeMapping = new UserBadgeMapping();
				userBadgeMapping.setId(searchCriteriaId);
			}
		} catch(Exception e) {
			userBadgeMapping.setId(searchCriteriaId);
		}
		userBadgeMapping.setBadge(badge);
		userBadgeMapping.setProcessedTime(new Date());
		
		userBadgeDAO.merge(userBadgeMapping);
		
	}
}
