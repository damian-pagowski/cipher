package ie.gmit.dip;


import java.io.*;

/*
Utility class - used to read from and write to file.
 */
public class FileUtil {

    /*
    reads file, returns content as string.
     */
    public String readFile(String pathToFile) throws IOException {
        FileInputStream fstream = new FileInputStream(pathToFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        StringBuffer content = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            String cleanedLine = line.replaceAll("[^A-Za-z]", "").toUpperCase();
            content.append(cleanedLine);
        }
        return content.toString();
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
