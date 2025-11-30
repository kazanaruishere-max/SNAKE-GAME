import greenfoot.*;
import java.util.ArrayList;

/**
 * Snake - Class utama untuk ular
 * Mengontrol pergerakan dan pertumbuhan ular
 */
public class Snake extends Actor
{
    private ArrayList<BodySegment> body;
    private String direction = "right";
    private String nextDirection = "right";
    private int actCount = 0;
    private MyWorld world;
    private boolean hasEaten = false;
    private boolean isDead = false;
    
    public Snake(MyWorld w)
    {
        world = w;
        body = new ArrayList<BodySegment>();
        
        // Set gambar kepala
        GreenfootImage head = new GreenfootImage(20, 20);
        head.setColor(Color.GREEN);
        head.fillOval(0, 0, 20, 20);
        setImage(head);
    }
    
    protected void addedToWorld(World w)
    {
        // Tambahkan 3 segmen tubuh awal
        for (int i = 1; i <= 3; i++) {
            BodySegment segment = new BodySegment();
            world.addObject(segment, getX() - i, getY());
            body.add(segment);
        }
    }
    
    public void act()
    {
        // Jangan lakukan apapun jika sudah mati
        if (isDead) {
            return;
        }
        
        checkKeys();
        
        actCount++;
        if (actCount >= world.getGameSpeed()) {
            actCount = 0;
            move();
        }
    }
    
    private void checkKeys()
    {
        if (Greenfoot.isKeyDown("up") && !direction.equals("down")) {
            nextDirection = "up";
        }
        else if (Greenfoot.isKeyDown("down") && !direction.equals("up")) {
            nextDirection = "down";
        }
        else if (Greenfoot.isKeyDown("left") && !direction.equals("right")) {
            nextDirection = "left";
        }
        else if (Greenfoot.isKeyDown("right") && !direction.equals("left")) {
            nextDirection = "right";
        }
    }
    
    private void move()
    {
        direction = nextDirection;
        
        int oldX = getX();
        int oldY = getY();
        
        // Gerakkan kepala
        if (direction.equals("up")) {
            setLocation(getX(), getY() - 1);
        }
        else if (direction.equals("down")) {
            setLocation(getX(), getY() + 1);
        }
        else if (direction.equals("left")) {
            setLocation(getX() - 1, getY());
        }
        else if (direction.equals("right")) {
            setLocation(getX() + 1, getY());
        }
        
        // Cek tabrakan dengan dinding
        if (getX() < 0 || getX() >= world.getWidth() || 
            getY() < 0 || getY() >= world.getHeight()) {
            isDead = true;
            world.endGame();
            return;
        }
        
        // Cek tabrakan dengan tubuh sendiri
        if (isOccupied(getX(), getY())) {
            isDead = true;
            world.endGame();
            return;
        }
        
        // Cek makan makanan
        Food food = (Food) getOneIntersectingObject(Food.class);
        if (food != null) {
            hasEaten = true;
            world.increaseScore();
            world.spawnFood();
            
            // Increase speed setiap 50 points
            if ((world.getScore() % 50 == 0) && world.getScore() > 0) {
                world.increaseSpeed();
            }
        }
        
        // Gerakkan tubuh
        moveBody(oldX, oldY);
    }
    
    private void moveBody(int oldHeadX, int oldHeadY)
    {
        if (body.isEmpty()) return;
        
        // Simpan posisi segmen terakhir
        BodySegment lastSegment = body.get(body.size() - 1);
        int lastX = lastSegment.getX();
        int lastY = lastSegment.getY();
        
        // Gerakkan setiap segmen ke posisi segmen di depannya
        for (int i = body.size() - 1; i > 0; i--) {
            BodySegment current = body.get(i);
            BodySegment previous = body.get(i - 1);
            current.setLocation(previous.getX(), previous.getY());
        }
        
        // Segmen pertama mengikuti kepala
        body.get(0).setLocation(oldHeadX, oldHeadY);
        
        // Jika baru makan, tambah segmen baru di ekor
        if (hasEaten) {
            BodySegment newSegment = new BodySegment();
            world.addObject(newSegment, lastX, lastY);
            body.add(newSegment);
            hasEaten = false;
        }
    }
    
    public boolean isOccupied(int x, int y)
    {
        for (BodySegment segment : body) {
            if (segment.getX() == x && segment.getY() == y) {
                return true;
            }
        }
        return false;
    }
}