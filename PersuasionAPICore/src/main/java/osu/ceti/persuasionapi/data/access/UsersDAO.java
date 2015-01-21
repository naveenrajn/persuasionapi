package osu.ceti.persuasionapi.data.access;

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.model.Users;

/**
 * DAO for domain model Users
 * @see osu.ceti.persuasionapi.data.model.Users
 */
public class UsersDAO {

	private static final Log log = LogFactory.getLog(UsersDAO.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Users merge(Users detachedInstance) throws DatabaseException {
		log.debug("merging Users instance");
		try {
			Users result = (Users) sessionFactory.getCurrentSession().merge(detachedInstance);
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

	public Users findById(java.lang.String id) throws DatabaseException {
		log.debug("getting Users instance with id: " + id);
		try {
			Users instance = (Users) sessionFactory.getCurrentSession().get(Users.class, id);
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
			throw new DatabaseException("Failed to retrieve UserBadgeMappings from database", e);
		}
	}

}
