package blackjack.GameComponents;

import blackjack.features.Bank;
import blackjack.features.Hint;

import java.util.List;

public class BlackJackGame {
    private final Dealer dealer;
    private final Player player;
    private final Bank bank;
    private CardSelector dealerCardSelector, playerCardSelector;
    String result;
    public int roundCounter = 1;
    Hint hintMaker;
    boolean roundIsOver = false;

    public BlackJackGame() {
        dealerCardSelector = new CardSelector("Random");
        playerCardSelector = new CardSelector("Random");
        dealer = new Dealer(dealerCardSelector);
        player = new Player(playerCardSelector);
        bank = new Bank();
        hintMaker = new Hint();
        bank.setCurrency(1000.0); // gives user 100 currency by default @ launch of game as there is no way to save the user's currency atm (NEEDS UPDATING)
        initRound();
    }

    public void initRound() {
        roundIsOver = false;
        roundCounter++;
        player.resetHands();
        bank.setBet(5.0); // CURRENTLY EVERY ROUND'S BET IS 5.0 CURRENCY WHEN UI IS FINISHED THIS WILL NEED UPDATING
        // Create initial hands for player and dealer and print cards and hand values.
        dealer.initHand();
        player.initHand();

    }

    public void determineWinner() {
        roundIsOver = true;
        for (Hand hand : player.getHandsList()) {
            if (hand.hasBust()) {
                hand.setResult("You Lose!");
            } else if (dealer.hasBust()) {
                bank.scoreWin(player.getHandBet());
                hand.setResult("You Win!");
            } else if (dealer.getHandValue() > hand.getHandValue()) {
                hand.setResult("You Lose!");
            } else if (dealer.getHandValue() < hand.getHandValue()) {
                if (player.getHandValue() == 21) {
                    bank.scoreBlackJack(hand.getBet());
                } else {
                    bank.scoreWin(hand.getBet());
                }
                hand.setResult("You Win!");
            } else {
                bank.scorePush(hand.getBet());
                hand.setResult("Push!");
            }
        }
    }

    public Card hitPlayer() {
        Card card = player.hit();
        if (player.hasBust()) {
            player.getCurrentHand().setIsOver();
            player.getCurrentHand().setResult("You Lose!");

            if (player.hasAllHandsOver()) {
                if (player.hasAnyStays()) {
                    System.out.println("Playing dealer turn");
                    roundIsOver = true;
                    dealer.playTurn();
                }
                determineWinner();
            }
        }
        return card;
    }

    /**
     * Tells dealer to play turn, or to switch to next hand
     * @return true if there is another hand to play due to splitting.
     */
    public boolean playerStays() {
        player.getCurrentHand().setIsOver();
        player.getCurrentHand().setIsStayed();
        // Player decided to stay, dealer will now play his turn or player will play next hand.
        if (!player.hasAllHandsOver()) {
            player.moveToOpenHand();
            return true;
        } else {
            dealer.playTurn();
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
        return bank.subtractMoney(player.getHandBet());
    }

    public void moveToPreviousHand() {
        player.moveToPreviousHand();
    }
    public void moveToForwardHand() {
        player.moveToForwardHand();
    }

    public String getHint() {
        return hintMaker.getHint(dealer.getHand(), player.getCurrentHand());
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
        return player.getCurrentHand().getResult();
    }

    public String getCurrency() {
        return Double.toString(bank.getCurrency());
    }
    public boolean roundIsOver() {
        return roundIsOver;
    }

    public int getCurrentHandIndex() {
        return player.getCurrentHandIndex();
    }

    public boolean splitabilibity() {
        return player.canSplit();
    }

    public boolean playerHasNextHand() {
        return player.hasNextHand();
    }
    public boolean playerHasPreviousHand() {
        return player.hasPreviousHand();
    }

    public void nextSplitHand() {
        if (player.hasNextHand()) {
            player.moveToOpenHand();
        }
    }

    public boolean currentHandIsOver() {
        return player.getCurrentHand().isOver();
    }

    public boolean playerHasAnyStays() {
        return player.hasAnyStays();
    }

}
