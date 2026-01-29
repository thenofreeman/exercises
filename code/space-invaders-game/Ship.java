import greenfoot.*;

public class Ship extends Actor {
    private int speed;
    private int counter;
    private int RELOAD_TIME = 40;

    public Ship() {
        speed = 5;
        counter = 0;
    }

    public void act() {
        if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d"))
            setLocation(getX() + speed, getY());

        if (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a"))
            setLocation(getX() - speed, getY());

        if (counter > 0) {
            counter --;
        }

        if ((Greenfoot.isKeyDown("up") || (Greenfoot.isKeyDown("space")) || (Greenfoot.isKeyDown("w"))) && counter == 0) {
            ShipBullet shipBullet = new ShipBullet();
            getWorld().addObject(shipBullet, getX() , getY());
            Greenfoot.playSound("shoot.wav");
            counter = RELOAD_TIME;
        }
    }
}
