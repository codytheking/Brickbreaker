import javafx.scene.paint.Color;

/**
 * Brick gets hit by a ball and gets points depending on the color.
 * Brick has a base color and changes color when it is hit.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Brick
{
    private int x, y, w, h, points;
    private Color color;
    private boolean visible;
    
    public Brick(int x, int y, int w, int h, int c)
    {
        visible = true;
        
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        
        points = 0;
        
        if(c == 0)
            this.color = Color.BLUE;
            
        else if(c == 1)
            this.color = Color.YELLOW;
            
        else if(c == 2)
            this.color = Color.RED;
            
        else if(c == 3)
            this.color = Color.GREEN;
    }
    
    public void brickWasHit()
    {            
        if(getColor() == Color.BLUE)
        {
            color = Color.YELLOW;
            points += 4;
        }
        
        else if(getColor() == Color.YELLOW)
        {
            color = Color.RED;
            points += 3;
        }
        
        else if(getColor() == Color.RED)
        {
            color = Color.GREEN;
            points += 2;
        }
        
        else if(getColor() == Color.GREEN)
        {
            visible = false;
            points++;
        }
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
    
    public int getPoints()
    {
        return points;
    }
    
    public Color getColor()
    {
        return color;
    }
}
