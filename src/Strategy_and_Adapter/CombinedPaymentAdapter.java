package Strategy_and_Adapter;

import java.util.HashMap;
import java.util.Map;

public class CombinedPaymentAdapter implements CombinedPaymentStrategy {
    private Map<PaymentStrategy, Double> paymentStrategies;
    private double totalAmountPaid;

    public CombinedPaymentAdapter() {
        paymentStrategies = new HashMap<>();
        totalAmountPaid = 0.0;
    }

    @Override
    public void pay(double amount) {
        paymentStrategies.forEach((strategy, strategyAmount) -> {
            strategy.pay(strategyAmount);
            totalAmountPaid += strategyAmount;
        });
        System.out.println("Total amount paid: " + totalAmountPaid);
    }

    @Override
    public void addPaymentStrategy(PaymentStrategy paymentStrategy, double amount) {
        paymentStrategies.put(paymentStrategy, amount);
    }
}
