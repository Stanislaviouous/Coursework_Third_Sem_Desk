package com.example.shopdelivery.controllers;

import com.example.shopdelivery.entitys.Delivery;
import com.example.shopdelivery.entitys.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class StoremanActivity implements Initializable {
    private static HttpURLConnection con;
    @FXML
    private TextArea editTextD;

    @FXML
    private Button runD;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextArea infoText = new TextArea();
    @FXML
    private Text nameText;
    @FXML
    private ImageView img;
    @FXML
    private ListView<String> listViewD;

    String name = WelcomActivity.nameAll;

    ArrayList<Delivery> deliveries = new ArrayList<>();
    String[] list;
    public static String nameOfDelivery = "";
    //    private ArrayList<Product> getData() {
//        ArrayList<Product> productList = new ArrayList<>();
//        String str = new String(Files.readAllBytes(Paths.get(fileNameProducts)));
//        productList = gson.fromJson(s1, new TypeToken<ArrayList<Product>>(){}.getType());
//        return productList;
//    }
    @FXML
    void onRun(MouseEvent event) {
        String str = editTextD.getText();
        Scanner sc = new Scanner(str);
        while (sc.hasNextLine()){
            String s = sc.nextLine();
            System.out.println(s.substring(0,1));
            switch (s.substring(0,1)){
                case "-":
                    int index = -1;
                    String idi = s.substring("- Подтвердить (".length(), s.length() - 1);
                    String r = "";
                    var url = "http://localhost:5050/setStatus?idDelivery=" + idi + "&status=Complete";
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
                        r = content.toString();
                        editTextD.setText(r.trim());
                        editTextD.appendText("\n" + "Подтвержденно");
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } finally {
                        con.disconnect();
                    }
                    System.out.println(r);
                    break;

                case "/":
                    index = -1;
                    idi = s.substring("/ Собрать (".length(), s.length() - 1);
                    r = "";
                    url = "http://localhost:5050/setStatus?idDelivery=" + idi + "&status=Collect";
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
                        r = content.toString();
                        editTextD.setText(r.trim());
                        editTextD.appendText("\n" + "Собранно");
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } finally {
                        con.disconnect();
                    }
                    System.out.println(r);
                    break;
                case "+":
                    index = -1;
                    idi = s.substring("+ Отдать курьеру (".length(), s.length() - 1);
                    r = "";
                    url = "http://localhost:5050/setStatus?idDelivery=" + idi + "&status=Put";
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
                        r = content.toString();
                        editTextD.setText(r.trim());
                        editTextD.appendText("\n" + "Переданно курьеру");
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } finally {
                        con.disconnect();
                    }
                    System.out.println(r);
                    break;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String D = ""; // Номер доставки
        nameText.setText(name);
        var url = "http://localhost:5050/deliveries";
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
            Gson gson = new Gson();
            s1 = content.toString();
            System.out.println(s1);
            deliveries = gson.fromJson(s1, new TypeToken<ArrayList<Delivery>>(){}.getType());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            con.disconnect();
        }
        for (int i = 0; i < deliveries.size(); i++) {
            listViewD.getItems().add(deliveries.get(i).toString());
        }

//        Node[] nodes = new Node[products.size()];
//        for (int i = 0; i < products.size(); i++) {
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("product_item.fxml")));
//                nodes[i] = fxmlLoader.load();
//                layot.getChildren().add(nodes[i]);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }


    }
}
