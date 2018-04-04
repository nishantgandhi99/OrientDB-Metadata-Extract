
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class AppRunner {

    public static void main(String[] args) {

        try {
            Class.forName("com.orientechnologies.orient.jdbc.OrientJdbcDriver");

            Properties info = new Properties();
            info.put("user", "admin");
            info.put("password", "admin");
            Connection conn = DriverManager.getConnection("jdbc:orient:remote:localhost/OpenBeer", info);//TO DO: OpenBeer will be configurable
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT from (select expand(classes) from metadata:schema) "
                    + "where \"V\" in superClass or \"E\" in superClass;");
            print(rs);
            rs.close();
            rs = stmt.executeQuery("SELECT name from (select expand(classes) from metadata:schema) "
                    + "where \"V\" in superClass;");
            ArrayList <String> tableList = new ArrayList<String>();
          while (!rs.isLast()) {
            rs.next();
            
                String string = rs.getString("name");
                tableList.add(string);
          }
           rs.close();
          for(String s : tableList){
              System.out.println(s);
              rs = stmt.executeQuery("select expand(properties) from (select expand(classes) from metadata:schema) "
                      + "where name = '"+s+"'");
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

                System.out.print(rs.getNString(col_name) + "|");
            }
            System.out.println();
        }
    }

}
