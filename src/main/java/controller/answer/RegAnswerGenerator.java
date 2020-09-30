package controller.answer;

import org.json.JSONObject;

/**
 * CLass is registration answer generator
 *
 * @author Денис Висков
 * @version 1.0
 * @since 29.09.2020
 */
public class RegAnswerGenerator implements Answer<JSONObject> {
    /**
     * Operation
     */
    private volatile boolean lastOperation;

    /**
     * Method returns ready json for send to client
     *
     * @return JSONObject
     */
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
