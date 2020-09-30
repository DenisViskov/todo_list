package controller.answer;

import model.Task;
import model.User;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;
import persistence.HbStore;
import persistence.Store;
import persistence.UserStorage;

import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

public class IndexAnswerGeneratorTest {

    @Test
    public void toFormAnswerOnLoadTest() {
        JSONObject expected = new JSONObject();
        expected.put("login", "name");
        HbStore store = Mockito.mock(HbStore.class);
        IndexAnswerGenerator generator = new IndexAnswerGenerator(store, "on load page");
        User user = new User(0,
                "login",
                "pass",
                new Task(0, "name", "desc", null, false));
        when(store.getNotDone()).thenReturn(List.of(new Task(0,
                "name",
                "desc",
                null,
                false)));
        when(store.getAllUser()).thenReturn(List.of(user));
        JSONObject jsonObject = generator.toFormAnswer();
        assertThat(jsonObject.toString(), is(expected.toString()));
    }

    @Test
    public void toFormAnsweralltasksTest() {
        JSONObject expected = new JSONObject();
        expected.put("login", "name");
        HbStore store = Mockito.mock(HbStore.class);
        IndexAnswerGenerator generator = new IndexAnswerGenerator(store, "get all tasks");
        User user = new User(0,
                "login",
                "pass",
                new Task(0, "name", "desc", null, true));
        when(store.getAll()).thenReturn(List.of(new Task(0,
                "name",
                "desc",
                null,
                true)));
        when(store.getAllUser()).thenReturn(List.of(user));
        JSONObject jsonObject = generator.toFormAnswer();
        assertThat(jsonObject.toString(), is(expected.toString()));
    }
}