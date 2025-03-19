package blackjack;

public class Bank {
    private double currency;
    private double bet;

    //Constructor
    public Bank() {
    }

    public Bank (double amount) {
        currency = amount;
    }

    //getters and setters for betting and user's currency
    public double getBet() {
        return bet;
    }
    public double getCurrency() {
        return currency;
    }
    public void setBet(double bet) {

        if(bet < getCurrency()) {
            setCurrency(getCurrency() - bet); // reduces user's currency based on the bet prior to the game being played
            this.bet = bet;
        } else {
            System.out.println("ERROR: Bet is more than current user currency value!");
        }
    }
    public void setCurrency(double currency) {
        this.currency = currency;
    }

    //Tip the dealer
    public void tipDealer(double amount) {
        if(amount <= getCurrency()) {
            System.out.println("Tip dealer: " + amount);
            currency -= amount;
            System.out.println("Amount left in account: " + currency);
        } else {
            System.out.println("Cannot tip dealer more than you have in your account");
        }
    }
}
