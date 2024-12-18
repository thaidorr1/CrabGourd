
package crabgourd;

public class player {
    private String name ;
    private double balance ;

    public player( String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    // ham nap tien 
    public void addbalance (double money ) {
        if (money > 0 ) {
            this.balance += money ;
        }
    }
    public boolean deductBalance(double money ) {
        if (money > this.balance) {
            return false; // không đủ tiền
        }
        this.balance -= money ;
        return true;
    }
    public String infor () {
        return "Player{" +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
