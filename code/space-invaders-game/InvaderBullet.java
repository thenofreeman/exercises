import greenfoot.*;

public class InvaderBullet extends Actor {   
    private int speed;

    public InvaderBullet() {
        speed = 8;
    }

    private boolean checkCollision() {
        if (getOneIntersectingObject (ShipBullet.class) != null) {
            getWorld().removeObject(getOneIntersectingObject (ShipBullet.class));
            getWorld().removeObject(this);
            return true;
        }

        if (getOneIntersectingObject (Barrier.class) != null) {
            getWorld().removeObject(getOneIntersectingObject (Barrier.class));
            getWorld().removeObject(this);
            return true;
        } else if (getOneIntersectingObject (Ship.class) != null) {
            getWorld().gameOver(false);
            getWorld().removeObject(getOneIntersectingObject (Ship.class));
            getWorld().removeObject(this);
            return true;
        }

        return false;
    }

    public void act() {
        if (getY() >= getWorld().getHeight()-5) {
            getWorld().removeObject(this);
        } else {
            for (int i = 0; i < speed; i ++) {
                setLocation(getX(), getY() + 1);

                if (checkCollision()) {
                    break;
                }
            }
        }
    }    

    public GameWorld getWorld()
    {
        return (GameWorld)(super.getWorld());
    }
}
