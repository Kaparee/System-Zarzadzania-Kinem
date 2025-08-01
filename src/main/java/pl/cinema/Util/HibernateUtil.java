package pl.cinema.Util;

import org.hibernate.HibernateError;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private final static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateError he){
            System.err.println("Wystąpił błąd podczas tworzenia obiektu SessionFactory: " + he);
            throw new ExceptionInInitializerError(he);
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
