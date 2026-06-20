
package projectjava;
/**
 * Utility service component managing data persistence via File Output (I/O).
 * Safely writes text receipts, run time confirmations, and final billing statements 
 * directly into physical storage logs.
 */
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.FormatterClosedException;

public class MyFileWriter {

    private Formatter output;

    public void openTextFile(String fileName) {
        try {
            output = new Formatter(fileName);
        } catch (SecurityException ex) {
            System.err.println("You do not have write access to this file.");
        } catch (FileNotFoundException ex) {
            System.err.println("Error opening or creating file.");
        }
    }

    public void writeToFile(String content) {
        try {
            output.format(content + "\n============================\n");
        } catch (FormatterClosedException ex) {
            System.err.println("Error writing to file");
        }
    }

    public void closeFile() {
        if (output != null) {
            output.close();
        }
    }
}