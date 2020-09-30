package controller.answer;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

public class RegAnswerGeneratorTest {

    @Test
    public void toFormAnswerTest() {
        JSONObject expected = new JSONObject();
        expected.put("user", "was added");
        RegAnswerGenerator regAnswerGenerator = new RegAnswerGenerator();
        regAnswerGenerator.setLastOperation(true);
        JSONObject out = regAnswerGenerator.toFormAnswer();
        assertThat(out.toString(), is(expected.toString()));
        regAnswerGenerator.setLastOperation(false);
        expected.remove("user");
        expected.put("user", "exist");
        out = regAnswerGenerator.toFormAnswer();
        assertThat(out.toString(), is(expected.toString()));
    }
}