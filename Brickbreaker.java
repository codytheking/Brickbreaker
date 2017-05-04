/****************************************************************
 * This is the main class that runs the BrickBreaker game.      *
 *                                                              *
 * Player moves paddle and bounces ball off of it into bricks.  *
 * Points are collected when ball hits bricks.                  *
 * Life is lost each time ball misses the paddle.               *
 * Game is over when player has 0 lives left.                   *
 *                                                              *
 * Cheat code: pressing 'l' gives additional lives              *
 *                                                              *
 * @author Cody King                                            *
 *                                                              *
 * version 1.0: 03/11/2017                                      *
 *      -Initial working game created                           *
 *                                                              *
 * version 1.1: 04/29/2017                                      *
 *      -Improved documentation in code                         *
 *      -High scores saved to file and displayed in game        *
 *                                                              *
 * version 1.2: 05/03/2017                                      *
 *      -Fixed exception handling in initializeHighScores and   *
 *      saveScores (try-catch instead of throws for method)     *
 *                                                              *
 ****************************************************************/

import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class Brickbreaker extends Application 
{
    private final Duration TRANSLATE_DURATION = Duration.seconds(0.25);
    private final int VEL = 2;

    private boolean gamePaused, recorded;
    private int screenHeight, screenWidth;
    private int points, lives, numHighScores; 
    private Ball ball;
    private Paddle p1;
    private BrickGroup brickGroup;
    private List<HighScore> highScores;

    /**
     * Initialize class variables.
     * Create a Ball, Paddle, and BrickGroup.
     */
    public Brickbreaker() throws IOException
    {
        gamePaused = true;
        recorded = false;
        screenHeight = 500;
        screenWidth = 635;
        points = 0;
        lives = 2;
        numHighScores = 5;

        ball = new Ball(10, VEL, VEL, screenWidth, screenHeight);
        p1 = new Paddle(315, 485, 70, 15, VEL * 10);
        brickGroup = new BrickGroup(4, 6, screenWidth, 100, 15);
        highScores = initializeHighScores();
    }

    /**
     * Runs the application.
     */
    public static void main(String[] args)
    {
        Application.launch(args);
    }

    /**
     * Sets up GUI and key/mouse handlers.
     */
    @Override 
    public void start(Stage stage) throws Exception 
    {
        Canvas canvas = new Canvas(screenWidth, screenHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), (ActionEvent e) -> run(gc)));
        /*Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), 
        new EventHandler<ActionEvent>()
        {
        @Override
        public void handle(ActionEvent e) 
        {
        run(gc);
        }
        }));*/

        tl.setCycleCount(Timeline.INDEFINITE);

        canvas.setFocusTraversable(true);

        canvas.setOnKeyPressed((KeyEvent e) ->
            {
                if(e.getCode() == KeyCode.LEFT) 
                {    
                    p1.move("left");
                }

                else if(e.getCode() == KeyCode.RIGHT)
                {
                    p1.move("right");
                }

                else if(e.getCode() == KeyCode.L)
                {
                    lives++;
                }
            });

        /*canvas.setOnKeyPressed(new EventHandler<KeyEvent>() 
        {
        @Override
        public void handle(KeyEvent e) 
        {
        if(e.getCode() == KeyCode.LEFT) 
        {    
        p1.move("left");
        }

        else if(e.getCode() == KeyCode.RIGHT)
        {
        p1.move("right");
        }

        else if(e.getCode() == KeyCode.L)
        {
        lives++;
        }
        }
        });*/

        canvas.setOnMouseMoved((MouseEvent e) -> p1.move((int) e.getX(), screenWidth));
        /*canvas.setOnMouseMoved(new EventHandler<MouseEvent>() 
        {
        @Override
        public void handle(MouseEvent e) 
        {
        p1.move((int) e.getX(), screenWidth);
        }
        });*/ 

        canvas.setOnMouseClicked((MouseEvent e) -> 
            {
                if(gamePaused)
                {
                    ball.resetVals();
                    gamePaused = false;
                }
            });
        /*canvas.setOnMouseClicked(new EventHandler<MouseEvent>() 
        {
        @Override
        public void handle(MouseEvent e)
        {
        if(gamePaused)
        {
        ball.resetVals();
        gamePaused = false;
        }
        }
        });*/

        stage.setTitle("Brickbreaker by King");
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        tl.play();
    }

    /**
     * Create visuals on screen for the game.
     */
    private void run(GraphicsContext gc)
    {
        // color for background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, screenWidth, screenHeight);

        // color for ball and paddle
        gc.setFill(Color.WHITE);

        // draw ball if game isn't paused
        if(!gamePaused) 
        {
            gamePaused = ball.move(p1, screenWidth, screenHeight, brickGroup);
            gc.fillOval(ball.getX(), ball.getY(), ball.getR(), ball.getR());

            if(gamePaused)
            {
                lives--;
            }
        }

        // draw paddle
        gc.fillRect(p1.getX(), p1.getY(), p1.getW(), p1.getH());

        points = 0;
        // draw bricks
        for(int i = 0; i < brickGroup.numRows(); i++)
        {
            for(int j = 0; j < brickGroup.numCols(); j++)
            {
                Brick b = brickGroup.getBrick(i, j);

                if(b.isVisible())
                {
                    gc.setFill(b.getColor());
                    gc.fillRect(b.getX(), b.getY(), b.getW(), b.getH());
                }

                points += b.getPoints();
            }
        }

        // text for game info
        gc.setFill(Color.WHITE);
        gc.fillText("Points: " + points, screenWidth / 2 - 30, 100);

        // end game, delcare player has won
        if(!brickGroup.bricksVisible())
        {
            gamePaused = true;
            gc.fillText("You won!", screenWidth / 2 - 30, 175);
        }

        // end game, declare player has lost
        else if(lives <= 0)
        {
            gamePaused = true;
            gc.fillText("You lost!", screenWidth / 2 - 30, 175);
            gc.fillText("Name\t\t\tScore\n\n", screenWidth / 2 - 75, 275);

            String nameList = "";
            String scoreList = "";
            for(HighScore high: highScores)
            {
                nameList += high.getName() + "\n";
                scoreList += high.getScore() + "\n";
            }

            // display high scores
            gc.fillText(nameList, screenWidth / 2 - 75, 295);
            gc.fillText(scoreList, screenWidth / 2 + 45, 295);

            if(!recorded && isInTopScores())
            {
                Scanner in = new Scanner(System.in);
                System.out.print("You scored in the top 5! Enter your name: ");
                String name = in.nextLine();
                HighScore newHigh = new HighScore(name, points);
                insertNewScore(newHigh);
                saveScores();
                recorded = true;
            }
        }

        // tell user they lost a life and how to resume game
        else if(gamePaused)
        {
            gc.fillText("Lives:   " + lives, screenWidth / 2 - 30, 125);
            gc.fillText("Click to resume", screenWidth / 2 - 30, 175);
        }

        // show the number of lives left, if game is not over
        else
        {
            gc.fillText("Lives:   " + lives, screenWidth / 2 - 30, 125);
        }
    }

    /****************************
     * Private helper methods   *
     ****************************/

    /**
     * Post: returns true if player score is in top 5, false otherwise.
     */
    private boolean isInTopScores()
    {
        return (highScores.size() < numHighScores) || (points > highScores.get(highScores.size() - 1).getScore());
    }

    /**
     * @parameters highScore is the HighScore object going into the top 5 list.
     * Post: highScore is put into highScores.
     */
    private void insertNewScore(HighScore highScore)
    {
        boolean wasAdded = false;

        for(int i = 0; i < highScores.size(); i++)
        {
            if(highScore.getScore() > highScores.get(i).getScore())
            {
                highScores.add(i, highScore);
                wasAdded = true;
                break;
            }
        }

        if(!wasAdded && highScores.size() < numHighScores)
        {
            highScores.add(highScore);
        }

        if(highScores.size() > numHighScores)
        {
            highScores.remove(numHighScores);
        }
    }

    /**
     * Post: new high score list has been created in HighScores.txt
     */
    private void saveScores()
    {
        File file = new File("HighScores.txt");

        if(file.exists())
        {
            file.delete();
        }

        try
        {
            file.createNewFile();
        }
        catch(IOException e)
        {}

        try(Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("HighScores.txt"), "utf-8")))
        {
            for(HighScore high: highScores)
            {
                writer.write(high.getName() + ", " + high.getScore() + "\n");
            }
        }
        catch(IOException e)
        {}
    }

    /**
     * List of high scores is read from HighScores.txt and put into <>highScores</>
     */
    private List<HighScore> initializeHighScores()
    {
        List<HighScore> scores = new ArrayList<HighScore>();
        File file = new File("HighScores.txt");
        Scanner inFile = null;

        try
        {
            file.createNewFile();
        }
        catch(IOException e)
        {}
        
        try
        {
            inFile = new Scanner(file);
        }
        catch(IOException e)
        {}

        while(inFile.hasNext())
        {
            String line = inFile.nextLine();
            int comma = line.indexOf(",");
            String name = line.substring(0, comma);
            int score = Integer.parseInt(line.substring(comma + 2));
            scores.add(new HighScore(name, score));
        }

        inFile.close();

        return scores;
    }
}