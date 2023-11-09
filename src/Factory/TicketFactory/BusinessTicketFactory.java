package Factory.TicketFactory;

public class BusinessTicketFactory implements TicketFactory{
    @Override
    public AirlineTicket createTicket() {
        return new BusinessTicket();
    }
}
