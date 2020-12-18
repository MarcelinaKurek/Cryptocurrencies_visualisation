package org.crypto.gui.objects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class RankListViewCell extends ListCell<Coin> {

    @FXML
    private GridPane gridPane;

    @FXML
    private Label score;

    @FXML
    private ImageView logo;

    @FXML
    private Button name;

    @FXML
    private Label symbol;

    @FXML
    private Label price;

    @FXML
    private Label percent_change_24;

    @FXML
    private Label market_cap;


    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Coin cryptoCurrency, boolean empty) {
        super.updateItem(cryptoCurrency, empty);
        this.getStyleClass().add("cell-pane");

        if(empty || cryptoCurrency == null) {
            setText(null);
            setGraphic(null);

        } else {
            System.out.println("not null");
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/rankListViewCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (symbol == null) {
                symbol = new Label();
            }
            if (score == null) {
                score = new Label();
            }
            if (price == null) {
                price = new Label();
            }
            if (percent_change_24 == null) {
                percent_change_24 = new Label();
            }
            if (market_cap == null) {
                market_cap = new Label();
            }

            score.setText(cryptoCurrency.getName());
            symbol.setText("46767488");
            price.setText("Price");
            percent_change_24.setText("%%%%%");
            market_cap.setText("Market");
            name.setText("Guzik");

            setText(null);
            setGraphic(gridPane);
        }
    }
}

