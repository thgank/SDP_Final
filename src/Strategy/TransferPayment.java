package Strategy;

public class TransferPayment implements PaymentStrategy{

    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " by transfer");
    }
}
