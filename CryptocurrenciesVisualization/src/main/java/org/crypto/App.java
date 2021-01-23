package org.crypto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.crypto.gui.controllers.CoinViewController;
import org.crypto.gui.controllers.CompareCoinsViewController;
import org.crypto.gui.controllers.MainViewController;
import org.crypto.gui.plots.Plots;
import tech.tablesaw.plotly.components.Figure;

import java.util.function.Consumer;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        MainViewController mainViewController = new MainViewController("eur");
        CoinViewController coinViewController = new CoinViewController();
        CompareCoinsViewController compareCoinsViewController = new CompareCoinsViewController();

        FXMLLoader firstLoader = new FXMLLoader(getClass().getResource("/mainView.fxml"));
        firstLoader.setController(mainViewController);
        Parent root = firstLoader.load();
        root.getStylesheets().add(String.valueOf(getClass().getResource("/stylesheets/style.css")));
        Scene firstScene  = new Scene(root, 1600, 800);

        FXMLLoader secondLoader = new FXMLLoader(getClass().getResource("/coinView.fxml"));
        secondLoader.setController(coinViewController);
        Parent secondPane = secondLoader.load();
        secondPane.getStylesheets().add(String.valueOf(getClass().getResource("/stylesheets/style.css")));
        Scene secondScene = new Scene(secondPane, 1200, 900);

        FXMLLoader thirdLoader = new FXMLLoader(getClass().getResource("/compareCoinsView.fxml"));
        thirdLoader.setController(compareCoinsViewController);
        Parent thirdPane = thirdLoader.load();
        thirdPane.getStylesheets().add(String.valueOf(getClass().getResource("/stylesheets/style.css")));
        Scene thirdScene = new Scene(thirdPane, 1200, 500);

        mainViewController.setCoinViewController(coinViewController);
        mainViewController.setSecondScene(secondScene);
        mainViewController.setThirdScene(thirdScene);

        Consumer<String> redirectToUrl = (url) -> getHostServices().showDocument(url);

        coinViewController.setUrlRedirectingFunc(redirectToUrl);
        coinViewController.setSecondScene(firstScene);

        compareCoinsViewController.setSecondScene(firstScene);
        compareCoinsViewController.setCoinViewController(coinViewController);
        compareCoinsViewController.setThirdScene(secondScene);

        primaryStage.setTitle("Cryptocurrencies Visualization");
        primaryStage.setScene(firstScene);
        primaryStage.show();
    }


}
