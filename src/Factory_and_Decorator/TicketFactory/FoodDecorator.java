package Factory_and_Decorator.TicketFactory;

public class FoodDecorator implements TicketDecorator {
    protected AirlineTicket decoratedTicket;

    public FoodDecorator(AirlineTicket decoratedTicket) {
        this.decoratedTicket = decoratedTicket;
    }

    @Override
    public void book() {
        decoratedTicket.book();
        System.out.println("Adding food to the ticket.");
    }

    @Override
    public void cancel() {
        decoratedTicket.cancel();
        System.out.println("Removing food from the ticket.");
    }
    @Override
    public String getDescription() {
        String baseDescription = decoratedTicket.getDescription();
        if (baseDescription.endsWith("Ticket")) {
            return baseDescription + " with food";
        } else {
            return baseDescription + ", food";
        }
    }
}
