package dishhunt;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DishHuntApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dish Hunt");
        primaryStage.setScene(new Scene(new Label("Welcome to Dish Hunt!"), 300, 100));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
