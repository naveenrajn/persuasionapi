package osu.ceti.persuasionapi.data.access;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.InternalErrorCodes;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.model.ActivityLog;

/**
 * DAO for domain model ActivityLog
 * @see osu.ceti.persuasionapi.data.model.ActivityLog
 */
public class ActivityLogDAO {

	private static final Log log = LogFactory.getLog(ActivityLogDAO.class);
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public ActivityLog merge(ActivityLog detachedInstance) throws DatabaseException {
		log.debug("merging ActivityLog instance");
		try {
			ActivityLog result = (ActivityLog) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (Exception e) {
			log.error("merge ActivityLog to database failed"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to persist/merge ActivityLog to database", e);
		}
	}

	public ActivityLog findById(osu.ceti.persuasionapi.data.model.ActivityLogId id) throws DatabaseException {
		log.debug("getting ActivityLog instance with id: " + id);
		try {
			ActivityLog instance = (ActivityLog) sessionFactory.getCurrentSession().get(
							ActivityLog.class, id);
			return instance;
		} catch (Exception e) {
			log.error("find ActivityLog by Id failed"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to retrieve ActivityLog from database", e);
		}
	}

	public List getAllActivityLogsForUser(String userId) throws DatabaseException {
		log.debug("finding ActivityLog instance for userId");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("osu.ceti.persuasionapi.data.model.ActivityLog")
					.add(Restrictions.eq("id.users.userId", userId)).list();
			log.debug("find ActivityLog for userId successful, result size: "
					+ results.size());
			return results;
		} catch (Exception e) {
			log.error("Failed to retrieve all activity logs for user: " + userId
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to retrieve all activity logs from database"
					+ " for user " + userId, e);
		}
	}
	
}
