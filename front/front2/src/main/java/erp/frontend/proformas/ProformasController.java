package main.java.erp.frontend.proformas;

import javafx.fxml.FXMLLoader;
import main.java.erp.backend.SharedData;
import main.java.erp.backend.api.erp.ProformasApiController;
import main.java.erp.backend.model.erp.Proforma;
import main.java.erp.frontend.loading.LoadingDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by marcin on 07.09.16.
 */
public class ProformasController implements Initializable{
    @FXML
    private TableView<Proforma> proformasTable;
    @FXML
    private TableColumn<Proforma, String> proformaNumberColumn;
    @FXML
    private TableColumn<Proforma, String> clientColumn;
    @FXML
    private TableColumn<Proforma, Float> netColumn;
    @FXML
    private TableColumn<Proforma, String> paymentMethodColumn;
    @FXML
    private TableColumn<Proforma, String> orderNumberColumn;
    @FXML
    private Button editBtn, pdfBtn,refBtn;
    @FXML
    private TextField findField;
    @FXML
    private Button findBtn, xBtn, deleteBtn;
    private AddEditProformaController addEditProformaController;
    private ObservableList<Proforma> dane = FXCollections.observableArrayList();
    private FXMLLoader loader;
    private ProformasApiController controller = new ProformasApiController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/addEditProforma.fxml"));
            loader.load();
            addEditProformaController = loader.getController();
            addEditProformaController.setProformasController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        proformaNumberColumn.setCellValueFactory(new PropertyValueFactory<>("proformaNumber"));
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
        netColumn.setCellValueFactory(new PropertyValueFactory<>("netPrice"));
        paymentMethodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
        proformasTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        KeyCombination pdfCombination = new KeyCodeCombination(KeyCode.E,KeyCombination.CONTROL_DOWN);
        proformasTable.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if(pdfCombination.match(event)){
                exportToPdf();
            }
        });
        updateTable();
        setEvents();
    }

    private void setEvents() {
        refBtn.setOnAction(e->updateTable());
        editBtn.setOnAction(e -> {
            if(!proformasTable.getSelectionModel().isEmpty()){
                addEditProformaController.show(proformasTable.getSelectionModel().getSelectedItem());
            }
        });
        pdfBtn.setOnAction(e -> exportToPdf());


        findBtn.setOnAction(e -> find(findField.getText().isEmpty() ? "" : findField.getText()));

        xBtn.setOnAction(e -> {

            findField.clear();
        });
        findField.setOnKeyReleased(e -> {
            //wyszukiwanie wg wartości w findField
        });

        proformasTable.setOnMouseClicked(e -> {
            if(e.getClickCount()==2 && !proformasTable.getSelectionModel().isEmpty())
                addEditProformaController.show(proformasTable.getSelectionModel().getSelectedItem());
        });
        deleteBtn.setOnAction(e -> {
            controller.deleteProforma(proformasTable.getSelectionModel().getSelectedItem().getId());
        });
    }

    private void exportToPdf() {
        Proforma item = proformasTable.getSelectionModel().getSelectedItem();
        if(item!=null)
            addEditProformaController.exportToPDF(item, false);
    }




    public void updateTable() {
        /*controller.updateProforma(
                proformasTable.getSelectionModel().getSelectedItem().getId(),
                proformasTable.getSelectionModel().getSelectedItem()
        );*/
    }

    private void find(String s) {
    }
    
}
