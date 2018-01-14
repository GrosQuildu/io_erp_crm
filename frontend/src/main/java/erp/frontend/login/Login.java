package main.java.erp.frontend.login;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import main.java.erp.Main;
import main.java.erp.backend.SharedData;
import main.java.erp.backend.api.ConnectionApi;
import main.java.erp.frontend.mainView.MainController;
import main.java.erp.frontend.settings.SettingsController;

import java.io.*;
import java.util.Optional;
import java.util.Scanner;

public class Login {
    private Button loginBtn, setBtn;
    private TextField loginField;
    private PasswordField passField;
    private VBox mainBox;
    private Label loginLabel, passLabel, statusLabel;
    private Scene s;
    private Stage st;
    private Stage primaryStage;
    Boolean res=false;
    public static ConnectionApi connection;
    public Login(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    public Boolean show(){
        String[] data = readLoginData();
        mainBox = new VBox();
        loginLabel = new Label("Login");
        passLabel = new Label("Password");
        statusLabel = new Label("");
        mainBox.setSpacing(20);
        mainBox.setPadding(new Insets(10,10,10,10));
        loginBtn = new Button("Log in!");
        setBtn = new Button("Settings");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        setBtn.setMaxWidth(Double.MAX_VALUE);

        setBtn.setOnAction(e -> {
            VBox setBox = new VBox();
            setBox.setSpacing(20);
            setBox.setPadding(new Insets(10,10,10,10));
            Label adresSerweraLabel = new Label("Server address");
            TextField adresSerweraField = new TextField();

            Button zatwierdzBtn = new Button("Confirm");


            Button anulujBtn = new Button("Abort");
            zatwierdzBtn.setMaxWidth(Double.MAX_VALUE);
            anulujBtn.setMaxWidth(Double.MAX_VALUE);
            HBox btnBox = new HBox();
            btnBox.setMaxWidth(Double.MAX_VALUE);
            btnBox.setSpacing(10);
            btnBox.getChildren().addAll(zatwierdzBtn,anulujBtn);
            setBox.getChildren().addAll(adresSerweraLabel,adresSerweraField,btnBox);
            Stage stage = new Stage(StageStyle.DECORATED);
            Scene scene = new Scene(setBox);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);


            zatwierdzBtn.setOnAction(ev -> {
                stage.close();
                //zapis
                adresSerweraField.setText("Zapisany tekst");
            });
            anulujBtn.setOnAction(ev -> stage.close());
            stage.show();
        });

        mainBox.setFillWidth(true);
        loginField = new TextField(data[0]);
        passField = new PasswordField();
        passField.setText(data[1]);
        loginLabel.setStyle("-fx-text-fill: #ffffff");
        passLabel.setStyle("-fx-text-fill: #ffffff");
        statusLabel.setStyle("-fx-text-fill: #ffffff");
        mainBox.setStyle("-fx-background-color: #284B63;");

        s=new Scene(mainBox);
        st=new Stage(StageStyle.DECORATED);
        s.getStylesheets().add(Main.css);
        st.setScene(s);
        st.setTitle("ERP");
        st.setWidth(250);
        mainBox.getChildren().addAll(loginLabel, loginField, passLabel, passField, loginBtn, statusLabel);
        loginField.setOnKeyReleased(ev -> {
            if(ev.getCode() == KeyCode.ENTER) startMain();
        });
        passField.setOnKeyReleased(ev -> {
            if(ev.getCode()==KeyCode.ENTER) startMain();
        });
        st.show();
        loginBtn.setOnAction(ev -> startMain());
        return res;
    }

    private void startMain() {
        connection = new ConnectionApi(loginField.getText(), passField.getText());
        if(!connection.getToken().equals("")) {
            saveLoginData(loginField.getText(), passField.getText());
            SharedData.setMail(loginField.getText());
            SharedData.setHaslo(passField.getText());
            res = true;
            close();
            BorderPane root;

            try {
                root = FXMLLoader.load(getClass().getResource("/fxmlFiles/mainView.fxml"));
                VBox clientsBox = FXMLLoader.load(getClass().getResource("/fxmlFiles/clients.fxml"));
                TabPane settingsBox = FXMLLoader.load(getClass().getResource("/fxmlFiles/settings.fxml"));
                VBox deliveryBox = FXMLLoader.load(getClass().getResource("/fxmlFiles/deliveryCosts.fxml"));
                VBox articlesBox = FXMLLoader.load(getClass().getResource("/fxmlFiles/articles.fxml"));
                VBox ordersBox = FXMLLoader.load(getClass().getResource("/fxmlFiles/orders.fxml"));
                VBox proformasBox = FXMLLoader.load(getClass().getResource("/fxmlFiles/proformas.fxml"));
                MainController.settingsBox = settingsBox;
                MainController.clientsBox = clientsBox;
                MainController.articlesBox = articlesBox;
                MainController.deliveryBox = deliveryBox;
                MainController.ordersBox = ordersBox;
                MainController.proformasBox = proformasBox;
                primaryStage.setTitle("ERP");
                root.setRight(null);
                Scene scene = new Scene(root, 1000, 600);
                scene.getStylesheets().add(Main.css);
                root.setCenter(clientsBox);


                primaryStage.setOnCloseRequest(this::askForClose);

                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            statusLabel.setText("Couldn't connect.");
        }

    }

    private void saveLoginData(String login, String haslo) {
        try {
            PrintWriter writer = new PrintWriter(SettingsController.getUserDataDirectory()+"ld.txt", "UTF-8");
            writer.println(login);
            writer.println(haslo);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String[] readLoginData() {
        File file = new File(SettingsController.getUserDataDirectory()+"ld.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Scanner sc;
        try {
            sc = new Scanner(file, "UTF-8");
            sc.useDelimiter("$^");
            String s="";
            if(sc.hasNext())
                s = sc.next();
            sc.close();
            return s.split("\n").length>=2 ? s.split("\n") : new String[]{"", ""};
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new String[]{"", ""};
    }



    private void askForClose(WindowEvent event) {
        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
        al.setTitle("Confirmation of closing app");
        al.setHeaderText(null);
        al.setContentText("Are you sure you want to close app?");
        al.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        ButtonType yesBtn = new ButtonType("Yes");
        ButtonType noBtn = new ButtonType("No");
        al.getButtonTypes().clear();
        al.getButtonTypes().addAll(yesBtn,noBtn);
        Optional<ButtonType> res = al.showAndWait();
        if(res.isPresent()){
            if(res.get().equals(yesBtn)){
                Platform.exit();
                System.exit(0);
            } else {
                event.consume();
            }
        }
    }


    public void close() {
        st.close();
    }
    
}
