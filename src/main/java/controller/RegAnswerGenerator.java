package controller;

import org.json.JSONObject;

import java.util.List;

/**
 * @author Денис Висков
 * @version 1.0
 * @since 29.09.2020
 */
public class RegAnswerGenerator implements Answer<JSONObject> {
    /**
     * Operation
     */
    private volatile boolean lastOperation;

    @Override
    public synchronized JSONObject toFormAnswer() {
        JSONObject json = new JSONObject();
        if (lastOperation) {
            json.put("user", "was added");
        }
        if (!lastOperation) {
            json.put("user", "exist");
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
