package Factory_and_Decorator;

public class EconomyTicketFactory implements TicketFactory{

    @Override
    public AirlineTicket createTicket() {
        return new EconomyTicket();
    }
}
