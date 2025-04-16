package blackjack.features;

public class Bank {
    private double currency;
    private double bet = 5.0;

    //Constructor
    public Bank() {
    }

    public void setBet(double amount){
        bet = amount;
    }

    public double placeBet(){
        currency -= bet;
        return bet;
    }

    public double placeBet(double amount){
        currency -= amount;
        return amount;
    }

    public Bank (double amount) {
        currency = amount;
    }

    //getters and setters for betting and user's currency
    public double getCurrency() {
        return currency;
    }

    public double getBet(){
        return bet;
    }

    public void setCurrency(double currency) {
        this.currency = currency;
    }

    //Tip the dealer
    public void tipDealer(int amount) {
        if(amount <= getCurrency()) {
            System.out.println("Tip dealer: " + amount);
            currency -= amount;
            System.out.println("Amount left in account: " + currency);
        } else {
            System.out.println("Cannot tip dealer more than you have in your account");
        }
    }

    public void scoreWin(double bet) {
        currency += bet * 2;
    }

    public void scoreBlackJack(double bet) {
        currency += bet * 2.5;
    }

    public void scorePush(double bet) {
        currency += bet;
    }

    public double subtractMoney(double amount) {
        currency -= amount;
        return currency;
    }
}
