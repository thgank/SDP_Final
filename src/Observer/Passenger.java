package Observer;

public class Passenger implements Observer{
    private String name;

    public Passenger(String name){
        this.name = name;
    }

    @Override
    public void update(String flightDetails) {
        System.out.println(name + " received an update");
    }
}
