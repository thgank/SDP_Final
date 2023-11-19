package Factory_and_Decorator.FlightFactory;

public class FlightFactory {
    public Flight createFlight(String flightDetails){
        return new Flight(flightDetails);
    }
}
