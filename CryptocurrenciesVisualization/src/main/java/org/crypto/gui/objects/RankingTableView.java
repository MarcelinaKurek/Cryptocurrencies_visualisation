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
        TableColumn<Coin, String> rScore = new TableColumn<>("#");
        rScore.setCellValueFactory(new PropertyValueFactory<>("score"));

        TableColumn<Coin, Image> rLogo = new TableColumn<>("");

        rLogo.setCellFactory(param -> {
            //Set up the ImageView
            final ImageView imageview = new ImageView();
            imageview.setFitHeight(50);
            imageview.setFitWidth(50);

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

        rLogo.setCellValueFactory(new PropertyValueFactory<Coin, Image>("logo"));

        TableColumn<Coin, Button> rCoin = new TableColumn<>("Coin");

        rCoin.setCellFactory(param -> {
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
        rCoin.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Coin, String> rSymbol = new TableColumn<>("");
        rSymbol.setCellValueFactory(new PropertyValueFactory<>("symbol"));

        TableColumn<Coin, String> r24h = new TableColumn<>("24h");
        rSymbol.setCellValueFactory(new PropertyValueFactory<>("_24h"));

        TableColumn<Coin, String> r24hVolume = new TableColumn<>("24h Volume");
        rSymbol.setCellValueFactory(new PropertyValueFactory<>("_24hVolume"));

        TableColumn<Coin, String> rMktCap = new TableColumn<>("Mkt Cap");
        rMktCap.setCellValueFactory(new PropertyValueFactory<>("mktCap"));

        TableColumn<Coin, String> rPrice = new TableColumn<>("Price");
        rPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        this.getColumns().addAll(rScore, rLogo, rCoin, rSymbol, rPrice, r24h, r24hVolume, rMktCap);
    }
}
