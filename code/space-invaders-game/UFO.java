import greenfoot.*;

public class UFO extends Actor {

    public void act() {
        if (getX() >= 999) {
            getWorld().removeObject(this);
        } else {
            for (int i = 0; i < 5; i ++) {
                setLocation(getX()+1, getY());
            }
        }
    }

    public GameWorld getWorld() {
        return (GameWorld)(super.getWorld());
    }
}
