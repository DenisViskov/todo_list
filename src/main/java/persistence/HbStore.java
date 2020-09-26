package persistence;

import model.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.function.Function;

/**
 * Class is an HbStore
 *
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

    private HbStore() {
    }

    /**
     * Singleton initialization
     */
    private static final class Lazy {
        /**
         * Instance
         */
        private static final Store INST = new HbStore();
    }

    /**
     * Method returns store instance
     *
     * @return store
     */
    public static Store instOf() {
        return Lazy.INST;
    }

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
     * Method returns not done tasks from DB
     *
     * @return list
     */
    @Override
    public List<Task> getNotDone() {
        return find(
                session -> session.createQuery("from model.Task where done=false").list()
        );
    }

    /**
     * Method returns all tasks from DB
     *
     * @return list
     */
    @Override
    public List<Task> getAll() {
        return find(
                session -> session.createQuery("from model.Task").list()
        );
    }

    /**
     * Method is a filter for tasks
     *
     * @param command
     * @param <T>
     * @return T
     */
    private <T> T find(Function<Session, T> command) {
        Session session = sf.openSession();
        session.beginTransaction();
        T result = command.apply(session);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * Method execute update task in DB
     *
     * @param task
     */
    @Override
    public void update(Task task) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(task);
        session.getTransaction().commit();
        session.close();
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
