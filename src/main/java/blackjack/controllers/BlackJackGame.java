package blackjack.controllers;
import blackjack.*;
import blackjack.controllers.CardSelector;

import java.util.List;

public class BlackJackGame {
    private final Dealer dealer;
    private final Player player;
    private final Bank bank;
    private final CardSelector cardSelector;
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
        player.resetHands();
        player.initHand();

    }

    public String determineWinner() {
        double blackjackPayout = bank.getBet() * 2.5;
        double regularPayout = bank.getBet() * 2;
        double newTotalCurrencyOnWin = (bank.getCurrency() + regularPayout);
        double newTotalCurrencyOnBlackJack = (bank.getCurrency() + blackjackPayout);
        double newTotalPush = (bank.getCurrency() + bank.getBet());
        if (player.hasBust() ) {
            System.out.println("New total currency = " + bank.getCurrency());
            return "You lose!";
        } else if (dealer.hasBust()) {
            bank.setCurrency(newTotalCurrencyOnWin);
            System.out.println("New total currency = " + bank.getCurrency());
            return ("You win!");
        } else if (dealer.getHandValue() > player.getHandValue()) {
            System.out.println("New total currency = " + bank.getCurrency());
            return "You lose!";
        } else if (dealer.getHandValue() < player.getHandValue()) {
            if(player.getHandValue() == 21){
                bank.setCurrency(newTotalCurrencyOnBlackJack);
            } else {
                bank.setCurrency(newTotalCurrencyOnWin);
            }
            System.out.println("New total currency = " + bank.getCurrency());
            return "You win!";
        } else {
            System.out.println("Push!");
            bank.setCurrency(newTotalPush);
            return "Push!";
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
    }

    public String getHint() {
        return hintMaker.getHint(dealer.getHand(), player.getHand());
    }
    public List<Card> getPlayerCards() {
        return player.getActiveHand().getCards();
    }

    public List<Card> getDealerCards() {
        return dealer.getCards();
    }
    public int getPlayerHandValue() {
        int val = player.getActiveHand().getHandValue();
        return val;
    }


    public int getDealerUpCardValue() {
        return dealer.getCards().get(0).getValue();
    }

    public int getDealerHandValue() {
        return dealer.getHandValue();
    }

    public boolean canSplit() {
        return player.canSplit() && bank.canSplit();
    }

    public void split() {
        player.split();
        bank.applySplitBet();
    }

    public boolean playerHasNextHand() {
        return player.hasNextHand();
    }

    public void moveToNextHand() {
        player.moveToNextHand();
    }


}
