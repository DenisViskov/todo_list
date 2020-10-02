package persistence;

/**
 * Interface of category
 *
 * @author Денис Висков
 * @version 1.0
 * @since 02.10.2020
 */
public interface CategoryStore<V> {
    /**
     * Method should return category by given id
     *
     * @param id
     * @return V
     */
    V getCategory(int id);
}
