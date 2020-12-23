package org.crypto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.crypto.gui.controllers.CoinViewController;
import org.crypto.gui.controllers.MainViewController;
import org.crypto.services.APIClient;

public class App extends Application {
    private String currency = "eur";
    private String coinId = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        APIClient apiClient = new APIClient();

        MainViewController mainViewController = new MainViewController(currency, apiClient);
        CoinViewController coinViewController = new CoinViewController(apiClient);

        mainViewController.setCoinViewController(coinViewController);

        FXMLLoader firstLoader = new FXMLLoader(getClass().getResource("/mainView.fxml"));
        firstLoader.setController(mainViewController);
        Parent root = firstLoader.load();
        root.getStylesheets().add(String.valueOf(getClass().getResource("/stylesheets/style.css")));

        Scene firstScene  = new Scene(root, 1600, 800);

        FXMLLoader secondLoader = new FXMLLoader(getClass().getResource("/coinView.fxml"));
        secondLoader.setController(coinViewController);
        Parent secondPane = secondLoader.load();
        secondPane.getStylesheets().add(String.valueOf(getClass().getResource("/stylesheets/style.css")));

        Scene secondScene = new Scene(secondPane, 1600, 800);


        mainViewController.setSecondScene(secondScene);

        primaryStage.setTitle("Cryptocurrencies Visualization");
        primaryStage.setScene(firstScene);
        primaryStage.show();
    }

}
