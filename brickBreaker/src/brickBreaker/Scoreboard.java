package brickBreaker;
import javafx.fxml.FXML;
/**
*This is the Scoreboard class.  It allows for access to scoreboard information and for manipulating that scoreboard information.
*/
class Scoreboard{

    /**initializes number of lives and score to 3 and 0 respectively
    *@arg lives current number of lives remaining
    *@arg score current score
    */
    @FXML private int lives = 5;
    @FXML private int score = 0;
    @FXML private int highScore = 0;
    @FXML private int level = 1;

    /**
     *@return current level
     */

    public int getLevel(){
        return this.level;
    }

    /**
     * set the level
     */

    public void setLevel(int level){
        this.level=level;
    }

    /**
     * set the high score
     */

    public void setHighScore(int highScore){
        this.highScore = highScore;
    }

    /**
     *@return current high score
     */

    public int getHighScore(){
        return this.highScore;
    }

    /**
     * set the score
     */

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * set the number of lives
     */

    public void setNumLives(int lives) {
        this.lives = lives;
    }

    /**
     *@return number of lives remaining
     */

    public int getNumLives(){
        return this.lives;
    }

    /**
    *@return current score
    */
    public int getScore(){
        return this.score;
    }

    /**
     @return number of lives remaining after losing one life
     */
    public int lostLife(){
        this.lives = this.lives - 1;
        return this.lives;
    }

    /**
     * add one point to score
     */

    public void addPoint(){
        this.score = this.score + 1;
    }


}

