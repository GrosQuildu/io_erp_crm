package main.java.erp.frontend.settings;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import main.java.erp.backend.SharedData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.erp.backend.api.common.ClientTypesApi;
import main.java.erp.backend.api.erp.UnitControllerApi;
import main.java.erp.backend.model.DBData;
import main.java.erp.backend.model.common.ClientType;
import main.java.erp.backend.model.common.Unit;
import main.java.erp.frontend.clients.AddClientTypeController;
import main.java.erp.frontend.units.AddUnitController;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SettingsController implements Initializable {
    public TableView<ClientType> clientTypesTableView;
    public Button addClientTypeBtn;
    public Button deleteClientTypeBtn;
    public Button addUnitBtn;
    public Button deleteUnitBtn;
    public TableView<Unit> unitsTableView;
    @FXML
    private TextField senderField;
    @FXML
    private TextField ccField;
    @FXML
    private TextField protocolPathField;
    @FXML
    private Button saveSettingsBtn;
    @FXML
    private TextField mailAddressField;
    @FXML
    private TextField serverField;
    @FXML
    private TextField portField;
    @FXML
    private TextField mailPasswordField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField mailField;
    @FXML
    private TextField telephoneField;
    @FXML
    private PasswordField passField;
    @FXML
    private Button saveProfileBtn;
    @FXML
    private TextArea ordersArea, commentsArea, mailContentArea;
    @FXML
    private Button saveDefaultBtn;
    @FXML
    private TextField proformaPathField, ordersPathField;
    @FXML
    private TextField scanField;

    private ClientTypesApi clientTypesApi = new ClientTypesApi();
    private UnitControllerApi unitControllerApi = new UnitControllerApi();

    private AddClientTypeController addClientTypeController;

    private AddUnitController addUnitController;
    public void refresh() {
        unitControllerApi.getUnits();
        clientTypesApi.getClientTypes();
    }


    private enum DefaultText {WARUNKI,UWAGI,TRESC,ADRES_PROFORMY, ADRES_ZAMOWIENIA, SKAN_PODPISU, ADRES_PROTOKOLU, DW, NADAWCA}
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ordersArea.setText(getDefaultText(DefaultText.WARUNKI));
        commentsArea.setText(getDefaultText(DefaultText.UWAGI));
        mailContentArea.setText(getDefaultText(DefaultText.TRESC));
        String paths = getDefaultText(DefaultText.ADRES_ZAMOWIENIA);
        ordersPathField.setText(paths);
        SharedData.setZamowieniaPath(paths);
        paths = getDefaultText(DefaultText.ADRES_PROFORMY);
        proformaPathField.setText(paths);
        SharedData.setProformyPath(paths);
        paths = getDefaultText(DefaultText.SKAN_PODPISU);
        SharedData.setSkanPath(paths);
        scanField.setText(paths);
        paths = getDefaultText(DefaultText.ADRES_PROTOKOLU);
        SharedData.setProtokolyPath(paths);
        protocolPathField.setText(paths);
        paths = getDefaultText(DefaultText.NADAWCA);
        paths = paths.trim().isEmpty()? SharedData.getImie()+" "+ SharedData.getNazwisko()+" | Firma":paths;
        SharedData.setNadawca(paths);
        senderField.setText(paths);

        firstNameField.setText(SharedData.getImie());
        nameField.setText(SharedData.getNazwisko());
        mailField.setText(SharedData.getMail());
        telephoneField.setText(SharedData.getTelefon());
        passField.setText(SharedData.getHaslo());
        paths = getDefaultText(DefaultText.DW);
        ccField.setText(paths);
        SharedData.setDw(paths);
        setEvents();
        refresh();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFiles/addUnit.fxml"));
            loader.load();
            addUnitController = loader.getController();
            addUnitController.setSettingsController(this);

            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/addClientType.fxml"));
            loader.load();
            addClientTypeController = loader.getController();
            addClientTypeController.setSettingsController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bindings.bindContent(unitsTableView.getItems(), DBData.getUnits());
        Bindings.bindContent(clientTypesTableView.getItems(), DBData.getClientTypes());
    }

    private void setEvents() {
        deleteClientTypeBtn.setOnAction(e -> {
            ClientType item = clientTypesTableView.getSelectionModel().getSelectedItem();
            if(item!=null) {
                clientTypesApi.deleteClientType(item);
                refresh();
            }
        });
        deleteUnitBtn.setOnAction(e ->{
            Unit item = unitsTableView.getSelectionModel().getSelectedItem();
            if(item!=null) {
                unitControllerApi.deleteUnit(item);
                refresh();
            }
        });
        addClientTypeBtn.setOnAction(e -> addClientTypeController.show());
        addUnitBtn.setOnAction(e -> addUnitController.show());
        saveSettingsBtn.setOnAction(e -> {
            //zapis ustawień
        });
        saveProfileBtn.setOnAction(e -> {
            //Zapis profilu pracownika
        });
        proformaPathField.setOnMouseClicked(event -> {
            //popup do ustawienia domyślnej ścieżki dla proform
        });
        ordersPathField.setOnMouseClicked(event -> {
            //popup do ustawienia domyślnej ścieżki dla zamówień
        });

        protocolPathField.setOnMouseClicked(event -> {
            //popup do ustawienia domyślnej ścieżki dla protokołów odbioru
        });
        scanField.setOnMouseClicked(e -> {
            //popup do ustawienia ścieżki dla skanu podpisu
        });
        saveDefaultBtn.setOnAction(e->{
            //zapis domyślnych ustawień
        });
    }

    //zgarnięcie ścieżki katalogu ustawień
    public static String getUserDataDirectory() {
        String path = System.getProperty("user.home") + File.separator + ".ERP-MB" + File.separator;
        File tmp = new File(path);
        if(!tmp.exists()) tmp.mkdir();
        return path;
    }
    //Przykład zapisu ustawień - robione na plikach :D
    private String saveDefaultPath(DefaultText text) {
        PrintWriter pw = null;
        if(text == DefaultText.SKAN_PODPISU){
            FileChooser fc = new FileChooser();
            fc.setTitle("Wybór pliku ze skanem podpisu");
            FileChooser.ExtensionFilter filtr = new FileChooser.ExtensionFilter("Obraz", "*jpg", "*.jpeg", "*.png","*.gif");
            fc.getExtensionFilters().add(filtr);
            File file = fc.showOpenDialog(new Stage());
            try {
                pw = new PrintWriter(getUserDataDirectory()+"skan.txt", "UTF-8");
                pw.print(file.toPath().toString());
                pw.close();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return file.toPath().toString();
        }

        DirectoryChooser dc = new DirectoryChooser();
        File file = dc.showDialog(new Stage());
        try {
            if(text == DefaultText.ADRES_PROFORMY) {
                dc.setTitle("Wybór folderu dla proformy");
                pw = new PrintWriter(getUserDataDirectory()+"proformy.txt", "UTF-8");
            }
            else if(text == DefaultText.ADRES_ZAMOWIENIA) {
                dc.setTitle("Wybór folderu dla zamówień");
                pw = new PrintWriter(getUserDataDirectory()+"zamowienia.txt", "UTF-8");
            } else if(text == DefaultText.ADRES_PROTOKOLU){
                dc.setTitle("Wybór folderu dla protokołów");
                pw = new PrintWriter(getUserDataDirectory()+"protokoly.txt", "UTF-8");
            }
            assert pw != null;
            pw.print(file.toPath().toString());
            pw.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return file.toPath().toString();
    }

    private void zapiszDomyslne(String warunki, String uwagi, String tresc, String proformyPath, String zamowieniaPath, String protokolyPath) {
        PrintWriter writer;
        try {
            if(warunki!=null) {
                writer = new PrintWriter(getUserDataDirectory()+"warunki.txt", "UTF-8");
                writer.print(warunki);
                writer.close();
            }
            if(uwagi!=null) {
                writer = new PrintWriter(getUserDataDirectory()+"uwagi.txt", "UTF-8");
                writer.print(uwagi);
                writer.close();
            }
            if(tresc!=null){
                writer = new PrintWriter(getUserDataDirectory()+"tresc.txt", "UTF-8");
                writer.print(tresc);
                writer.close();
            }
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Alert al = new Alert(Alert.AlertType.INFORMATION);
        al.setHeaderText(null);
        al.setTitle("Ustawienia");
        al.setContentText("Ustawienia zostały zapisane.");
        al.getDialogPane().getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        al.show();
    }


    private String getDefaultText(DefaultText pole) {
        File file=null;
        if(pole==DefaultText.WARUNKI)
            file = new File(getUserDataDirectory()+"warunki.txt");
        else if(pole==DefaultText.UWAGI)
            file = new File(getUserDataDirectory()+"uwagi.txt");
        else if(pole==DefaultText.TRESC)
            file = new File(getUserDataDirectory()+"tresc.txt");
        else if(pole==DefaultText.ADRES_PROFORMY)
            file = new File(getUserDataDirectory()+"proformy.txt");
        else if(pole==DefaultText.ADRES_ZAMOWIENIA)
            file = new File(getUserDataDirectory()+"zamowienia.txt");
        else if(pole==DefaultText.SKAN_PODPISU)
            file = new File(getUserDataDirectory()+"skan.txt");
        else if(pole==DefaultText.ADRES_PROTOKOLU){
            file = new File(getUserDataDirectory()+"protokoly.txt");
        } else if (pole == DefaultText.DW){
            file = new File(getUserDataDirectory()+"dw.txt");
        } else if (pole == DefaultText.NADAWCA){
            file = new File(getUserDataDirectory()+"nadawca.txt");
        }
        assert file != null;
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
            return s;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
