package osu.ceti.persuasionapi.data.access;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.model.Activity;

/**
 * DAO for domain model Activities
 * @see osu.ceti.persuasionapi.data.model.Activities
 */
public class ActivityDAO {

	private static final Log log = LogFactory.getLog(ActivityDAO.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void persist(Activity transientInstance) throws PersuasionAPIException {
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
	
	public Activity findById(String id) throws PersuasionAPIException {
		log.debug("getting Activity instance with id: " + id);
		try {
			Activity instance = (Activity) sessionFactory.getCurrentSession()
					.get(Activity.class, id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (Exception e) {
			log.error("find Activity by activity name failed. Activity Name: " + id
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to lookup Activity from database"
					+ " with name: " + id, e);
		}
	}

}
