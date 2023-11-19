package Factory_and_Decorator.TicketFactory;

public class EconomyTicket implements AirlineTicket{
    @Override
    public void book() {
        System.out.println("Booking an Economy ticket.");
    }

    @Override
    public void cancel() {
        System.out.println("Cancelling an Economy Ticket.");
    }
    @Override
    public String getDescription() {
        return "Ticket Description: Economy Ticket";
    }
}
