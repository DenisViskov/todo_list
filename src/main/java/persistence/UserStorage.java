package persistence;

import java.util.List;

/**
 * Интерфейс реализующий способность
 *
 * @author Денис Висков
 * @version 1.0
 * @since 27.09.2020
 */
public interface UserStorage<V> {
    V addUser(V user);

    void updateUser(V user);

    List<V> getAllUser();
}
