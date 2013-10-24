package eu.accessitplus.moodle.xml;

import eu.accessitplus.moodle.lesson.LessonInput;
import eu.accessitplus.moodle.lesson.LessonInputTest;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomHandlerTest {

    static final int NUMBER_PAGE_TAGS_IN_EXAMPLE_DOCUMENT = 2;
    static Document exampleDocument;
    DomHandler testedHandler;

    @BeforeClass
    public static void initializeExampleDocument() {
        exampleDocument = LessonInput.loadLesson(LessonInputTest.EXAMPLE_LESSON_FILE);
    }

    @Before
    public void initializeDomHandler() {
        testedHandler = new DomHandler(exampleDocument);
    }

    @Test
    public void shouldReturnListOfNodesWithGivenName() {

        //when
        NodeList nodes = testedHandler.getNodesWithName("page");

        //then
        assertEquals(NUMBER_PAGE_TAGS_IN_EXAMPLE_DOCUMENT, nodes.getLength());
    }

    @Test
    public void shouldGetChildNodesWithGivenName() {
        //given
        Node parent = testedHandler.getNodesWithName("page").item(0);

        //when
        List<Node> childNodes = testedHandler.getChildNodesWithGivenName(parent, "qtype");

        //then
        assertEquals(1, childNodes.size());
    }

    @Test
    public void shouldCheckIfNodeHaveChildren() {
        //given
        Node parent = testedHandler.getNodesWithName("page").item(0);

        //when
        boolean result = testedHandler.checkIfNodeHaveChildren(parent);

        //then
        assertTrue(result);
    }

    @Test
    public void shouldCheckIfNodeDoesNotHaveChildren() {
        //given
        Node parent = testedHandler.getNodesWithName("branches").item(0);

        //when
        boolean result = testedHandler.checkIfNodeHaveChildren(parent);

        //then
        assertFalse(result);
    }
}
