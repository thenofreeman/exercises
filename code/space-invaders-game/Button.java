import greenfoot.*;

public class Button extends Actor {
    private String text;
    
    public Button(String text) {
        this.text = text;

        GreenfootImage image = new GreenfootImage(150, 50);
        image.setColor(Color.LIGHT_GRAY);
        image.fill();
        image.setColor(Color.BLACK);
        image.drawRect(0, 0, 149, 49);
        
        Font font = new Font("Arial", true, false, 20);
        image.setFont(font);
        image.setColor(Color.BLACK);
        
        int textWidth = image.getWidth();
        image.drawString(text, (150 - textWidth) / 2 + 10, 30);
        
        setImage(image);
    }
}