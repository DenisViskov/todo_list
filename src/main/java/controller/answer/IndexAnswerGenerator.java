package controller.answer;

import model.Task;
import model.User;
import org.json.JSONObject;
import persistence.TaskStore;
import persistence.UserStorage;

import java.io.IOException;
import java.util.List;

/**
 * Class is an Index answer generator
 *
 * @author Денис Висков
 * @version 1.0
 * @since 29.09.2020
 */
public class IndexAnswerGenerator implements Answer<JSONObject> {

    /**
     * Store
     */
    private final TaskStore taskStore;
    /**
     * Key
     */
    private final String key;

    public IndexAnswerGenerator(TaskStore taskStore, String key) {
        this.taskStore = taskStore;
        this.key = key;
    }

    /**
     * Method of generate answer
     *
     * @return JSONObject
     */
    @Override
    public JSONObject toFormAnswer() {
        JSONObject json = new JSONObject();
        if (key.equals("on load page")) {
            try {
                json = getJSON(taskStore.getNotDone());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (key.equals("get all tasks")) {
            try {
                json = getJSON(taskStore.getAll());
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
        UserStorage userStorage = (UserStorage) taskStore;
        List<User> users = userStorage.getAllUser();
        JSONObject json = new JSONObject();
        tasks.forEach(task -> users.forEach(user -> {
            if (user.getTask().getId() == task.getId()) {
                json.put(user.getLogin(), task.getName());
            }
        }));
        return json;
    }

    /**
     * Setter
     *
     * @param lastOperation
     */
    @Override
    public void setLastOperation(boolean lastOperation) {
    }
}
