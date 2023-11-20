package SQL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static void main(String[] args) {
        String dbUrl = "jdbc:postgresql://localhost:5432/sdp_final";
        String username = "postgres";
        String password = "mercytop38";

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password)) {
            if (conn != null) {
                System.out.println("Connected to the PostgreSQL database!");

                // Create a table query
                String createTableSQL = "CREATE TABLE IF NOT EXISTS users (Name TEXT, Password TEXT)";

                try (Statement statement = conn.createStatement()) {
                    // Execute the query to create the table
                    statement.execute(createTableSQL);
                    System.out.println("Table 'users' created successfully");
                }
            }
        } catch (SQLException e) {
            System.out.println("Table creation error: " + e.getMessage());
        }
    }
}
