import Factory_and_Decorator.FlightFactory.Flight;
import Factory_and_Decorator.FlightFactory.FlightFactory;
import Factory_and_Decorator.TicketFactory.*;
import Singleton.FlightInformationSystem;
import Observer.FlightUpdates;
import Strategy_and_Adapter.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FlightUpdates flightUpdates = new FlightUpdates();
        FlightInformationSystem infoSystem = FlightInformationSystem.getInstance();

        infoSystem.startMonitoringFlights(flightUpdates);

        Scanner scanner = new Scanner(System.in);

        infoSystem.showUpdatedFlights();

        PaymentContext paymentContext = new PaymentContext();

        // Create individual payment strategies
        PaymentStrategy cardPayment = new CardPayment();
        PaymentStrategy cashPayment = new CashPayment();
        PaymentStrategy transferPayment = new TransferPayment();

        // Create combined payment adapter
        CombinedPaymentAdapter combinedPaymentAdapter = new CombinedPaymentAdapter();

        // Create a Flight using the FlightFactory
        FlightFactory flightFactory = new FlightFactory();

        // Create ticket factories for different ticket types
        TicketFactory economyTicketFactory = new EconomyTicketFactory();
        TicketFactory businessTicketFactory = new BusinessTicketFactory();

        // Create a list to store purchased flights and their total amounts
        List<Flight> purchasedFlights = new ArrayList<>();

        // Allow the user to purchase multiple flights
        while (true) {
            int totalPriceForTicket = 0;
            // Choose a flight
            System.out.println("Choose a flight: 1. Flight A ($100), 2. Flight B ($90), 3. Flight C ($80), 4. Finish");
            int flightChoice = scanner.nextInt();

            if (flightChoice == 4) {
                break; // Finish purchasing flights
            }

            Flight chosenFlight;
            switch (flightChoice) {
                case 1:
                    chosenFlight = flightFactory.createFlight("Flight A");
                    totalPriceForTicket += 100;
                    break;
                case 2:
                    chosenFlight = flightFactory.createFlight("Flight B");
                    totalPriceForTicket += 90;
                    break;
                case 3:
                    chosenFlight = flightFactory.createFlight("Flight C");
                    totalPriceForTicket += 80;
                    break;
                default:
                    System.out.println("Invalid choice. Exiting...");
                    return;
            }

            // Choose a ticket type for the selected flight
            System.out.println("Choose ticket type for " + chosenFlight.getDescription() +
                    ": 1. Economy (+$0), 2. Business (+$100)");
            int ticketType = scanner.nextInt();
            TicketFactory ticketFactory;
            switch (ticketType) {
                case 1:
                    ticketFactory = new EconomyTicketFactory();
                    break;
                case 2:
                    ticketFactory = new BusinessTicketFactory();
                    totalPriceForTicket += 100;
                    break;
                default:
                    System.out.println("Invalid choice. Exiting...");
                    return;
            }

            // Create a ticket based on the chosen flight and ticket type
            AirlineTicket ticket = ticketFactory.createTicket();
            ticket = addDecorators(ticket, totalPriceForTicket); // Allow adding multiple decorators

            // Choose a payment strategy for the ticket
            PaymentStrategy paymentStrategy = choosePaymentStrategy();
            paymentContext.setPaymentStrategy(paymentStrategy);

            // Book the ticket
            ticket.book();

            // Process payment for the ticket and update total amount paid
            combinedPaymentAdapter.addPaymentStrategy(paymentStrategy, totalPriceForTicket);


            // Add the purchased flight to the list
            purchasedFlights.add(chosenFlight);
        }

        // Display the total amount paid for all purchased flights
        // Process combined payment
        paymentContext.processCombinedPayment(combinedPaymentAdapter);
    }

    // Helper method to allow adding multiple decorators to a ticket
    private static AirlineTicket addDecorators(AirlineTicket ticket, int totalPriceForTicket) {
        while (true) {
            System.out.println("Do you want to add an extra decorator? 1. Food (+$20), 2. Cinema (+$30), 3. Music (+$10), 4. No");

            Scanner scanner = new Scanner(System.in);
            int decoratorChoice = scanner.nextInt();

            if (decoratorChoice == 4) {
                break; // Finish adding decorators
            }

            if (decoratorChoice == 1) {
                ticket = DecoratorApplier.applyFoodDecorator(ticket);
                totalPriceForTicket += 20;
            } else if (decoratorChoice == 2) {
                ticket = DecoratorApplier.applyCinemaDecorator(ticket);
                totalPriceForTicket += 30;
            } else if (decoratorChoice == 3) {
                ticket = DecoratorApplier.applyMusicDecorator(ticket);
                totalPriceForTicket += 10;
            } else {
                System.out.println("Invalid choice. Exiting...");
                return null;
            }
        }
        return ticket;
    }

    // Helper method to choose a payment strategy
    private static PaymentStrategy choosePaymentStrategy() {
        System.out.println("Choose payment strategy: 1. Card, 2. Cash, 3. Transfer");
        Scanner scanner = new Scanner(System.in);
        int paymentChoice = scanner.nextInt();

        switch (paymentChoice) {
            case 1:
                return new CardPayment();
            case 2:
                return new CashPayment();
            case 3:
                return new TransferPayment();
            default:
                System.out.println("Invalid choice. Using default payment strategy (Card).");
                return new CardPayment();
        }
    }
}

