import greenfoot.*;

public class TransitionWorld extends World {
    private GreenfootImage backgroundImage;
    private World transitionWorld;
    private Label labelTransition;

    public TransitionWorld(String prompt, World transitionWorld) {
        super(1000, 700, 1); 

        this.transitionWorld = transitionWorld;

        backgroundImage = new GreenfootImage(getWidth(), getHeight());
        backgroundImage.setColor(Color.DARK_GRAY);
        backgroundImage.fill();
        setBackground(backgroundImage);

        labelTransition = new Label(prompt + "\n\nClick to begin.", 30);
        addObject(labelTransition, getWidth()/2, getHeight()/2);
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            WorldManager.swapCurrentWorld(transitionWorld);
        }
    }
}
