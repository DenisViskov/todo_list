package controller.answer;

import org.json.JSONObject;

/**
 * Class is a sign answer generator
 *
 * @author Денис Висков
 * @version 1.0
 * @since 29.09.2020
 */
public class SignAnswerGenerator implements Answer<JSONObject> {

    /**
     * Operation
     */
    private volatile boolean lastOperation;

    /**
     * Method return ready json for send to client
     *
     * @return JSONObject
     */
    @Override
    public synchronized JSONObject toFormAnswer() {
        JSONObject json = new JSONObject();
        if (lastOperation) {
            json.put("answer", true);
        }
        if (!lastOperation) {
            json.put("answer", false);
        }
        lastOperation = false;
        return json;
    }

    /**
     * Setter
     *
     * @param lastOperation
     */
    @Override
    public synchronized void setLastOperation(boolean lastOperation) {
        this.lastOperation = lastOperation;
    }
}
