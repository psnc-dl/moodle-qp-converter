package eu.accessitplus.moodle.lesson.converter;

import eu.accessitplus.moodle.Configuration;
import eu.accessitplus.moodle.ConfigurationTest;
import eu.accessitplus.moodle.lesson.LessonInput;
import eu.accessitplus.moodle.lesson.LessonInputTest;
import eu.accessitplus.moodle.xml.DomHandler;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;
import static org.junit.matchers.JUnitMatchers.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class QuestionPageConverterTest {
    
    List<Node> questionPages;
    DomHandler handler;
    Document exampleDocument;
    QuestionPageConverter converter;
    
    @Before
    public void initialize() {
        this.exampleDocument = LessonInput.loadLesson(LessonInputTest.EXAMPLE_LESSON_FILE);
        this.handler = new DomHandler(exampleDocument);
        this.questionPages = QuestionPageSelector.filter(handler);
        this.converter = new QuestionPageConverter(new Configuration(ConfigurationTest.TEST_CONFIGURATION_FILE));
    }
    
    @Test
    public void shouldChangeQTypeTo20() {
        //given
        Node questionPage = this.questionPages.get(0);
        
        //when
        converter.processPage(questionPage, handler);
        
        //then
        Node qtype = handler.getFirstChildNodeWithGivenName(questionPage, "qtype");
        assertTrue(handler.checkIfNodeHasGivenContent(qtype, "20"));
    }
    
    @Test
    public void shouldChangeLayoutTypeTo1() {
        //given
        Node questionPage = this.questionPages.get(0);
        
        //when
        converter.processPage(questionPage, handler);
        
        //then
        Node layout = handler.getFirstChildNodeWithGivenName(questionPage, "layout");
        assertTrue(handler.checkIfNodeHasGivenContent(layout, "1"));
    }
    
    @Test
    public void shouldChangeDisplayTo1() {
        //given
        Node questionPage = this.questionPages.get(0);
        
        //when
        converter.processPage(questionPage, handler);
        
        //then
        Node display = handler.getFirstChildNodeWithGivenName(questionPage, "display");
        assertTrue(handler.checkIfNodeHasGivenContent(display, "1"));
    }
    
    @Test
    public void shouldAddAnswersSection() {
        //given
        Node questionPage = this.questionPages.get(0);
        
        //when
        converter.processPage(questionPage, handler);

        //then
        Node answers = handler.getFirstChildNodeWithGivenName(questionPage, "answers");
        assertTrue(handler.checkIfNodeHaveChildren(answers));
    }
    
    @Test
    public void shouldConvertMultipleQuestionPages() {
        
        //when
        for (Node questionPage : this.questionPages) {
            converter.processPage(questionPage, handler);
        }
        
        //then
        NodeList convertedAnswersNode = handler.getNodesWithName("answers");
        for (int i=0; i<convertedAnswersNode.getLength();i++) {
            Node answers = convertedAnswersNode.item(i);
            Boolean result = Boolean.valueOf(handler.checkIfNodeHaveChildren(answers));
            assertThat(result, is(Boolean.TRUE));
        }
        
    }

    @Test
    public void shouldGenerateAnswersContentWithSeqNo() {
        //when
        String answersContent = converter.getAnswerTagContent();
        //then
        assertThat(answersContent, containsString("<answer id=\"2\">"));
    }
    
    @Test
    public void shouldGenerateDifferentSeqIdForEachNewAnswerSection(){
        //when
        String answersContent1 = converter.getAnswerTagContent();
        String answersContent2 = converter.getAnswerTagContent();
        
        //then
        assertThat(answersContent1, containsString("<answer id=\"2\">"));
        assertThat(answersContent2, containsString("<answer id=\"3\">"));
    }

    @Test
    public void shouldGenerateCustomContinueText() {
        //when
        String answersContent = converter.getAnswerTagContent();

        //then
        assertThat(answersContent, containsString("Kontynuuj"));
    }
}
