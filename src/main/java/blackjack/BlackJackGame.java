package blackjack;
import java.util.*;
public class BlackJackGame {
    private Dealer dealer;
    private Player player;
    private CardSelector cardSelector;
    public int roundCounter = 1;

    public BlackJackGame() {
        cardSelector = new CardSelector("Shuffle");
        dealer = new Dealer(cardSelector);
        player = new Player(cardSelector);
        initRound();
    }

    public void initRound() {
        roundCounter++;

        // Create initial hands for player and dealer and print cards and hand values.
        dealer.initHand();
        player.initHand();


    }

    public void determineWinner() {
        if (player.hasBust() ) {
            System.out.println("You lose!");
        } else if (dealer.hasBust()) {
            System.out.println("You win!");
        } else if (dealer.getHandValue() > player.getHandValue()) {
            System.out.println("You lose!");
        } else if (dealer.getHandValue() < player.getHandValue()) {
            System.out.println("You win!");
        } else {
            System.out.println("Push!");
        }

        System.out.println("Press Restart to play again");
    }

    public Card hitPlayer() {
        Card card = player.hit();
        if (player.hasBust()) {
            // Player busted, determine winner
            determineWinner();

        // Player did not bust, print new hand and value.
        }
        return card;

    }
    public void playerStays() {
        // Player decided to stay, dealer will now play his turn and then determine winner.
        dealer.playTurn(getPlayerHandValue());
        determineWinner();
    }


    public List<Card> getPlayerHand() {
        return player.getHand();
    }

    public List<Card> getDealerHand() {
        return dealer.getHand();
    }

    public int getPlayerHandValue() {
        return player.getHandValue();
    }

}
