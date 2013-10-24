package eu.accessitplus.moodle.lesson.converter;

import eu.accessitplus.moodle.Configuration;
import eu.accessitplus.moodle.xml.DomHandler;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class QuestionPageConverter {

    final static String ANSWERS_PART = "<answer id=\"%s\">\n"
            + "<jumpto>-1</jumpto>\n"
            + "<grade>0</grade>\n"
            + "<score>0</score>\n"
            + "<flags>0</flags>\n"
            + "<timecreated>%s</timecreated>\n"
            + "<timemodified>0</timemodified>\n"
            + "<answer_text>%s</answer_text>\n"
            + "<response>$@NULL@$</response>\n"
            + "<answerformat>0</answerformat>\n"
            + "<responseformat>0</responseformat>\n"
            + "<attempts>\n"
            + "</attempts>\n"
            + "</answer>";
    final Configuration configuration;
    long seq = 0;

    public QuestionPageConverter(Configuration configuration) {
        this.configuration = configuration;
        seq = configuration.getInitialSeq();
    }

    public Node processPage(Node questionPage, DomHandler domHandler) {

        Node qtype = domHandler.getFirstChildNodeWithName(questionPage, "qtype");
        qtype.setTextContent("20");

        Node layout = domHandler.getFirstChildNodeWithName(questionPage, "layout");
        layout.setTextContent("1");

        Node display = domHandler.getFirstChildNodeWithName(questionPage, "display");
        display.setTextContent("1");

        Node answers = domHandler.getFirstChildNodeWithName(questionPage, "answers");
        appendAnswersNode(answers);

        return questionPage;
    }

    private String getTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    private long getNextInSeq() {
        return ++seq;
    }

    private void appendAnswersNode(Node parent) throws QuestionPageConversionException {
        try {
            Document doc = parent.getOwnerDocument();
            DocumentBuilder docBuilder = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder();
            Node fragmentNode = docBuilder.parse(
                    new InputSource(new StringReader(getAnswerTagContent())))
                    .getDocumentElement();
            fragmentNode = doc.importNode(fragmentNode, true);
            parent.appendChild(fragmentNode);
        } catch (Exception e) {
            throw new QuestionPageConversionException(e);
        }
    }

    String getAnswerTagContent() {
        return String.format(ANSWERS_PART, getNextInSeq(),
                getTimestamp(), configuration.getContinueLabel());
    }
}
