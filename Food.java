import greenfoot.*;

/**
 * Food - Makanan untuk ular
 */
public class Food extends Actor
{
    private int animationCount = 0;
    
    public Food()
    {
        updateImage();
    }
    
    public void act()
    {
        // Animasi makanan berkedip
        animationCount++;
        if (animationCount % 10 == 0) {
            updateImage();
        }
    }
    
    private void updateImage()
    {
        GreenfootImage food = new GreenfootImage(16, 16);
        
        // Ganti warna secara bergantian untuk efek berkedip
        if ((animationCount / 10) % 2 == 0) {
            food.setColor(Color.RED);
        } else {
            food.setColor(new Color(255, 100, 100));
        }
        
        food.fillOval(0, 0, 16, 16);
        setImage(food);
    }
}