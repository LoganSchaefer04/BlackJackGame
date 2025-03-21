package blackjack.controllers;
import blackjack.*;
import blackjack.controllers.CardSelector;

import java.util.List;

public class BlackJackGame {
    private final Dealer dealer;
    private final Player player;
    private final Bank bank;
    private final CardSelector cardSelector;
    String result;
    public int roundCounter = 1;
    Hint hintMaker;

    public BlackJackGame() {
        cardSelector = new CardSelector("Random");

        dealer = new Dealer(cardSelector);
        player = new Player(cardSelector);
        bank = new Bank();
        hintMaker = new Hint();
        bank.setCurrency(100.0); // gives user 100 currency by default @ launch of game as there is no way to save the user's currency atm (NEEDS UPDATING)
        initRound();
    }

    public void initRound() {
        roundCounter++;
        bank.setBet(5.0); // CURRENTLY EVERY ROUND'S BET IS 5.0 CURRENCY WHEN UI IS FINISHED THIS WILL NEED UPDATING
        // Create initial hands for player and dealer and print cards and hand values.
        dealer.initHand();
        player.initHand();

    }

    public void determineWinner() {
        if (player.hasBust() ) {
            result = "You lose!";
        } else if (dealer.hasBust()) {
            bank.scoreWin();
            System.out.println("Scoring win!");
            result = "You win!";
        } else if (dealer.getHandValue() > player.getHandValue()) {
            result = "You Lose!";
        } else if (dealer.getHandValue() < player.getHandValue()) {
            if (player.getHandValue() == 21) {
                System.out.println("Scoring Blackjack!");
                bank.scoreBlackJack();
            } else {
                System.out.println("Scoring win!");
                bank.scoreWin();
            }
            result = "You Win!";
        } else {
            bank.scorePush();
            // bank.setCurrency(newTotalPush);
            result = "Push!";
        }
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

    public String getHint() {
        return hintMaker.getHint(dealer.getHand(), player.getHand());
    }
    public List<Card> getPlayerCards() {
        return player.getCards();
    }
    public List<Card> getDealerCards() {
        return dealer.getCards();
    }
    public int getPlayerHandValue() {
        return player.getHandValue();
    }
    public int getDealerUpCardValue() {
        return dealer.getCards().get(0).getValue();
    }
    public String getResult() {
        return result;
    }

    public String getCurrency() {
        return Double.toString(bank.getCurrency());
    }

    public int getDealerHandValue() {
        return dealer.getHandValue();
    }

}
