package ie.gmit.dip;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {


    public String readFile(String pathToFile) throws IOException {
        return new String(Files.readAllBytes(Paths.get(pathToFile)));
    }


}
