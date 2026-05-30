import java.util.*;
class Stock {
    private String symbol;
    private double price;
    private List<Double> history;
    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.pr 
        ice = price;
        this.history = new ArrayList<>();
        history.add(price);
    }
    public String getSymbol() { return symbol; }
    public double getPrice() { return price; }
    public void updatePrice(double volatility) {
        price += (Math.random() - 0.5) * volatility;
        if (price < 1) price = 1; 
        history.add(price);
    }
    public List<Double> getHistory() { return history; }
}
class Transaction {
    private String type; 
    private Stock stock;
    private int quantity;
    private Date timestamp;
    public Transaction(String type, Stock stock, int quantity) {
        this.type = type;
        this.stock = stock;
        this.quantity = quantity;
        this.timestamp = new Date();
    }
    @Override
    public String toString() {
        return type + " " + quantity + " of " + stock.getSymbol() + " at " + stock.getPrice() + " on " + timestamp;
    }
}
class User {
    private String name;
    private double balance;
    private Map<Stock, Integer> portfolio;
    private List<Transaction> transactions;
    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.portfolio = new HashMap<>();
        this.transactions = new ArrayList<>();
    }
    public void buyStock(Stock stock, int qty) {
        double cost = stock.getPrice() * qty;
        if (balance >= cost) {
            balance -= cost;
            portfolio.put(stock, portfolio.getOrDefault(stock, 0) + qty);
            transactions.add(new Transaction("BUY", stock, qty));
            System.out.println("Bought " + qty + " of " + stock.getSymbol());
        } else {
            System.out.println("Insufficient balance!");
        }
    }
    public void sellStock(Stock stock, int qty) {
        int owned = portfolio.getOrDefault(stock, 0);
        if (owned >= qty) {
            portfolio.put(stock, owned - qty);
            balance += stock.getPrice() * qty;
            transactions.add(new Transaction("SELL", stock, qty));
            System.out.println("Sold " + qty + " of " + stock.getSymbol());
        } else {
            System.out.println("Not enough shares to sell!");
        }
    }
    public void showPortfolio() {
        System.out.println("Portfolio of " + name + ":");
        for (Map.Entry<Stock, Integer> entry : portfolio.entrySet()) {
            System.out.println(entry.getKey().getSymbol() + " - " + entry.getValue() + " shares");
        }
        System.out.println("Balance: " + balance);
    }
    public double portfolioValue() {
        double total = balance;
        for (Map.Entry<Stock, Integer> entry : portfolio.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }
}
public class StockTradingSimulation {
    public static void main(String[] args) {
        Stock apple = new Stock("AAPL", 150);
        Stock google = new Stock("GOOG", 2800);
        User user = new User("Samyuktha", 10000);
        user.buyStock(apple, 10);
        user.buyStock(google, 2);
        apple.updatePrice(10);
        google.updatePrice(50);
        user.sellStock(apple, 5);
        user.showPortfolio();
        System.out.println("Total Portfolio Value: " + user.portfolioValue());
    }
}
