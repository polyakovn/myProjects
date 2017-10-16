Names:
Teddy Willard
Nicki Polyakov

Phase 1:

For our final project, we will be making the classic game Brick Breaker.  In this game, the user controls a paddle at the bottom of the screen using the left and right arrows on the keyboard. The goal is to use the paddle to bounce a moving ball into a set of bricks that are located at the top of the screen and to “break” these bricks. 

MVC is a good choice for this project, because it makes sense that the user would interact with the controller (the paddle), which would update the state of the model, which would in turn update the view the user has.  Our Brick Breaker Model will contain the following classes:  Brick, Scoreboard, Ball, Controller, and Model, which will aggregate these classes into various potential games states (depending on level).  The View will be the game screen itself, complete with a ball, a pause button, a scoreboard, a paddle, and bricks.  Our classes will be ball, paddle, and bricks.

Classes:
Brick
Scoreboard
Ball
Controller
Model
GameBoard
StatusView

Phase 2: Our programs works and runs as expected. 

We really tried to make our interface user friendly according to the principles of Don’t Make Me Think. We have a “pause” button in our game screen and a “start over” button in our game over screen. We made sure that the pause button only shows up during game play and the start over button only shows up when the game is over. 

Some other changes we made that might not be obvious are:
•added a game over screen that turns into a “you win” screen if the user beats the game
•we added a high score
•we made it so that the user cannot move the paddle when the game is paused
•we updated the paddle algorithm so that the ball accurately detects the paddle and so that the angle at which it bounces off the paddle is dependent on the place in which it hits the paddle
•our readBrickSettings and setUpBricks parses through levels.txt which contains brick settings for 9 different levels
