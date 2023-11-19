package Observer;
import java.util.List;
import java.util.ArrayList;

public class FlightUpdates implements Subject {
    private List<Observer> observers;

    public FlightUpdates() {
        observers = new ArrayList<>();
    }

    public void addFlight(String flightDetails) {
        notifyObservers(flightDetails);
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String flightDetails) {
        for (Observer observer : observers) {
            observer.update(flightDetails);
        }
    }
}
