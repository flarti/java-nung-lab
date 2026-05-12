package ua.edu.nung.fit.vdsplatform.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ua.edu.nung.fit.vdsplatform.model.Server;
import ua.edu.nung.fit.vdsplatform.util.HibernateUtil;

import java.util.List;

public class ServerDao {

    public Server findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Server.class, id);
        }
    }

    public Server findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Server s where s.name = :name", Server.class)
                    .setParameter("name", name)
                    .uniqueResult();
        }
    }

    public List<Server> findByOwnerId(Long ownerId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Server s where s.ownerId = :ownerId order by s.createdAt desc", Server.class)
                    .setParameter("ownerId", ownerId)
                    .list();
        }
    }

    public List<Server> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Server s order by s.createdAt desc", Server.class).list();
        }
    }

    public Server saveOrUpdate(Server server) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(server);
            transaction.commit();
            return server;
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
            Server server = session.get(Server.class, id);
            if (server != null) {
                session.remove(server);
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

