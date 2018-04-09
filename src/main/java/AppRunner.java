import org.apache.commons.cli.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class AppRunner {

    private static String DRIVER_NAME = "com.orientechnologies.orient.jdbc.OrientJdbcDriver";
    private static String CONNECTION_STRING = "jdbc:orient:remote";

    public static void main(String[] args) throws Exception {

        CommandLine cmd = ConfigurationUtils.parseArgs(args);
        if (null == cmd) {
            return;
        }

        Class.forName(DRIVER_NAME);

        Properties info = new Properties();
        info.put("user", cmd.getOptionValue("u"));
        info.put("password", cmd.getOptionValue("p"));

        Connection conn = DriverManager.getConnection(CONNECTION_STRING + ":" + cmd.getOptionValue("h") +
                "/" + cmd.getOptionValue("d"), info);

        ArrayList<String> header = new ArrayList<>();
        header.add("Database");
        header.add("Entity");
        header.add("Name");
        header.add("PropertyName");
        header.add("PropertyValue");
        ArrayList<Dataset> datasets = new ArrayList<>();

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT from (select expand(classes) from metadata:schema) "
                + "where \"V\" in superClass or \"E\" in superClass;");

        while (rs.next()) {
            Dataset data = new Dataset(cmd.getOptionValue("d"), rs.getString("superClass"), rs.getString("name"));

            datasets.add(data);
        }


        for (int i = 0; i < datasets.size(); i++) {
            rs = stmt.executeQuery("select expand(properties) from (select expand(classes) from metadata:schema) "
                    + "where name = '" + datasets.get(i).name + "'");
            while (rs.next()) {
                datasets.get(i).addProperty(rs.getString("name"), DBUtils.type(rs.getString("type")));
            }
            System.out.println(datasets.get(i));
        }
        CSVUtils.writeToFile(cmd.getOptionValue("e"), header, datasets, ',');
    }
}