
package brickBreaker;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Controller implements EventHandler<KeyEvent> {
    final private double FRAMES_PER_SECOND = 20.0;
    @FXML private Button pauseButton;
    @FXML private Button startOverButton;
    @FXML private Label scoreLabel;
    @FXML private Label levelLabel;
    @FXML private Label livesLabel;
    @FXML private AnchorPane gameBoard;
    @FXML private Rectangle paddle;
    @FXML private Ball ball;
    ArrayList<Brick> brickList = new ArrayList<Brick>();
    HashMap<Integer, List<String>> levelsMap = new HashMap<>();

    private int gameOverScreen = 0;
    private int score;
    Scoreboard scoreboard = new Scoreboard();
    private boolean paused;
    private Timer timer;

    public Controller() {
        this.paused = false;
    }

    public void initialize() {
        this.startTimer();
    }

    /**
     * This method takes care of placing bricks onto the game screen based on the current level. It does this by
     * parsing the levels.txt file and creating a brick list for the current level.
     * @param level     the current level
     * */

    public void setUpBricks(int level) {

        List<String> brickSettings=this.levelsMap.get(level);
        for (int i = 0; i < brickSettings.size(); i++){
            String brickSettingsString = brickSettings.get(i);
            brickSettingsString = brickSettingsString.substring(1,brickSettingsString.length()-1);
            List<String> brickSettingsList = Arrays.asList(brickSettingsString.split(", "));
            Double xCoordinate = Double.parseDouble(brickSettingsList.get(0));
            Double yCoordinate = Double.parseDouble(brickSettingsList.get(1));
            Integer levelSet = Integer.parseInt(brickSettingsList.get(2));
            Brick brick = new Brick();
            brick.setBrick(xCoordinate, yCoordinate, levelSet);
            this.brickList.add(brick);
        }

        for (int i=0; i<this.brickList.size(); i++) {
            this.gameBoard.getChildren().add(this.brickList.get(i));
        }
    }

    /**
     * This method reads and begins to parse the levels.txt file. It creates a hashMap called levelsMap that
     * correlates the level with its corresponding bricks.
     * @param filename     the file that the function is going to read (in this case, levels.txt)
     * */

    public void readLevelSettings(String filename) {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String brickSettingsString = line.split(": ")[1];
                List<String> brickSettings = Arrays.asList(brickSettingsString.split(" ! "));
                this.levelsMap.put(Integer.parseInt(line.split(":")[0]), brickSettings);
            }

            reader.close();
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
        }
    }

    /**
     * This method starts the game timer and calls the methods that update the animation based on paddle movement
     * and based on the ball's interaction with the brick.
     * */
    private void startTimer() {
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if (gameOverScreen == 0) {
                            updateAnimation();
                            updateBrickAnimation();
                        }
                    }
                });
            }
        };

        long frameTimeInMilliseconds = (long)(75.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }


    /**
     * This method updates the brick animation by seeing when the ball hits a brick,
     * and then either updates it to have less lives or removes it from the board if it has 0 lives.
     * This method also updates the player's score, level, and lives remaining.
     * */
    private void updateBrickAnimation() {

        double ballCenterX = Math.round(this.ball.getCenterX() + this.ball.getLayoutX());
        double ballCenterY = Math.round(this.ball.getCenterY() + this.ball.getLayoutY());
        double ballRadius = this.ball.getRadius();
        double ballVelocityX = this.ball.getVelocityX();
        double ballVelocityY = this.ball.getVelocityY();
        double ballTop = ballCenterY - ballRadius;
        double ballBottom = ballCenterY + ballRadius;
        double ballLeft = ballCenterX - ballRadius;
        double ballRight = ballCenterX + ballRadius;

        for (int i = 0; i < this.brickList.size(); i++) {
            double brickTop = brickList.get(i).getY();
            double brickBottom = brickTop + 15.0;
            double brickLeft = brickList.get(i).getX();
            double brickRight = brickLeft + 50.0;

            //For if the ball hits the bottom of the brick
            if (ballVelocityY < 0 && brickRight >= ballLeft && brickLeft <= ballRight && brickBottom == ballTop) {
                this.ball.setVelocityY(-ballVelocityY);
                int lives = this.brickList.get(i).getNumBrickLives() - 1;
                this.brickList.get(i).setNumBrickLives(lives);
                this.scoreboard.addPoint();
            }
            //For if the ball hits the bottom of the brick
            else if (ballVelocityY > 0 && brickRight >= ballLeft && brickLeft <= ballRight && brickTop == ballBottom) {
                this.ball.setVelocityY(-ballVelocityY);
                int lives = this.brickList.get(i).getNumBrickLives() - 1;
                this.brickList.get(i).setNumBrickLives(lives);
                this.scoreboard.addPoint();
            }
            //For if the ball hits the left side of a brick
            else if (ballVelocityX > 0 && brickTop <= ballBottom && brickBottom >= ballTop && brickLeft == ballRight) {
                this.ball.setVelocityX(-ballVelocityX);
                int lives = this.brickList.get(i).getNumBrickLives() - 1;
                this.brickList.get(i).setNumBrickLives(lives);
                this.scoreboard.addPoint();
            }
            //For if the ball hits the right side of a brick
            else if (ballVelocityX < 0 && brickTop <= ballBottom && brickBottom >= ballTop && brickRight == ballLeft) {
                this.ball.setVelocityX(-ballVelocityX);
                int lives = this.brickList.get(i).getNumBrickLives() - 1;
                this.brickList.get(i).setNumBrickLives(lives);
                this.scoreboard.addPoint();
            }

            if (this.brickList.get(i).getNumBrickLives() == 0) {
                this.gameBoard.getChildren().remove(brickList.get(i));
                this.brickList.remove(brickList.get(i));
            }

            this.scoreLabel.setText(String.format("Score: %d", scoreboard.getScore()));
            this.levelLabel.setText(String.format("Level: %d", scoreboard.getLevel()));
            this.livesLabel.setText(String.format("Lives: %d", scoreboard.getNumLives()));
        }

        if (this.brickList.size() == 0 && gameOverScreen == 0) {
            int level = this.scoreboard.getLevel() + 1;
            this.scoreboard.setLevel(level);
            if (level==10){
                gameOver();
            }
            this.ball.setCenterX(0);
            this.ball.setCenterY(0);
            this.ball.setVelocityX(0.7);
            this.ball.setVelocityY(-0.7);
            this.setUpBricks(level);
            System.out.println(this.brickList.size());
        }
    }

    /**
     * This method updates the animation by seeing when the ball hits a paddle. We have updated Jeff's
     * algorithm so that it can accurately detect when the ball has hit the paddle, and it changes the angle of
     * the ball's movement based on where on the paddle it hit. If the ball does not hit the paddle, the player loses
     * a life. If the player loses all their lives, this method calls the "gameOver" method.
     * */
    private void updateAnimation() {
        double ballCenterX = this.ball.getCenterX() + this.ball.getLayoutX();
        double ballCenterY = this.ball.getCenterY() + this.ball.getLayoutY();
        double ballRadius = this.ball.getRadius();
        double ballLeft = ballCenterX - ballRadius;
        double ballRight = ballCenterX + ballRadius;
        double paddleTop = this.paddle.getY() + this.paddle.getLayoutY();
        double paddleBottom = paddleTop + this.paddle.getHeight();
        double paddleLeft = this.paddle.getX() + this.paddle.getLayoutX();
        double paddleRight = paddleLeft + this.paddle.getWidth();
        double ballBottom = Math.round(ballCenterY + ballRadius);

        if (this.ball.getVelocityY() > 0 && ballRight >= paddleLeft && ballLeft <= paddleRight && ballBottom >= paddleTop && ballBottom <= paddleBottom) {
            double paddleLocation = (ballCenterX - paddleLeft) / this.paddle.getWidth();
            double exitAngle = (paddleLocation - 0.5) * 2.5;
            double totalBallVelocitySquared = this.ball.getVelocityX() * this.ball.getVelocityX() + this.ball.getVelocityY() * this.ball.getVelocityY();
            double totalBallVelocity = Math.sqrt(totalBallVelocitySquared);
            double VelocityX = totalBallVelocity * Math.sin(exitAngle);
            double VelocityY = -(totalBallVelocity * Math.cos(exitAngle));
            this.ball.setVelocityX(VelocityX);
            this.ball.setVelocityY(VelocityY);
        }

        // Bounce off walls
        double ballVelocityX = this.ball.getVelocityX();
        double ballVelocityY = this.ball.getVelocityY();
        if (ballCenterX + ballRadius >= this.gameBoard.getWidth() && ballVelocityX > 0) {
            this.ball.setVelocityX(-ballVelocityX);
        } else if (ballCenterX - ballRadius < 0 && ballVelocityX < 0) {
            this.ball.setVelocityX(-ballVelocityX);
        } else if (ballCenterY + ballRadius >= this.gameBoard.getHeight() && ballVelocityY > 0) {
            this.ball.setVelocityY(-ballVelocityY);
            this.scoreboard.lostLife();
        } else if (ballCenterY - ballRadius < 0 && ballVelocityY < 0) {
            this.ball.setVelocityY(-ballVelocityY);
        }

        if (scoreboard.getNumLives()==0 && gameOverScreen == 0){
            gameOver();
            gameOverScreen = 1;
        }

        // Move the ball
        this.ball.step();
    }

    /**
     * If the player loses all their lives, this method is called. A "game over" screen is shown, and the user is asked
     * to click the "start over" button to play again.*/
    public void gameOver () {

        for (int i=this.brickList.size()-1; i>-1; i--) {
            this.gameBoard.getChildren().remove(brickList.get(i));
            this.brickList.remove(brickList.get(i));
        }
        this.gameBoard.getChildren().remove(ball);
        this.gameBoard.getChildren().remove(paddle);

        int score = this.scoreboard.getScore();
        int highScore = this.scoreboard.getHighScore();
        if (score >= highScore){
            this.scoreboard.setHighScore(score);
        }

        if (this.scoreboard.getLevel() < 10) {
            Text gameOver = new Text(120, 170, "Game Over");
            gameOver.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
            gameOver.setFill(Color.WHITE);
            this.gameBoard.getChildren().add(gameOver);
        } else {
            Text youWin = new Text(120, 170, "YOU WIN!!!");
            youWin.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
            youWin.setFill(Color.WHITE);
            this.gameBoard.getChildren().add(youWin);
        }

        Text finalScore = new Text(180, 230, (String.format("Final Score: %d", this.scoreboard.getScore())));
        finalScore.setFont(Font.font("Verdana", 20));
        Text bestScore = new Text (180, 260, (String.format("High Score: %d", this.scoreboard.getHighScore())));
        bestScore.setFont(Font.font("Verdana", 20));
        Text instructions = new Text (125, 310, "Press Start Over to Play Again");
        instructions.setFont(Font.font("Verdana", 17));
        this.startOverButton.setVisible(true);
        this.pauseButton.setVisible(false);
        this.gameOverScreen = 1;
        finalScore.setFill(Color.WHITE);
        bestScore.setFill(Color.WHITE);
        instructions.setFill(Color.WHITE);
        
        this.gameBoard.getChildren().add(finalScore);
        this.gameBoard.getChildren().add(bestScore);
        this.gameBoard.getChildren().add(instructions);
        this.ball.setVelocityY(0);
        this.ball.setVelocityX(0);
    }


    /**
     * This method handles moving the paddle left and right.
     * @param keyEvent      the user either pressing A or the left arrow key to go left,
     *                      or D or the right arrow key to go right*/
    @Override
    public void handle(KeyEvent keyEvent) {
        if (this.paused == false) {
            KeyCode code = keyEvent.getCode();
            double paddlePosition = this.paddle.getLayoutX();
            double stepSize = 20.0;
            if (code == KeyCode.LEFT || code == KeyCode.A) {
                // move paddle left
                if (paddlePosition > stepSize) {
                    this.paddle.setLayoutX(this.paddle.getLayoutX() - stepSize);
                } else {
                    this.paddle.setLayoutX(0);
                }
                keyEvent.consume();
            } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
                // move paddle right
                if (paddlePosition + this.paddle.getWidth() + stepSize < this.gameBoard.getWidth()) {
                    this.paddle.setLayoutX(this.paddle.getLayoutX() + stepSize);
                } else {
                    this.paddle.setLayoutX(this.gameBoard.getWidth() - this.paddle.getWidth());
                }
                keyEvent.consume();
            }
        }
    }

    /**This method controls what happens when the user clicks the pause button. It pauses the game if the user clicks
     * "pause" and then continues it if the user clicks "continue."
     * @param actionEvent   the mouse clicking on the button*/
    public void onPauseButton(ActionEvent actionEvent) {
        if (this.paused) {
            this.pauseButton.setText("Pause");
            this.startTimer();
        } else {
            this.pauseButton.setText("Continue");
            this.timer.cancel();
        }
        this.paused = !this.paused;
    }

    /**This method controls what happens when the user clicks the start over button on the game over screen.
     * It starts the game over from the beginning by clearing the brickList, gameBoard, and cancelling the timer, and then
     * setting everything up again.
     * @param actionEvent   the mouse clicking on the button*/
    public void onStartOverButton(ActionEvent actionEvent) {
        this.startOverButton.setVisible(false);
        this.pauseButton.setVisible(true);
        this.gameOverScreen = 0;

        this.timer.cancel();
        this.gameBoard.getChildren().clear();
        this.brickList.clear();
        this.scoreboard.setScore(0);
        this.scoreboard.setLevel(1);
        this.scoreboard.setNumLives(5);
        this.ball.setCenterX(0);
        this.ball.setCenterY(0);
        this.ball.setVelocityX(0.7);
        this.ball.setVelocityY(-0.7);

        setUpBricks(1);
        this.gameBoard.getChildren().add(paddle);
        this.gameBoard.getChildren().add(ball);
        this.startTimer();

    }




}
