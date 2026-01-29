import greenfoot.*;

public class StartupWorld extends World {
    private GreenfootImage backgroundImage;
    private Label instructionLabel;

    public StartupWorld() {    
        super(1000, 700, 1); 

        // ???
        WorldManager.pushWorld(this);

        backgroundImage = new GreenfootImage(getWidth(), getHeight());

        backgroundImage.setColor(Color.DARK_GRAY);
        backgroundImage.fill();
        setBackground(backgroundImage);

        instructionLabel = new Label("Space Re-Invaders\n\nPress Run to Begin...", 30);
        addObject(instructionLabel, getWidth()/2, getHeight()/2);
    }

    public void act() {
        WorldManager.pushWorld(new MenuWorld());
    }
}
