
/**
 * Write a description of class BrickGroup here.
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

    public int hitBrick(int x, int y)
    {
        for(int r = 0; r < bricks.length; r++)
        {
            for(int c = 0; c < bricks[r].length; c++)
            {
                Brick b = bricks[r][c];

                 // hit side of brick (pretty good, but some bugs)
                if(b.isVisible() && ((x >= b.getX() - 12 && x <= b.getX()) ||
                        (x > b.getX() + b.getW() && x <= b.getX() + b.getW() + 2)) && 
                        y >= b.getY() - 7 && y <= b.getY() + b.getH() + 2)
                {
                    b.brickWasHit();
                    return 2;
                }
                
                // hit top or bottom of brick
                else if(b.isVisible() && (x > b.getX() && x < b.getX() + b.getW() - 10) &&
                        y >= b.getY() && y <= b.getY() + b.getH())
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
