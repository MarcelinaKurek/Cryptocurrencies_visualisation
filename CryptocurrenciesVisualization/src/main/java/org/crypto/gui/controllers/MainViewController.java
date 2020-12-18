package org.crypto.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.textfield.TextFields;
import org.crypto.gui.objects.*;


import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML
    private BorderPane borderPane;

    @FXML
    private GridPane gridPane;

    private RankingTableView ranking;

    private TrendingTableView trending;

    @FXML
    private TextField textField;

    @FXML
    private Button go;

    private final ObservableList<Coin> trendingObservableList;

    private final ObservableList<Coin> rankingObservableList;

    public MainViewController()  {

        trendingObservableList = FXCollections.observableArrayList();

        trendingObservableList.addAll(
                new Coin("ice"),
                new Coin("ego"),
                new Coin("hollow"),
                new Coin("internet"),
                new Coin("stage"),
                new Coin("jumbo"),
                new Coin("zoo")
        );

        rankingObservableList = FXCollections.observableArrayList();

        rankingObservableList.addAll(
                new Coin("hello"),
                new Coin("bitcoin"),
                new Coin("tether"),
                new Coin("internet"),
                new Coin("bambo"),
                new Coin("jumbo"),
                new Coin("zoo231s")
        );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        trending = new TrendingTableView();

        ranking = new RankingTableView();

        trending.getItems().add(new Coin("Bitcoin", "f", "kjkj", "klkka", "ksja", "kalsk", "price"));
        trending.getItems().add(new Coin("Bitcoin", "f", "kjkj", "klkka", "ksja", "kalsk", "price"));
        trending.getItems().add(new Coin("Bitcoin", "f", "kjkj", "klkka", "ksja", "kalsk", "price"));
        trending.getItems().add(new Coin("Bitcoin", "f", "kjkj", "klkka", "ksja", "kalsk", "price"));
        trending.getItems().add(new Coin("Bitcoin", "f", "kjkj", "klkka", "ksja", "kalsk", "price"));
        trending.getItems().add(new Coin("Bitcoin", "f", "kjkj", "klkka", "ksja", "kalsk", "price"));
        trending.getItems().add(new Coin("Bitcoin", "f", "kjkj", "klkka", "ksja", "kalsk", "price"));

        ranking.getItems().add(new Coin("Bitcoin", "f", "kjkj", "klkka", "ksja", "kalsk", "price"));
        ranking.getItems().add(new Coin("Bitcoin", "f", "kjkj", "klkka", "ksja", "kalsk", "price"));
        ranking.getItems().add(new Coin("Bitcoin", "f", "kjkj", "klkka", "ksja", "kalsk", "price"));
        ranking.getItems().add(new Coin("Bitcoin", "f", "kjkj", "klkka", "ksja", "kalsk", "price"));
        ranking.getItems().add(new Coin("Bitcoin", "f", "kjkj", "klkka", "ksja", "kalsk", "price"));
        ranking.getItems().add(new Coin("Bitcoin", "f", "kjkj", "klkka", "ksja", "kalsk", "price"));
        ranking.getItems().add(new Coin("Bitcoin", "f", "kjkj", "klkka", "ksja", "kalsk", "price"));
        ranking.setFixedCellSize(60);
        ranking.setMinHeight(450);

        TextFields.bindAutoCompletion(textField, "Kot", "Pies", "Mysz", "Kotlet", "dziura");
        gridPane.add(ranking, 0, 1);
        gridPane.add(trending, 1, 1);
    }

    public void displayCCurrency() {
        System.out.println("hello you clicked go!");
    }

}
