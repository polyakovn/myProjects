package brickBreaker;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/** This class is in charge of managing Bricks, including creating them and removing them.
  */

public class Brick extends Rectangle{
    @FXML
    public int numLives;


    /**
     *This method returns a brick object that can be painted on the screen. The method sets the location, color,
     * and number of initial lives for the brick.
     *
     * @param xCoordinate   the upper left x-coordinate of the brick
     * @param yCoordinate   the upper left y-coordinate of the brick
     * @param numLives      the amount of lives the brick will start off with
     * @return              a brick object
     * */
    public void setBrick(double xCoordinate, double yCoordinate, int numLives) {
        this.setX(xCoordinate);
        this.setY(yCoordinate);
        this.setWidth(50);
        this.setHeight(15);
        this.setNumBrickLives(numLives);
        this.setBrickColor();
        this.setStroke(Color.BLACK);
    }

    /**
     * @return numLives     the number of lives the brick has remaining
     * */
    public int getNumBrickLives() {
        return numLives;
    }

    /***
     * Sets the number of lives the brick should have.
     * @param lives    the number of lives the brick should have
     * */
    public void setNumBrickLives(int lives) {
        this.numLives=lives;
        this.setBrickColor();
    }

    /**
     * Sets a bricks color based on the number of lives the brick has remaining
     * */
    public void setBrickColor(){
        int lives=this.getNumBrickLives();
        if (lives==1){
            this.setFill(Color.RED);
        } else if (lives==2){
            this.setFill(Color.ORANGE);
        } else if (lives==3){
            this.setFill(Color.YELLOW);
        } else if (lives==4){
            this.setFill(Color.BLUE);
        } else if (lives==5){
            this.setFill(Color.GREEN);
        }
    }

}