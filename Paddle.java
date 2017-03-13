
/**
 * Write a description of class Paddle here.
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
    
    public void move(String dir)
    {
        if(dir.equals("left"))
        {
            x -= vel;
        }
        
        else if(dir.equals("right"))
        {
            x += vel;
        }
    }
    
    public void move(int xPos, int screenW)
    {
        x = xPos;
        
        if(getX() < 0)
            x = 0;
            
        if(getX() > screenW - getW())
            x = screenW - getW();
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public int getW()
    {
        return w;
    }
    
    public int getH()
    {
        return h;
    }
}
