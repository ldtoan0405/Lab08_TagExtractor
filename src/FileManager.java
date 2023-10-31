import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    public static void saveFile(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            writer.write(content);
            System.out.println("File saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving file.");
        }
    }
}
