package org.crypto.gui.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import org.crypto.gui.objects.TableData;
import org.crypto.services.APIClient;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static org.crypto.gui.controllers.CommonFunctions.displayCoin;

public class CompareCoinsViewController implements Initializable {

    @FXML
    private VBox vBoxComparison;
    @FXML
    private HBox hBox;
    @FXML
    private GridPane gridPane;
    @FXML
    private Label title;
    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;
    @FXML
    private Button submitBtn;
    @FXML
    private Button backToMain;
    @FXML
    private ImageView c1Logo;
    @FXML
    private Button c1Name;
    @FXML
    private Label c1Price;
    @FXML
    private Label c1MktCap;
    @FXML
    private Label c1TradVol;
    @FXML
    private Label c1Low24;
    @FXML
    private Label c1High24;
    @FXML
    private Label c1MktCapRank;
    @FXML
    private Label c1ATH;
    @FXML
    private Label c1ATL;
    @FXML
    private ImageView c2Logo;
    @FXML
    private Button c2Name;
    @FXML
    private Label c2Price;
    @FXML
    private Label c2MktCap;
    @FXML
    private Label c2TradVol;
    @FXML
    private Label c2Low24;
    @FXML
    private Label c2High24;
    @FXML
    private Label c2MktCapRank;
    @FXML
    private Label c2ATH;
    @FXML
    private Label c2ATL;
    @FXML
    private Label cPrice;
    @FXML
    private Label cMktCap;
    @FXML
    private Label cTradVol;
    @FXML
    private Label cLow24;
    @FXML
    private Label cHigh24;
    @FXML
    private Label cMktCapRank;
    @FXML
    private Label cATH;
    @FXML
    private Label cATL;
    @FXML
    private Label cTitle;

    private Scene secondScene;
    private Scene thirdScene;
    private CoinViewController coinViewController;

