package osu.ceti.persuasionapi.data.access;

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;

import osu.ceti.persuasionapi.data.model.Badges;

/**
 * DAO for domain model Badges
 * @see osu.ceti.persuasionapi.data.model.Badges
 */
public class BadgesDAO {

	private static final Log log = LogFactory.getLog(BadgesDAO.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void persist(Badges transientInstance) {
		log.debug("persisting Badges instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Badges instance) {
		log.debug("attaching dirty Badges instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Badges instance) {
		log.debug("attaching clean Badges instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Badges persistentInstance) {
		log.debug("deleting Badges instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Badges merge(Badges detachedInstance) {
		log.debug("merging Badges instance");
		try {
			Badges result = (Badges) sessionFactory.getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Badges findById(java.lang.Integer id) {
		log.debug("getting Badges instance with id: " + id);
		try {
			Badges instance = (Badges) sessionFactory.getCurrentSession().get(
					"osu.ceti.persuasionapi.data.model.Badges", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Badges instance) {
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
}
