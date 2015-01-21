package osu.ceti.persuasionapi.core.operations;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.InternalErrorCodes;
import osu.ceti.persuasionapi.data.access.ActivitiesDAO;
import osu.ceti.persuasionapi.data.model.Activities;

@Component
public class ActivityOperations {
	
	private static final Log log = LogFactory.getLog(ActivityOperations.class);
	
	@Autowired private ActivitiesDAO activitiesDAO;
	
	/**
	 * Lookups the database to find the activity with the given name
	 * @param activityName
	 * @return activity handle if found, throw exception if not
	 * @throws PersuasionAPIException 
	 */
	public Activities lookupActivity(String activityName) throws PersuasionAPIException {
		return activitiesDAO.findbyActivityName(activityName);
	}
	
	/**
	 * Creates a new activity
	 * Throws exception of the specified activity already exists
	 * @param activityName
	 * @param activityDescription
	 * @return created activity handle
	 * @throws PersuasionAPIException 
	 */
	public Activities createNewActivity(String activityName, 
			String activityDescription) throws PersuasionAPIException {
		
		if(activityName == null) {
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION, 
					"Activity name cannot be empty to create a new activity");
		}
		
		Activities activity = lookupActivity(activityName);
		if(activity != null) {
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION,
					"Activity " + activityName + " already available. Cannot create a new "
							+ "activity with the same name");
		}
		
		activity = new Activities(activityName);
		activity.setActivityDesc(activityDescription);
		activitiesDAO.persist(activity);
		
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
	public Activities findOrCreateActivity(String activityName) throws PersuasionAPIException {
		Activities activity = lookupActivity(activityName);

		if(activity == null) {
			//TODO: Possible second call to lookupActivity(). Modify this to reduce a call
			activity = createNewActivity(activityName, null);
		}
		
		return activity;
	}
	
}
