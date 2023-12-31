package Singleton;
import Observer.FlightUpdates;
import SQL.CRUD;


public class FlightInformationSystem {
    private static volatile FlightInformationSystem instance;

    private FlightInformationSystem() {
    }

    public static FlightInformationSystem getInstance() {
        if (instance == null) {
            synchronized (FlightInformationSystem.class) {
                if (instance == null) {
                    instance = new FlightInformationSystem();
                }
            }
        }
        return instance;
    }

    public void createFlight(String flightNumber, String departure, String destination, String departureTime) {
        // Create the flight in the database using CRUD class
        CRUD.createFlight(flightNumber, departure, destination, departureTime);

        // Notify observers about the new flight created
        FlightUpdates flightUpdates = new FlightUpdates();
        flightUpdates.updateFlight("Flight " + flightNumber + " from " + departure + " to " + destination + " at " + departureTime);
    }

}
