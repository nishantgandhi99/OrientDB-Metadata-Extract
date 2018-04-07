import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileWriter;
import java.util.ArrayList;

public class CSVUtils {

    final static Logger logger = LoggerFactory.getLogger(CSVUtils.class);
    final static String NEW_LINE_SEPARATOR = "\n";

    public static void writeToFile(String pathWithFileName, ArrayList<String> header, ArrayList<Dataset> rows, char separators) {
        try {
            FileWriter writer = new FileWriter(pathWithFileName);
            for(int i=0; i<header.size()-1;i++) {
                writer.append(header.get(i));
                writer.append(separators);
            }
            writer.append(header.get(header.size()-1));
            writer.append(NEW_LINE_SEPARATOR);

            for(int i=0; i<rows.size();i++) {
                writer.append(rows.get(i).database);
                writer.append(separators);
                writer.append(rows.get(i).entity);
                writer.append(separators);
                writer.append(rows.get(i).name);
                writer.append(separators);
                Gson gson = new Gson();
                String jsonProperties = gson.toJson(rows.get(i).properties);
                writer.append(jsonProperties);
                writer.append(NEW_LINE_SEPARATOR);
            }

            writer.flush();
            writer.close();

        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Not able to create file. Please provide valid path.");
        }

    }
}
