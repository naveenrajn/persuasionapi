package osu.ceti.persuasionapi.services.external;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.InternalErrorCodes;
import osu.ceti.persuasionapi.core.operations.UserOperations;
import osu.ceti.persuasionapi.data.model.User;

@Service
@Transactional
public class UserServices {
	
	private static final Log log = LogFactory.getLog(UserServices.class);

	@Autowired private UserOperations userOperations;

	/**
	 * Creates a new user with the given userId. Do nothing if the user already exists
	 * @param userId
	 * @throws PersuasionAPIException
	 */
	public void createUser(String userId) throws PersuasionAPIException {
		try {
			//TODO(Future Work): Might want to change this to throw error if user is already available
			User user = userOperations.findOrCreateUser(userId);

			//TODO: Handle this exception appropriately after define API error handling mechanism
			//Also, we might want to catch and handle the error thrown by findOrCreateUser() method
			if(user == null) throw new RuntimeException("Error creating user");
		} catch (PersuasionAPIException e) {
			log.error("Failed to process createUser");
			throw e;
		} catch(Exception e) {
			log.error("Caught exception while processing createUser"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION
					, "Failed to create new user");
		}
	}

}
