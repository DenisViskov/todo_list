package controller.answer;

/**
 * Interface of Answer
 *
 * @author Денис Висков
 * @version 1.0
 * @since 29.09.2020
 */
public interface Answer<V> {
    /**
     * Method should form answer from server
     *
     * @return V
     */
    V toFormAnswer();

    /**
     * Method should set up flag
     *
     * @param lastOperation
     */
    void setLastOperation(boolean lastOperation);
}
