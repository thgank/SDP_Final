# SE-2224
## Serikov Nursultan and Assanali Rymgali
## Documentation of Software Design Pattern’s Final Project
___

### **Project Overview**

As a final project, we decided to recreate the airport through the eyes of the dispatcher and passengers. We considered this topic very suitable from the point of view of implementing design patterns, since this is where all their beauty and capabilities can be realized. Also, we had been planning to create something similar for a very long time, but at that time we did not have the design patterns we have now.

As I said, the idea of this project is to recreate the airport ecosystem. Firstly, this is something of a sketch of our vision of how airports work. Secondly, this is a rather interesting and extraordinary topic, because writing all the necessary classes and methods will take a lot of time.

The goal of our work is to try out a new approach to design patterns. This project is unusual in that we used the pattern fusion technique. Roughly speaking, several patterns are connected and essentially work separately, but are one single one.

We want to bring something new to the programming milestone. We want to create something unusual and interesting.

___

### **UML Diagram**

Here you can see UML Diagram of our Project:

![UML](https://github.com/thgank/SDP_Final/blob/master/UML.png)

___

### **Code Explanation**

In this project, six patterns were used, including: **Singleton, Decorator, Strategy, Factor method, Observer, Adapter.**

Also, the database was used using **PostgreSQL**

At first, let's check file's architecture of out project

+ src
    * Factory_and_Decorator
    * Observer
    * Singleton
    * SQL
    * Strategy_and_Adapter

So, as you can see, some design patterns are combined, and since that, they are working indpendently, but they are connected.

We will start from Factory method.

```Java 
public interface AirlineTicket {
    public void book();
    public void cancel();
    public String getDescription();
    public double getCost();
    
} 
```

```Java 
public interface TicketFactory {
    AirlineTicket createTicket();
} 
```

```Java 
public class EconomyTicket implements AirlineTicket{
    @Override
    public void book() {
        System.out.println("Booking an Economy ticket.");
    }

    @Override
    public void cancel() {
        System.out.println("Cancelling an Economy Ticket.");
    }
    @Override
    public String getDescription() {
        return "Ticket Description: Economy Ticket";
    }

    @Override
    public double getCost() {
        return 100;
    }
}
```

```Java 
public class BusinessTicket implements AirlineTicket{

    @Override
    public void book() {
        System.out.println("Booking a Business Ticket.");
    }

    @Override
    public void cancel() {
        System.out.println("Cancelling a Business Ticket.");
    }
    @Override
    public String getDescription() {
        return "Ticket Description: Business Ticket";
    }

    @Override
    public double getCost() {
        return 200;
    }
}
```

```Java 
public class EconomyTicketFactory implements TicketFactory{

    @Override
    public AirlineTicket createTicket() {
        return new EconomyTicket();
    }
}
```

```Java 
public class BusinessTicketFactory implements TicketFactory{
    @Override
    public AirlineTicket createTicket() {
        return new BusinessTicket();
    }
}
```

So, this is a basic factory method, by which we can generate different tickets. At this moment we thought, why can't we decorate tickets that was created by factory? So here you can see how  we combined Decorator with Factory:


```Java 
public interface TicketDecorator extends AirlineTicket{
}
```

```Java 
public class MusicDecorator implements TicketDecorator {
    protected AirlineTicket decoratedTicket;

    public MusicDecorator(AirlineTicket decoratedTicket) {
        this.decoratedTicket = decoratedTicket;
    }

    @Override
    public void book() {
        decoratedTicket.book();
        System.out.println("Adding music access to the ticket.");
    }

    @Override
    public void cancel() {
        decoratedTicket.cancel();
        System.out.println("Removing music access from the ticket.");
    }

    @Override
    public String getDescription() {
        String baseDescription = decoratedTicket.getDescription();
        if (baseDescription.endsWith("Ticket")) {
            return baseDescription + " with music access";
        } else {
            return baseDescription + ", music access";
        }
    }

    @Override
    public double getCost() {
        return decoratedTicket.getCost()+10;
    }
}
```

```Java 
public class FoodDecorator implements TicketDecorator {
    protected AirlineTicket decoratedTicket;

    public FoodDecorator(AirlineTicket decoratedTicket) {
        this.decoratedTicket = decoratedTicket;
    }

    @Override
    public void book() {
        decoratedTicket.book();
        System.out.println("Adding food to the ticket.");
    }

    @Override
    public void cancel() {
        decoratedTicket.cancel();
        System.out.println("Removing food from the ticket.");
    }
    @Override
    public String getDescription() {
        String baseDescription = decoratedTicket.getDescription();
        if (baseDescription.endsWith("Ticket")) {
            return baseDescription + " with food";
        } else {
            return baseDescription + ", food";
        }
    }

    @Override
    public double getCost() {
        return decoratedTicket.getCost()+10;
    }
}
```

```Java 
public class BusinessTicketFactory implements TicketFactory{
    @Override
    public AirlineTicket createTicket() {
        return new BusinessTicket();
    }
}
```

```Java 
public class CinemaDecorator implements TicketDecorator {
    protected AirlineTicket decoratedTicket;

    public CinemaDecorator(AirlineTicket decoratedTicket) {
        this.decoratedTicket = decoratedTicket;
    }

    @Override
    public void book() {
        decoratedTicket.book();
        System.out.println("Adding cinema access to the ticket.");
    }

    @Override
    public void cancel() {
        decoratedTicket.cancel();
        System.out.println("Removing cinema access from the ticket.");
    }

    @Override
    public String getDescription() {
        String baseDescription = decoratedTicket.getDescription();
        if (baseDescription.endsWith("Ticket")) {
            return baseDescription + " with cinema access";
        } else {
            return baseDescription + ", cinema access";
        }
    }

    @Override
    public double getCost() {
        return decoratedTicket.getCost()+10;
    }
}
```

Now, every object generated by Factory can be decorated by different decorators! Simple magic.

Next Pattern is Observer:

```Java 
package Observer;
import java.util.List;
import java.util.ArrayList;

public class FlightUpdates implements Subject {
    private final List<Observer> observers;

    public FlightUpdates() {
        observers = new ArrayList<>();
    }

    public void updateFlight(String flightDetails) {
        notifyObservers(flightDetails);
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String flightDetails) {
        for (Observer observer : observers) {
            observer.update(flightDetails);
        }
    }
}

```

```Java 
package Observer;

public interface Observer {
    void update(String flightDetails);
}

```

```Java 
package Observer;

public class Passenger implements Observer{
    private String name;

    public Passenger(String name){
        this.name = name;
    }

    @Override
    public void update(String flightDetails) {
        System.out.println(name + " received an update");
    }
}


```

```Java 

package Observer;

public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String flightDetails);
}


```

This is a very simple Observer, and we thought why we shouldn't combine it with something? So we thought that we will definitely combine it with Singleton:

```Java
package Singleton;
import Observer.FlightUpdates;
import SQL.CRUD;


public class FlightInformationSystem {
    private static volatile FlightInformationSystem instance;

    private FlightInformationSystem() {
    }

    public static FlightInformationSystem getInstance() {
        if (instance == null) {
            synchronized (FlightInformationSystem.class) {
                if (instance == null) {
                    instance = new FlightInformationSystem();
                }
            }
        }
        return instance;
    }

    public void createFlight(String flightNumber, String departure, String destination, String departureTime) {
        // Create the flight in the database using CRUD class
        CRUD.createFlight(flightNumber, departure, destination, departureTime);

        // Notify observers about the new flight created
        FlightUpdates flightUpdates = new FlightUpdates();
        flightUpdates.updateFlight("Flight " + flightNumber + " from " + departure + " to " + destination + " at " + departureTime);
    }

}

```

So now what do we have? When every new flight created by Singleton, Observer will catch this creation and notify everyone. Another simple magic!

Next Pattern is Strategy and Adapter. I wouldn't show the full code:

```Java
package Strategy_and_Adapter;

public interface PaymentStrategy {
    void pay(double amount);
}

```

```Java
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

```

```Java
package Strategy_and_Adapter;

public class CardPayment implements PaymentStrategy{

    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using card");
    }
}

```

and etc classes. So we thought it is boring, and since according to OOP we should follow abstractions, we decide to use adapter over here. We can use adapter to make combined payments!

```Java
package Strategy_and_Adapter;

public interface CombinedPaymentStrategy extends PaymentStrategy {
    void addPaymentStrategy(PaymentStrategy paymentStrategy, double amount);
}

```

```Java
package Strategy_and_Adapter;

import java.util.HashMap;
import java.util.Map;

public class CombinedPaymentAdapter implements CombinedPaymentStrategy {
    private final Map<PaymentStrategy, Double> paymentStrategies;
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

```

So now, we have adapter as a part of strategy, and this adapter help us to adapt other payment processes, so we can mix them, we can use all of them by once if it is necessary.

Also, since we used SQL, there is a class for all CRUDs we have:

```Java
package SQL;
import java.sql.*;

public class CRUD {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/db_name";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "password";

    public static void createUsersTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            if (conn != null) {
                System.out.println("Connected to the PostgreSQL database!");

                // Create a table query
                String createTableSQL = "CREATE TABLE IF NOT EXISTS users (Name TEXT, Password TEXT)";

                try (Statement statement = conn.createStatement()) {
                    // Execute the query to create the table
                    statement.execute(createTableSQL);
                    System.out.println("Table 'users' created or already created!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Table creation error: " + e.getMessage());
        }
    }

    public static void createFlightsTable() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            String createTableSQL = "CREATE TABLE IF NOT EXISTS flights (" +
                    "id SERIAL PRIMARY KEY," +
                    "flight_number VARCHAR(50) NOT NULL," +
                    "departure VARCHAR(100) NOT NULL," +
                    "destination VARCHAR(100) NOT NULL," +
                    "departure_time VARCHAR NOT NULL" +
                    ")";

            statement.execute(createTableSQL);
            System.out.println("flights table created or already exists!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // and some others methods
```

In main class, we decided to use CLI as an interface.

At first you will have a choice, if you're a passenger or dispatcher.

In case if you are dispatcher, you can login or register. And then, you can manage flights, or notify passengers!

As a passenger you can check, find flights, purchase tickets.

You can just start program and see it, but firstly put your data in CRUD class.
___

### **Conclusion**

This project took a lot of time, because we wanted to create something really interesting. Combining patterns is a very hard objective. For example, combining Strategy and Adapter was a really difficult task, we did not know how to implement it, but we did.

Combining Factory and Decorator was hard too, since we had to make a unique logic.

It was a really great experience for us, this is a project that we wanted to create. It was really interesting to create a really uniue logic, create something new for us. We broke the vision of design patterns a bit, but we did not broke their responsibilities. As a result, it is a harmonical project, where each part works with other, and everything is connected.

Looking at the future, we think that we will create a GUI for out project, and also we will do some refactor to make code work faster. Also, we will add some new functionality to our project. And as a result, we want to see a full-cycle airport programm, where you can try yourself from two different parts: dispatcher and passenger.

# References
*Gamma, E., Helm, R., Johnson, R. E., & Vlissides, J. (1994). Design patterns: elements of reusable Object-Oriented software. http://www.ulb.tu-darmstadt.de/tocs/59840579.pdf*

*Pree, W., & Sikora, H. (1994). Design patterns for Object-Oriented software Development. http://ci.nii.ac.jp/ncid/BA25355531*

*Bhargava, A. (2016). Grokking Algorithms: An illustrated guide for programmers and other curious people. https://dl.acm.org/citation.cfm?id=3002869*

*Momjian, B. (2000). PostgreSQL: Introduction and Concepts. https://index-of.es/Linux/Ebook-Pdf/Addison%20Wesley%20-%20PostgreSQL%20-%20Introduction%20and%20Concepts.pdf*
