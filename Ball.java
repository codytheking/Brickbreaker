
/**
 * Ball bounces off walls, Paddle, and Brick.
 * It gives player points when hitting Brick.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ball
{
    private int x, y;
    private int d;
    private int vx, vy;
    private int screenW, screenH;
    private int[] defaults;

    public Ball(int d, int vx, int vy, int screenW, int screenH)
    {
    	this.screenW = screenW;
    	this.screenH = screenH;
        x = (int) ((screenW / 2) * (Math.random() + 0.5));
        y = screenH / 2;
        this.d = d;
        this.vx = vx;
        this.vy = vy;
        
        defaults = new int[] {x, y, vx, vy};
    }

    /**
     * 
     * @return x coordinate of ball.
     */
    public int getX()
    {
        return x;
    }

    /**
     * 
     * @return y coordinate of ball.
     */
    public int getY()
    {
        return y;
    }
    
    /**
     * 
     * @return radius of ball.
     */
    public int getR()
    {
        return d/2;
    }

    /**
     * 
     * @return horizontal velocity of ball.
     */
    public int getVx()
    {
        return vx;
    }

    /**
     * 
     * @return vertical velocity of ball.
     */
    public int getVy()
    {
        return vy;
    }

    /**
     * Logic for hitting walls, bricks, and paddle.
     * 
     * @param p the Paddle object
     * @param bG the BrickGroup object
     * @return true if life is lost, false otherwise
     */
    public boolean move(Paddle p, BrickGroup bG)
    {
        // SHOULD I BREAK UP THIS METHOD??

        // account for hitting off side of paddle)
        
        int hit = bG.hitBrick(this);
        
        // bounce off paddle (middle: normal bounce)
        if(getX() >= p.getX() + (p.getW() / 10 * 3) && getX() <= p.getX() + (p.getW() / 10 * 7) && 
                (getY() + 2*getR() >= screenH - p.getH())) 
        {
            vy = -vy;
        }

        // bounce off paddle (left quarter: bounce back to left, faster)
        else if(getX() >= p.getX() && getX() < p.getX() + (p.getW() / 10 * 3) && 
                (getY() + 2*getR() >= screenH - p.getH())) 
        {
            vy = -vy;
            vx = -2 * Math.abs(vx);
        }
        
        // bounce off paddle (right quarter: bounce back to right, faster)
        else if(getX() > p.getX() + (p.getW() / 10 * 7) && getX() <= p.getX() + p.getW() && 
                (getY() + 2*getR() >= screenH - p.getH())) 
        {
            vy = -vy;
            vx = 2 * Math.abs(vx);
        }
        
        // bounce off side wall
        else if(getX() <= 0 || getX() >= screenW - getR())
        {
            vx = -vx;
        }

        // bounce off top wall
        else if(getY() <= 0)
        {
            vy = -vy;
        }
        
        // bounce off brick (top or bottom)
        else if(hit == 1) 
        {
            vy = -vy;
        }
        
        // bounce off brick (side)
        else if(hit == 2) 
        {
            vx = -vx;
        }
        
        // lose life by hitting bottom (or moving vertically or lost off screen)
        if(getY() > screenH + 30 || getY() < -30 || vx > 64)
        {
            return true;
        }

        // normal ball movement
        x += vx;
        y += vy;
        
        return false;
    }
    
    /**
     * Resets ball when life is lost.
     */
    public void resetVals()
    {
        x = (int) ((Math.random() * (screenW - 75)) + 40);
        y = defaults[1];
        vx = defaults[2];
        vy = defaults[3];
    }
}
