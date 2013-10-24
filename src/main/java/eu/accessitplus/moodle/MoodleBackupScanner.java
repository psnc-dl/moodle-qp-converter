package eu.accessitplus.moodle;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MoodleBackupScanner {

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String ACTIVITIES_FOLDER_NAME = "activities";
    final File backupBaseDir;

    public MoodleBackupScanner(File backupBaseDir) {
        this.backupBaseDir = backupBaseDir;
        validateBackupFolder();
    }

    private void validateBackupFolder() {
        if (backupBaseDir == null || !backupBaseDir.exists() || !backupBaseDir.isDirectory()) {
            throw new RuntimeException("Given backup dir " + backupBaseDir + " does not exist");
        }

        File activitiesFolder = new File(backupBaseDir, ACTIVITIES_FOLDER_NAME);
        if (!activitiesFolder.exists() || !activitiesFolder.isDirectory()) {
            throw new RuntimeException("Activities folder does not exist in " + backupBaseDir);
        }

        if (activitiesFolder.list().length == 0) {
            throw new RuntimeException("Activities folder is empty");
        }
    }

    public List<File> getLessonFiles() {

        File activitiesFolder = new File(backupBaseDir, ACTIVITIES_FOLDER_NAME);
        File[] lessonsFolders = activitiesFolder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith("lesson");
            }
        });

        List<File> lessonFiles = new ArrayList<File>();
        for (File lessonFolder : lessonsFolders) {
            File lessonFile = new File(lessonFolder, "lesson.xml");
            if (!lessonFile.exists()) {
                logger.log(Level.INFO, "lesson file not found in {0}", lessonFile);
            } else {
                lessonFiles.add(lessonFile);
            }
        }
        return lessonFiles;
    }
}