    private Map<String, String> allCoins;
    private String currency = "eur";
    private TableData coin1Data;
    private TableData coin2Data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridPane.getChildren().forEach(node -> {
            if (node instanceof Label) {
                if (GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) != 0) {
                    node.getStyleClass().add("label-content");
                }
            }
        });
        allCoins = APIClient.getCoinList();
        List<String> currencies = APIClient.getSupportedCurrencies();

        List<String> allCoinsNames = new ArrayList<>(allCoins.values());
        TextFields.bindAutoCompletion(textField1, allCoinsNames);
        TextFields.bindAutoCompletion(textField2, allCoinsNames);

        c1Name.setVisible(false);
        c2Name.setVisible(false);
        cTitle.setVisible(false);
        cTitle.setStyle("-fx-font-weight: bold");

        List<String> currenciesUpper = currencies.stream().map(String::toUpperCase).collect(Collectors.toList());
        ComboBox<String> comboBox = new ComboBox<String>();
        comboBox.getItems().addAll(currenciesUpper);
        comboBox.setValue("EUR");
        comboBox.setVisibleRowCount(4);
        comboBox.setOnAction(e -> {
            currency = comboBox.getValue().toLowerCase();
            loadData(textField1.getText(), textField2.getText());
            loadTable();
        });
        hBox.getChildren().add(comboBox);

        submitBtn.setOnAction(e -> {
            if (allCoins.containsValue(textField1.getText()) && allCoins.containsValue((textField2.getText()))) {
                loadData(textField1.getText(), textField2.getText());
                loadTable();
                loadComparisonColumn();
            }
        });

        c1Name.setOnAction(e -> displayCoinView(e, textField1.getText()));
        c2Name.setOnAction(e -> displayCoinView(e, textField2.getText()));

        backToMain.setOnAction(this::goBackToMainView);
    }

    private void loadData(String name1, String name2) {
        String id1 = CommonFunctions.findIdByName(allCoins, name1);
        String id2 = CommonFunctions.findIdByName(allCoins, name2);

        this.coin1Data = APIClient.getTableData(id1, currency);
        this.coin2Data = APIClient.getTableData(id2, currency);
    }

    /**
     * Funkcja przypisuje dane z obiektów TableData do kolumn tabeli
     * */
    private void loadTable() {
        String upperCurrency = currency.toUpperCase();
        c1Logo.setImage(new Image(coin1Data.getImageSmallUrl()));
        c1Name.setText(coin1Data.getName());
        c1Name.setVisible(true);
        c1Price.setText(coin1Data.getCurrentPrice() + " " + upperCurrency);
        c1MktCap.setText(coin1Data.getMarketCap() + " " + upperCurrency);
        c1TradVol.setText(coin1Data.getTotalVolume() + " " + upperCurrency);
        if (coin1Data.getLow24h() != null) {
            c1Low24.setText(coin1Data.getLow24h() + " " + upperCurrency);
        } else {
            c1Low24.setText("-");
        }
        if (coin1Data.getHigh24h() != null) {
            c1High24.setText(coin1Data.getHigh24h() + " " + upperCurrency);
        } else {
            c1High24.setText("-");
        }
        c1MktCapRank.setText(String.valueOf(coin1Data.getMarketCapRank()));
        c1ATH.setText(coin1Data.getAth() + " " + upperCurrency);
        c1ATL.setText(coin1Data.getAtl() + " " + upperCurrency);

        c2Logo.setImage(new Image(coin2Data.getImageSmallUrl()));
        c2Name.setText(coin2Data.getName());
        c2Name.setVisible(true);
        c2Price.setText(coin2Data.getCurrentPrice() + " " + upperCurrency);
        c2MktCap.setText(coin2Data.getMarketCap() + " " + upperCurrency);
        c2TradVol.setText(coin2Data.getTotalVolume() + " " + upperCurrency);
        if (coin2Data.getLow24h() != null) {
            c2Low24.setText(coin2Data.getLow24h() + " " + upperCurrency);
        } else {
            c2Low24.setText("-");
        }
        if (coin2Data.getHigh24h() != null) {
            c2High24.setText(coin2Data.getHigh24h() + " " + upperCurrency);
        } else {
            c2High24.setText("-");
        }
        c2MktCapRank.setText(String.valueOf(coin2Data.getMarketCapRank()));
        c2ATH.setText(coin2Data.getAth() + " " + upperCurrency);
        c2ATL.setText(coin2Data.getAtl() + " " + upperCurrency);
    }

    /**
     * Funkcja porównuje wartości między sobą i wpisuje porównanie do kolumny Comparison
     * */
    private void loadComparisonColumn() {
        cTitle.setVisible(true);
        long priceDiff = (long) ((coin1Data.getCurrentPrice() / coin2Data.getCurrentPrice()) * 100);
        cPrice.setText(CommonFunctions.formatNumber(priceDiff) + " %");
        cMktCap.setText(String.valueOf(coin1Data.getMarketCap() - coin2Data.getMarketCap()));
        if (coin1Data.getHigh24h() != null && coin2Data.getHigh24h() != null) {
            cHigh24.setText(CommonFunctions.formatNumber(coin1Data.getHigh24h() / coin2Data.getHigh24h() * 100) + " %");
        } else {
            cHigh24.setText("-");
        }
        if (coin1Data.getLow24h() != null && coin2Data.getLow24h() != null) {
            cLow24.setText(CommonFunctions.formatNumber(coin1Data.getLow24h() / coin2Data.getLow24h() * 100) + " %");
        } else {
            cLow24.setText("-");
        }

        if (coin2Data.getTotalVolume() != 0) {
            double tradingVolDiff = (coin1Data.getTotalVolume() * 1.0 / coin2Data.getTotalVolume() * 1.0) * 100;
            cTradVol.setText(CommonFunctions.formatNumber(tradingVolDiff) + " %");
        } else {
            cTradVol.setText("-");
        }

        if (coin1Data.getMarketCapRank().equals("-") || coin2Data.getMarketCapRank().equals("-")) {
            cMktCapRank.setText("-");
        } else {
            cMktCapRank.setText(String.valueOf(Math.abs(Integer.parseInt(coin1Data.getMarketCapRank()) - Integer.parseInt(coin2Data.getMarketCapRank()))));
        }
        if (coin1Data.getAth() != null && coin2Data.getAth() != null) {

            cATH.setText(CommonFunctions.formatNumber((coin1Data.getAth() / coin2Data.getAth()) * 100) + " %");
        } else {
            cATH.setText("-");
        }
        if (coin1Data.getAtl() != null && coin2Data.getAtl() != null) {
            cATL.setText(CommonFunctions.formatNumber((coin1Data.getAtl() / coin2Data.getAtl() * 100))+ " %");
        } else {
            cATL.setText("-");
        }
    }

    private void goBackToMainView(Event actionEvent) {
        Stage primaryStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(secondScene);
    }

    private void displayCoinView(Event actionEvent, String name) {
        displayCoin(actionEvent, name, allCoins, coinViewController, currency, thirdScene);
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
