package org.crypto.gui.controllers;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.crypto.gui.objects.TableData;
import org.crypto.gui.plots.Plots;
import org.crypto.services.APIClient;
import tech.tablesaw.plotly.components.Figure;

import javafx.scene.web.*;

import java.net.URL;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class CoinViewController implements Initializable {

    @FXML
    private ImageView logo;
    @FXML
    private BorderPane coinViewBorderPane;
    @FXML
    private GridPane gridPaneTop;
    @FXML
    private GridPane gridPaneTable;
    @FXML
    private HBox titlePane;
    @FXML
    private Label name;
    @FXML
    private Hyperlink websiteTop;
    @FXML
    private Label priceTop;
    @FXML
    private Button goBackToMain;
    @FXML
    private Label price;
    @FXML
    private Label marketCap;
    @FXML
    private Label mktCapTop;
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
    @FXML
    private ComboBox<String> currencyTop;

    private Figure fig;
    private WebView webView = new WebView();
    private Scene secondScene = null;

    private List<String> currencies;

    private String currency;
    private String coinId;
    private TableData coinData = null;
    private int days = 7;
    private Plots.PlotType plotType = Plots.PlotType.EXCHANGE_RATE;

    private boolean readyScene = false;
    private Consumer<String> redirectToUrl = null;

    public CoinViewController() {
        this.currencies = APIClient.getSupportedCurrencies();
        webView.setMaxSize(800, 600);
    }

//    public CoinViewController(String currency, String coinId) {
//        this.currency = currency;
//        this.coinId = coinId;
//        webView.setMaxSize(800, 600);
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Stream.of(price, marketCap, marketCapDom, mktCapRank, lowHigh24, allTimeHigh, allTimeLow, tradingVol)
                .forEach(e -> e.getStyleClass().add("table-element-content"));
        gridPaneTable.setGridLinesVisible(false);
        gridPaneTable.setAlignment(Pos.CENTER_LEFT);
        gridPaneTable.setPadding(new Insets(10));
        gridPaneTable.getRowConstraints().forEach(row -> row.setMaxHeight(40));
        gridPaneTable.getColumnConstraints().forEach(col -> col.setMinWidth(150));
    }

    public void prepareScene() {
        if (coinData != null) {
            System.out.println(coinData.toString());
            readyScene = true;
            logo.setImage(new Image(coinData.getImageLargeUrl()));
            name.setText(coinData.getName());
            websiteTop.setText(coinData.getWebsite());
            websiteTop.setTooltip(new Tooltip(coinData.getWebsite()));
            websiteTop.setOnAction((ActionEvent event) -> {
                Hyperlink h = (Hyperlink) event.getTarget();
                String s = h.getTooltip().getText();
                redirectToUrl.accept(s);
                event.consume();
            });

            currencyTop.getItems().addAll(currencies);
            currencyTop.setValue(currency);
            currencyTop.setOnAction(e -> {
                currency = currencyTop.getValue();
                updateScene();
                updateFig();
            });
            goBackToMain.setOnAction(this::goBackToMainView);
            titlePane.setSpacing(20);
            titlePane.setAlignment(Pos.CENTER_LEFT);
            name.setStyle("-fx-font-size: 20px");

            VBox vBox = new VBox();
            vBox.getChildren().addAll(dateAndTypeButtons(), webView);
            coinViewBorderPane.setCenter(vBox);
            reloadAfterChangingCurrency();

            // hide scrollbars
            webView.getChildrenUnmodifiable().addListener(new ListChangeListener<Node>() {
                @Override public void onChanged(Change<? extends Node> change) {
                    Set<Node> deadSeaScrolls = webView.lookupAll(".scroll-bar");
                    for (Node scroll : deadSeaScrolls) {
                        scroll.setVisible(false);
                    }
                }
            });
        }
    }

    public void updateScene() {
        coinData = APIClient.getTableData(coinId, currency);
        if (readyScene) {
            reloadAfterChangingCurrency();
        } else {
            prepareScene();
        }
    }

    private void reloadAfterChangingCurrency() {
        if (coinData != null) {
            priceTop.setText(coinData.getCurrentPrice() + " " + currency);
            mktCapTop.setText(String.valueOf(coinData.getMarketCap()));
            price.setText(coinData.getCurrentPrice() + " " + currency);
            marketCap.setText(String.valueOf(coinData.getMarketCap()));
            tradingVol.setText(coinData.getTotalVolume() + " " + currency);

            if (coinData.getLow24h() == null || coinData.getHigh24h() == null) {
                lowHigh24.setText(" - / -");
            } else {
                lowHigh24.setText(coinData.getLow24h() + " " + currency + " / " + coinData.getHigh24h() + " " + currency);
            }

            mktCapRank.setText(coinData.getMarketCapRank());
            allTimeLow.setText(coinData.getAtl() + " " + currency);
            allTimeHigh.setText(coinData.getAth() + " " + currency);
        }
    }

    private HBox dateAndTypeButtons() {
        HBox hBox = new HBox();
        Button h_24 = new Button("24h");
        h_24.setOnAction(e -> dateButtonAction(1));
        Button d_7 = new Button("7d");
        d_7.setOnAction(e -> dateButtonAction(7));
        Button d_30 = new Button("30d");
        d_30.setOnAction(e -> dateButtonAction(30));
        Button y_1 = new Button("1y");
        y_1.setOnAction(e -> dateButtonAction(365));
        Button candle = new Button("Candle plot");
        candle.setOnAction(e -> plotTypeButtonAction(Plots.PlotType.CANDLE_PLOT));
        Button exchangeRate = new Button("Linear plot");
        exchangeRate.setOnAction(e -> plotTypeButtonAction(Plots.PlotType.EXCHANGE_RATE));
        Button exchangeRateDiff = new Button("Linear plot - diff");
        exchangeRateDiff.setOnAction(e -> plotTypeButtonAction(Plots.PlotType.EXCHANGE_RATE_DIFF));
        hBox.getChildren().addAll(h_24, d_7, d_30, y_1, candle, exchangeRate, exchangeRateDiff);
        return hBox;
    }

    private void dateButtonAction(int days) {
        this.days = days;
        updateFig();
    }

    private void plotTypeButtonAction(Plots.PlotType plotType) {
        this.plotType = plotType;
        updateFig();
    }

    private void updateFig() {
        if (plotType.equals(Plots.PlotType.CANDLE_PLOT)) {
            fig = Plots.CandleStickPlot(coinId, currency, days);
        } else if (plotType.equals(Plots.PlotType.EXCHANGE_RATE)) {
            fig = Plots.ExchangeRatePlot(coinId, currency, days);
        } else if (plotType.equals(Plots.PlotType.EXCHANGE_RATE_DIFF)) {
            fig = Plots.ExchangeRateDiffPlot(coinId, currency, days);
        }
        webView.getEngine().loadContent(Plots.toHtml(fig), "text/html");
    }

    private void goBackToMainView(Event actionEvent) {
        Stage primaryStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(secondScene);
        readyScene = false;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
        coinData = APIClient.getTableData(coinId, currency);
    }

    public void setFig(Figure fig) {
        this.fig = fig;
        webView.getEngine().loadContent(Plots.toHtml(fig), "text/html");
    }

    public void setUrlRedirectingFunc(Consumer<String> func) {
        this.redirectToUrl = func;
    }

    public void setSecondScene(Scene scene) {
        this.secondScene = scene;
    }



}
