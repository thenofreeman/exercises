public class User {
    String username;
    int currentScore;
    int highscore;
    boolean newUser;

    public User(String username, boolean newUser, int highscore) {
        this.username = username;
        this.currentScore = 0;
        this.highscore = highscore;
        this.newUser = newUser;
    }

    public User(String username, boolean newUser) {
        this.username = username;
        this.currentScore = 0;
        this.highscore = 0;
        this.newUser = newUser;
    }

    public User(String username) {
        this.username = username;
        this.currentScore = 0;
        this.highscore = 0;
        this.newUser = true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }
}
