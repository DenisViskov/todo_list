package persistence;


import org.hibernate.Session;

import java.util.List;
import java.util.function.Function;

/**
 * Interface store
 *
 * @author Денис Висков
 * @version 1.0
 * @since 20.09.2020
 */
public interface Store<V> extends AutoCloseable {
    /**
     * Method should add task to DB
     *
     * @param task
     * @return V
     */
    V add(V task);

    /**
     * Method should return not done tasks from DB
     *
     * @return list
     */
    List<V> getNotDone();

    /**
     * Method should return all elements from DB
     *
     * @return list
     */
    List<V> getAll();

    /**
     * Method should update task in DB
     *
     * @param task
     */
    void update(V task);
}
