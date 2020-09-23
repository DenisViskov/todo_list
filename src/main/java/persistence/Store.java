package persistence;


import java.util.List;

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
     * Method should return all element from DB
     *
     * @return list
     */
    List<V> findAll();

    /**
     * Method should update task in DB
     *
     * @param task
     */
    void update(V task);
}
