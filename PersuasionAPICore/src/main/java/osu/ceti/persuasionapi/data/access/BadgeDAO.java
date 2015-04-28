package osu.ceti.persuasionapi.data.access;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import osu.ceti.persuasionapi.core.exceptions.DatabaseException;
import osu.ceti.persuasionapi.core.helpers.StringHelper;
import osu.ceti.persuasionapi.data.model.Badge;

/**
 * DAO for domain model Badge
 * @see osu.ceti.persuasionapi.data.model.Badge
 */
public class BadgeDAO {

	private static final Logger log = Logger.getLogger(BadgeDAO.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void persist(Badge transientInstance) {
		log.debug("persisting Badges instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Badge instance) {
		log.debug("attaching dirty Badges instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Badge instance) {
		log.debug("attaching clean Badges instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Badge persistentInstance) throws DatabaseException {
		log.debug("deleting Badges instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (Exception e) {
			log.error("Failed to delete Badge: " + persistentInstance.toString()
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to delete Badge in database");
		}
	}

	public Badge merge(Badge detachedInstance) throws DatabaseException {
		log.debug("merging Badges instance");
		try {
			Badge result = (Badge) sessionFactory.getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (Exception e) {
			log.error("Failed to create/update Badge: " + detachedInstance.toString()
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to create/update Badge in database");
		}
	}

	public Badge findById(java.lang.Integer id) throws DatabaseException {
		log.debug("getting Badges instance with id: " + id);
		try {
			Badge instance = (Badge) sessionFactory.getCurrentSession().get(
					Badge.class, id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (Exception e) {
			log.error("find Badge by Id failed"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to retrieve Badge from database", e);
		}
	}

	public List findByExample(Badge instance) {
		log.debug("finding Badges instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("osu.ceti.persuasionapi.data.model.Badges")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	public List getAllBadges() throws DatabaseException {
		log.debug("retrieving full Badge list");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria(Badge.class).list();
			log.debug("retrieve full Badge list successful, result size: "
					+ results.size());
			return results;
		} catch (Exception e) {
			log.error("Failed to retrieve full Badge list"
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to full Badge list from database");
		}
	}

	public List getAllBadgesForClass(String badgeClass) throws DatabaseException {
		log.debug("retrieving all badges for class");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria(Badge.class)
					.add(Restrictions.eq("badgeClass", badgeClass)).list();
			log.debug("retrieve badge for class succesful, result size: "
					+ results.size());
			return results;
		} catch (Exception e) {
			log.error("Failed to retrieve Badge list for class: " + badgeClass
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to full Badge list from database");
		}
	}
	
	public void updateBadgeClass(String oldClassName, String newClassName) throws DatabaseException {
		log.debug("updating class name");
		try {
			Query query = sessionFactory.getCurrentSession().createQuery(
					"update Badge set badgeClass=:newValue where badgeClass=:oldValue");
			query.setParameter("oldValue", oldClassName);
			query.setParameter("newValue", newClassName);
			query.executeUpdate();
			log.debug("update badge class successful. Old class: " + oldClassName 
					+ ". New class: " + newClassName);
		} catch (Exception e) {
			log.error("Failed to update badge class in Badge. Old class: " + oldClassName 
					+ ". New class: " + newClassName
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed update badge class: " + e.getMessage());
		}
	}

	public Badge findByClassAndLevel(String badgeClass, Integer badgeLevel) throws DatabaseException {
		log.debug("retrieving Badge for class and level");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria(Badge.class)
					.add(Restrictions.eq("badgeClass", badgeClass))
					.add(Restrictions.eq("badgeLevel", badgeLevel)).list();
			log.debug("retrieve badge for class and level succesful, result size: "
					+ results.size());
			return (Badge) ((results.size()>0) ? results.get(0) : null);
		} catch (Exception e) {
			log.error("Failed to retrieve Badge for class: " + badgeClass
					+ " and level: " + badgeLevel
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to full Badge list from database");
		}
	}

	public long lookupBadgeCountForClass(String badgeClass) throws DatabaseException {
		log.debug("checking if badges with the badge class exists");
		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Badge.class);
			criteria.add(Restrictions.eq("badgeClass", badgeClass));
			criteria.setProjection(Projections.rowCount());
			Long rowCount = (Long) criteria.uniqueResult();
			log.debug("lookup badge count for class succesful: " + rowCount);
			return rowCount;
		} catch (Exception e) {
			log.error("Failed to retrieve Badge count for class: " + badgeClass
					+ ". Exception type: " + e.getClass().getName()
					+ ". Exception message: " + e.getMessage());
			log.debug(StringHelper.stackTraceToString(e));
			throw new DatabaseException("Failed to lookup badgeClass from database");
		}
	}
}
