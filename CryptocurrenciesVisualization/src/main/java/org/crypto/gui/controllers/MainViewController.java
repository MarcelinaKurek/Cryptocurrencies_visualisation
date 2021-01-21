package org.crypto.gui.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;
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


    private Button okBtn = new Button();
    @FXML
    private HBox hbox;

    private ComboBox<String> comboBox;
    private RankingTableView ranking;
    private TrendingTableView trending;
    private ObservableList<Coin> trendingObservableList;
    private ObservableList<Coin> rankingObservableList;

    private List<Coin> top7Trending;
    private List<Coin> top7Ranking;
    private Map<String, String> allCoins;
    private List<String> currencies = null;

    private CoinViewController coinViewController = null;
    private Scene thirdScene;
    private Scene secondScene;
    private String currency;
    private String coinId;


    public MainViewController(String currency)  {
        this.currency = currency;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        load();
        textField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                displayCoin(e, textField.getText());
            }
        });
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

        comboBox = new ComboBox<String>();
        comboBox.getStyleClass().add("combo-box");
        comboBox.setVisibleRowCount(4);
        comboBox.getItems().addAll(currencies);
        comboBox.setValue("eur");
        comboBox.setOnAction(e -> {
            currency = comboBox.getValue();
            ranking.setCurrency(currency);
            load();
            updateScene();
        });
        Button openDialog = new Button("Compare coins");
        openDialog.setOnAction(this::diplayCompareView);
        hbox.getChildren().addAll(comboBox, openDialog);
        hbox.setSpacing(150);
    }

    /**
     * Pobiera niezbędne dane i przygotowuje obiekty
     * w taki sposob, aby można było stworzyć z nich tabelę.
     */
    public void load() {
        top7Trending = APIClient.getTrending();
        top7Ranking = APIClient.getTop(currency);
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

    /**
     * Znajduje id kryptowaluty dla nazwy i otwiera widok
     * kryptowaluty, jeśli podana nazwa nie istnieje nic się nie dzieje.
     * @param actionEvent
     * @param name - nazwa kryptowaluty
     */
   public void displayCoin(Event actionEvent, String name) {
       Stage primaryStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

       String id = CommonFunctions.findIdByName(allCoins, name);
       if (!id.equals("")) {
           coinId = id;
           coinViewController.setCurrency(currency);
           coinViewController.setCoinId(coinId);
           coinViewController.updateScene();
           primaryStage.setScene(secondScene);
       }
   }

   private void diplayCompareView(ActionEvent event) {
       Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
       primaryStage.setScene(thirdScene);
   }

   public void setSecondScene(Scene scene) {
        this.secondScene = scene;
   }

   public void setThirdScene(Scene scene) {
       this.thirdScene = scene;
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
