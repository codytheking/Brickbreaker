
/**
 * BrickGroup is a grid of Bricks.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BrickGroup
{
    private Brick[][] bricks;

    public BrickGroup(int r, int c, int screenW, int brickW, int brickH)
    {
        bricks = new Brick[r][c];

        for(int i = 0; i < r; i++)
        {
            for(int j = 0; j < c; j++)
            {
                bricks[i][j] = new Brick(5 * (j + 1) + brickW * j, 5 * (i + 1) + brickH * i, 
                    brickW, brickH, i);
            }
        }
    }

    public int hitBrick(Ball ball)
    {
        for(int r = 0; r < bricks.length; r++)
        {
            for(int c = 0; c < bricks[r].length; c++)
            {
                Brick b = bricks[r][c];

                int x = ball.getX();
                int y = ball.getY();
                int rad = ball.getR();
                 // hit side of brick (pretty good, but some bugs)
                if(b.isVisible() && ((x + 2*rad >= b.getX() && x <= b.getX()) ||
                        (x + rad > b.getX() + b.getW() && x <= b.getX() + b.getW())) && 
                        y + rad >= b.getY() && y + rad <= b.getY() + b.getH())
                {
                    b.brickWasHit();
                    return 2;
                }
                
                // hit top or bottom of brick
                else if(b.isVisible() && (x + rad > b.getX()  && x + rad < b.getX() + b.getW()) &&
                        y + 2*rad >= b.getY() && y <= b.getY() + b.getH())
                {
                    b.brickWasHit();
                    return 1;
                }
            }
        }

        return 0;
    }

    public boolean bricksVisible()
    {
        for(int r = 0; r < bricks.length; r++)
        {
            for(int c = 0; c < bricks[r].length; c++)
            {
                if(bricks[r][c].isVisible())
                {
                    return true;
                }
            }
        }

        return false;
    }

    public Brick getBrick(int r, int c)
    {
        return bricks[r][c];
    }

    public int numRows()
    {
        return bricks.length;
    }

    public int numCols()
    {
        return bricks[0].length;
    }
}
