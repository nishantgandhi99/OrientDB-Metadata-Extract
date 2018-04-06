
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class AppRunner {

    public static void main(String[] args) {

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter Username");
            String username = sc.next();
            System.out.println("Enter Password");
            String pwd = sc.next();
            System.out.println("Enter Database name");
            String db = sc.next();

            Class.forName("com.orientechnologies.orient.jdbc.OrientJdbcDriver");

            Properties info = new Properties();
            info.put("user", username);
            info.put("password", pwd);
            Connection conn = DriverManager.getConnection("jdbc:orient:remote:localhost/" + db, info);//TO DO: OpenBeer will be configurable
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT from (select expand(classes) from metadata:schema) "
                    + "where \"V\" in superClass or \"E\" in superClass;");
            print(rs);
            rs.close();
            rs = stmt.executeQuery("SELECT name from (select expand(classes) from metadata:schema) "
                    + "where \"V\" in superClass;");
            ArrayList<String> tableList = new ArrayList<String>();
            while (!rs.isLast()) {
                rs.next();

                String string = rs.getString("name");
                tableList.add(string);
            }
            rs.close();
            for (String s : tableList) {
                System.out.println(s);
                rs = stmt.executeQuery("select expand(properties) from (select expand(classes) from metadata:schema) "
                        + "where name = '" + s + "'");
                print(rs);
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void print(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int colCount = md.getColumnCount();

        for (int i = 1; i <= colCount; i++) {

            String col_name = md.getColumnName(i);

            System.out.print(col_name + "|");
        }
        System.out.println();
        while (rs.next()) {

            for (int i = 1; i <= colCount; i++) {

                String col_name = md.getColumnName(i);
                if (col_name.equals("type")) {
                    System.out.print(type(rs.getNString(col_name)) + "|");
                } else {
                    System.out.print(rs.getNString(col_name) + "|");
                }
            }
            System.out.println();
        }
    }

    private static String type(String num) {
        switch (num) {
            case "0":
                return "Boolean";
            case "1":
                return "Integer";
            case "2":
                return "Short";
            case "3":
                return "Long";
            case "4":
                return "Float";
            case "5":
                return "Double";
            case "6":
                return "Datetime";
            case "7":
                return "String";
            case "8":
                return "Binary";
            case "9":
                return "Embedded";
            case "10":
                return "Embedded list";
            case "11":
                return "Embedded set";
            case "12":
                return "Embedded map";
            case "13":
                return "Link";
            case "14":
                return "Link list";
            case "15":
                return "Link set";
            case "16":
                return "Link map";
            case "17":
                return "Byte";
            case "18":
                return "Transient";
            case "19":
                return "Date";
            case "20":
                return "Custom";
            case "21":
                return "Decimal";
            case "22":
                return "LinkBag";
            default:
                return "Any";

        }
    }

}
