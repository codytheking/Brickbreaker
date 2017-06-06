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
    
    /**
     * Changes brick color if it is hit (or makes it no longer visible).
     */
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
    
    /**
     * 
     * @return true if brick is still visible on screen, false otherwise.
     */
    public boolean isVisible()
    {
        return visible;
    }
    
    /**
     * 
     * @return x coordinate of brick.
     */
    public int getX()
    {
        return x;
    }
    
    /**
     * 
     * @return y coordinate of brick.
     */
    public int getY()
    {
        return y;
    }
    
    /**
     * 
     * @return width of brick.
     */
    public int getW()
    {
        return w;
    }
    
    /**
     * 
     * @return height of brick.
     */
    public int getH()
    {
        return h;
    }
    
    /**
     * 
     * @return point value of brick.
     */
    public int getPoints()
    {
        return points;
    }
    
    /**
     * 
     * @return Color of brick.
     */
    public Color getColor()
    {
        return color;
    }
}
