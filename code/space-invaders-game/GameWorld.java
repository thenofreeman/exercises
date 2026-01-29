import java.util.ArrayList;
import java.util.List;

import greenfoot.*;

public class GameWorld extends World {
    private GreenfootImage backgroundImage;
    private Button buttonPause;
    private Label labelScore;
    private User user;
    private boolean gameOverState;
    private ArrayList<Barrier> barriers;
    private ArrayList<SpaceInvader> invaders;

    private int invaderLevel;
    private int timerSpeed;
    private int fireSpeed;
    private int counter;
    private int sound;
    private Ship player;
    private int currentLevel;
    private int layers = 6;
    private int columns = 11;
    private int score;

    public GameWorld(User user) {
        super(1000, 700, 1);

        SpaceInvader spaceInvader = new SpaceInvader();

        this.barriers = new ArrayList<>();
        this.invaders = new ArrayList<>();

        this.user = user;

        player = new Ship();
        addObject(player, getWidth() / 2, getHeight() / 2 + getHeight() / 3 + getHeight() / 10);

        loadLevel(1);

        buttonPause = new Button("Pause");
        addObject(buttonPause, getWidth() - 50, 50);

        labelScore = new Label("Score: ", 30);
        addObject(labelScore, 100, 50);

        timerSpeed = 50;
        counter = 0;
        sound = 4;
        score = 0;
    }

    private void loadLevel(int level) {
        gameOverState = false;
        currentLevel = level;

        switch (level) {
            case 1:
                loadLevel1();
                break;
            case 2:
                loadLevel2();
                break;
            case 3:
                loadLevel3();
                break;
        }
    }

    private void loadLevel1() {
        layers = 3;
        columns = 6;
        // columns = 1;
        fireSpeed = 60;
        invaderLevel = 3;

        addSpaceInvaders(layers, columns);

        addBarriers(getWidth() / 2 + getWidth() / 4, 32, 2);
        addBarriers(getWidth() / 2, 32, 2);
        addBarriers(getWidth() / 4, 32, 2);
    }

    private void loadLevel2() {
        layers = 3;
        columns = 6;
        // columns = 1;
        fireSpeed = 100;
        invaderLevel = 4;

        addSpaceInvaders(layers, columns);
        addSpaceInvaders(layers, columns);

        addBarriers(getWidth() / 2, 32, 2);
    }

    private void loadLevel3() {
        layers = 3;
        columns = 11;
        // columns = 1;
        fireSpeed = 100;
        invaderLevel = 5;

        addSpaceInvaders(layers, columns);
    }

    public void gameOver(boolean playerWin) {
        for (Barrier b : barriers)
            removeObject(b);
        for (SpaceInvader s : invaders)
            removeObject(s);

        DBManager.saveUser(user);

        if (playerWin) {
            Greenfoot.playSound("WinLevel.mp3");

            if (currentLevel == 3)
                WorldManager.pushWorld(new PauseWorld("Level Completed!", "Start Over"));
            else
                WorldManager.pushWorld(new PauseWorld("Level Completed!", "Next Level"));

            loadLevel((currentLevel % 3) + 1);
        } else {
            Greenfoot.playSound("explosion.wav");

            WorldManager.pushWorld(new PauseWorld("Game Over", "Try Again"));
            gameOverState = true;
        }
    }

    private void addSpaceInvaders(int l, int c) {
        for (int i = 0; i < c; i++) {
            for (int j = 1; j <= l / 3; j++) {
                SpaceInvader spaceInvader = new SpaceInvader(3, j, i);
                int x = (int) ((i * (spaceInvader.getImage().getWidth() + 5)) + 55);
                int y = (int) ((j * (spaceInvader.getImage().getHeight() + 5)));
                this.invaders.add(spaceInvader);
                addObject(spaceInvader, x, y);
            }

            for (int j = 2; l / 3 < j && j <= 2 * l / 3; j++) {
                SpaceInvader spaceInvader = new SpaceInvader(2, j, i);
                int x = (int) ((i * (spaceInvader.getImage().getWidth() + 5)) + 55);
                int y = (int) ((j * (spaceInvader.getImage().getHeight() + 5)));
                this.invaders.add(spaceInvader);
                addObject(spaceInvader, x, y);
            }

            for (int j = 3; 2 * l / 3 < j && j <= l; j++) {
                SpaceInvader spaceInvader = new SpaceInvader(1, j, i);
                int x = (int) ((i * (spaceInvader.getImage().getWidth() + 5)) + 55);
                int y = (int) ((j * (spaceInvader.getImage().getHeight() + 5)));
                this.invaders.add(spaceInvader);
                addObject(spaceInvader, x, y);
            }
        }
    }

