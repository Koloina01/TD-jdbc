import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private final String url;
    private final String user;
    private final String password;

    public DBConnection() {
        this.url = "jdbc:mysql://localhost:5432/product_management_db";
        this.user = "product_management_user";
        this.password = "123456";
    }
    public Connection getDBConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
            return null;
        }
    }
}
