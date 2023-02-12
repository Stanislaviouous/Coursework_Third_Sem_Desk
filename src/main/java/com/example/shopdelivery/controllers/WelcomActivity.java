package com.example.shopdelivery.controllers;

import com.example.shopdelivery.entitys.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
        import javafx.scene.control.PasswordField;
        import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class WelcomActivity {
    private static HttpURLConnection con;
    public static String nameAll;
    public static String role;

    @FXML
    private PasswordField Rpassword;

    @FXML
    private TextField Rusername;


    @FXML
    private PasswordField confirmPassword;

    @FXML
    private TextField email;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private Button loginButton;

    @FXML
    private Text textSApi;

    @FXML
    private PasswordField password;

    @FXML
    private Button registerButton;

    @FXML
    void onLogin(MouseEvent event) {
        String mail = Rusername.getText();
        String pass = Rpassword.getText();
        var url = "http://localhost:5050/authorization?email=" + mail + "&password=" + pass;
        System.out.println(url);
        try {
            var myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();
            con.setRequestMethod("GET");
            StringBuilder content;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String line;
                content = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            String s1 = "";
            s1 = content.toString();
            if (s1.equals("false")){
                textSApi.setText("Не корекктные данные");
            }
            else {
                nameAll = s1.split("-")[0];
                role = s1.split("-")[1];
            }
        } catch (Error e) {
            textSApi.setText("Не корекктные данные");
        } catch (Exception e) {
            textSApi.setText("Не корекктные данные");
        } finally {
            con.disconnect();
        }
        Stage stage = new Stage();
        Parent root;
        System.out.println(role);
        switch (role.strip()) {
            case "admin":
                // Hide this current window (if this is what you want)
                try {
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("admin_activity.fxml"));
                    stage.setTitle("");
                    stage.setScene(new Scene(root));
                    stage.show();
                    // Hide this current window (if this is what you want)
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "storeman":
                System.out.println("sdsd");

                try {
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("storeman_activity.fxml"));
                    stage = new Stage();
                    stage.setTitle("");
                    stage.setScene(new Scene(root));
                    stage.show();
                    // Hide this current window (if this is what you want)
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "dispatcher":
                System.out.println("sddsd");
                try {
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("dispatcher_activity.fxml"));
                    stage = new Stage();
                    stage.setTitle("");
                    stage.setScene(new Scene(root));
                    stage.show();
                    // Hide this current window (if this is what you want)
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "courier":
                System.out.println("sdsd");
                try {
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("сourier_activity.fxml"));
                    stage = new Stage();
                    stage.setTitle("");
                    stage.setScene(new Scene(root));
                    stage.show();
                    // Hide this current window (if this is what you want)
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }
}
/*
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.Cursor?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.PasswordField?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.BorderPane?>
<?import javafx.scene.text.Font?>
<?import javafx.scene.text.Text?>


<BorderPane layoutY="400.0" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/19" xmlns:fx="http://javafx.com/fxml/1">
   <left>
      <AnchorPane prefHeight="400.0" prefWidth="300.0" style="-fx-background-color: #404040;" BorderPane.alignment="CENTER" />
   </left>
   <right>
      <AnchorPane prefHeight="400.0" prefWidth="300.0" BorderPane.alignment="CENTER">
         <children>
            <Text fill="#404040" layoutX="47.0" layoutY="53.0" strokeType="OUTSIDE" strokeWidth="0.0" text="User Registration" wrappingWidth="206.24869537353516">
               <font>
                  <Font name="Wingdings 3" size="27.0" />
               </font>
            </Text>
            <TextField fx:id="firstName" layoutX="49.0" layoutY="76.0" prefHeight="25.0" prefWidth="202.0" promptText="First Name" style="-fx-background-color: transparent; -fx-border-color: #404040; -fx-border-width: 0px 0px 2px 0px;" />
            <TextField fx:id="lastName" layoutX="49.0" layoutY="113.0" prefHeight="25.0" prefWidth="202.0" promptText="Last Name" style="-fx-background-color: transparent; -fx-border-color: #404040; -fx-border-width: 0px 0px 2px 0px;" />
            <TextField fx:id="email" layoutX="49.0" layoutY="147.0" prefHeight="25.0" prefWidth="202.0" promptText="Email" style="-fx-background-color: transparent; -fx-border-color: #404040; -fx-border-width: 0px 0px 2px 0px;" />
            <PasswordField fx:id="password" layoutX="49.0" layoutY="187.0" prefHeight="25.0" prefWidth="202.0" promptText="Password" style="-fx-background-color: transparent; -fx-border-color: #404040; -fx-border-width: 0px 0px 2px 0px;" />
            <PasswordField fx:id="Role" layoutX="49.0" layoutY="229.0" prefHeight="25.0" prefWidth="202.0" promptText="Role" style="-fx-background-color: transparent; -fx-border-color: #404040; -fx-border-width: 0px 0px 2px 0px;" />
            <Button fx:id="registerButton" layoutX="112.0" layoutY="273.0" mnemonicParsing="false" prefHeight="32.0" prefWidth="76.0" style="-fx-background-color: #404040; -fx-background-radius: 0px;" text="Submit" textFill="WHITE">
               <cursor>
                  <Cursor fx:constant="HAND" />
               </cursor>
            </Button>
         </children>
      </AnchorPane>
   </right>
</BorderPane>

* */