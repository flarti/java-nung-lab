package ua.edu.nung.fit.vdsplatform.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ua.edu.nung.fit.vdsplatform.model.ResourceUsage;
import ua.edu.nung.fit.vdsplatform.util.HibernateUtil;

import java.util.List;

public class ResourceUsageDao {

    public ResourceUsage findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(ResourceUsage.class, id);
        }
    }

    public List<ResourceUsage> findByServerId(Long serverId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from ResourceUsage r where r.serverId = :serverId order by r.timestamp desc", ResourceUsage.class)
                    .setParameter("serverId", serverId)
                    .list();
        }
    }

    public List<ResourceUsage> findLatestByServerId(Long serverId, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from ResourceUsage r where r.serverId = :serverId order by r.timestamp desc", ResourceUsage.class)
                    .setParameter("serverId", serverId)
                    .setMaxResults(limit)
                    .list();
        }
    }

    public ResourceUsage saveOrUpdate(ResourceUsage resourceUsage) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(resourceUsage);
            transaction.commit();
            return resourceUsage;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void delete(Long id) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            ResourceUsage resourceUsage = session.get(ResourceUsage.class, id);
            if (resourceUsage != null) {
                session.remove(resourceUsage);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}

