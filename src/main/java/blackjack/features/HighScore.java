package blackjack.features;

import java.io.PrintWriter;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

import static java.util.Comparator.comparingInt;


public class HighScore {
    private List<Score> scoresList;
    private double recentScore;
    private class Score {
        final String name;
        final double score;

        public Score(double score, String name) {
            this.name = name;
            this.score = score;
        }

        public double getScore() {
            return score;
        }
    }

    public HighScore() {
        scoresList = new ArrayList<>();
        readCurrentScores();
    }

    private void readCurrentScores() {
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/Text Files/Scores.txt"));

            while (scanner.hasNextLine()) {
                Score score = new Score(scanner.nextDouble(), scanner.nextLine());
                scoresList.add(score);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void insertScore(String name, double score) {
        Score newScore = new Score(score, name);
        scoresList.add(newScore);
        scoresList.sort(Comparator.comparingDouble(Score::getScore));
        recentScore = newScore.score;
    }

    public void insertScore(String name) {
        Score newScore = new Score(recentScore, name);
        scoresList.add(newScore);
        scoresList.sort(Comparator.comparingDouble(Score::getScore).reversed());
        String path = "src/main/resources/Text Files/Scores.txt";
        PrintWriter writer = null;
        File file = new File(path);
        System.out.println(file.canWrite());
        try {
            writer = new PrintWriter("src/main/resources/Text Files/Scores.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(Score score : scoresList) {
            System.out.println(score.score + " " + score.name);
            writer.println(score.score + " " + score.name);
            writer.flush();
        }
        writer.close();

    }

    public String getScore(int index) {
        Score score = scoresList.get(index);
        return score.score + " " + score.name;
    }

    public void setRecentScore(double score) {
        recentScore = score;
    }

    public String getRecentScore() {
        return Double.toString(recentScore);
    }
}