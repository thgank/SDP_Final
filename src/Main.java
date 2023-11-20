import Factory_and_Decorator.*;
import Observer.Passenger;
import Singleton.FlightInformationSystem;
import Observer.FlightUpdates;
import Strategy_and_Adapter.*;
import SQL.CRUD;
import java.util.Scanner;

public class Main {
    private static final FlightUpdates flightUpdates = new FlightUpdates();
    static FlightInformationSystem infoSystem = FlightInformationSystem.getInstance();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        CRUD.createUsersTable();
        CRUD.createFlightsTable();
        start();
    }

    public static void start() {
        System.out.println("--- Welcome to NQZ Airport! ---");
        System.out.println("""
                Please, choose who are you:
                1. Passenger
                2. Dispatcher
                3. Exit""");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> passengerLogIn();
            case 2 -> dispatcherLogIn();
            case 3 -> System.exit(0);
        }
    }

    public static void dispatcherLogIn() {
        System.out.println("--- DISPATCHER CONTROL SYSTEM ---");
        System.out.println("--- PLEASE LOGIN OR REGISTER ---");
        System.out.println("""
                1. Login
                2. Register""");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                String userName, password;
                scanner.nextLine();
                System.out.print("Your username: ");
                userName = scanner.nextLine();
                System.out.print("Your password: ");
                password = scanner.nextLine();
                CRUD.loginUser(userName, password);
                dispatcherControl(userName);
            }
            case 2 -> {
                String userName, password;
                scanner.nextLine(); // Consume newline character
                System.out.print("Your username: ");
                userName = scanner.nextLine();
                System.out.print("Your password: ");
                password = scanner.nextLine();
                CRUD.createUser(userName, password);
                dispatcherLogIn();
            }
        }
    }

    public static void dispatcherControl(String userName) {
        System.out.println("--- Welcome back to work, " + userName + " ---");
        System.out.println("Choose what you want to do: ");
        System.out.println("""
                1. Add Flight
                2. Update Flight
                3. Cancel Flight
                4. Check Existing Flights
                5. Notify about flight delay
                6. Exit""");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> createFlight(userName);
            case 2 -> updateFlight(userName);
            case 3 -> deleteFlight(userName);
            case 4 -> {
                showFlights();
                dispatcherControl(userName);
            }
            case 5 -> delayNotify(userName);
            case 6 -> start();
        }
    }
    public static void delayNotify(String userName) {
        System.out.println("Choose which flight is delayed: ");
        CRUD.readFlights();
        scanner.nextLine();

        String choice = scanner.nextLine();
        String flightDetails = CRUD.getFlightByNumber(choice);

        flightUpdates.updateFlight(flightDetails);
        dispatcherControl(userName);
    }
    public static void createFlight(String userName) {
        scanner.nextLine();
        System.out.println("Enter flight number: ");
        String flightNumber = scanner.nextLine();

        System.out.println("Enter departure location: ");
        String departure = scanner.nextLine();

        System.out.println("Enter destination: ");
        String destination = scanner.nextLine();

        System.out.println("Enter departure time: ");
        String departureTime = scanner.nextLine();

        infoSystem.createFlight(flightNumber, departure, destination, departureTime);
        String flightDetails = "New flight: " + flightNumber + " " + departure + " " + destination + " " + departureTime;
        flightUpdates.updateFlight(flightDetails);
        dispatcherControl(userName);
    }
    public static void showFlights() {
        CRUD.readFlights();
    }
    public static void updateFlight(String username){
        CRUD.readFlights();
        System.out.println("Enter the flight number to update:");
        scanner.nextLine(); // Consume newline
        String flightNumber = scanner.nextLine();

        System.out.println("Enter the new departure:");
        String newDeparture = scanner.nextLine();

        System.out.println("Enter the new destination:");
        String newDestination = scanner.nextLine();

        System.out.println("Enter the new departure time:");
        String newDepartureTime = scanner.nextLine();

        CRUD.updateFlight(flightNumber, newDeparture, newDestination, newDepartureTime);
        String update = "Flight was updated";
        flightUpdates.updateFlight(update);
        dispatcherControl(username);
    }
    public static void deleteFlight(String username) {
        CRUD.readFlights();
        System.out.println("Enter the flight number to delete:");
        scanner.nextLine(); // Consume newline
        String flightNumber = scanner.nextLine();

        CRUD.deleteFlight(flightNumber);
        String update = "Flight was deleted";
        flightUpdates.updateFlight(update);
        dispatcherControl(username);
    }



    public static void passengerLogIn() {
        System.out.println("--- Welcome to the flight booking system! --- ");
        System.out.println("Please enter your name:");
        scanner.nextLine();
        String passengerName = scanner.nextLine();

        Passenger passenger = new Passenger(passengerName);
        flightUpdates.registerObserver(passenger);
        passengerControl(passengerName);
    }

    public static void passengerControl(String passengerName) {
        System.out.println("""
                What you want to do?\s
                1. Check Flights
                2. Find a flight
                3. Purchase a ticket
                4. Exit""");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                showFlights();
                passengerControl(passengerName);
            }
            case 2 ->{
                findFlight(passengerName);
            }
            case 3 -> listFlights(passengerName);
            case 4 -> start();
        }
    }
    public static void findFlight(String passengerName) {
        scanner.nextLine();
        System.out.println("Enter Departure place:");
        String departure = scanner.nextLine();

        System.out.println("Enter Destination place: ");
        String destination = scanner.nextLine();
        CRUD.searchFlightsByDepartureAndDestination(departure, destination);
        System.out.println("Which one you want to buy?" +
                "\nOr just write 0 to exit");
        String choice = scanner.nextLine();
        if (choice.equals("0")) {
            passengerControl(passengerName);
        }
        else{
            buyTicket(passengerName, choice);
        }
    }

    public static void listFlights(String passengerName){
        System.out.println("Here is all Flights " + passengerName + ":");
        showFlights();
        System.out.println("Which Flight you want to book? Enter ID:");
        scanner.nextLine();
        String flightNumber = scanner.nextLine();
        buyTicket(passengerName,flightNumber);
    }

    public static void buyTicket(String passengerName, String flightNumber) {
        System.out.println("Purchasing a flight..." +
                "\nFull information about flight:");
        CRUD.getFlightByNumber(flightNumber);
        System.out.println("""
                Which Ticket you want to buy?
                1. Economy class
                2. Business class""");
        int choice = scanner.nextInt();
        AirlineTicket ticket = null;
        switch (choice) {
            case 1 -> {
                EconomyTicketFactory economyTicketFactory = new EconomyTicketFactory();
                ticket = economyTicketFactory.createTicket();
            }
            case 2 -> {
                BusinessTicketFactory businessTicketFactory = new BusinessTicketFactory();
                ticket = businessTicketFactory.createTicket();
            }
        }
        System.out.println("""
                Do you want to add something for your ticket?
                1. Yes
                2. No""");
        int decorate = scanner.nextInt();
        if (decorate == 1) {
            ticket = decorateTicket(ticket);
        }
        assert ticket != null;
        System.out.println(ticket.getDescription());
        payForTicket(passengerName, ticket, flightNumber);
    }
    public static void payForTicket(String passengerName, AirlineTicket ticket, String flightNumber) {
        double expectedAmount = ticket.getCost();
        double totalAmount = 0.0;
        boolean paymentCompleted = false;

        while (!paymentCompleted) {
            System.out.println("Total cost: " + expectedAmount);
            System.out.println("How would you like to pay for the ticket?");
            System.out.println("1. Card Payment");
            System.out.println("2. Cash Payment");
            System.out.println("3. Transfer Payment");
            System.out.println("4. Combined Payment");

            int paymentChoice = scanner.nextInt();
            PaymentStrategy paymentStrategy = null;

            if (paymentChoice >= 1 && paymentChoice <= 3) {
                paymentStrategy = switch (paymentChoice) {
                    case 1 -> new CardPayment();
                    case 2 -> new CashPayment();
                    case 3 -> new TransferPayment();
                    default -> paymentStrategy;
                };

                PaymentContext paymentContext = new PaymentContext();
                paymentContext.setPaymentStrategy(paymentStrategy);

                double amount = expectedAmount - totalAmount;
                paymentContext.processPayment(amount);
                totalAmount += amount;

                if (totalAmount == expectedAmount) {
                    paymentCompleted = true;
                } else if (totalAmount < expectedAmount) {
                    System.out.println("Remaining amount to be paid: " + (expectedAmount - totalAmount));
                } else {
                    // If paid amount exceeds expected cost, calculate and display change
                    double change = totalAmount - expectedAmount;
                    System.out.println("Paid amount exceeds the ticket cost. Your change is: " + change);
                    paymentCompleted = true;
                }
            } else if (paymentChoice == 4) {
                CombinedPaymentAdapter combinedPayment = new CombinedPaymentAdapter();

                System.out.println("Enter number of payment strategies you want to combine:");
                int numOfStrategies = scanner.nextInt();

                for (int i = 0; i < numOfStrategies; i++) {
                    System.out.println("Enter payment type (1 for Card, 2 for Cash, 3 for Transfer):");
                    int type = scanner.nextInt();
                    PaymentStrategy strategy;
                    double amount;

                    strategy = switch (type) {
                        case 1 -> new CardPayment();
                        case 2 -> new CashPayment();
                        case 3 -> new TransferPayment();
                        default -> null;
                    };

                    System.out.println("Enter amount for this payment strategy:");
                    amount = scanner.nextDouble();

                    combinedPayment.addPaymentStrategy(strategy, amount);
                    totalAmount += amount;
                }
                if (totalAmount == expectedAmount) {
                    combinedPayment.pay(totalAmount);
                    paymentCompleted = true;
                } else if (totalAmount < expectedAmount) {
                    System.out.println("Total combined payment doesn't match the ticket cost. Retry.");
                    totalAmount = 0.0;
                } else {
                    // If paid amount exceeds expected cost, calculate and display change
                    double change = totalAmount - expectedAmount;
                    System.out.println("Paid amount exceeds the ticket cost. Your change is: " + change);
                    paymentCompleted = true;
                }
            } else {
                System.out.println("Invalid payment choice.");
            }
        }
        System.out.println("Thanks for purchase, " + passengerName +
                "\nYour ticket: ");
        System.out.println(ticket.getDescription());
        CRUD.getFlightByNumber(flightNumber);
        passengerControl(passengerName);
    }



    public static AirlineTicket decorateTicket(AirlineTicket ticket) {
        boolean decorating = true;
        while (decorating) {
            System.out.println("""
                    Choose what you want to add:\s
                    1. Food
                    2. Cinema
                    3. Music
                    4. Exit""");
            int decorationChoice = scanner.nextInt();
            switch (decorationChoice) {
                case 1:
                    ticket = new FoodDecorator(ticket);
                    break;
                case 2:
                    ticket = new CinemaDecorator(ticket);
                    break;
                case 3:
                    ticket = new MusicDecorator(ticket);
                    break;
                case 4:
                    decorating = false; // Exit the loop
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
        return ticket;
    }
}

