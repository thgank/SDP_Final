package Factory_and_Decorator;

public class BusinessTicket implements AirlineTicket{

    @Override
    public void book() {
        System.out.println("Booking a Business Ticket.");
    }

    @Override
    public void cancel() {
        System.out.println("Cancelling a Business Ticket.");
    }
    @Override
    public String getDescription() {
        return "Ticket Description: Business Ticket";
    }

    @Override
    public double getCost() {
        return 200;
    }
}
