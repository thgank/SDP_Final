package Factory_and_Decorator;

public class CinemaDecorator implements TicketDecorator {
    protected AirlineTicket decoratedTicket;

    public CinemaDecorator(AirlineTicket decoratedTicket) {
        this.decoratedTicket = decoratedTicket;
    }

    @Override
    public void book() {
        decoratedTicket.book();
        System.out.println("Adding cinema access to the ticket.");
    }

    @Override
    public void cancel() {
        decoratedTicket.cancel();
        System.out.println("Removing cinema access from the ticket.");
    }

    @Override
    public String getDescription() {
        String baseDescription = decoratedTicket.getDescription();
        if (baseDescription.endsWith("Ticket")) {
            return baseDescription + " with cinema access";
        } else {
            return baseDescription + ", cinema access";
        }
    }

    @Override
    public double getCost() {
        return decoratedTicket.getCost()+10;
    }
}
