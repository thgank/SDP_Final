package Strategy;

public class PaymentContext {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy){
        this.paymentStrategy = paymentStrategy;
    }

    public void processPayment(double amount){
        if (paymentStrategy != null){
            paymentStrategy.pay(amount);
        } else {
            System.out.println("Type of Payment is not set.");
        }
    }
}
