import Singleton.FlightInformationSystem;
import Observer.FlightUpdates;
import Strategy_and_Adapter.*;

public class Main {
    public static void main(String[] args) {
        FlightUpdates flightUpdates = new FlightUpdates();
        FlightInformationSystem infoSystem = FlightInformationSystem.getInstance();

        infoSystem.startMonitoringFlights(flightUpdates);

        flightUpdates.addFlight("Flight A");
        flightUpdates.addFlight("Flight B");

        infoSystem.showUpdatedFlights();

        PaymentContext paymentContext = new PaymentContext();

        // Create individual payment strategies
        PaymentStrategy cardPayment = new CardPayment();
        PaymentStrategy cashPayment = new CashPayment();
        PaymentStrategy transferPayment = new TransferPayment();

        // Create combined payment adapter
        CombinedPaymentAdapter combinedPaymentAdapter = new CombinedPaymentAdapter();

        // Add individual payment strategies with amounts to the combined payment adapter
        combinedPaymentAdapter.addPaymentStrategy(cardPayment, 50.0); // Pay $50 using card
        combinedPaymentAdapter.addPaymentStrategy(cashPayment, 30.0); // Pay $30 using cash

        // Process combined payment
        paymentContext.processCombinedPayment(combinedPaymentAdapter);
    }
}

