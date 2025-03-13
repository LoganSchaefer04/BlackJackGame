package blackjack;

public class Bank {
    private double currency;
    private double bet;

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
}
