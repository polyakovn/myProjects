/** This class initializes the ball that is used to hit the bricks in brick breaker.
 */

package brickBreaker;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

public class Ball extends Circle {
    @FXML private double velocityX;
    @FXML private double velocityY;

    public Ball() {
    }

    public void step() {
        this.setCenterX(this.getCenterX() + this.velocityX);
        this.setCenterY(this.getCenterY() + this.velocityY);
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }
}