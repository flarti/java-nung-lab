package ua.edu.nung.fit.vdsplatform.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ua.edu.nung.fit.vdsplatform.model.Deployment;
import ua.edu.nung.fit.vdsplatform.util.HibernateUtil;

import java.util.List;

public class DeploymentDao {

    public Deployment findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Deployment.class, id);
        }
    }

    public List<Deployment> findByServerId(Long serverId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Deployment d where d.serverId = :serverId order by d.createdAt desc", Deployment.class)
                    .setParameter("serverId", serverId)
                    .list();
        }
    }

    public List<Deployment> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Deployment d order by d.createdAt desc", Deployment.class).list();
        }
    }

    public Deployment saveOrUpdate(Deployment deployment) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(deployment);
            transaction.commit();
            return deployment;
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
            Deployment deployment = session.get(Deployment.class, id);
            if (deployment != null) {
                session.remove(deployment);
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

