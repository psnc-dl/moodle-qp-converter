package eu.accessitplus.moodle.lesson;

import java.io.File;
import static org.hamcrest.core.IsNull.*; 
import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.rules.TemporaryFolder;
import org.w3c.dom.Document;

public class LessonInputTest {

    public final static File EXAMPLE_LESSON_FILE = new File("./src/test/resources/lesson.xml");
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test(expected = LessonLoadingException.class)
    public void shouldThrowExceptionWhenLessonDoesNotExist() {
        //given
        File fakeFile = new File("input" + System.currentTimeMillis() + ".xml");

        //when
        LessonInput.loadLesson(fakeFile);
    }

    @Test(expected = LessonLoadingException.class)
    public void shouldThrowExceptionWhenLessonFileIsEmpty() throws IOException {
        //given
        File notAnXmlFile = tempFolder.newFile("input.xml");
                
        //when
        LessonInput.loadLesson(notAnXmlFile);
    }
    
    @Test
    public void shouldParseValidXml() {
        
        //when
        Document document = LessonInput.loadLesson(EXAMPLE_LESSON_FILE);
        
        //then
        assertThat(document, notNullValue());
    }
    
}