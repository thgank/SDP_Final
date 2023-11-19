package Factory_and_Decorator.TicketFactory;

public class BusinessTicketFactory implements TicketFactory{
    @Override
    public AirlineTicket createTicket() {
        return new BusinessTicket();
    }
}
