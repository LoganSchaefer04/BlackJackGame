package blackjack;
import java.util.*;
public class BlackJackGame {
    private Dealer dealer;
    private Player player;
    private CardSelector cardSelector;
    private boolean gameOver;
    private Scanner scanner;
    public int roundCounter = 1;

    public BlackJackGame() {
        cardSelector = new CardSelector("random");
        dealer = new Dealer(cardSelector);
        scanner = new Scanner(System.in);
        gameOver = false;
        player = new Player(cardSelector);
        initRound();
    }

    public void initRound() {
        System.out.printf("\n ---------- ROUND %d ----------\n", roundCounter);
        roundCounter++;
        dealer.initHand();
        player.initHand();
        dealer.printUpCard();
        dealer.printUpCardValue();
        player.printHand();
        System.out.println("New hand value: " + player.getHandValue());
        gameOver = false;


    }

    public void dealerTurn(){
        System.out.println("Now dealer turn");
        dealer.playTurn();
        System.out.println("\nDealer's final hand: " + dealer.getHandValue());
    }

    public void determineWinner() {
        if (player.hasBust() ) {
            System.out.println("You lose!");
        } else if (dealer.hasBust()) {
            System.out.println("You win!");
        } else if (dealer.getHandValue() > player.getHandValue()) {
            System.out.println("You lose!");
        } else if (player.getHandValue() > dealer.getHandValue()) {
            System.out.println("You win!");
        } else {
            System.out.println("Push!");
        }

        System.out.println("Press Restart to play again");
    }

    public void hitPlayer() {
        // Hit player. Check if player busted.
        if (player.hit()) {
            determineWinner();
        } else {
            player.printHand();
            System.out.println("Current Player Hand Value: " + player.getHandValue());
        }

    }

    public void playerStays() {
        dealerTurn();
        determineWinner();
    }
}
