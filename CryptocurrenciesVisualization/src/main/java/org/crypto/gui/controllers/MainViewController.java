package org.crypto.gui.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import org.crypto.gui.objects.*;
import org.crypto.services.APIClient;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static org.crypto.gui.controllers.CommonFunctions.displayCoin;

public class MainViewController implements Initializable {
    /**
     * Obsługuje widok główny.
     */
    @FXML
    private BorderPane borderPane;
    @FXML
    private GridPane gridPane;
    @FXML
    private TextField textField;
    @FXML
    private HBox hbox;

    private ComboBox<String> comboBox;
    private RankingTableView ranking;
    private TrendingTableView trending;
    private ObservableList<Coin> trendingObservableList;
    private ObservableList<Coin> rankingObservableList;

    private Map<String, String> allCoins;
    private List<String> currencies = null;

    private CoinViewController coinViewController = null;
    private Scene thirdScene;
    private Scene secondScene;
    private String currency;


    public MainViewController(String currency)  {
        this.currency = currency;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        load();
        textField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                displayCoinView(e, textField.getText());
            }
        });

        trending = new TrendingTableView();
        ranking = new RankingTableView();

        trending.getItems().addAll(trendingObservableList);
        trending.setFixedCellSize(50);
        trending.prefHeightProperty().bind(Bindings.size(trending.getItems()).multiply(trending.getFixedCellSize()).add(7));
        trending.setMinHeight(trending.getFixedCellSize() * 7 + 40);

        ranking.getItems().addAll(rankingObservableList);
        ranking.setFixedCellSize(50);
        ranking.prefHeightProperty().bind(Bindings.size(ranking.getItems()).multiply(ranking.getFixedCellSize()).add(7));
        ranking.setMinHeight(ranking.getFixedCellSize() * 7 + 40);

        gridPane.add(ranking, 0, 1);
        gridPane.add(trending, 1, 1);

        List<String> allCoinsNames = new ArrayList<>(allCoins.values());
        TextFields.bindAutoCompletion(textField, allCoinsNames);

        List<String> currenciesUpper = currencies.stream().map(String::toUpperCase).collect(Collectors.toList());
        comboBox = new ComboBox<String>();
        comboBox.getStyleClass().add("combo-box");
        comboBox.setVisibleRowCount(4);
        comboBox.getItems().addAll(currenciesUpper);
        comboBox.setValue("EUR");
        comboBox.setOnAction(e -> {
            currency = comboBox.getValue().toLowerCase();
            ranking.setCurrency(currency);
            load();
            updateScene();
        });

        Button goToComparison = new Button("Compare coins");
        goToComparison.getStyleClass().add("button-top");
        goToComparison.setOnAction(this::displayCompareView);
        Button goToCoinView = new Button("Submit");
        goToCoinView.getStyleClass().add("button-top");
        goToCoinView.setOnAction(e -> displayCoinView(e, textField.getText()));

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(goToCoinView,goToComparison, comboBox);
        hbox.getChildren().addAll(hBox);
        hbox.setSpacing(150);
    }

    /**
     * Pobiera niezbędne dane i przygotowuje obiekty
     * w taki sposob, aby można było stworzyć z nich tabelę.
     */
    public void load() {
        List<Coin> top7Trending = APIClient.getTrending();
        List<Coin> top7Ranking = APIClient.getTop(currency);
        allCoins = APIClient.getCoinList();
        currencies = APIClient.getSupportedCurrencies();

        trendingObservableList = FXCollections.observableArrayList();
        trendingObservableList.addAll(top7Trending);

        rankingObservableList = FXCollections.observableArrayList();
        rankingObservableList.addAll(top7Ranking);
    }

    /**
     * Aktualizuje widok, np. po zmianie waluty
     */
    public void updateScene() {
        trending.getItems().clear();
        trending.getItems().addAll(trendingObservableList);
        trending.refresh();

        ranking.getItems().clear();
        ranking.getItems().addAll(rankingObservableList);
        ranking.refresh();

    }


   public void displayCoinView(Event actionEvent, String name) {
       displayCoin(actionEvent, name, allCoins, coinViewController, currency, secondScene);
   }

   private void displayCompareView(ActionEvent event) {
       Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
       primaryStage.setScene(thirdScene);
   }

   public void setSecondScene(Scene scene) {
        this.secondScene = scene;
   }

   public void setThirdScene(Scene scene) {
       this.thirdScene = scene;
   }

   public void setCoinViewController(CoinViewController coinViewController) {
        this.coinViewController = coinViewController;
   }

}
