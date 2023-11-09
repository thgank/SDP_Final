package Factory.TicketFactory;

public class EconomyTicketFactory implements TicketFactory{

    @Override
    public AirlineTicket createTicket() {
        return new EconomyTicket();
    }
}
