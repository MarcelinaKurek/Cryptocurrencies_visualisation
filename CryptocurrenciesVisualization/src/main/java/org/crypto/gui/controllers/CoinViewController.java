package org.crypto.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CoinViewController implements Initializable {
    @FXML
    private Label name;

    private String currency;
    private String coinId;

    public CoinViewController() {

    }

    public CoinViewController(String currency, String coinId) {
        this.currency = currency;
        this.coinId = coinId;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void prepareScene() {

    }

    public void updateScene() {
        name.setText(coinId);
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }
}
