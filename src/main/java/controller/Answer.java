package controller;

/**
 * Интерфейс реализующий способность
 *
 * @author Денис Висков
 * @version 1.0
 * @since 29.09.2020
 */
public interface Answer<V> {
    V toFormAnswer();
    void setLastOperation(boolean lastOperation);
    boolean isLastOperation();
}
