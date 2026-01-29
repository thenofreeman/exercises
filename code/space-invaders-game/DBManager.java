import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBManager {
    private static final String SCORES_FILE = "scores.txt";

    public DBManager() { }

    public static User getUser(String username) {
        int newHighscore = 0;
        boolean newUser = true;

        for (UserScore s : getScores()) {
            if (s.getUsername().equals(username)) {
                newHighscore = s.getScore();
                newUser = false;

                break;
            }
        }

        return new User(username, newUser, newHighscore);
    }

    public static void saveUser(User u) {
        boolean userFound = false;

        try {
            Path path = Paths.get(SCORES_FILE);

            if (Files.notExists(path))
                Files.createFile(path);

            List<String> lines = Files.readAllLines(path);

            for (int i = 0; i < lines.size(); ++i) {

                if (lines.get(i).contains(u.getUsername())) {
                    userFound = true;

                    if (u.getCurrentScore() > Integer.parseInt(lines.get(i).split(",")[1].trim()))
                        lines.set(i, u.getUsername() + ", " + u.getCurrentScore());
                         
                    break;
                }
            }

            if (!userFound)
                lines.add(u.getUsername() + ", " + u.getCurrentScore());

            Files.write(Paths.get(SCORES_FILE), lines);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<UserScore> getScores() {
        ArrayList<UserScore> scores = new ArrayList<>();

        try {
            File file = new File(SCORES_FILE);

            if (!file.exists())
                file.createNewFile();

            try (Scanner scanner = new Scanner(new File(SCORES_FILE))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] tokens = line.split(",");

                    scores.add(new UserScore(tokens[0], Integer.parseInt(tokens[1].trim())));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return scores;
    }
    
}
