package Strategy;

public class CashPayment implements PaymentStrategy{

    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using cash.");
    }
}
