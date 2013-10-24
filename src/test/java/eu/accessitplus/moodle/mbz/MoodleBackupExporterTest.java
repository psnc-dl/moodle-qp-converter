package eu.accessitplus.moodle.mbz;

import java.io.File;
import java.io.IOException;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import static junitparams.JUnitParamsRunner.*;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class MoodleBackupExporterTest {

    public final static File EXAMPLE_BACKUP_FOR_COMPRESSION = new File("./src/test/resources/backups/with_contentpage/");
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    File workingDir;

    @Test(expected = IllegalArgumentException.class)
    @Parameters(method = "incorrectInputValues")
    public void shouldThrowExceptionForWrongInputParams(File backupFolder, File destFile) {
        //when
        MoodleBackupExporter.compress(backupFolder,destFile);
    }

    @SuppressWarnings("unused")
    private Object incorrectInputValues() { 
        return $($(new File("./bleble12"), null),
                 $(new File("./src/test/resources/backups/corrupted/"), null),
                 $(null, null)
                );
    }
    
    @Test
    public void shouldCompressEntireFolder() throws IOException, Exception {
        //given
        File destFile = tempFolder.newFile("moodle_backup.mbz");

        //when
        MoodleBackupExporter.compress(EXAMPLE_BACKUP_FOR_COMPRESSION, destFile);

        //then
        assertTrue(destFile.length() > 0);
    }
}
