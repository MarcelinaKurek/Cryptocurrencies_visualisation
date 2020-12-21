package org.crypto.gui.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import org.crypto.gui.objects.*;
import org.crypto.services.APIClient;

import java.net.URL;
import java.util.*;

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
    private Button okBtn;

    @FXML
    private HBox hboxRanking;

    private ChoiceBox<String> choiceBox;
    private RankingTableView ranking;
    private TrendingTableView trending;
    private ObservableList<Coin> trendingObservableList;
    private ObservableList<Coin> rankingObservableList;

    private APIClient apiClient = null;
    private List<Coin> top7Trending;
    private List<Coin> top7Ranking;
    private Map<String, String> allCoins;
    private final String[] currencies = {"eur", "usd", "pln"};

    private CoinViewController coinViewController = null;
    private Scene secondScene;
    private String currency;
    private String coinId;


    public MainViewController(String currency)  {
        this.currency = currency;
        apiClient = new APIClient();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        load();

        okBtn.setOnAction(e -> displayCoin(e, textField.getText()));

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

        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(currencies);
        choiceBox.setValue("eur");
        choiceBox.setOnAction(e -> {
            currency = choiceBox.getValue();
            load();
            updateScene();
        });
        hboxRanking.getChildren().add(choiceBox);
    }

    /**
     * Pobiera niezbędne dane i przygotowuje obiekty
     * w taki sposob, aby można było stworzyć z nich tabelę.
     */
    public void load() {
        top7Trending = apiClient.getTrending();
        top7Ranking = apiClient.getTop(currency);
        allCoins = apiClient.getCoinList();

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

    /**
     * Znajduje id kryptowaluty dla nazwy i otwiera widok
     * kryptowaluty, jeśli podana nazwa nie istnieje nic się nie dzieje.
     * @param actionEvent
     * @param name - nazwa kryptowaluty
     */
    public void displayCoin(ActionEvent actionEvent, String name) {
        Stage primaryStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Optional<String> id = allCoins
                .entrySet()
                .stream()
                .filter(entry -> name.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();

        if (id.isPresent()) {
            coinId = id.get();
            coinViewController.setCurrency(currency);
            coinViewController.setCoinId(coinId);
            coinViewController.updateScene();
            primaryStage.setScene(secondScene);
        }
    }

    public void setSecondScene(Scene scene) {
        this.secondScene = scene;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCoinViewController(CoinViewController coinViewController) {
        this.coinViewController = coinViewController;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCoinId() {
        return coinId;
    }

}
