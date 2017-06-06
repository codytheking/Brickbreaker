
/**
 * BrickGroup is a grid of Bricks.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BrickGroup
{
    private Brick[][] bricks;
    private int brickW, brickH;

    public BrickGroup(int r, int c, int screenW, int brickW, int brickH)
    {
        bricks = new Brick[r][c];
        this.brickW = brickW;
        this.brickH = brickH;

        newGame();
    }

    /**
     * 
     * @param ball Ball object
     * @return 0 if no brick is hit, 1 if top or bottom of brick is hit, 2 if side of brick is hit.
     */
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

    /**
     * 
     * @return true if there is at least one brick still visible, false otherwise.
     */
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

    /**
     * 
     * @param r row in grid of bricks
     * @param c column in grid of bricks
     * @return brick object at row and column of grid.
     */
    public Brick getBrick(int r, int c)
    {
        return bricks[r][c];
    }

    /**
     * 
     * @return number of rows in brick grid (doesn't change when bricks aren't visible)
     */
    public int numRows()
    {
        return bricks.length;
    }

    /**
     * 
     * @return number of columns in brick grid (doesn't change when bricks aren't visible)
     */
    public int numCols()
    {
        return bricks[0].length;
    }
    
    public void newGame()
    {
    	for(int i = 0; i < numRows(); i++)
        {
            for(int j = 0; j < numCols(); j++)
            {
                bricks[i][j] = new Brick(5 * (j + 1) + brickW * j, 5 * (i + 1) + brickH * i, 
                    brickW, brickH, i);
            }
        }
    }
}
