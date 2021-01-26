package org.crypto.gui.controllers;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Optional;

public class CommonFunctions {
    public static String findIdByName(Map<String, String> allCoins, String name) {
        Optional<String> id = allCoins
                .entrySet()
                .stream()
                .filter(entry -> name.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();

        return id.orElse("");
    }

    public static String formatNumber(double n){
        NumberFormat numFormat;
        numFormat = new DecimalFormat("0.####E0");
        return numFormat.format(n).replace("E", " x 10^").replace(",", ".");
    }

    /**
     * Znajduje id kryptowaluty dla nazwy i otwiera widok
     * kryptowaluty, jeśli podana nazwa nie istnieje nic się nie dzieje.
     * @param name - nazwa kryptowaluty
     */
    public static void displayCoin(Event actionEvent, String name, Map<String, String> allCoins, CoinViewController coinViewController, String currency, Scene scene) {
        Stage primaryStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        String id = CommonFunctions.findIdByName(allCoins, name);
        if (!id.equals("")) {
            coinViewController.setCurrency(currency);
            coinViewController.setCoinId(id);
            coinViewController.updateScene();
            primaryStage.setScene(scene);
        }
    }
}
