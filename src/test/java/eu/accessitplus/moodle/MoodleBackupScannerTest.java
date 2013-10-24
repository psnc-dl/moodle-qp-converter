package eu.accessitplus.moodle;

import java.io.File;
import java.util.List;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class MoodleBackupScannerTest {

    @Test(expected = RuntimeException.class)
    @Parameters({"./bleble12", 
        "./src/test/resources/backups/corrupted/", 
        "./src/test/resources/backups/corrupted/activities/"
    })
    public void shouldThrowExceptionWhenBackupFolderIsInvalid(String baseBackupDir) {
        //when
        MoodleBackupScanner mbs = new MoodleBackupScanner(new File(baseBackupDir));
    }

    @Test
    @Parameters({
         "./src/test/resources/backups/with_contentpage/, 2", 
         "./src/test/resources/backups/with_modifications/, 3"
    })
    public void shouldReturnListOfLessonFiles(String backupFolder, int numberOfPages) {
        //given
        MoodleBackupScanner bqpScanner = new MoodleBackupScanner(new File(backupFolder));

        //when
        List<File> lessonFiles = bqpScanner.getLessonFiles();

        //then
        assertThat(lessonFiles.size(), is(numberOfPages));
    }
    
}
