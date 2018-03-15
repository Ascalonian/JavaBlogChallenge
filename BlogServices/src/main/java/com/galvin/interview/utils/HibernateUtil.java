package com.galvin.interview.utils;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.context.internal.ManagedSessionContext;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	private static Configuration configuration;
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			initialize();
		}
	    
	    return sessionFactory;
	}	
	
	public static Configuration getConfiguration() {
		if (configuration == null) {
			initialize();
		}
		
		return configuration;
	}
	
	private static void initialize() {
		configuration = new Configuration();
	    configuration.configure();
	    
	    PropertyResourceBundle prop = (PropertyResourceBundle) ResourceBundle.getBundle("caci-db");
	    
	    // Basic connection information
	    configuration.setProperty("hibernate.connection.username", prop.getString("db.username"));
	    configuration.setProperty("hibernate.connection.password", prop.getString("db.password"));
	    configuration.setProperty("hibernate.connection.url", prop.getString("db.url"));
	    configuration.setProperty("hibernate.connection.driver_class", prop.getString("db.driver"));
	    configuration.setProperty("hibernate.dialect", prop.getString("db.dialect"));
	    
	    configuration.setProperty("hibernate.current_session_context_class", prop.getString("hibernate.session_context"));
	    
	    // Handling SQL statements in the logs
	    configuration.setProperty("hibernate.show_sql", prop.getString("hibernate.show_sql"));
	    configuration.setProperty("hibernate.format_sql", prop.getString("hibernate.format_sql"));
	    configuration.setProperty("hibernate.use_sql_comments", prop.getString("hibernate.use_sql_comments"));
	    
	    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
	
	public static void beginTransaction() {
		Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.COMMIT);
        ManagedSessionContext.bind(session);
        
        // start a transaction
        session.beginTransaction();
	}
	
	// call this method to terminate the current transaction and start a new
    // transaction, the new transaction will stay open until the session
    // closes or this method is called again.
	private static void newTransaction(boolean rollback) {
		Session currentSession = getSessionFactory().getCurrentSession();
		
		if (rollback) {
        	currentSession.getTransaction().rollback();
        	currentSession.clear();
        }else {
        	currentSession.getTransaction().commit();            	
        }
		
		currentSession.setFlushMode(FlushMode.COMMIT);
		
		currentSession.beginTransaction();
	}
	
	public static void newTransaction() {
    	newTransaction(false);
    }
	
	public static void endTransaction(boolean rollback) {
		Session currentSession = null;
		
		try {
			currentSession = ManagedSessionContext.unbind(sessionFactory); 
			
			if (rollback) {
	            currentSession.getTransaction().rollback();
	        }else {
	        	currentSession.getTransaction().commit();
	        }
		}finally {
            if (currentSession != null) {
                // the session must not be reused, close the session 
                currentSession.close(); 
            }
        }
	}
}
