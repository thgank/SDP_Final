package Strategy_and_Adapter;

public class PaymentContext {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void processPayment(double amount) {
        if (paymentStrategy != null) {
            paymentStrategy.pay(amount);
        } else {
            System.out.println("Type of Payment is not set.");
        }
    }

    public void processCombinedPayment(CombinedPaymentStrategy combinedPaymentStrategy) {
        combinedPaymentStrategy.pay(0); // Pass any amount, as it will be overridden by individual strategies
    }
}
