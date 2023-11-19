package Strategy_and_Adapter;

public interface CombinedPaymentStrategy extends PaymentStrategy {
    void addPaymentStrategy(PaymentStrategy paymentStrategy, double amount);
}
