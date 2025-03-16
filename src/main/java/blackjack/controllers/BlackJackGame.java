package blackjack.controllers;
import blackjack.Dealer;
import blackjack.Player;
import blackjack.Bank;

public class BlackJackGame {
    private final Dealer dealer;
    private final Player player;
    private final Bank bank;
    public int roundCounter = 1;

    public BlackJackGame() {
        CardSelector cardSelector = new CardSelector("Shuffle");
        dealer = new Dealer(cardSelector);
        player = new Player(cardSelector);
        bank = new Bank();
        bank.setCurrency(100.0); // gives user 100 currency by default @ launch of game as there is no way to save the user's currency atm (NEEDS UPDATING)
        initRound();
    }

    public void initRound() {
        System.out.printf("\n ---------- ROUND %d ----------\n", roundCounter);
        roundCounter++;
        bank.setBet(5.0); // CURRENTLY EVERY ROUND'S BET IS 5.0 CURRENCY WHEN UI IS FINISHED THIS WILL NEED UPDATING
        // Create initial hands for player and dealer and print cards and hand values.
        dealer.initHand();
        player.initHand();
        dealer.printUpCard();
        dealer.printUpCardValue();
        player.printHand();
        System.out.println("New hand value: " + player.getHandValue());


    }

    public void dealerTurn(){
        System.out.println("Now dealer turn");
        dealer.playTurn();
        System.out.println("\nDealer's final hand: " + dealer.getHandValue());
    }

    public void determineWinner() {
        double blackjackPayout = bank.getBet() * 2.5;
        double regularPayout = bank.getBet() * 2;
        double newTotalCurrencyOnWin = (bank.getCurrency() + regularPayout);
        double newTotalCurrencyOnBlackJack = (bank.getCurrency() + blackjackPayout);
        double newTotalPush = (bank.getCurrency() + bank.getBet());
        if (player.hasBust() ) {
            System.out.println("You lose!");
            System.out.println("New total currency = " + bank.getCurrency());
        } else if (dealer.hasBust()) {
            bank.setCurrency(newTotalCurrencyOnWin);
            System.out.println("You win!");
            System.out.println("New total currency = " + bank.getCurrency());
        } else if (dealer.getHandValue() > player.getHandValue()) {
            System.out.println("You lose!");
            System.out.println("New total currency = " + bank.getCurrency());
        } else if (dealer.getHandValue() < player.getHandValue()) {
            if(player.getHandValue() == 21){
                bank.setCurrency(newTotalCurrencyOnBlackJack);
            } else {
                bank.setCurrency(newTotalCurrencyOnWin);
            }
            System.out.println("You win!");
            System.out.println("New total currency = " + bank.getCurrency());
        } else {
            System.out.println("Push!");
            bank.setCurrency(newTotalPush);
        }

        System.out.println("Press Restart to play again");
    }

    public void hitPlayer() {
        // Hit player. Check if player busted.
        if (player.hit()) {
            // Player busted, determine winner
            determineWinner();

        // Player did not bust, print new hand and value.
        } else {
            System.out.println("Current Player Hand Value: " + player.getHandValue());
        }

    }

    public void doubleDownPlayer() {
        if((2* bank.getBet()) > bank.getCurrency()) {
            //player cannot double down
        } else {
            player.hit();
            bank.setBet(2 * bank.getBet());
            dealerTurn();
            determineWinner();
        }

    }
    public void playerStays() {
        // Player decided to stay, dealer will now play his turn and then determine winner.
        dealerTurn();
        determineWinner();
    }
}
