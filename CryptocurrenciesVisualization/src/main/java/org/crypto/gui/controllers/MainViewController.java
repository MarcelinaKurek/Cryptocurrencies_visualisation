package org.crypto.gui.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.controlsfx.control.textfield.TextFields;
import org.crypto.gui.objects.CryptoCurrency;
import org.crypto.gui.objects.CCurrencyListViewCell;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private ListView<CryptoCurrency> trending;

    @FXML
    private TextField textField;

    @FXML Button go;

    private final ObservableList<CryptoCurrency> cryptoCurrencyObservableList;

    public MainViewController()  {

        cryptoCurrencyObservableList = FXCollections.observableArrayList();

        cryptoCurrencyObservableList.addAll(
                new CryptoCurrency("ice"),
                new CryptoCurrency("ego"),
                new CryptoCurrency("hollow"),
                new CryptoCurrency("internet"),
                new CryptoCurrency("stage"),
                new CryptoCurrency("jumbo"),
                new CryptoCurrency("zoo")
        );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        trending.setItems(cryptoCurrencyObservableList);
        trending.setCellFactory(cryptoCurrencyListView -> new CCurrencyListViewCell());

        TextFields.bindAutoCompletion(textField, "Kot", "Pies", "Mysz", "Kotlet", "dziura");
    }

    public void displayCCurrency() {
        System.out.println("hello you clicked go!");
    }

}
