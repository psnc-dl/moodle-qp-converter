package eu.accessitplus.moodle;

import eu.accessitplus.moodle.lesson.LessonInput;
import eu.accessitplus.moodle.lesson.LessonOutput;
import eu.accessitplus.moodle.lesson.converter.QuestionPageConverter;
import eu.accessitplus.moodle.lesson.converter.QuestionPageSelector;
import eu.accessitplus.moodle.mbz.MoodleBackupExporter;
import eu.accessitplus.moodle.mbz.MoodleBackupExtractor;
import eu.accessitplus.moodle.xml.DomHandler;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class App {

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) throws IOException {

        Configuration configuration = null;
        if (args == null || args.length == 0) {
            configuration = new Configuration();
            logger.info("Default configuration was loaded");
        } else {
            configuration = new Configuration(args[0]);
            logger.log(Level.INFO, "Custom configuration was loaded from {0}", args[0]);
        }

        logger.info(configuration.toString());
        
        File outputFile = configuration.getOutputFile();
        File inputFile = configuration.getInputFile();
        
        File workingCopyFolder = App.prepareWorkingCopy(inputFile,configuration);

        MoodleBackupScanner backupScanner = new MoodleBackupScanner(workingCopyFolder);
        List<File> lessonFiles = backupScanner.getLessonFiles();

        QuestionPageConverter converter = new QuestionPageConverter(configuration);
        for (File lessonFile : lessonFiles) {
            Document dom = LessonInput.loadLesson(lessonFile);
            DomHandler domHandler = new DomHandler(dom);
            List<Node> questionPagesNodes = QuestionPageSelector.filter(domHandler);
            for (Node questionPage : questionPagesNodes) {
                converter.processPage(questionPage, domHandler);
            }
            LessonOutput.saveOutput(dom, lessonFile);
            logger.log(Level.INFO, "Saved changes in {0}", lessonFile);
        }
        
        MoodleBackupExporter.compress(workingCopyFolder, outputFile);
        
        logger.info("Conversion finished successfuly");
    }

    public static File prepareWorkingCopy(File backupFile, Configuration configuration) throws IOException {
        File workspaceLocation = configuration.getTempDir();
        File workingCopyFolder = new File(workspaceLocation, backupFile.getName());
        workingCopyFolder.mkdirs();
        MoodleBackupExtractor.extract(backupFile, workingCopyFolder);
        return workingCopyFolder;
    }
}
