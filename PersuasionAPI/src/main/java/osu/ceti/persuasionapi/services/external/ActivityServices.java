package osu.ceti.persuasionapi.services.external;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.InternalErrorCodes;
import osu.ceti.persuasionapi.core.operations.ActivityLogOperations;
import osu.ceti.persuasionapi.core.operations.ActivityOperations;
import osu.ceti.persuasionapi.core.operations.UserOperations;
import osu.ceti.persuasionapi.data.model.Activity;
import osu.ceti.persuasionapi.data.model.User;

@Service
@Transactional
public class ActivityServices {
	
	private static final Log log = LogFactory.getLog(ActivityServices.class);

	@Autowired private UserOperations userOperations;
	@Autowired private ActivityOperations activityOperations;
	@Autowired private ActivityLogOperations activityLogOperations;

	/**
	 * Record user activity completion
	 * @param userId
	 * @param activityName
	 * @param value
	 * @throws PersuasionAPIException
	 */
	public void reportUserActivity(String userId, String activityName, String value) throws PersuasionAPIException {
		try {
			//TODO(Future Work): Might want to change this to throw error if user 
			//is not available
			User user = userOperations.findOrCreateUser(userId);

			//Check for activity and create if not available
			//TODO(Future Work): Might want to change this to throw error if activity 
			//is not available
			Activity activity = activityOperations.findOrCreateActivity(activityName);

			//Log the activity
			activityLogOperations.logUserActivity(user, activity, value);
		} catch (PersuasionAPIException e) {
			log.error("Failed to process reportUserActivity");
			throw e;
		} catch(Exception e) {
			log.error("Caught exception while processing reportUserActivity"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION
					, "Failed to report user activity to the Persuasion API");
		}
	}

}
