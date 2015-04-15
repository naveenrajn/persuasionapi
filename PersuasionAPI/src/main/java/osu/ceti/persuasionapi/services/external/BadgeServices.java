package osu.ceti.persuasionapi.services.external;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.InternalErrorCodes;
import osu.ceti.persuasionapi.core.operations.UserBadgeOperations;
import osu.ceti.persuasionapi.data.model.Badge;
import osu.ceti.persuasionapi.data.model.UserBadgeMapping;

@Service
@Transactional
public class BadgeServices {
	
	private static final Logger log = Logger.getLogger(ActivityServices.class);
	
	@Autowired UserBadgeOperations userBadgeOperations;

	/**
	 * Fetches and returns badge level of a given badge class for the given user
	 * @param userId
	 * @param badgeClass
	 * @return badge
	 * @throws PersuasionAPIException
	 */
	public Badge getUserBadgeForBadgeClass(String userId, String badgeClass)
			throws PersuasionAPIException {
		try {
			UserBadgeMapping mapping = userBadgeOperations
					.getSingleClassBadgeForUser(userId, badgeClass);
			if(mapping == null) return null;
			
			Hibernate.initialize(mapping.getBadge());
			return mapping.getBadge();
		} catch (PersuasionAPIException e) {
			log.error("Failed to process getUserBadgeForBadgeClass");
			throw e;
		} catch(Exception e) {
			log.error("Caught exception while processing getUserBadgeForBadgeClass"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION
					, "Failed to retrieve badge details for user");
		}
	}
	
	/**
	 * Fetches and returns all badges assignments for a given user
	 * @param userId
	 * @return list of badges the user possesses
	 * @throws PersuasionAPIException
	 */
	public List<Badge> getAllBadgesForUser(String userId) throws PersuasionAPIException {
		try {
			List<UserBadgeMapping> userBadgeMappings = 
					userBadgeOperations.getAllBadgesForUser(userId);
			
			List<Badge> returnList = new ArrayList<Badge>();
			for(UserBadgeMapping mapping : userBadgeMappings) {
				Hibernate.initialize(mapping.getBadge());
				returnList.add(mapping.getBadge());
			}
			return returnList;
		} catch (PersuasionAPIException e) {
			log.error("Failed to process getAllBadgesForUser");
			throw e;
		} catch (Exception e) {
			log.error("Caught exception while processing getAllBadgesForUser"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION
					, "Failed to retrieve badge details for user");
		}
	}

}
