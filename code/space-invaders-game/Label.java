import greenfoot.*;

public class Label extends Actor {
    int fontSize;
    Color color;

    public Label(String text, int fontSize) {
        this.fontSize = fontSize;
        this.color = Color.WHITE;

        GreenfootImage image = new GreenfootImage(text, fontSize, color, null);
        setImage(image);
    }

    public void setLabel(String label) {
        setImage(new GreenfootImage(label, fontSize, color, null));  
    }
}
