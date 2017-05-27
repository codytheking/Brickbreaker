
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
    private int[] defaults;

    public Ball(int d, int vx, int vy, int screenW, int screenH)
    {
        x = (int) ((screenW / 2) * (Math.random() + 0.5));
        y = screenH / 2;
        this.d = d;
        this.vx = vx;
        this.vy = vy;
        
        defaults = new int[] {x, y, vx, vy};
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
    
    public int getR()
    {
        return d/2;
    }

    public int getVx()
    {
        return vx;
    }

    public int getVy()
    {
        return vy;
    }

    public boolean move(Paddle p, int screenW, int screenH, BrickGroup bG)
    {
        // SHOULD I BREAK UP THIS METHOD??

        // account for hitting off side of paddle)
        
        int hit = bG.hitBrick(this);
        
        // bounce off paddle (middle: normal bounce)
        if(getX() >= p.getX() + (p.getW() / 3) && getX() <= p.getX() + (p.getW() / 3 * 2) && 
                (getY() + 2*getR() >= screenH - p.getH())) 
        {
            vy = -vy;
        }

        // bounce off paddle (left third: bounce back to left, faster)
        else if(getX() >= p.getX() && getX() < p.getX() + (p.getW() / 3) && 
                (getY() + 2*getR() >= screenH - p.getH())) 
        {
            vy = -vy;
            vx = -2 * Math.abs(vx);
        }
        
        // bounce off paddle (right third: bounce back to right, faster)
        else if(getX() > p.getX() + (p.getW() / 3 * 2) && getX() <= p.getX() + p.getW() && 
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
        
        // lose life by hitting bottom
        else if(getY() + 2*getR() >= screenH)
        {
            return true;
        }

        // normal ball movement
        x += vx;
        y += vy;
        
        return false;
    }
    
    public void resetVals()
    {
        x = (int) (defaults[0] * (Math.random() + 0.5));
        y = defaults[1];
        vx = defaults[2];
        vy = defaults[3];
    }
}
