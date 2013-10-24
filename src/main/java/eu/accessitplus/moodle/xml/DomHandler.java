package eu.accessitplus.moodle.xml;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomHandler {

    private final Document document;

    public DomHandler(Document document) {
        this.document = document;
    }

    //todo it is a bit inconsistent that this method returns nodelist instead of list
    public NodeList getNodesWithName(String nodeName) {
        return document.getElementsByTagName(nodeName);
    }

    public Node getFirstChildNodeWithName(Node parent, String childName) {
        return getChildNodesWithGivenName(parent, childName).get(0);
    }

    public List<Node> getChildNodesWithGivenName(Node parent, String childName) {
        List<Node> nodes = new ArrayList<Node>();
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (childName.equals(child.getNodeName())) {
                nodes.add(child);
            }
        }
        return nodes;
    }

    public boolean checkIfNodeHasChildren(Node givenNode) {
        NodeList answersSubnodes = givenNode.getChildNodes();
        if (answersSubnodes.getLength() == 0) {
            return false;
        }
        if (answersSubnodes.getLength() == 1) {
            short nodeType = answersSubnodes.item(0).getNodeType();
            if (nodeType == Node.TEXT_NODE) {
                return false;
            }
        }
        return true;
    }

    public boolean checkIfNodeHasGivenContent(Node givenNode, String content) {
        String nodeContent = givenNode.getTextContent();
        return content.equals(nodeContent);
    }
}
