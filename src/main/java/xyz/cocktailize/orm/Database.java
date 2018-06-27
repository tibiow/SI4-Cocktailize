package xyz.cocktailize.orm;

import org.hibernate.Session;
import xyz.cocktailize.utils.HibernateUtil;

public abstract class Database<T> {

    private Session session;
    private Class<T> tClass;

    Database(Class<T> tClass) {
        session = HibernateUtil.getSessionFactory().openSession();
        this.tClass = tClass;
    }

    public Session getSession() {
        return session;
    }

    public T getById(int id) {
        return session.get(tClass, id);
    }

    public T getByName(String name) {
        return session.createQuery("FROM " + tClass.getSimpleName() + " WHERE name=:name", tClass)
                .setParameter("name", name)
                .uniqueResult();
    }

    public void saveOrUpdate(Object o) {
        session.beginTransaction();
        session.saveOrUpdate(o);
        session.getTransaction().commit();
    }

    public int count() {
        return ((Long) session.createQuery("SELECT COUNT(*) FROM " + tClass.getSimpleName()).uniqueResult()).intValue();
    }
}
