import greenfoot.*;

public class PauseWorld extends World {
    private Label labelTitle;
    private Button buttonScorelist;
    private Button buttonResume;
    private Button buttonQuit;

    public PauseWorld(String titleString, String optionString) {
        super(1000, 700, 1);

        GreenfootImage backgroundImage;
        backgroundImage = new GreenfootImage(getWidth(), getHeight());
        backgroundImage.setColor(Color.DARK_GRAY);
        backgroundImage.fill();
        setBackground(backgroundImage);

        buttonScorelist = new Button("Scores");
        addObject(buttonScorelist, 100, 50);
        
        labelTitle = new Label(titleString, 25);
        addObject(labelTitle, getWidth()/2, 200);

        buttonResume = new Button(optionString);
        addObject(buttonResume, getWidth()/2, getHeight()/2);

        buttonQuit = new Button("Main Menu");
        addObject(buttonQuit, getWidth()/2, getHeight()/2 + 100);

    }

    public void act() {
        if (Greenfoot.mouseClicked(buttonResume))
            WorldManager.popWorld();
        else if (Greenfoot.mouseClicked(buttonQuit))
            WorldManager.popAll();
        else if (Greenfoot.mouseClicked(buttonScorelist))
            WorldManager.pushWorld(new ScorelistWorld());
    }
}
