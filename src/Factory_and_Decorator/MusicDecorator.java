package Factory_and_Decorator;

public class MusicDecorator implements TicketDecorator {
    protected AirlineTicket decoratedTicket;

    public MusicDecorator(AirlineTicket decoratedTicket) {
        this.decoratedTicket = decoratedTicket;
    }

    @Override
    public void book() {
        decoratedTicket.book();
        System.out.println("Adding music access to the ticket.");
    }

    @Override
    public void cancel() {
        decoratedTicket.cancel();
        System.out.println("Removing music access from the ticket.");
    }

    @Override
    public String getDescription() {
        String baseDescription = decoratedTicket.getDescription();
        if (baseDescription.endsWith("Ticket")) {
            return baseDescription + " with music access";
        } else {
            return baseDescription + ", music access";
        }
    }

    @Override
    public double getCost() {
        return decoratedTicket.getCost()+10;
    }
}
