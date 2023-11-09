package Factory.TicketFactory;

public class BusinessTicket implements AirlineTicket{

    @Override
    public void book() {
        System.out.println("Booking a Business Ticket.");
    }

    @Override
    public void cancel() {
        System.out.println("Cancelling a Business Ticket.");
    }
}
