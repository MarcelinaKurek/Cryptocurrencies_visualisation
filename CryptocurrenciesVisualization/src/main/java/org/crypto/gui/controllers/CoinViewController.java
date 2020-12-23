package org.crypto.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.crypto.gui.objects.TableData;
import org.crypto.services.APIClient;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class CoinViewController implements Initializable {

    @FXML
    private ImageView logo;
    @FXML
    private ChoiceBox<String> currencyTop;
    @FXML
    private BorderPane coinViewBorderPane;
    @FXML
    private GridPane gridPaneTop;
    @FXML
    private GridPane gridPaneTable;
    @FXML
    private Label name;
    @FXML
    private Label priceTop;
    @FXML
    private Label price;
    @FXML
    private Label marketCap;
    @FXML
    private Label marketCapDom;
    @FXML
    private Label tradingVol;
    @FXML
    private Label lowHigh24;
    @FXML
    private Label mktCapRank;
    @FXML
    private Label allTimeHigh;
    @FXML
    private Label allTimeLow;


    private APIClient apiClient;
    private List<String> currencies;

    private String currency;
    private String coinId;
    private TableData coinData = null;

    public CoinViewController(APIClient apiClient) {
        this.apiClient = apiClient;
        this.currencies = apiClient.getSupportedCurrencies();

    }

    public CoinViewController(String currency, String coinId) {
        this.currency = currency;
        this.coinId = coinId;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Stream.of(price, marketCap, marketCapDom, mktCapRank, lowHigh24, allTimeHigh, allTimeLow, tradingVol)
                .forEach(e -> e.getStyleClass().add("table-element-content"));
        gridPaneTable.setGridLinesVisible(true);
        gridPaneTable.setAlignment(Pos.CENTER_LEFT);
        gridPaneTable.getRowConstraints().forEach(row -> row.setMaxHeight(40));

    }

    public void prepareScene() {
        if (coinData != null) {
            logo.setImage(new Image(coinData.getImageLargeUrl()));
            name.setText(coinData.getId());
            priceTop.setText(String.valueOf(coinData.getCurrentPrice()));
            price.setText(String.valueOf(coinData.getCurrentPrice()));
            marketCap.setText(String.valueOf(coinData.getMarketCap()));
            tradingVol.setText(String.valueOf(coinData.getTotalVolume()));
            lowHigh24.setText(coinData.getLow24h() + " / " + coinData.getHigh24h());
            mktCapRank.setText(String.valueOf(coinData.getMarketCapRank()));
            allTimeLow.setText(String.valueOf(coinData.getAtl()));
            allTimeHigh.setText(String.valueOf(coinData.getAth()));

            currencyTop.getItems().addAll(currencies);
            currencyTop.setValue(currency);
            currencyTop.setOnAction(e -> {
                currency = currencyTop.getValue();
                updateScene();
            });
        }
    }

    public void updateScene() {
        coinData = apiClient.getTableData(coinId, currency);
        prepareScene();
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
        coinData = apiClient.getTableData(coinId, currency);
    }
}
