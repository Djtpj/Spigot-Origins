import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        ArrayList<String> results = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(new File(Main.class.getResource("materials.txt").toURI())));
        String line = reader.readLine();

        while (line != null) {
            String result = line.split("\\(")[0];
            results.add("\"" + result.trim() + "\",\n");

            // read next line
            line = reader.readLine();
        }

        reader.close();

        StringBuilder builder = new StringBuilder();

        results.forEach(builder::append);

        writeToFile(builder.toString(), new File("results.txt"));
    }

    private static void writeToFile(String content, File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(content);

        writer.close();
    }
}
