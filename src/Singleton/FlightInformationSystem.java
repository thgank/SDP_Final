package Singleton;

import Observer.FlightUpdates;
import Observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class FlightInformationSystem {
    private static FlightInformationSystem instance;
    private List<String> currentFlights;
    private boolean updated;

    private FlightInformationSystem() {
        currentFlights = new ArrayList<>();
        updated = false;
    }

    public static FlightInformationSystem getInstance() {
        if (instance == null) {
            instance = new FlightInformationSystem();
        }
        return instance;
    }

    public void startMonitoringFlights(FlightUpdates flightUpdates) {
        flightUpdates.registerObserver(new FlightObserver());
    }

    public void showUpdatedFlights() {
        if (!updated) {
            showFlightInformation();
            updated = true;
        }
    }

    private class FlightObserver implements Observer {
        @Override
        public void update(String flightDetails) {
            currentFlights.add(flightDetails);
        }
    }

    private void showFlightInformation() {
        System.out.println("Current Flights:");
        for (String flight : currentFlights) {
            System.out.println("- " + flight);
        }
    }
}
