import Factory_and_Decorator.FlightFactory.Flight;
import Factory_and_Decorator.FlightFactory.FlightFactory;
import Factory_and_Decorator.TicketFactory.*;
import Singleton.FlightInformationSystem;
import Observer.FlightUpdates;
import Strategy_and_Adapter.*;

import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static int totalPriceForTicket = 0;

    public static void main(String[] args) {
        FlightUpdates flightUpdates = new FlightUpdates();
        FlightInformationSystem infoSystem = FlightInformationSystem.getInstance();

        infoSystem.startMonitoringFlights(flightUpdates);
        infoSystem.showUpdatedFlights();

        PaymentContext paymentContext = new PaymentContext();

        // Create combined payment adapter
        CombinedPaymentAdapter combinedPaymentAdapter = new CombinedPaymentAdapter();

        // Create a list to store purchased flights and their total amounts
        List<Flight> purchasedFlights = new ArrayList<>();

        // Allow the user to purchase multiple flights
        while (true) {
            totalPriceForTicket = 0;
            int flightChoice = chooseFlight();
            if (flightChoice == 4) {
                break; // Finish purchasing flights
            }

            Map.Entry<Flight, Integer> result = getFlightByChoice(flightChoice, totalPriceForTicket);
            totalPriceForTicket = result.getValue();
            Flight chosenFlight = result.getKey();

            totalPriceForTicket = chooseTicketType(chosenFlight.getDescription(), totalPriceForTicket);
            TicketFactory ticketFactory = (totalPriceForTicket == 0) ? new EconomyTicketFactory() : new BusinessTicketFactory();

            AirlineTicket ticket = createTicket(ticketFactory);
            totalPriceForTicket = addDecorators(ticket, totalPriceForTicket);

            PaymentStrategy paymentStrategy = choosePaymentStrategy();
            paymentContext.setPaymentStrategy(paymentStrategy);

            ticket.book();
            combinedPaymentAdapter.addPaymentStrategy(paymentStrategy, totalPriceForTicket);

            purchasedFlights.add(chosenFlight);
        }

        paymentContext.processCombinedPayment(combinedPaymentAdapter);
    }

    private static int chooseFlight() {
        System.out.println("Choose a flight: 1. Flight A ($100), 2. Flight B ($90), 3. Flight C ($80), 4. Finish");
        return scanner.nextInt();
    }

    private static Map.Entry<Flight, Integer> getFlightByChoice(int flightChoice, int totalPriceForTicket) {
        Flight chosenFlight;
        FlightFactory flightFactory = new FlightFactory();

        switch (flightChoice) {
            case 1 -> {
                chosenFlight = flightFactory.createFlight("Flight A");
                totalPriceForTicket += 100;
            }
            case 2 -> {
                chosenFlight = flightFactory.createFlight("Flight B");
                totalPriceForTicket += 90;
            }
            case 3 -> {
                chosenFlight = flightFactory.createFlight("Flight C");
                totalPriceForTicket += 80;
            }
            default -> {
                System.out.println("Invalid choice. Exiting...");
                System.exit(1);
                return null; // Unreachable, but needed for compilation
            }
        }

        return new AbstractMap.SimpleEntry<>(chosenFlight, totalPriceForTicket);
    }




    private static int chooseTicketType(String flightDescription, int totalPriceForTicket) {
        System.out.println("Choose ticket type for " + flightDescription +
                ": 1. Economy (+$0), 2. Business (+$100)");
        int ticketType = scanner.nextInt();

        switch (ticketType) {
            case 1:
                // No additional cost for Economy ticket
                break;
            case 2:
                totalPriceForTicket += 100;
                break;
            default:
                System.out.println("Invalid choice. Exiting...");
                System.exit(1);
                break; // Unreachable, but needed for compilation
        }

        return totalPriceForTicket;
    }


    private static AirlineTicket createTicket(TicketFactory ticketFactory) {
        return ticketFactory.createTicket();
    }

    private static int addDecorators(AirlineTicket ticket, int totalPriceForTicket) {
        while (true) {
            System.out.println("Do you want to add an extra decorator? 1. Food (+$20), 2. Cinema (+$30), 3. Music (+$10), 4. No");
            int decoratorChoice = scanner.nextInt();

            if (decoratorChoice == 4) {
                break; // Finish adding decorators
            }

            if (decoratorChoice >= 1 && decoratorChoice <= 3) {
                ticket = addDecoratorByChoice(ticket, decoratorChoice);
                totalPriceForTicket += getDecoratorPriceByChoice(decoratorChoice);
            } else {
                System.out.println("Invalid choice. Exiting...");
                System.exit(1);
            }
        }
        return totalPriceForTicket;
    }

    private static AirlineTicket addDecoratorByChoice(AirlineTicket ticket, int decoratorChoice) {
        return switch (decoratorChoice) {
            case 1 -> DecoratorApplier.applyFoodDecorator(ticket);
            case 2 -> DecoratorApplier.applyCinemaDecorator(ticket);
            case 3 -> DecoratorApplier.applyMusicDecorator(ticket);
            default -> ticket; // Unreachable, but needed for compilation
        };
    }

    private static int getDecoratorPriceByChoice(int decoratorChoice) {
        return switch (decoratorChoice) {
            case 1 -> 20;
            case 2 -> 30;
            case 3 -> 10;
            default -> 0; // Unreachable, but needed for compilation
        };
    }

    private static PaymentStrategy choosePaymentStrategy() {
        System.out.println("Choose payment strategy: 1. Card, 2. Cash, 3. Transfer");
        int paymentChoice = scanner.nextInt();

        switch (paymentChoice) {
            case 1 -> {
                return new CardPayment();
            }
            case 2 -> {
                return new CashPayment();
            }
            case 3 -> {
                return new TransferPayment();
            }
            default -> {
                System.out.println("Invalid choice. Using default payment strategy (Card).");
                return new CardPayment();
            }
        }
    }
}

