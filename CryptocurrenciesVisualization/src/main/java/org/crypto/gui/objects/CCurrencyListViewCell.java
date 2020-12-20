package org.crypto.gui.objects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class CCurrencyListViewCell extends ListCell<Coin> {

    @FXML
    private GridPane gridPane;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Coin cryptoCurrency, boolean empty) {
        super.updateItem(cryptoCurrency, empty);

        if(empty || cryptoCurrency == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/cCurrencyListViewCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (label1 == null) {
                label1 = new Label();
            }
            if (label2 == null) {
                label2 = new Label();
                System.out.println(label2.getText());
            }
            label1.setText(cryptoCurrency.getName());
            label2.setText("786");
            setText(null);
            setGraphic(gridPane);
        }
    }
}
