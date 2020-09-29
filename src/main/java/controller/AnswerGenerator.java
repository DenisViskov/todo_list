package controller;

import org.json.JSONObject;

import java.util.List;

/**
 * @author Денис Висков
 * @version 1.0
 * @since 29.09.2020
 */
public class AnswerGenerator implements Answer<JSONObject> {
    /**
     * Operation
     */
    private volatile boolean lastOperation = false;

    @Override
    public synchronized JSONObject toFormAnswer(String request) {
        JSONObject result = new JSONObject();
        if (request.equals("Registration Answer")) {
            result = regAnswer();
        }
        return result;
    }

    private synchronized JSONObject regAnswer() {
        JSONObject json = new JSONObject();
        if (lastOperation) {
            json.put("user", "was added");
        }
        if (!lastOperation) {
            json.put("user", "exist");
        }
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
