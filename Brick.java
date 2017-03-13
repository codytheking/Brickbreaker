import javafx.scene.paint.Color;

/**
 * Write a description of class Brick here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Brick
{
    private int x, y, w, h;
    private Color color;
    private boolean visible;
    
    public Brick(int x, int y, int w, int h, int c)
    {
        visible = true;
        
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        
        if(c == 0)
            this.color = Color.BLUE;
            
        else if(c == 1)
            this.color = Color.YELLOW;
    }
    
    public void brickWasHit()
    {
        visible = false;
    }
    
    public boolean isVisible()
    {
        return visible;
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
    
    public Color getColor()
    {
        return color;
    }
}
