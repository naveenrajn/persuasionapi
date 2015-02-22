package osu.ceti.persuasionapi.data.access;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.model.UserAttributeValue;

/**
 * DAO for domain model UserAttributeValue
 * @see osu.ceti.persuasionapi.data.model.UserAttributeValue
 */
public class UserAttributeValueDAO {

	private static final Log log = LogFactory
			.getLog(UserAttributeValueDAO.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void persist(UserAttributeValue transientInstance) {
		log.debug("persisting UserAttributeValue instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(UserAttributeValue instance) {
		log.debug("attaching dirty UserAttributeValue instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserAttributeValue instance) {
		log.debug("attaching clean UserAttributeValue instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(UserAttributeValue persistentInstance) {
		log.debug("deleting UserAttributeValue instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserAttributeValue merge(UserAttributeValue detachedInstance) {
		log.debug("merging UserAttributeValue instance");
		try {
			UserAttributeValue result = (UserAttributeValue) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UserAttributeValue findById(
			osu.ceti.persuasionapi.data.model.UserAttributeValueId id) throws DatabaseException {
		log.debug("getting UserAttributeValue instance with id: " + id);
		try {
			UserAttributeValue instance = (UserAttributeValue) sessionFactory
					.getCurrentSession()
					.get(UserAttributeValue.class, id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (Exception e) {
			log.error("get userAttributeValue from Id failed"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to retrieve UserAttributeValue from database", e);
		}
	}

	public List findByExample(UserAttributeValue instance) {
		log.debug("finding UserAttributeValue instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"osu.ceti.persuasionapi.data.model.UserAttributeValue")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<UserAttributeValue> getAllAttributeValuesForUser(String userId) {
		log.debug("finding UserAttributeValues for user: " + userId);
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(UserAttributeValue.class)
					.add(Restrictions.eq("id.user.userId", userId)).list();
			log.debug("getAllAttributeValuesForUser succesful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			//TODO: Add appropriate error handling
			log.error("getAllAttributeValuesForUser failed", re);
			throw re;
		}
	}
}
