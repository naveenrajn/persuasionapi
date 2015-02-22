package osu.ceti.persuasionapi.core.operations;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.Constants;
import osu.ceti.persuasionapi.core.helpers.InternalErrorCodes;
import osu.ceti.persuasionapi.core.helpers.JMSMessageSender;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.access.ActivityLogDAO;
import osu.ceti.persuasionapi.data.model.Activity;
import osu.ceti.persuasionapi.data.model.ActivityLog;
import osu.ceti.persuasionapi.data.model.ActivityLogId;
import osu.ceti.persuasionapi.data.model.User;

@Component
public class ActivityLogOperations {

	private static final Log log = LogFactory.getLog(ActivityLogOperations.class);

	@Autowired private ActivityLogDAO activityLogDAO;
	@Autowired private JMSMessageSender jmsMessageSender;
	@Autowired private JmsTemplate jmsQueue;

	/**
	 * Logs the user activity and posts the update to internal JMS queue for processing
	 * @param user
	 * @param activity
	 * @param value
	 * @return the inserted/updated ActivityLog object from the database
	 * @throws PersuasionAPIException
	 */
	public ActivityLog logUserActivity(User user, Activity activity, 
			String value) throws PersuasionAPIException {

		ActivityLog activityLog = null;
		try {
			//Create the composite Activity Log object
			ActivityLogId activityLogId = new ActivityLogId();
			activityLogId.setUser(user);
			activityLogId.setActivity(activity);

			try {
				log.debug("Searching for user an existing activity log entry for userId " 
						+ user.getUserId() + " and activityName " + activity.getActivityName());
				activityLog = activityLogDAO.findById(activityLogId);
				log.debug("Found existing user activity log entry. Incrementing the counter");
				activityLog.setCount(activityLog.getCount()+1); //Increment the counter
			} catch(PersuasionAPIException e) {
				throw e;
			} catch(Exception e) {
				log.debug("Existing user activity log entry not found. Creating a new entry");
				activityLog = new ActivityLog();
				activityLog.setId(activityLogId);
				activityLog.setCount(1);
			}

			activityLog.setValue(value);
			//TODO: Modify this to use DB timestamp
			activityLog.setLogTime(new Date());

			//Create or update corresponding activity log entry
			activityLogDAO.merge(activityLog);
		} catch(PersuasionAPIException e) {
			throw e;
		} catch(Exception e) {
			log.error("Caught exception while logging user activity."
					+ " User ID: " + user.getUserId()
					+ " . Activity Name: " + activity.getActivityName()
					+ " Exception type: " + e.getClass().getName()
					+ " Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION, 
					"Failed to log the user activity", e);
		}

		try {
			//Post the update to the JMS Queue
			Map<String, String> activityLogUpdate = new HashMap<String, String>();
			activityLogUpdate.put(Constants.USER_ID, user.getUserId());
			activityLogUpdate.put(Constants.ACTIVITY_NAME, activity.getActivityName().toString());

			String logDate = StringHelper.dateToString(activityLog.getLogTime());
			activityLogUpdate.put(Constants.TIMESTAMP, logDate);

			jmsMessageSender.sendJMSMessage(jmsQueue, activityLogUpdate);
		} catch(Exception e) {
			log.error("Caught exception while posting user activity to JMS."
					+ " User ID: " + user.getUserId()
					+ " . Activity Name: " + activity.getActivityName()
					+ " Exception type: " + e.getClass().getName()
					+ " Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION, 
					"Failed to post the logged user activity to JMS", e);
		}
		return activityLog;
	}

	/**
	 * Retrieve all activity logs for the user with given userId
	 * @param userId
	 * @return List of ActivityLog entries indexed by activityId in a HashMap
	 * @throws PersuasionAPIException
	 */
	public Map<String, ActivityLog> getAllActivityLogsForUser(String userId)
			throws PersuasionAPIException {
		try {
			List<ActivityLog> activityLogs = activityLogDAO.getAllActivityLogsForUser(userId);

			Map<String, ActivityLog> returnMap = new HashMap<String, ActivityLog>();
			for(ActivityLog activityLog : activityLogs) {
				returnMap.put(activityLog.getId().getActivity().getActivityName(), activityLog);
			}
			return returnMap;
		} catch(PersuasionAPIException e) {
			throw e;
		} catch(Exception e) {
			log.error("Failed to retrieve activity log entries from database"
					+ " for user ID: " + userId
					+ "Exception type: " + e.getClass().getName()
					+ "Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION,
					"Failed to retrieve activity log entries from database", e);
		}
	}

}
