
/**
 * Paddle is moved by player and bounces ball off of it into bricks.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Paddle
{
    private int x, y;
    private int w, h;
    private int vel;
    
    public Paddle(int x, int y, int w, int h, int vel)
    {
        this.w = w;
        this.h = h;
        this.vel = vel;
        this.x = x;
        this.y = y;
    }
    
    /**
     * Moves paddle on screen left or right depending on value of dir from a key press.
     * 
     * @param dir 0 if left direction, 1 if right
     */
    public void move(int dir)
    {
        if(dir == 0)
        {
            x -= vel;
        }
        
        else if(dir == 1)
        {
            x += vel;
        }
    }
    
    /**
     * Moves paddle to position of mouse cursor.
     * 
     * @param xPos position of mouse
     * @param screenW width of game window
     */
    public void move(int xPos, int screenW)
    {
        x = xPos;
        
        if(getX() < 0)
            x = 0;
            
        if(getX() > screenW - getW())
            x = screenW - getW();
    }
    
    /**
     * 
     * @return x coordinate of paddle
     */
    public int getX()
    {
        return x;
    }
    
    /**
     * 
     * @return y coordinate of padddle
     */
    public int getY()
    {
        return y;
    }
    
    /**
     * 
     * @return width of paddle
     */
    public int getW()
    {
        return w;
    }
    
    /**
     * 
     * @return height of paddle
     */
    public int getH()
    {
        return h;
    }
}
