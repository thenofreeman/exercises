import greenfoot.*;

public class SpaceInvader extends Actor {
    private int type;
    private boolean form;
    private int speed;
    private int layer;
    private int column;
    private int timer ;
    private int level ;
    private int reloadTime;
    private int reloadCounter;
    private int explosion;
    private boolean image;

    public SpaceInvader() {
        this(1,1,1);
    }

    public SpaceInvader(int invaderType, int invaderLayer, int invaderColumn) {
        type = invaderType;
        form = true;
        speed= 20;
        layer = invaderLayer;
        column = invaderColumn;
        timer = 1;
        level = 0;
        reloadTime=50;
        reloadCounter = 0;
        explosion = 0;
        image = false;

        setImage(new GreenfootImage("Alien " + String.valueOf(type) + "A.png"));
    }

    public void dropDown() {
        if (level <getWorld().getInvaderLevel()) {
            level++;

            speed *= -1;
            setLocation(getX(), getY() + getImage().getHeight());
        }
    }

    private void checkGameOver() {
        if (getY() >= getWorld().getHeight()/2 + getWorld().getHeight()/3 + getWorld().getHeight()/10) {
            getWorld().gameOver(false);
        }
    }

    public void fire() {
        if (reloadCounter == 0 && explosion == 0) {
            if (this != null) {
                getWorld().addObject(new InvaderBullet(), getX(), getY() + getImage().getHeight()/2);
                
                reloadCounter = reloadTime;
            }
        }
    }

    public void act() {
        if (reloadCounter > 0) {
            reloadCounter--;
        }
        timer ++;

        if (timer % getWorld().getTimerSpeed() == 0) {
            dropDown();
            setLocation(getX() + speed, getY());

            image = !image;

            if (!image)
                setImage(new GreenfootImage("Alien " + String.valueOf(type) + "A.png"));
            else 
                setImage(new GreenfootImage("Alien " + String.valueOf(type) + "B.png"));
        }

        checkGameOver();
    }

    public GameWorld getWorld() {
        return (GameWorld)(super.getWorld());
    }

    public int getColumn() {
        return column;
    }

    public int getLayer() {
        return layer;
    }

    public void setSpeed(int newSpeed) {
        speed = newSpeed;
    }

    public int getSpeed() {
        return speed;
    }
}
