package org.crypto.gui.objects;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RankingTableView extends TableView<Coin> {

    public RankingTableView() {
        //brakuje atrybutu
        TableColumn<Coin, String> rScore = new TableColumn<>("#");
        rScore.setCellValueFactory(new PropertyValueFactory<>("marketCapRank"));
        rScore.getStyleClass().add("table-column-short");

        TableColumn<Coin, String> rLogo = new TableColumn<>("");
        rLogo.setCellFactory(param -> {
            //Set up the ImageView
            final ImageView imageview = new ImageView();

            //Set up the Table
            TableCell<Coin, String> cell = new TableCell<Coin, String>() {
                public void updateItem(String thumb, boolean empty) {
                    if (empty || thumb == null) {
                        setGraphic(null);
                    } else {
                        imageview.setImage(new Image(thumb));
                        setGraphic(imageview);
                    }
                }
            };
            return cell;
        });
        rLogo.setCellValueFactory(new PropertyValueFactory<>("imageUrl"));

        TableColumn<Coin, String> rCoin = new TableColumn<>("Coin");
        rCoin.setCellFactory(param -> {
            Button btnName = new Button();
            TableCell<Coin, String> cell = new TableCell<Coin, String>() {
                public void updateItem(String name, boolean empty) {
                    super.updateItem(name, empty);
                    if (empty || name == null) {
                        setGraphic(null);
                    }
                    else  {
                        btnName.setText(name);
                        btnName.getStyleClass().add("table-cell-btn");
                    }
                }
            };
            cell.setGraphic(btnName);
            return cell;
        });
        rCoin.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Coin, String> rSymbol = new TableColumn<>("");
        rSymbol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        rSymbol.getStyleClass().add("column-symbol");

        TableColumn<Coin, String> r24h = new TableColumn<>("24h");
        r24h.setCellValueFactory(new PropertyValueFactory<>("priceChangePercentage24h"));
        r24h.getStyleClass().add("right-column");

//        TableColumn<Coin, String> r24hVolume = new TableColumn<>("24h Volume");
//        rSymbol.setCellValueFactory(new PropertyValueFactory<>("_24hVolume"));

        TableColumn<Coin, String> rMktCap = new TableColumn<>("Mkt Cap");
        rMktCap.setCellValueFactory(new PropertyValueFactory<>("marketCap"));
        rMktCap.getStyleClass().add("right-column");

        TableColumn<Coin, String> rPrice = new TableColumn<>("Price");
        rPrice.setCellValueFactory(new PropertyValueFactory<>("currentPrice"));
        rPrice.getStyleClass().add("right-column");

        this.getColumns().addAll(rScore, rLogo, rCoin, rSymbol, rPrice, r24h, rMktCap);

        double[] widths = {40, 30, 200, 120, 80, 80, 150};
        double sum = 0;
        for (double i : widths) {
            sum += i;
        }
        this.setMinWidth(sum);
        this.setMaxWidth(sum);
        for (int i = 0; i < widths.length; i++) {
            this.getColumns().get(i).prefWidthProperty().bind(
                    this.widthProperty().multiply(widths[i] / sum));

        }
    }
}
