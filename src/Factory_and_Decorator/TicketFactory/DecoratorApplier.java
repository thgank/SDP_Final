package Factory_and_Decorator.TicketFactory;

public class DecoratorApplier {
    public static AirlineTicket applyFoodDecorator(AirlineTicket ticket) {
        return new FoodDecorator(ticket);
    }
    public static AirlineTicket applyCinemaDecorator(AirlineTicket ticket) {
        return new CinemaDecorator(ticket);
    }

    public static AirlineTicket applyMusicDecorator(AirlineTicket ticket) {
        return new MusicDecorator(ticket);
    }
    public static String getTicketDescription(AirlineTicket ticket) {
        return ticket.getDescription();
    }
}
