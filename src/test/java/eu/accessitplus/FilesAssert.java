package eu.accessitplus;

import java.io.File;
import java.util.Collection;
import static org.junit.Assert.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

public class FilesAssert {

    /**
     * @param folder - folder containing files
     * @param numberOfFiles - expected number of files in given folder
     */
    public static void assertNumberOfFiles(File folder, int numberOfFiles) {
        Collection<File> files = FileUtils.listFiles(folder, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        if (numberOfFiles != files.size()) {
            fail("Excepted " + numberOfFiles + " files but found " + files.size());
        }
    }
    
    public static Matcher<File> exists() {
        return new TypeSafeMatcher<File>() {
            File fileTested;
 
            @Override
            public boolean matchesSafely(File item) {
                fileTested = item;
                return item.exists();
            }
 
            @Override
            public void describeTo(Description description) {
                description.appendText(" file ");
                description.appendValue(fileTested);
                description.appendText(" exists");
            }
        };
    }
}
