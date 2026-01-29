import greenfoot.*;

public class ShipBullet extends Actor {
    int speed;
    boolean check;

    public ShipBullet() {
        speed = 10;
    }

    public void act() {
        if (getY() <= 0) {
            getWorld().removeObject(this);
        } else {
            for (int i = 0; i < speed; i ++) {
                setLocation(getX(), getY() - 1);
                if (checkCollision()) {
                    break;
                }
            }
        }
    }

    public boolean checkCollision() {
        if (getOneIntersectingObject (SpaceInvader.class) != null) {
            getWorld().removeObject(getOneIntersectingObject (SpaceInvader.class));
            if (getWorld().getTimerSpeed() >= 25) {
                getWorld().setTimerSpeed(getWorld().getTimerSpeed()-1);
            }

            Greenfoot.playSound("invaderkilled.wav");
            getWorld().setScore(getWorld().getScore()+ 50);

            if (getWorld().getObjects(SpaceInvader.class).size() == 0) {
                getWorld().gameOver(true);
            }
            getWorld().removeObject(this);
            return true;
        } else if (getOneIntersectingObject (Barrier.class) != null) {
            getWorld().removeObject(getOneIntersectingObject (Barrier.class));
            getWorld().removeObject(this);

            return true;
        } else if (getOneIntersectingObject (UFO.class) != null) {
            getWorld().removeObject(getOneIntersectingObject (UFO.class));
            getWorld().setScore(getWorld().getScore()+ 300);
            getWorld().removeObject(this);
            
            return true;
        }

        return false;
    }

    public GameWorld getWorld() {
        return (GameWorld)(super.getWorld());
    }
}
