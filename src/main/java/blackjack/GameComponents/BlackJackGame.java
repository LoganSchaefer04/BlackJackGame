package blackjack.GameComponents;

import blackjack.features.Bank;
import blackjack.features.Hint;

import java.util.List;

public class BlackJackGame {
    private final Dealer dealer;
    private final Player player;
    private final Bank bank;
    private Hint hintMaker;
    private boolean revealedCards = false;
    private boolean roundOver = false;
    public int roundCounter = 1;
    private static final double INITIAL_CURRENCY = 1000.0;
    private static final double DEFAULT_BET = 5.0;
    private double currentBet;


    public BlackJackGame() {
        CardSelector cardSelector = new CardSelector("Random");
        dealer = new Dealer(cardSelector);
        player = new Player(cardSelector);
        bank = new Bank();
        hintMaker = new Hint();
        bank.setCurrency(INITIAL_CURRENCY);
        //bank.setBet(DEFAULT_BET);// gives user 100 currency by default @ launch of game as there is no way to save the user's currency atm (NEEDS UPDATING)
        initRound();
    }

    public void initRound() {
        System.out.println("Amount before bet");
        System.out.println(bank.getCurrency());
        revealedCards = false;
        roundCounter++;
        currentBet = bank.getBet();
        bank.subtractMoney(currentBet);
        System.out.println("Amount after bet");
        System.out.println(bank.getCurrency());
        player.resetHands();
        dealer.initHand();
        player.initHand();
    }

    public void determineWinner() {
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

    /**
     * Tells the player to hit
     * @return If the player busted.
     */
    public boolean hitPlayer() {
        if (player.hit()) {
            if (player.hasAnyStays()) {
                dealer.playTurn();

            }
            determineWinner();
            if (player.hasAllHandsOver()) {
                roundOver = true;
            }
            return true;
        }

        return false;
    }

    /**
     * Tells dealer to play turn, or to switch to next hand
     * @return true if there is another hand to play due to splitting.
     */
    public boolean playerStays() {
        // Player decided to stay, dealer will now play his turn or player will play next hand.
        player.stay();

        if (!player.hasAllHandsOver()) {
            return true;
        } else {
            dealer.playTurn();
            determineWinner();
            roundOver = true;
            return false;
        }

    }

    /**
     *
     * @param tipAmount
     */
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
    public boolean dealerHasPlayed() {
        return dealer.hasPlayed();
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
    public boolean isRoundOver() {
        return roundOver;
    }

    public String recentCardRank() {
        return player.recentCardRank();
    }

    public String recentCardSuit() {
        return player.recentCardSuit();
    }

    public String getCardCount() {
        return Integer.toString(player.getCardCount());
    }

    public int getDealerRecentValue() {
        return dealer.getRecentValue();
    }

    public List<String> getDealerCardNames() {
        return dealer.getDealerCardNames();
    }
    public int getDealerCardValue(int index) {
        return dealer.getCardValue(index);
    }

}
