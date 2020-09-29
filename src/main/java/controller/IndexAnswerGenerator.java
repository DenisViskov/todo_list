package controller;

import model.Task;
import model.User;
import org.json.JSONObject;
import persistence.Store;
import persistence.UserStorage;

import java.io.IOException;
import java.util.List;

/**
 * @author Денис Висков
 * @version 1.0
 * @since 29.09.2020
 */
public class IndexAnswerGenerator implements Answer<JSONObject> {

    private final Store store;
    private final String key;

    public IndexAnswerGenerator(Store store, String key) {
        this.store = store;
        this.key = key;
    }

    @Override
    public JSONObject toFormAnswer() {
        JSONObject json = new JSONObject();
        if (key.equals("on load page")) {
            try {
                json = getJSON(store.getNotDone());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (key.equals("get all tasks")) {
            try {
                json = getJSON(store.getAll());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return json;
    }

    /**
     * Method return ready json for send to client
     *
     * @return JSONObject
     * @throws IOException
     */
    private JSONObject getJSON(List<Task> tasks) throws IOException {
        UserStorage userStorage = (UserStorage) store;
        List<User> users = userStorage.getAllUser();
        JSONObject json = new JSONObject();
        tasks.forEach(task -> users.forEach(user -> {
            if (user.getTask().getId() == task.getId()) {
                json.put(user.getLogin(), task.getName());
            }
        }));
        return json;
    }

    @Override
    public void setLastOperation(boolean lastOperation) {

    }

    @Override
    public boolean isLastOperation() {
        return false;
    }
}