    public void addBarriers(int position, int width, int layer) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < layer; j++) {
                Barrier barrier = new Barrier();
                barriers.add(barrier);
                addObject(barrier, (position) + i * 2 - width, getHeight() / 2 + getHeight() / 4 + j * 2);
            }
        }
    }

    public void checkLeftEdge(List<SpaceInvader> invaders) {
        SpaceInvader invader = invaders.get(0);

        for (int i = 0; invaders.size() > i; i++)
            if ((invaders.get(i)).getColumn() < invader.getColumn())
                invader = invaders.get(i);

        if (invader.getX() + invader.getSpeed() - invader.getImage().getWidth() / 2 < 0)
            invaderLevel++;
    }

    public void checkRightEdge(List<SpaceInvader> invaders) {
        SpaceInvader invader = invaders.get(0);

        for (int i = 0; invaders.size() > i; i++)
            if ((invaders.get(i)).getColumn() > invader.getColumn())
                invader = invaders.get(i);

        if (invader.getX() + invader.getSpeed() + invader.getImage().getWidth() / 2 > getWidth())
            invaderLevel++;
    }

    public SpaceInvader findLowestInvader(List<SpaceInvader> invaders, int column) {
        SpaceInvader invader = new SpaceInvader(1, -1, 12);

        for (int i = 0; invaders.size() > i; i++)
            if ((invaders.get(i)).getColumn() == column)
                if ((invaders.get(i)).getLayer() >= invader.getLayer())
                    invader = invaders.get(i);

        if (invader.getLayer() == -1)
            return null;

        return invader;
    }

    public void spawnUFO(int x, int y) {
        if (Greenfoot.getRandomNumber(400) == 0)
            addObject(new UFO(), x, y);
    }

    public void act() {
        if (gameOverState) {
            WorldManager.swapCurrentWorld(new GameWorld(user));

            return;
        }

        if (Greenfoot.mouseClicked(buttonPause)) {
            DBManager.saveUser(user);
            WorldManager.pushWorld(new PauseWorld("Paused", "Resume"));

            return;
        }

        List<SpaceInvader> invaders = getObjects(SpaceInvader.class);
        labelScore.setLabel("Score : " + score);
        user.setCurrentScore(score);

        if (counter % timerSpeed == 0)
            Greenfoot.playSound("fastinvader" + (sound++ % 3 + 1) + ".wav");

        if (invaderLevel % 2 == 0)
            checkRightEdge(invaders);
        else
            checkLeftEdge(invaders);

        if (++counter % fireSpeed == 0) {
            int nFiringRows = Greenfoot.getRandomNumber(columns) + 1;
            for (int i = 0; i < nFiringRows; ++i) {
                SpaceInvader spaceInvader = findLowestInvader(invaders, Greenfoot.getRandomNumber(columns));

                if (spaceInvader != null)
                    spaceInvader.fire();
            }
        }

        switch (currentLevel) { // intended fallthrough
            case 3: spawnUFO(40, 400);
            case 2: spawnUFO(30, 300);
            case 1: spawnUFO(20, 200);
        }
    }

    public Ship getPlayer() {
        return player;
    }

    public int getTimerSpeed() {
        return timerSpeed;
    }

    public void setTimerSpeed(int timer) {
        timerSpeed = timer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int newScore) {
        score = newScore;
    }

    public void setInvaderLevel(int level) {
        invaderLevel = level;
    }

    public int getInvaderLevel() {
        return invaderLevel;
    }

    public int getLayers() {
        return layers;
    }

    public int getColumns() {
        return columns;
    }
}
