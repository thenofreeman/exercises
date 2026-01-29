import greenfoot.*;

public class TextField extends Actor {
    private GreenfootImage image;
    private String text = "";
    
    public TextField(int width, int height) {
        image = new GreenfootImage(width, height);

        updateImage();
    }
    
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            String input = Greenfoot.ask("Enter username:");
            if (input != null) {
                text = input;
                updateImage();
            }
        }
    }
    
    private void updateImage() {
        image.setColor(Color.WHITE);
        image.fill();
        image.setColor(Color.BLACK);
        image.drawRect(0, 0, image.getWidth()-1, image.getHeight()-1);
        
        Font font = new Font("Arial", false, false, 20);
        image.setFont(font);
        image.setColor(Color.BLACK);
        image.drawString(text, 10, image.getHeight() - 10);
        
        setImage(image);
    }
    
    public String getText() {
        return text;
    }
}