import java.util.Random;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.embed.swing.JFXPanel;
import javafx.application.Platform;

/**
 * Write a description of class MovingBall here.
 * 
 * @author Cody King 
 * @version 03/11/2017
 */

public class Brickbreaker extends Application 
{
    private final Duration TRANSLATE_DURATION = Duration.seconds(0.25);
    private final int VEL = 1;

    private boolean gamePaused;
    private int screenHeight, screenWidth;
    private int points, lives; 
    private Ball ball;
    private Paddle p1;
    private BrickGroup brickGroup;

    public Brickbreaker()
    {
        gamePaused = true;
        screenHeight = 500;
        screenWidth = 635;
        points = 0;
        lives = 2;

        ball = new Ball(10, VEL, VEL, screenWidth, screenHeight);
        p1 = new Paddle(315, 485, 70, 15, VEL * 10);
        brickGroup = new BrickGroup(2, 6, screenWidth, 100, 15);
    }

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override 
    public void start(Stage stage) throws Exception 
    {
        Canvas canvas = new Canvas(screenWidth, screenHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), (ActionEvent e) -> run(gc)));
        /*Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), 
                new EventHandler<ActionEvent>()
                {
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
                }
            });*/

        canvas.setOnMouseMoved((MouseEvent e) -> p1.move((int) e.getX(), screenWidth));
        /*canvas.setOnMouseMoved(new EventHandler<MouseEvent>() 
            {
                public void handle(MouseEvent e) 
                {
                    p1.move((int) e.getX(), screenWidth);
                }
            });*/ 
            
        canvas.setOnMouseClicked((MouseEvent e) -> {
                ball.resetVals();
                gamePaused = false;
            });
        /*canvas.setOnMouseClicked(new EventHandler<MouseEvent>() 
        {
        public void handle(MouseEvent e)
        {
        ball.resetPos(screenWidth, screenHeight);
        gamePaused = false;
        }
        });*/

        stage.setTitle("Brickbreaker by King");
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        tl.play();
    }

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

                else
                {
                    points++;
                }
            }
        }

        // text for game info
        gc.setFill(Color.WHITE);
        gc.fillText("Points: " + ball.getVx(), screenWidth / 2 - 30, 100);

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

    /*private Label createInstructions() 
    {
    Label instructions = new Label(
    "Use the arrow keys to move the circle in small increments\n" +
    "Click the mouse to move the circle to a given location\n" +
    "Ctrl + Click the mouse to slowly translate the circle to a given location" + c      
    );
    instructions.setTextFill(Color.FORESTGREEN);

    return instructions;
    }*/
}