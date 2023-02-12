package com.example.shopdelivery.controllers;

import com.example.shopdelivery.entitys.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductItemController implements Initializable {

    @FXML
    private Text itemId;

    @FXML
    private ImageView itemImg;

    @FXML
    private Text itemPrice;

    @FXML
    private Text itemTitle;

    private Product product;

    public void setData(Product product) {
        this.product = product;
        itemId.setText(product.getId());
        itemTitle.setText(product.getTitle());
        itemPrice.setText(product.getPrice() + " руб.");
        itemImg.setImage(new Image(product.getLink()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}
