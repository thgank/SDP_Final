package SQL;
import java.sql.*;

public class CRUD {

    public static void main(String[] args) {
        createUser("babaa", "dadsad");
        createUser("babaa", "dadsad");
    }
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/sdp_final";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "mercytop38";
    private static void createUser(String name, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            // Check if the user already exists
            PreparedStatement checkStatement = conn.prepareStatement("SELECT Name FROM users WHERE Name = ?");
            checkStatement.setString(1, name);
            ResultSet resultSet = checkStatement.executeQuery();

            if (!resultSet.next()) { // User doesn't exist, proceed to insert
                PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO users (Name, Password) VALUES (?, ?)");
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, password);
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("User " + name + " inserted successfully");
                }
            } else {
                System.out.println("User " + name + " already exists");
            }
        } catch (SQLException e) {
            System.out.println("Create user error: " + e.getMessage());
        }
    }

    // Retrieve all users
    private static void selectUsers() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
            System.out.println("Users:");
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String password = resultSet.getString("Password");
                System.out.println("Name: " + name + ", Password: " + password);
            }
        } catch (SQLException e) {
            System.out.println("Select users error: " + e.getMessage());
        }
    }

    // Update user's password
    private static void updateUserPassword(String name, String newPassword) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement("UPDATE users SET Password = ? WHERE Name = ?")) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, name);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Password for user " + name + " updated successfully");
            } else {
                System.out.println("User " + name + " not found");
            }
        } catch (SQLException e) {
            System.out.println("Update user password error: " + e.getMessage());
        }
    }

    // Delete a user
    private static void deleteUser(String name) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM users WHERE Name = ?")) {
            preparedStatement.setString(1, name);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User " + name + " deleted successfully");
            } else {
                System.out.println("User " + name + " not found");
            }
        } catch (SQLException e) {
            System.out.println("Delete user error: " + e.getMessage());
        }
    }
}
