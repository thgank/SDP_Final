package SQL;
import java.sql.*;

public class CRUD {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/name_of_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "password";

    public static void createUsersTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            if (conn != null) {
                System.out.println("Connected to the PostgreSQL database!");

                // Create a table query
                String createTableSQL = "CREATE TABLE IF NOT EXISTS users (Name TEXT, Password TEXT)";

                try (Statement statement = conn.createStatement()) {
                    // Execute the query to create the table
                    statement.execute(createTableSQL);
                    System.out.println("Table 'users' created or already created!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Table creation error: " + e.getMessage());
        }
    }

    public static void createFlightsTable() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            String createTableSQL = "CREATE TABLE IF NOT EXISTS flights (" +
                    "id SERIAL PRIMARY KEY," +
                    "flight_number VARCHAR(50) NOT NULL," +
                    "departure VARCHAR(100) NOT NULL," +
                    "destination VARCHAR(100) NOT NULL," +
                    "departure_time VARCHAR NOT NULL" +
                    ")";

            statement.execute(createTableSQL);
            System.out.println("flights table created or already exists!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void createUser(String name, String password) {
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
    public static void selectUsers() {
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
    public static void updateUserPassword(String name, String newPassword) {
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
    public static void deleteUser(String name) {
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
    public static void loginUser(String name, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            PreparedStatement checkStatement = conn.prepareStatement("SELECT * FROM users WHERE Name = ? AND Password = ?");
            checkStatement.setString(1, name);
            checkStatement.setString(2, password);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("User " + name + " logged in successfully");
            } else {
                System.out.println("Invalid username or password");
            }
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }
    }
    public static void createFlight(String flightNumber, String departure, String destination, String departureTime) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO flights (flight_number, departure, destination, departure_time) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, flightNumber);
            preparedStatement.setString(2, departure);
            preparedStatement.setString(3, destination);
            preparedStatement.setString(4, departureTime);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Flight created successfully");
            }
        } catch (SQLException e) {
            System.out.println("Create flight error: " + e.getMessage());
        }
    }

    public static void readFlights() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM flights")) {
            System.out.println("Flights:");
            while (resultSet.next()) {
                String flightNumber = resultSet.getString("flight_number");
                String departure = resultSet.getString("departure");
                String destination = resultSet.getString("destination");
                String departureTime = resultSet.getString("departure_time");
                System.out.println("Flight Number: " + flightNumber + ", Departure: " + departure + ", Destination: " + destination + ", Departure Time: " + departureTime);
            }
        } catch (SQLException e) {
            System.out.println("Read flights error: " + e.getMessage());
        }
    }
    public static void getFlightByNumber(String flightNumber) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String query = "SELECT * FROM flights WHERE flight_number = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, flightNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String departure = resultSet.getString("departure");
                String destination = resultSet.getString("destination");
                String departureTime = resultSet.getString("departure_time");

                System.out.println("Flight Number: " + flightNumber + ", Departure: " + departure +
                        ", Destination: " + destination + ", Departure Time: " + departureTime);
            } else {
                System.out.println("Flight with number " + flightNumber + " not found.");
            }
        } catch (SQLException e) {
            System.out.println("Get flight by number error: " + e.getMessage());
        }
    }
}
