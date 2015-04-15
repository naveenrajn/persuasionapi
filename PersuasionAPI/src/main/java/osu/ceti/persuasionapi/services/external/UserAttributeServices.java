package osu.ceti.persuasionapi.services.external;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.InternalErrorCodes;
import osu.ceti.persuasionapi.core.operations.UserAttributeOperations;
import osu.ceti.persuasionapi.core.operations.UserOperations;
import osu.ceti.persuasionapi.data.model.UserAttributeValue;

@Service
@Transactional
public class UserAttributeServices {
	
	private static final Logger log = Logger.getLogger(UserAttributeServices.class);
	
	@Autowired UserOperations userOperations;
	@Autowired UserAttributeOperations userAttributeOperations;

	/**
	 * Updates the attribute value of an attribute for the given user
	 * @param userId
	 * @param attributeName
	 * @param value
	 * @throws PersuasionAPIException
	 */
	public void updateUserAttribute(String userId, String attributeName, String value) 
			throws PersuasionAPIException {
		try {
			userOperations.findOrCreateUser(userId);
			userAttributeOperations.findOrCreateUserAttribute(attributeName, null);
			
			userAttributeOperations.updateUserAttributeValue(userId, attributeName, value);
		} catch (PersuasionAPIException e) {
			log.error("Failed to process updateUserAttribute");
			throw e;
		} catch(Exception e) {
			log.error("Caught exception while processing updateUserAttribute"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION
					, "Failed to update user attribute value");
		}
	}

	/**
	 * Fetches and returns the value of the requested attribute for the given user
	 * @param userId
	 * @param attributeName
	 * @return attribute value
	 * @throws PersuasionAPIException
	 */
	public UserAttributeValue getUserAttributeValue(String userId, String attributeName) 
			throws PersuasionAPIException {
		try {
			return userAttributeOperations.getUserAttribute(userId, attributeName);
		} catch (PersuasionAPIException e) {
			log.error("Failed to process updateUserAttribute");
			throw e;
		} catch(Exception e) {
			log.error("Caught exception while processing getUserAttributeValue"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION
					, "Failed to retrieve user attribute value");
		}
	}
	
}
