package osu.ceti.persuasionapi.data.access;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.model.User;

/**
 * DAO for domain model User
 * @see osu.ceti.persuasionapi.data.model.User
 */
public class UserDAO {

	private static final Logger log = Logger.getLogger(UserDAO.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public User merge(User detachedInstance) throws DatabaseException {
		log.debug("merging Users instance");
		try {
			User result = (User) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (Exception e) {
			log.error("merge User to database failed"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to persist/merge User to database", e);
		}
	}

	public User findById(java.lang.String id) throws DatabaseException {
		log.debug("getting Users instance with id: " + id);
		try {
			User instance = (User) sessionFactory.getCurrentSession().get(User.class, id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (Exception e) {
			log.error("find User by Id failed. User ID: " + id
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to retrieve User from database", e);
		}
	}
}
