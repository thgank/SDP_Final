package Factory.FlightFactory;

public class FlightFactory {
    public Flight createFlight(String flightDetails){
        return new Flight(flightDetails);
    }


}
