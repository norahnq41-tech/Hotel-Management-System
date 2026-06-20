
package projectjava;
/**
 * Utility service component dedicated to standard File Input (I/O) handling.
 * Streamlines system file reading routines and provides structural safety barriers 
 * for external database/text record retrievals.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MyFileReader {

    private Scanner input;

    public void openTextFile(String fileName) {
        try {
            input = new Scanner(new File(fileName));
        } catch (FileNotFoundException ex) {
            System.err.println("Error opening file.");
        }
    }

    public void readFromFile() {
        try {
            while (input.hasNextLine()) {
                System.out.println(input.nextLine());
            }
        } catch (NoSuchElementException ex) {
            System.err.println("File improperly formed.");
        } catch (IllegalStateException ex) {
            System.err.println("Error reading from file.");
        }
    }

    public void closeFile() {
        if (input != null) {
            input.close();
        }
    }
}