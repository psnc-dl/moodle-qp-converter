package eu.accessitplus.moodle.mbz;

import java.io.IOException;

class MoodleBackupExtractorException extends RuntimeException {

    public MoodleBackupExtractorException(IOException e) {
        super(e);
    }
    
}
