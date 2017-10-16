/**
 * Main.java
 *
 * This method is largely taken from Jeff's pong main method. It includes timer-based animation and keystroke handling.
 * It also introduces AnchorPane to keep the gameboard tied to the window's boundaries, and to keep the paddle tied to the bottom of the gameboard.
     * @author Jeff Ondich
     * @author Teddy Willard
     * @author Nicki Polyakov
     * @version 1.5
     * @since 1.0
 */

package brickBreaker;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("brickBreaker.fxml"));
        Parent root = (Parent)loader.load();

        Controller controller = loader.getController();
        controller.readLevelSettings("levels.txt");
        controller.setUpBricks(1);

        // Set up a KeyEvent handler so we can respond to keyboard activity.
        root.setOnKeyPressed(controller);

        primaryStage.setTitle("Brick Breaker");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.setResizable(false);
        primaryStage.show();

    }




    public static void main(String[] args) {
        launch(args);
    }
}
