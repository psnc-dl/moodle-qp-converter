package eu.accessitplus.moodle.mbz;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.io.IOUtils;

public class MoodleBackupExtractor {

    public static void extract(File moodleBackupArchive, File workingDirFolderForBackup) {

        validateInputParams(moodleBackupArchive, workingDirFolderForBackup);

        ZipFile zipFile = createZipFile(moodleBackupArchive);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            InputStream in = null;
            OutputStream out = null;
            try {
                ZipEntry entry = entries.nextElement();
                File entryDestination = new File(workingDirFolderForBackup, entry.getName());
                if (entry.isDirectory()) {
                    entryDestination.mkdirs();
                } else {
                    entryDestination.getParentFile().mkdirs();

                    in = zipFile.getInputStream(entry);
                    out = new FileOutputStream(entryDestination);

                    IOUtils.copy(in, out);
                }
            } catch (Exception ex) {
                throw new MoodleBackupExporterException(ex);
            } finally {
                IOUtils.closeQuietly(in);
                IOUtils.closeQuietly(out);
            }
        }
    }

    private static void validateInputParams(File moodleBackupArchive, File workingDirFolderForBackup) {

        if (moodleBackupArchive == null || !moodleBackupArchive.exists()) {
            throw new IllegalArgumentException("Moodle backup file does not exist");
        }

        if (workingDirFolderForBackup == null || !workingDirFolderForBackup.isDirectory()) {
            throw new IllegalArgumentException("Destination folder must exist");
        }

    }

    private static ZipFile createZipFile(File moodleBackupArchive) {
        try {
            ZipFile result = new ZipFile(moodleBackupArchive);
            return result;
        } catch (IOException e) {
            throw new MoodleBackupExtractorException(e);
        }
    }
}
