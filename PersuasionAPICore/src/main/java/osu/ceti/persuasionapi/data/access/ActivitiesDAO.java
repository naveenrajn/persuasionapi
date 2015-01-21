package osu.ceti.persuasionapi.data.access;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.model.Activities;

/**
 * DAO for domain model Activities
 * @see osu.ceti.persuasionapi.data.model.Activities
 */
public class ActivitiesDAO {

	private static final Log log = LogFactory.getLog(ActivitiesDAO.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void persist(Activities transientInstance) throws PersuasionAPIException {
		log.debug("persisting Activities instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (Exception e) {
			log.error("persist Activity to database failed"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to persist Activity to database", e);
		}
	}

	public Activities findbyActivityName(String activityName) throws PersuasionAPIException {
		log.debug("finding Activities instance by activityName: " + activityName);
		Activities searchResult = null;
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria(Activities.class)
					.add(Restrictions.eq("activityName", activityName)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			if(results.size() == 1) {
				searchResult = (Activities) results.get(0);
			} else if(results.size() > 1) {
				log.error("More than one activity entry found for name: " + activityName);
				throw new DatabaseException("More than one activity entry found"
						+ " for name: " + activityName);
			} else {
				searchResult = null;
			}
		} catch(PersuasionAPIException e) {
			throw e;
		}catch (Exception e) {
			log.error("find Activity by activity name failed. Activity Name: " + activityName
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to lookup Activity from database"
					+ " with name: " + activityName, e);
		}
		return searchResult;
	}
}
