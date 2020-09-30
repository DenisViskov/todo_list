package controller.answer;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

public class SignAnswerGeneratorTest {

    @Test
    public void toFormAnswerTest() {
        JSONObject expected = new JSONObject();
        expected.put("answer", true);
        SignAnswerGenerator signAnswerGenerator = new SignAnswerGenerator();
        signAnswerGenerator.setLastOperation(true);
        JSONObject out = signAnswerGenerator.toFormAnswer();
        assertThat(out.toString(), is(expected.toString()));
        signAnswerGenerator.setLastOperation(false);
        expected.remove("answer");
        expected.put("answer", false);
        out = signAnswerGenerator.toFormAnswer();
        assertThat(out.toString(), is(expected.toString()));
    }
}