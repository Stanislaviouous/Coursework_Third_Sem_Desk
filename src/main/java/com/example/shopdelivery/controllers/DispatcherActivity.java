package com.example.shopdelivery.controllers;

import com.example.shopdelivery.entitys.Delivery;
import com.example.shopdelivery.entitys.Order;
import com.example.shopdelivery.entitys.Product;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class DispatcherActivity implements Initializable {
    private static HttpURLConnection con;
    @FXML
    private TextArea editTextD;
    @FXML
    private ListView<String> listViewD1;

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

    ArrayList<Order> orders = new ArrayList<>();
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
                    String idi = s.substring("- Удалить (".length(), "- Удалить (".length() + 2);
                    for (int i = 0; i < orders.size(); i++) {
                        if (idi.equals(orders.get(i).getId())){
                            index = i;
                        }
                    }
                    if (index != -1) listViewD.getItems().remove(index);
                    editTextD.setText("");

                    String r = "";
                    var url = "http://localhost:5050/orderDelete?id=" + idi;
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
                    nameOfDelivery = "D" + (int)(Math.random() * 30 + 20);
                    String[] o = s.substring("+ Обьеденить (".length(), s.length()- 1).split(",");
                    System.out.println(Arrays.toString(o));
                    for (int i = 0; i < o.length; i++) {

                    }
                    Delivery d = new Delivery(nameOfDelivery, new ArrayList<>(List.of(o)));
                    deliveries.add(d);
                    listViewD1.getItems().add(d.toString());
                    editTextD.setText("");
                    String ro = "";

                    var urlo1 = "http://localhost:5050/joinOrdersInDelivery?listIdOfOrders=" + nameOfDelivery;
                    for (int i = 0; i < o.length; i++) {
                        urlo1 += "," + o[i];
                    }
                    System.out.println(urlo1);
                    try {
                        var myurl = new URL(urlo1);
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
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } finally {
                        con.disconnect();
                    }
                    System.out.println(r);
                    break;
                case "=":

                    String[] n = s.substring("= Назначить курьера (".length(), s.length() - 1).split(",");
                    editTextD.setText("");
                    ro = "";
                    var urlo = "http://localhost:5050/setCourier?name=" + n[0] + "&idDelivery=" + n[1];
                    var u = "http://localhost:5050/setStatus?idDelivery=" + n[1] + "&status=Courier";
                    try {
                        var myurl = new URL(urlo);
                        var myu = new URL(u);
                        con = (HttpURLConnection) myurl.openConnection();
                        HttpURLConnection c = (HttpURLConnection) myu.openConnection();
                        con.setRequestMethod("GET");
                        c.setRequestMethod("GET");
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
                        StringBuilder content1;
                        try (BufferedReader in = new BufferedReader(
                                new InputStreamReader(c.getInputStream()))) {
                            String line;
                            content1 = new StringBuilder();
                            while ((line = in.readLine()) != null) {
                                content1.append(line);
                                content1.append(System.lineSeparator());
                            }
                        }
                        ro = content.toString();
                        System.out.println("D");
                        System.out.println(ro.length());
                        if (ro.trim().equals("true")) {
                            editTextD.setText("Назначен курьер");
                        }
                        else {
                            editTextD.setText("Курьер не назначен - ошибка данных");
                        }
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } finally {
                        con.disconnect();
                    }
                    break;
                case "<":

                    String p = s.substring("< Получить статус (".length(), s.length() - 1);
                    editTextD.setText("");
                    ro = "";
                    urlo = "http://localhost:5050/getStatus?idDelivery=" + p;
                    try {
                        var myurl = new URL(urlo);
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
                        ro = content.toString();
                        if (ro.trim().equals("Courier")) {
                            editTextD.setText(ro.trim());
                            editTextD.appendText("\n" + "Курьер назначен");
                        }
                        else if (ro.trim().equals("Collect")) {
                            editTextD.setText(ro.trim());
                            editTextD.appendText("\n" + "Собрано");
                        }
                        else if (ro.trim().equals("Complete")) {
                            editTextD.setText(ro.trim());
                            editTextD.appendText("\n" + "Подтверждено");
                        }
                        else if (ro.trim().equals("Put")) {
                            editTextD.setText(ro.trim());
                            editTextD.appendText("\n" + "Передано курьеру");
                        }
                        else if (ro.trim().equals("delivered")) {
                            editTextD.setText(ro.trim());
                            editTextD.appendText("\n" + "Доставлено");
                        }
                        else {
                            editTextD.setText("Такой не существует");
                        }
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } finally {
                        con.disconnect();
                    }

                    break;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameText.setText(name);
        var url = "http://localhost:5050/orders";
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
            orders = gson.fromJson(s1, new TypeToken<ArrayList<Order>>(){}.getType());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            con.disconnect();
        }
        for (int i = 0; i < orders.size(); i++) {
            listViewD.getItems().add(orders.get(i).toString());
        }
        url = "http://localhost:5050/deliveries";
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
            listViewD1.getItems().add(deliveries.get(i).toString());
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
