import greenfoot.*;

/**
 * MyWorld - World untuk Snake Game
 * Ukuran: 600x600 pixels dengan cell size 20x20
 */
public class MyWorld extends World
{
    private Snake snake;
    private Food food;
    private int score = 0;
    private boolean gameOver = false;
    private int gameSpeed = 10; // Semakin besar semakin lambat
    
    public MyWorld()
    {    
        super(30, 30, 20); // 30x30 cells, masing-masing 20 pixels
        prepare();
    }
    
    private void prepare()
    {
        // Buat snake di tengah
        snake = new Snake(this);
        addObject(snake, 15, 15);
        
        // Spawn makanan pertama
        spawnFood();
        
        // Tampilkan score
        showScore();
        
        // Set background color
        getBackground().setColor(Color.BLACK);
        getBackground().fill();
    }
    
    public void act()
    {
        if (gameOver) {
            checkRestart();
            return;
        }
        
        // Update score display
        showScore();
    }
    
    private void checkRestart()
    {
        if (Greenfoot.isKeyDown("r")) {
            Greenfoot.setWorld(new MyWorld());
        }
    }
    
    public void spawnFood()
    {
        // Hapus makanan lama jika ada
        if (food != null) {
            removeObject(food);
        }
        
        // Cari posisi random yang tidak ada snake
        int x, y;
        boolean validPosition;
        
        do {
            x = Greenfoot.getRandomNumber(30);
            y = Greenfoot.getRandomNumber(30);
            validPosition = !snake.isOccupied(x, y);
        } while (!validPosition);
        
        food = new Food();
        addObject(food, x, y);
    }
    
    public void increaseScore()
    {
        score += 10;
        // Sound opsional - hapus jika tidak ada file suara
        try {
            Greenfoot.playSound("eat.wav");
        } catch (Exception e) {
            // Tidak ada file suara, lanjutkan tanpa suara
        }
    }
    
    public void showScore()
    {
        showText("Score: " + score, 5, 1);
        showText("Speed: " + (11 - gameSpeed), 25, 1);
    }
    
    public void endGame()
    {
        gameOver = true;
        showText("GAME OVER!", 15, 15);
        showText("Final Score: " + score, 15, 17);
        showText("Press 'R' to Restart", 15, 19);
        // Sound opsional - hapus jika tidak ada file suara
        try {
            Greenfoot.playSound("gameover.wav");
        } catch (Exception e) {
            // Tidak ada file suara, lanjutkan tanpa suara
        }
        // JANGAN panggil Greenfoot.stop() agar restart bisa berfungsi
    }
    
    public int getGameSpeed()
    {
        return gameSpeed;
    }
    
    public int getScore()
    {
        return score;
    }
    
    public boolean isGameOver()
    {
        return gameOver;
    }
    
    public void increaseSpeed()
    {
        if (gameSpeed > 2) {
            gameSpeed--;
        }
    }
}