package ua.edu.nung.fit.vdsplatform.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.edu.nung.fit.vdsplatform.model.User;
import ua.edu.nung.fit.vdsplatform.model.Server;
import ua.edu.nung.fit.vdsplatform.model.Deployment;
import ua.edu.nung.fit.vdsplatform.model.ResourceUsage;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            Properties properties = new Properties();

            try (InputStream input = HibernateUtil.class
                    .getClassLoader()
                    .getResourceAsStream("project.properties")) {

                if (input == null) {
                    throw new IllegalStateException("project.properties not found");
                }

                properties.load(input);
            }

            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            configuration.addAnnotatedClass(User.class);

            configuration.addAnnotatedClass(Server.class);
            configuration.addAnnotatedClass(Deployment.class);
            configuration.addAnnotatedClass(ResourceUsage.class);

            configuration.setProperty("hibernate.connection.driver_class", properties.getProperty("db.driver"));
            configuration.setProperty("hibernate.connection.url", properties.getProperty("db.url"));
            configuration.setProperty("hibernate.connection.username", properties.getProperty("db.user"));
            configuration.setProperty("hibernate.connection.password", properties.getProperty("db.password"));

            configuration.setProperty("hibernate.dialect", properties.getProperty("hibernate.dialect"));
            configuration.setProperty("hibernate.show_sql", properties.getProperty("hibernate.show_sql"));
            configuration.setProperty("hibernate.hbm2ddl.auto", properties.getProperty("hibernate.hbm2ddl.auto"));

            sessionFactory = configuration.buildSessionFactory();

        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

