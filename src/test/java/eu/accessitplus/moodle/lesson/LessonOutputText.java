package eu.accessitplus.moodle.lesson;

import java.io.File;
import java.io.IOException;
import static org.hamcrest.core.IsNot.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.w3c.dom.Document;

public class LessonOutputText {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void shouldSaveLessonXml() throws IOException {
        //given
        Document document = LessonInput.loadLesson(LessonInputTest.EXAMPLE_LESSON_FILE);
        File outFile = tempFolder.newFile("outputName.xml");

        //when
        LessonOutput.saveOutput(document, outFile);

        //then
        assertThat(outFile.length(), not(0l));
    }
    
    
    
}