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
    private static final double INITIAL_CURRENCY = 1000.0;
    private static final double DEFAULT_BET = 5.0;
    private double currentBet;


    public BlackJackGame() {
        dealerCardSelector = new CardSelector("Random");
        playerCardSelector = new CardSelector("Random");
        dealer = new Dealer(dealerCardSelector);
        player = new Player(playerCardSelector);
        bank = new Bank();
        hintMaker = new Hint();
        bank.setCurrency(INITIAL_CURRENCY);
        //bank.setBet(DEFAULT_BET);// gives user 100 currency by default @ launch of game as there is no way to save the user's currency atm (NEEDS UPDATING)
        initRound();
    }

    public void initRound() {
        System.out.println("Amount before bet");
        System.out.println(bank.getCurrency());
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
        return bank.subtractMoney(player.getHandBet());
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
