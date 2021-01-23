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
    private Label c1MktCapDom;
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
    private Label c2MktCapDom;
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
    private Label cMktCapDom;
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
    private List<String> currencies = null;
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
        currencies = APIClient.getSupportedCurrencies();

        List<String> allCoinsNames = new ArrayList<>(allCoins.values());
        TextFields.bindAutoCompletion(textField1, allCoinsNames);
        TextFields.bindAutoCompletion(textField2, allCoinsNames);
        c1Name.setVisible(false);
        c2Name.setVisible(false);
        cTitle.setVisible(false);
        cTitle.setStyle("-fx-font-weight: bold");

        ComboBox<String> comboBox = new ComboBox<String>();
        comboBox.getItems().addAll(currencies);
        comboBox.setValue("eur");
        hBox.getChildren().add(comboBox);
        comboBox.setVisibleRowCount(4);
        comboBox.setOnAction(e -> {
            currency = comboBox.getValue();
            loadData(textField1.getText(), textField2.getText());
            loadTable();
        });

        submitBtn.setOnAction(e -> {
            if (allCoins.containsValue(textField1.getText()) && allCoins.containsValue((textField2.getText()))) {
                loadData(textField1.getText(), textField2.getText());
                loadTable();
                loadComparisonColumn();
            }
        });

        c1Name.setOnAction(e -> displayCoin(e, textField1.getText()));
        c2Name.setOnAction(e -> displayCoin(e, textField2.getText()));

        backToMain.setOnAction(this::goBackToMainView);
    }

    private void loadData(String name1, String name2) {
        String id1 = CommonFunctions.findIdByName(allCoins, name1);
        String id2 = CommonFunctions.findIdByName(allCoins, name2);

        this.coin1Data = APIClient.getTableData(id1, currency);
        this.coin2Data = APIClient.getTableData(id2, currency);
    }

    private void loadTable() {
        c1Logo.setImage(new Image(coin1Data.getImageSmallUrl()));
        c1Name.setText(coin1Data.getName());
        c1Name.setVisible(true);
        c1Price.setText(coin1Data.getCurrentPrice() + " " + currency);
        c1MktCap.setText(coin1Data.getMarketCap() + " " + currency);
        c1TradVol.setText(coin1Data.getTotalVolume() + " " + currency);
        c1Low24.setText(coin1Data.getLow24h() + " " + currency);
        c1High24.setText(coin1Data.getHigh24h() + " " + currency);
        c1MktCapRank.setText(String.valueOf(coin1Data.getMarketCapRank()));
        c1ATH.setText(coin1Data.getAth() + " " + currency);
        c1ATL.setText(coin1Data.getAtl() + " " + currency);

        c2Logo.setImage(new Image(coin2Data.getImageSmallUrl()));
        c2Name.setText(coin2Data.getName());
        c2Name.setVisible(true);
        c2Price.setText(coin2Data.getCurrentPrice() + " " + currency);
        c2MktCap.setText(coin2Data.getMarketCap() + " " + currency);
        c2TradVol.setText(coin2Data.getTotalVolume() + " " + currency);
        c2Low24.setText(coin2Data.getLow24h() + " " + currency);
        c2High24.setText(coin2Data.getHigh24h() + " " + currency);
        c2MktCapRank.setText(String.valueOf(coin2Data.getMarketCapRank()));
        c2ATH.setText(coin2Data.getAth() + " " + currency);
        c2ATL.setText(coin2Data.getAtl() + " " + currency);
    }

    private void loadComparisonColumn() {
        cTitle.setVisible(true);
        Double priceDiff = (coin1Data.getCurrentPrice() / coin2Data.getCurrentPrice()) * 100;
        cPrice.setText(priceDiff + "%");
        cMktCap.setText(String.valueOf(coin1Data.getMarketCap() - coin2Data.getMarketCap()));
        cMktCapDom.setText("g");
        if (coin1Data.getHigh24h() != null && coin2Data.getHigh24h() != null) {
            cHigh24.setText((coin1Data.getHigh24h() / coin2Data.getHigh24h()) * 100 + "%");
        } else {
            cHigh24.setText("-");
        }
        if (coin1Data.getLow24h() != null && coin2Data.getLow24h() != null) {
            cLow24.setText((coin1Data.getLow24h() / coin2Data.getLow24h()) * 100 + "%");
        } else {
            cLow24.setText("-");
        }

        if (coin2Data.getTotalVolume() != 0) {
            long tradingVolDiff = (coin1Data.getTotalVolume() / coin2Data.getTotalVolume()) * 100;
            cTradVol.setText(tradingVolDiff + "%");
        } else {
            cTradVol.setText("-");
        }
        cMktCapRank.setText(String.valueOf(coin1Data.getMarketCapRank() - coin2Data.getMarketCapRank()));
        if (coin1Data.getAth() != null && coin2Data.getAth() != null) {
            cATH.setText((coin1Data.getAth() / coin2Data.getAth()) * 100 + "%");
        } else {
            cATH.setText("-");
        }
        if (coin1Data.getAtl() != null && coin2Data.getAtl() != null) {
            cATL.setText((coin1Data.getAtl() / coin2Data.getAtl()) * 100 + "%");
        } else {
            cATL.setText("-");
        }
    }

    private void goBackToMainView(Event actionEvent) {
        Stage primaryStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(secondScene);
    }

    public void setSecondScene(Scene scene) { this.secondScene = scene; }

    public void setThirdScene(Scene scene) {
        this.thirdScene = scene;
    }

    private void displayCoin(Event actionEvent, String name) {
        Stage primaryStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        String id = CommonFunctions.findIdByName(allCoins, name);
        if (!id.equals("")) {
            coinViewController.setCurrency(currency);
            coinViewController.setCoinId(id);
            coinViewController.updateScene();
            primaryStage.setScene(thirdScene);
        }
    }

    public void setCoinViewController(CoinViewController coinViewController) {
        this.coinViewController = coinViewController;
    }
}
