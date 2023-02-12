package com.example.shopdelivery.controllers;

import com.example.shopdelivery.entitys.Order;
import com.example.shopdelivery.entitys.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


public class AdminActivity implements Initializable {
    private static HttpURLConnection con;
    @FXML
    private TextArea editText;
    @FXML
    private GridPane grid;

    @FXML
    private Button run;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextArea infoText = new TextArea();
    @FXML
    private Text nameText;
    @FXML
    private VBox layot;
    @FXML
    private ImageView img;
    @FXML
    private ListView<String> listView;

    String name = WelcomActivity.nameAll;

    ArrayList<Product> products = new ArrayList<>();
    String[] list;
//    private ArrayList<Product> getData() {
//        ArrayList<Product> productList = new ArrayList<>();
//        String str = new String(Files.readAllBytes(Paths.get(fileNameProducts)));
//        productList = gson.fromJson(s1, new TypeToken<ArrayList<Product>>(){}.getType());
//        return productList;
//    }
    @FXML
    void onRun(MouseEvent event) {
        String str = editText.getText();
        Scanner sc = new Scanner(str);
        while (sc.hasNextLine()){
            String s = sc.nextLine();
            System.out.println(s.substring(0,1));
            switch (s.substring(0,1)){
                case "-":
                    int index = -1;
                    String idi = s.substring("- Удалить (".length(), "- Удалить (".length() + 2);
                    for (int i = 0; i < products.size(); i++) {
                        if (idi.equals(products.get(i).getId())){
                            index = i;
                        }
                    }
                    if (index != -1) listView.getItems().remove(index);
                    editText.setText("");

                    String r = "";
                    var url = "http://localhost:5050/productDelete?id=" + idi;
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
                    System.out.println(r);
                    break;

                case "+":
                    String[] o = s.substring("+ Добавить(".length(), s.length()- 1).split(",");
                    Product p1 = new Product(o[0].strip(), o[1].strip(), o[2].strip(), Double.valueOf(o[3].strip()));
                    products.add(p1);
                    listView.getItems().add(p1.toString());
                    System.out.println(products.size());
                    editText.setText("");
                    String ro = "";
                    System.out.println(products.get(products.size()-1).toString());
                    var urlo = "http://localhost:5050/addProduct?listArgs="+
                                                        products.get(products.size()-1).getId() + "," +
                                                        products.get(products.size()-1).getTitle() + "," +
                                                        products.get(products.size()-1).getLink() + "," +
                                                        String.valueOf(products.get(products.size()-1).getPrice());
                    System.out.println(urlo);
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
                    editText.setText("");
                    editText.setText("Редактирование завершенно");
                    break;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        layot = new VBox();
        nameText.setText(name);
        var url = "http://localhost:5050/products";
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
            products = gson.fromJson(s1, new TypeToken<ArrayList<Product>>(){}.getType());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            con.disconnect();
        }
        for (int i = 0; i < products.size(); i++) {
            listView.getItems().add(products.get(i).toString());
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

//File file = new File("src/Box13.jpg");
//        Image image = new Image(file.toURI().toString());
//        imageView.setImage(image);
