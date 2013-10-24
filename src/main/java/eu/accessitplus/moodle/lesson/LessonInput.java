package eu.accessitplus.moodle.lesson;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class LessonInput {

    //public static final Map<String, Document> loadLessons
    //lesson file->document
    public static Document loadLesson(File lessonXML) {

        try {
            InputStream is = new FileInputStream(lessonXML);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(is);

            return dom;
        } catch (Exception e) {
            throw new LessonLoadingException(e);
        }

    }
}
