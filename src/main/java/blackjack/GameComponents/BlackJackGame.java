package blackjack.GameComponents;

import blackjack.features.Bank;
import blackjack.features.Hint;
import blackjack.features.CardCounting;

import java.util.List;

public class BlackJackGame {
    private final Dealer dealer;
    private final Player player;
    private final Bank bank;
    private CardSelector dealerCardSelector, playerCardSelector;
    String result;
    public int roundCounter = 1;
    Hint hintMaker;

    private CardCounting cardCounting;  // Declare CardCounting instance

    public BlackJackGame() {
        dealerCardSelector = new CardSelector("Random");
        playerCardSelector = new CardSelector("Random");
        dealer = new Dealer(dealerCardSelector);
        player = new Player(playerCardSelector);
        bank = new Bank();
        hintMaker = new Hint();
        cardCounting = new CardCounting(); // Initialize CardCounting
        bank.setCurrency(1000.0); // gives user 100 currency by default @ launch of game as there is no way to save the user's currency atm (NEEDS UPDATING)
        initRound();
    }

    public void initRound() {
        roundCounter++;
        player.resetHands();
        bank.setBet(5.0); // CURRENTLY EVERY ROUND'S BET IS 5.0 CURRENCY WHEN UI IS FINISHED THIS WILL NEED UPDATING
        // Create initial hands for player and dealer and print cards and hand values.
        dealer.initHand();
        player.initHand();

        // Update the card count for the initial cards dealt to the player and dealer
        for (Card card : player.getCards()) {
            cardCounting.updateRunningCount(card);  // Update count for player's cards
        }

        for (Card card : dealer.getCards()) {
            cardCounting.updateRunningCount(card);  // Update count for dealer's cards
        }

    }

    public void determineWinner() {
        do {
            if (player.hasBust()) {
                result = "You lose!";
            } else if (dealer.hasBust()) {
                bank.scoreWin(player.getHandBet());
                result = "You win!";
            } else if (dealer.getHandValue() > player.getHandValue()) {
                result = "You Lose!";
            } else if (dealer.getHandValue() < player.getHandValue()) {
                if (player.getHandValue() == 21) {
                    bank.scoreBlackJack(player.getHandBet());
                } else {
                    bank.scoreWin(player.getHandBet());
                }
                result = "You Win!";
            } else {
                bank.scorePush(player.getHandBet());
                result = "Push!";
            }
        } while (player.moveToNextHand());
    }

    public Card hitPlayer() {
        Card card = player.hit();
        cardCounting.updateRunningCount(card);  // Update running count with each card dealt
        if (player.hasBust()) {
            // Player busted, determine winner
            determineWinner();

        // Player did not bust, print new hand and value.
        }
        return card;

    }

    /**
     * Tells dealer to play turn, or to switch to next hand
     * @return true if there is another hand to play due to splitting.
     */
    public boolean playerStays() {
        // Player decided to stay, dealer will now play his turn or player will play next hand.
        if (player.moveToNextHand()) {
            return true;
        } else {
            dealer.playTurn(getPlayerHandValue());
            determineWinner();
            return false;
        }
    }

    public void tipDealer(int tipAmount) {
        player.tipDealer(bank.getCurrency(), tipAmount);
        bank.tipDealer(tipAmount);
    }

    public double split() {
        player.splitCurrentHand();

        for (Card card : player.getCards()) {
            cardCounting.updateRunningCount(card);
        }

        return bank.subtractMoney(player.getHandBet());
    }

    public void doubleDown() {
        // Double the player's bet, ensure it is treated as an integer
        int currentBet = player.getHandBet();
        bank.setCurrency(bank.getCurrency() - currentBet); // Deduct the current bet from the player's currency

        // Double the bet and set it (using the player's current hand instance)
        player.getHand().setBet(currentBet * 2);  // Update the bet amount as an integer
    }





    public int getRunningCount() {
        return cardCounting.getRunningCount();
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
    public int getPlayerHandValue() {return player.getHandValue();}
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

    public boolean splitabilibity() {
        return player.canSplit();
    }

    public boolean playerHasNextHand() {
        return player.hasNextHand();
    }

    public void nextSplitHand() {
        if (player.hasNextHand()) {
            player.moveToNextHand();
        }
    }

}
