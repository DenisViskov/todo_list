package persistence;

import model.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

/**
 * @author Денис Висков
 * @version 1.0
 * @since 20.09.2020
 */
public class HbStore implements Store<Task> {
    /**
     * Registry
     */
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    /**
     * Session factory
     */
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();


    /**
     * Method add new task to DB
     *
     * @param task
     * @return task
     */
    @Override
    public Task add(Task task) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(task);
        session.getTransaction().commit();
        session.close();
        return task;
    }

    /**
     * Method returns all tasks from DB
     *
     * @return list
     */
    @Override
    public List<Task> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from model.Task").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * Method of destroy registry
     *
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
