import java.util.ArrayList;

import greenfoot.*;

public class ScorelistWorld extends World {
    private GreenfootImage backgroundImage;
    private Label labelHeading;
    private Button buttonBack;
    private Label listOfScores;

    public ScorelistWorld() {    
        super(1000, 700, 1); 

        backgroundImage = new GreenfootImage(getWidth(), getHeight());

        backgroundImage.setColor(Color.DARK_GRAY);
        backgroundImage.fill();
        setBackground(backgroundImage);
        
        labelHeading = new Label("Top Scores", 30);
        addObject(labelHeading, getWidth()/2, getHeight()/2 - 100);

        buttonBack = new Button("Back");
        addObject(buttonBack, 100, 50);

        ArrayList<UserScore> scores = DBManager.getScores();
        String labelTopScores = "";

        if (!scores.isEmpty()) {
            scores.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

            for (int i = 0; i < 5; ++i)
            {
                if (i >= scores.size()) break;

                UserScore s = scores.get(i);
                labelTopScores += s.getUsername() + " " + s.getScore() + "\n";
            }
        }

        if (labelTopScores.isEmpty())
            labelTopScores = "No scores to display:\nStart playing to add new scores.";

        labelHeading = new Label(labelTopScores, 30);
        addObject(labelHeading, getWidth()/2, getHeight()/2);

    }

    public void act() {
        if (Greenfoot.mouseClicked(buttonBack)) {
            removeObject(labelHeading);
            removeObject(buttonBack);

            WorldManager.popWorld();
        }
    }
}

