import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

class AppRunner {

    public static void main(String[] args) {

        try {
            Class.forName("com.orientechnologies.orient.jdbc.OrientJdbcDriver");

            Properties info = new Properties();
            info.put("user", "admin");
            info.put("password", "admin");
            Connection conn = DriverManager.getConnection("jdbc:orient:plocal:OpenBeer", info);
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select expand(classes) from metadata:schema");

            while (!rs.isLast()) {
                rs.next();
                System.out.println(rs.getString("name"));
            }
            rs.close();
            stmt.close();


        } catch (Exception e) {
            System.out.println(e);
        }

    }


}