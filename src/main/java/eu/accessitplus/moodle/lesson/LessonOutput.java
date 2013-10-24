package eu.accessitplus.moodle.lesson;

import java.io.File;
import java.io.FileOutputStream;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;

public class LessonOutput {

    public static void saveOutput(Document document, File file) {
        FileOutputStream outputStream = null;
        try {
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            outputStream = new FileOutputStream(file);
            Result result = new StreamResult(outputStream);
            Source source = new DOMSource(document);

            xformer.setOutputProperty(
                    OutputKeys.STANDALONE, "yes");
            xformer.setOutputProperty(OutputKeys.INDENT, "yes");
    
            xformer.transform(source, result);
        } catch (Exception e) {
            throw new LessonSavingException(e);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }

    }
}
