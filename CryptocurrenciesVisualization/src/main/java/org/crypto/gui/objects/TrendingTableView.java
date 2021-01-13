package org.crypto.gui.objects;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class TrendingTableView extends TableView<Coin> {

    public TrendingTableView() {

        TableColumn<Coin, String> score = new TableColumn<>("#");
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        score.getStyleClass().add("table-column-short");

        TableColumn<Coin, String> logo = new TableColumn<>("");
        logo.setCellFactory(param -> {
            //Set up the ImageView
            final ImageView imageview = new ImageView();

            //Set up the Table
            TableCell<Coin, String> cell = new TableCell<Coin, String>() {
                public void updateItem(String thumb, boolean empty) {
                    if (empty || thumb == null) {
                        setGraphic(null);
                    } else {
                        imageview.setImage(new Image(thumb));
                    }
                }
            };
            // Attach the imageview to the cell
            cell.setGraphic(imageview);
            return cell;
        });
        logo.setCellValueFactory(new PropertyValueFactory<>("imageUrl"));

        TableColumn<Coin, String> coin = new TableColumn<>("Coin");
        coin.setCellFactory(param -> {
            Button btnName = new Button();

            TableCell<Coin, String> cell = new TableCell<Coin, String>() {
                public void updateItem(String c, boolean empty) {
                    super.updateItem(c, empty);
                    if (empty || c == null) {
                        setGraphic(null);
                    }
                    else  {
                        btnName.setText(c);
                        btnName.getStyleClass().add("table-cell-btn");
                        setGraphic(btnName);
                    }
                }
            };
            cell.setGraphic(btnName);
            return cell;
        });
        coin.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Coin, String> symbol = new TableColumn<>("");
        symbol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        symbol.getStyleClass().add("column-symbol");

        TableColumn<Coin, String> mktCapRank = new TableColumn<>("Mkt Cap Rank");
        mktCapRank.setCellValueFactory(new PropertyValueFactory<>("marketCapRank"));
        mktCapRank.getStyleClass().add("right-column");

        this.getColumns().addAll(score, logo, coin, symbol, mktCapRank);

        double[] widths = {40, 30, 200, 120, 150};
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
