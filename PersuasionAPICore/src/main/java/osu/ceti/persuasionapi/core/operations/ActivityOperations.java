package osu.ceti.persuasionapi.core.operations;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.InternalErrorCodes;
import osu.ceti.persuasionapi.data.access.ActivityDAO;
import osu.ceti.persuasionapi.data.model.Activity;

@Component
public class ActivityOperations {
	
	private static final Log log = LogFactory.getLog(ActivityOperations.class);
	
	@Autowired private ActivityDAO activityDAO;
	
	/**
	 * Lookups the database to find the activity with the given name
	 * @param activityName
	 * @return activity handle if found, throw exception if not
	 * @throws PersuasionAPIException 
	 */
	public Activity lookupActivity(String activityName) throws PersuasionAPIException {
		return activityDAO.findById(activityName);
	}
	
	/**
	 * Creates a new activity
	 * Throws exception of the specified activity already exists
	 * @param activityName
	 * @param activityDescription
	 * @return created activity handle
	 * @throws PersuasionAPIException 
	 */
	public Activity createNewActivity(String activityName, 
			String activityDescription) throws PersuasionAPIException {
		
		if(activityName == null) {
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION, 
					"Activity name cannot be empty to create a new activity");
		}
		
		Activity activity = lookupActivity(activityName);
		if(activity != null) {
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION,
					"Activity " + activityName + " already available. Cannot create a new "
							+ "activity with the same name");
		}
		
		activity = new Activity(activityName);
		activity.setActivityDesc(activityDescription);
		activityDAO.persist(activity);
		
		return activity;
	}
	
	/**
	 * Looks up the database to check if an activity already exists
	 * If already available, fetches and returns the handle
	 * If not, creates a new one and returns the handle
	 * @param activityName
	 * @return created or looked up activity handle
	 * @throws PersuasionAPIException 
	 */
	public Activity findOrCreateActivity(String activityName) throws PersuasionAPIException {
		Activity activity = lookupActivity(activityName);

		if(activity == null) {
			//TODO: Possible second call to lookupActivity(). Modify this to reduce a call
			activity = createNewActivity(activityName, null);
		}
		
		return activity;
	}
	
}
