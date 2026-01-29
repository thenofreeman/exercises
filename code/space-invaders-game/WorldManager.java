import greenfoot.*;
import java.util.Stack;

public class WorldManager {
    private static Stack<World> worldStack = new Stack<>();

    public static void pushWorld(World world) {
        worldStack.push(world);
        Greenfoot.setWorld(getCurrentWorld());
    }

    public static void popWorld() {
        if (worldStack.isEmpty()) {
            System.out.println("Stack Empty.");
            return;
        }

        worldStack.pop();
        Greenfoot.setWorld(getCurrentWorld());
    }

    public static void popAll() {
        World w = null;
        while (!worldStack.isEmpty()) {
            w = getCurrentWorld();

            if (w instanceof MenuWorld)
                break;

            worldStack.pop();
        }

        if (w != null)
            Greenfoot.setWorld(w);
        else 
            Greenfoot.setWorld(new MenuWorld());
    }

    public static void swapCurrentWorld(World world) {
        worldStack.pop();
        worldStack.push(world);
        Greenfoot.setWorld(getCurrentWorld());
    }

    public static World getCurrentWorld() {
        return worldStack.isEmpty() 
            ? null
            : worldStack.peek();
    }
}
