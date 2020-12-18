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

        TableColumn<Coin, Image> logo = new TableColumn<>("");

        logo.setCellFactory(param -> {
            //Set up the ImageView
            final ImageView imageview = new ImageView();
            imageview.setFitHeight(40);
            imageview.setFitWidth(40);

            //Set up the Table
            TableCell<Coin, Image> cell = new TableCell<Coin, Image>() {
                public void updateItem(Image item, boolean empty) {
                    if (item != null) {
                        imageview.setImage(item);
                    }
                }
            };
            // Attach the imageview to the cell
            cell.setGraphic(imageview);
            return cell;
        });

        logo.setCellValueFactory(new PropertyValueFactory<Coin, Image>("logo"));

        TableColumn<Coin, Button> coin = new TableColumn<>("Coin");

        coin.setCellFactory(param -> {
            Button btnName = new Button();


            TableCell<Coin, Button> cell = new TableCell<Coin, Button>() {
                public void updateItem(String name, boolean empty) {
                    if (name != null) {
                        btnName.setText(name);
                    }
                }
            };
            cell.setGraphic(btnName);
            return cell;
        });
        coin.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Coin, String> symbol = new TableColumn<>("");
        symbol.setCellValueFactory(new PropertyValueFactory<>("symbol"));

        TableColumn<Coin, String> mktCapRank = new TableColumn<>("Mkt Cap Rank");
        mktCapRank.setCellValueFactory(new PropertyValueFactory<>("mktCap"));

        this.setMaxHeight(420);

        this.getColumns().addAll(score, logo, coin, symbol, mktCapRank);
    }
}
