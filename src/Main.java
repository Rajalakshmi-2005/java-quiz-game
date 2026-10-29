import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String QUESTION_FILE = "src/questions.txt";
    private static final String SCORE_FILE = "highscore.txt";

    private static List<Question> loadQuestions() {
        List<Question> list = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Path.of(QUESTION_FILE));
            for (String line : lines) {
                if (line.isBlank()) continue;
                String[] p = line.split("\\|");
                if (p.length != 6) {
                    System.out.println("Skipping badly formatted line: " + line);
                    continue;
                }
                try {
                    String[] options = {p[1], p[2], p[3], p[4]};
                    int correct = Integer.parseInt(p[5].trim());
                    list.add(new Question(p[0], options, correct));
                } catch (NumberFormatException e) {
                    System.out.println("Skipping line with a bad answer number: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read " + QUESTION_FILE);
        }
        return list;
    }

    private static int loadHighScore() {
        try {
            Path path = Path.of(SCORE_FILE);
            if (Files.exists(path)) {
                return Integer.parseInt(Files.readString(path).trim());
            }
        } catch (IOException | NumberFormatException e) {
            // no valid saved score, so start from 0
        }
        return 0;
    }

    private static void saveHighScore(int score) {
        try {
            Files.writeString(Path.of(SCORE_FILE), String.valueOf(score));
        } catch (IOException e) {
            System.out.println("Could not save the high score.");
        }
    }

    private static int readAnswer(Scanner sc, int max) {
        while (true) {
            System.out.print("Your answer (1-" + max + "): ");
            try {
                int a = Integer.parseInt(sc.nextLine().trim());
                if (a >= 1 && a <= max) return a;
            } catch (NumberFormatException e) {
                // fall through to the message below
            }
            System.out.println("Please enter a number between 1 and " + max + ".");
        }
    }

    private static int playRound(Scanner sc, List<Question> questions) {
        Collections.shuffle(questions);
        int score = 0;

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("\nQ" + (i + 1) + ". " + q.getText());

            String[] opts = q.getOptions();
            for (int j = 0; j < opts.length; j++) {
                System.out.println((j + 1) + ". " + opts[j]);
            }

            int answer = readAnswer(sc, opts.length);

            if (answer == q.getCorrectOption()) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong. The answer is: " + opts[q.getCorrectOption() - 1]);
            }
        }
        return score;
    }

    public static void main(String[] args) {
        List<Question> questions = loadQuestions();
        if (questions.isEmpty()) {
            System.out.println("No questions found. Check that " + QUESTION_FILE + " is next to the src folder.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        int highScore = loadHighScore();
        String again;

        System.out.println("=== Java Quiz Game ===");
        System.out.println("High score: " + highScore);

        do {
            int score = playRound(sc, questions);
            System.out.println("\nYou scored " + score + " out of " + questions.size());

            if (score > highScore) {
                highScore = score;
                saveHighScore(highScore);
                System.out.println("New high score!");
            }

            System.out.print("Play again? (y/n): ");
            again = sc.nextLine().trim().toLowerCase();
        } while (again.equals("y"));

        System.out.println("Thanks for playing! High score: " + highScore);
        sc.close();
    }
}