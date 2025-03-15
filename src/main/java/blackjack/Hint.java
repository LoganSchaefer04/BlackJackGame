package blackjack;

public class Hint {

    private int[][] softHitChart = {
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    private int[][] hardHitChart = {
            {1, 1, 0, 0, 0, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 1, 1, 1, 1, 1}
    };

    public Hint() {
        //Empty body
    }


    public String getHint(Hand dealerHand, Hand playerHand) {
        if (playerHand.isSoft()) {
            if (playerHand.getHandValue() <= 17) {
                return "Hit!";
            } else {
                System.out.println("HERE");
                return buildHint(softHitChart, playerHand.getHandValue() - 18,
                        dealerHand.getCards().get(0).getValue() - 2);
            }
        } else {
            if (playerHand.getHandValue() <= 11) {
                return "Hit!";
            }
            if (playerHand.getHandValue() >= 17) {
                return "Stay!";
            }
            return buildHint(hardHitChart, playerHand.getHandValue() - 12,
                    dealerHand.getCards().get(0).getValue() - 2);
        }
    }


    public String buildHint(int[][] chart, int rowIndex, int columnIndex) {
        if (chart[rowIndex][columnIndex] == 1) {
            return "Hit!";
        } else {
            return "Stay!";
        }
    }



}
