package controller.answer;

import org.junit.Test;
import org.mockito.Mockito;
import persistence.Store;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

public class IndexAnswerGeneratorTest {

    @Test
    public void toFormAnswerTest() {
        Store store = Mockito.mock(Store.class);
        IndexAnswerGenerator generator = new IndexAnswerGenerator(store,"on load page");

    }
}