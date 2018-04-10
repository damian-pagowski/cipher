package ie.gmit.dip;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
Utility class - used to read from and write to file.
 */
public class FileUtil {


    /*
    reads file, returns content as string.
     */
    public String readFile(String pathToFile) throws IOException {
        return new String(Files.readAllBytes(Paths.get(pathToFile)));
    }

    /*
    writes string to file.
     */
    public void writeToFile(String text, String pathToFile) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile));
        writer.write(text);
        writer.close();
    }

}
