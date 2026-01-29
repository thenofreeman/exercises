import greenfoot.*;

public class Barrier extends Actor {

    public void act() {
        if (getOneIntersectingObject(ShipBullet.class) != null) { 
            getWorld().removeObject(this);
        }
    }    
}