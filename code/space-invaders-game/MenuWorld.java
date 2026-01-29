import greenfoot.*;

public class MenuWorld extends World {
    private TextField usernameField;
    private Button buttonPlay;
    private Button buttonQuit;
    private Button buttonScorelist;
    private Label usernamePrompt;

    public MenuWorld() {    
        super(1000, 700, 1); 

        GreenfootImage backgroundImage;
        backgroundImage = new GreenfootImage(getWidth(), getHeight());
        backgroundImage.setColor(Color.DARK_GRAY);
        backgroundImage.fill();
        setBackground(backgroundImage);

        buttonScorelist = new Button("Scores");
        addObject(buttonScorelist, 100, 50);

        buttonQuit = new Button("Quit");
        addObject(buttonQuit, getWidth() - 50, 50);
        
        usernamePrompt = new Label("Enter Your Username:", 25);
        addObject(usernamePrompt, getWidth()/2, getHeight()/2 - 50);
        
        usernameField = new TextField(300, 40);
        addObject(usernameField, getWidth()/2, getHeight()/2);
        
        buttonPlay = new Button("Play");
        addObject(buttonPlay, getWidth()/2, getHeight()/2 + 100);
    }
    
    public void act() {
        if (Greenfoot.mouseClicked(buttonQuit)) {
            WorldManager.popWorld();
            Greenfoot.stop();
        } else if (Greenfoot.mouseClicked(buttonScorelist)) {
            WorldManager.pushWorld(new ScorelistWorld());
        } else if (Greenfoot.mouseClicked(buttonPlay)) {
            User user = login(usernameField.getText().trim());

            String prompt;

            if (user.isNewUser()) {
                prompt = "Hi new user '" + user.getUsername() + "'!\nLoading level one.";
                DBManager.saveUser(user);
            } else {
                prompt = "Welcome back '" + user.getUsername() + "'!\nLoading level one.";
            }

            WorldManager.pushWorld(new TransitionWorld(prompt, new GameWorld(user)));
        }
    }

    public User login(String username) {
        return DBManager.getUser(!username.isEmpty() ? username : "Anonymous");
    }
}