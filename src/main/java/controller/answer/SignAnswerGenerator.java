package controller.answer;

import org.json.JSONObject;

/**
 * @author Денис Висков
 * @version 1.0
 * @since 29.09.2020
 */
public class SignAnswerGenerator implements Answer<JSONObject> {

    /**
     * Operation
     */
    private volatile boolean lastOperation;

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

    @Override
    public synchronized boolean isLastOperation() {
        return lastOperation;
    }

    @Override
    public synchronized void setLastOperation(boolean lastOperation) {
        this.lastOperation = lastOperation;
    }
}
