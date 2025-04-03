package blackjack.features;

public class Bank {
    private double currency;

    //Constructor
    public Bank() {
    }

    public Bank (double amount) {
        currency = amount;
    }

    //getters and setters for betting and user's currency
    public double getCurrency() {
        return currency;
    }
    public void setBet(double bet) {
        currency -= bet; // reduces user's currency based on the bet prior to the game being played
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

    public void scoreWin(int bet) {
        currency += bet * 2;
    }

    public void scoreBlackJack(int bet) {
        currency += bet * 2.5;
    }

    public void scorePush(int bet) {
        currency += bet;
    }

    public double subtractMoney(int amount) {
        currency -= amount;
        return currency;
    }


}
