import Singleton.FlightInformationSystem;
import Observer.FlightUpdates;

public class Main {
    public static void main(String[] args) {
        FlightUpdates flightUpdates = new FlightUpdates();
        FlightInformationSystem infoSystem = FlightInformationSystem.getInstance();

        infoSystem.startMonitoringFlights(flightUpdates);

        flightUpdates.addFlight("Flight A");
        flightUpdates.addFlight("Flight B");

        infoSystem.showUpdatedFlights();
    }
}

