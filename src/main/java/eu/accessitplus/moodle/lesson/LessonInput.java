package eu.accessitplus.moodle.lesson;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;

public class LessonInput {

    public static Document loadLesson(File lessonXML) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(lessonXML);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(inputStream);

            return dom;
        } catch (Exception e) {
            throw new LessonLoadingException(e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
