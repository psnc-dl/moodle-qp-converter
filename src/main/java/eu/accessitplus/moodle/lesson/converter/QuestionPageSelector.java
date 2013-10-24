package eu.accessitplus.moodle.lesson.converter;

import eu.accessitplus.moodle.xml.DomHandler;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class QuestionPageSelector {

    public static List<Node> filter(DomHandler domHandler) {

        List<Node> selectedNodes = new ArrayList<Node>();

        NodeList pages = domHandler.getNodesWithName("page");
        for (int i = 0; i < pages.getLength(); i++) {
            Node page = pages.item(i);

            Node answers = domHandler.getFirstChildNodeWithGivenName(page, "answers");
            boolean answersNodeHaveChildren = domHandler.checkIfNodeHaveChildren(answers);

            Node qtype = domHandler.getFirstChildNodeWithGivenName(page, "qtype");
            boolean hasCorrectQType = domHandler.checkIfNodeHasGivenContent(qtype, "3");

            if (hasCorrectQType && !answersNodeHaveChildren) {
                selectedNodes.add(page);
            }
        }
        return selectedNodes;
    }
    
}
