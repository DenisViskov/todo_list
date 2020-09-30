package persistence;

import java.util.List;

/**
 * Interface of UserStore
 *
 * @author Денис Висков
 * @version 1.0
 * @since 27.09.2020
 */
public interface UserStorage<V> {
    /**
     * Method should add new user to store
     *
     * @param user
     * @return V
     */
    V addUser(V user);

    /**
     * Method should update user in DB
     *
     * @param user
     */
    void updateUser(V user);

    /**
     * Method should return all users from DB
     *
     * @return List
     */
    List<V> getAllUser();
}
