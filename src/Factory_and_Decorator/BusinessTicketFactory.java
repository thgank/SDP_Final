package Factory_and_Decorator;

public class BusinessTicketFactory implements TicketFactory{
    @Override
    public AirlineTicket createTicket() {
        return new BusinessTicket();
    }
}
