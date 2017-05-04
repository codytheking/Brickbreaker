
/**
 * HighScore contains information about player who has a high score (name and score)
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HighScore
{
    private String name;
    private int score;
    
    public HighScore(String name, int score)
    {
        this.name = name;
        this.score = score;
    }
    
    public String getName()
    {
        return name;
    }
    
    public int getScore()
    {
        return score;
    }
}
