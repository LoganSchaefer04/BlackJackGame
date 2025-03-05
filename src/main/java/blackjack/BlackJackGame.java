package blackjack;
import java.util.*;
public class BlackJackGame {
    private Dealer dealer;
    private CardSelector cardSelector;
    private boolean gameOver;
    private Scanner scanner;
    public int roundCounter = 1;

    public BlackJackGame() {
        cardSelector = new CardSelector("random");
        dealer = new Dealer(cardSelector);
        scanner = new Scanner(System.in);
        gameOver = false;
    }

    public void initRound() {
        System.out.printf("\n ---------- ROUND %d ----------\n", roundCounter);
        roundCounter++;
        dealer.initHand();
        gameOver = false;

    }

    public void dealerTurn(){
        System.out.println("Now dealer turn");
        dealer.playTurn();
        System.out.println("\nDealer's final hand: " +dealer.getHandVal());

    }

    public void determineWinner(){

    }

    public boolean askPlayAgain() {
        System.out.println("Would you like to play again?\nY: yes\nN: no");
        String input = scanner.nextLine();

        while (true) {
            if (input.equalsIgnoreCase("Y")) {
                return true;
            } else if (input.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
                System.out.println("Would you like to play again?\nY: yes\nN: no");
                input = scanner.nextLine();
            }
        }
    }

    public void playGame(){
        boolean stillPlaying = true;
        System.out.println("Please Be 21 Starting.......");

        while(stillPlaying){
            initRound();
            if(!gameOver){
                //playerTurn
            }
            if(!gameOver){
                dealerTurn();
            }
            if (!gameOver){
                determineWinner();
            }
            stillPlaying = askPlayAgain();

        }
        System.out.println("Player has ended game");
        scanner.close();
    }


    public static void main(String[] args) {
        BlackJackGame game = new BlackJackGame();
        game.playGame();
    }
}
