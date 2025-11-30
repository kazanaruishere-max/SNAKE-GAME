import greenfoot.*;

/**
 * BodySegment - Segmen tubuh ular
 */
public class BodySegment extends Actor
{
    public BodySegment()
    {
        // Buat gambar tubuh ular
        GreenfootImage body = new GreenfootImage(18, 18);
        body.setColor(new Color(0, 200, 0));
        body.fillRect(0, 0, 18, 18);
        setImage(body);
    }
}