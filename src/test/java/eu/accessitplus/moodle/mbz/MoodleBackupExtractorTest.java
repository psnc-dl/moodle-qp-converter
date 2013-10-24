package eu.accessitplus.moodle.mbz;

import static eu.accessitplus.FilesAssert.*;
import java.io.File;
import java.io.IOException;
import junitparams.JUnitParamsRunner;
import static junitparams.JUnitParamsRunner.$;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class MoodleBackupExtractorTest {

    public final static File ZIPPED_BACKUP = new File("./src/test/resources/zipped-backup.mbz");
    public final static int NO_OF_FILES_IN_ZIPPED_BACKUP = 79;
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    File workingDir;

    @Before
    public void prepareTestData() throws IOException {
        this.workingDir = tempFolder.newFolder("backup_folder");
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters(method = "incorrectInputParameters")
    public void shouldThrowIllegalArguemntExceptionForWrongInputParams(File moodleBackupFile, File destination) {
        MoodleBackupExtractor.extract(moodleBackupFile, destination);
    }

    @SuppressWarnings("unused")
    private Object incorrectInputParameters() {
        return $($(new File("./bleble12"), null),
                $(new File("./bleble12"), new File("./")),
                $(ZIPPED_BACKUP, null),
                $(null, null));
    }

    @Test
    public void shouldExtractContentOfMoodleBackup() throws IOException {
        //when
        MoodleBackupExtractor.extract(ZIPPED_BACKUP, workingDir);

        //then
        assertNumberOfFiles(workingDir, NO_OF_FILES_IN_ZIPPED_BACKUP);
    }
}
