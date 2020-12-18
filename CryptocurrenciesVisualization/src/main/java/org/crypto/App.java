package org.crypto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        System.out.println("Hi");
        Font.loadFont(Main.class.getResourceAsStream("/stylesheets/Comfortaa/Comfortaa.ttf"), 60);
        Font.loadFont(Main.class.getResourceAsStream("/stylesheets/Comfortaa/static/Comfortaa-Bold.ttf"), 60);
        Parent root = FXMLLoader.load(getClass().getResource("/mainView.fxml"));
        root.getStylesheets().add(String.valueOf(getClass().getResource("/stylesheets/style.css")));
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Cryptocurrencies Visualization");
        stage.show();
    }

}
