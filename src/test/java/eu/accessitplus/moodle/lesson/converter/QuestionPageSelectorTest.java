package eu.accessitplus.moodle.lesson.converter;

import eu.accessitplus.moodle.lesson.LessonInput;
import eu.accessitplus.moodle.lesson.LessonInputTest;
import eu.accessitplus.moodle.xml.DomHandler;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class QuestionPageSelectorTest {

    @Test
    public void shouldSelectQuestionPagesForTransformation() {
        //given
        Document exampleDocument = LessonInput.loadLesson(LessonInputTest.EXAMPLE_LESSON_FILE);
        DomHandler handler = new DomHandler(exampleDocument);
        
        //when
        List<Node> questionPages = QuestionPageSelector.filter(handler);
                
        //then
        assertEquals(2, questionPages.size());
    }
    
}
