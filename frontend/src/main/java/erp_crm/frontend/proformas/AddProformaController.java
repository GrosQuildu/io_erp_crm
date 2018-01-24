package main.java.erp_crm.frontend.proformas;

import com.lowagie.text.DocumentException;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.erp_crm.Main;
import main.java.erp_crm.backend.SharedData;
import main.java.erp_crm.backend.api.erp.ProformasControllerApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.erp.Order;
import main.java.erp_crm.backend.model.erp.Proforma;
import main.java.erp_crm.backend.pdf.PdfGenerator;
import main.java.erp_crm.frontend.loading.LoadingDialog;
import main.java.erp_crm.frontend.orders.OrderUtils;
import main.java.erp_crm.frontend.orders.OrdersController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProformaController implements Initializable {
    private Stage stage = new Stage();
    public VBox mainBox;
    public DatePicker issueDatePicker;
    public DatePicker saleDatePicker;
    public DatePicker paymentDatePicker;
    public ComboBox<String> paymentMethodBox;
    public Button applyBtn;
    public Button closeBtn;
    private Order order = null;
    private Proforma proforma = null;
    private OrdersController ordersController;
    private ProformasController proformasController;
    private ProformasControllerApi proformasControllerApi = new ProformasControllerApi();

    public void show(Order order){
        this.order = order;
        stage.show();
    }


    public void setOrdersController(OrdersController ordersController) {
        this.ordersController = ordersController;
    }

    public void setProformasController(ProformasController proformasController) {
        this.proformasController = proformasController;
    }

    private void setEvents() {
        closeBtn.setOnAction(e -> {
            order = null;
            stage.close();
        });
        applyBtn.setOnAction(e -> addProforma());
    }

    private void addProforma() {
        if(order != null){
            Proforma p = new Proforma();
            org.joda.time.LocalDate saleDate = org.joda.time.LocalDate.fromDateFields(Date.valueOf(saleDatePicker.getValue()));
            org.joda.time.LocalDate issueDate = org.joda.time.LocalDate.fromDateFields(Date.valueOf(issueDatePicker.getValue()));
            org.joda.time.LocalDate paymentDate = org.joda.time.LocalDate.fromDateFields(Date.valueOf(paymentDatePicker.getValue()));
            p.setSaleDate(saleDate);
            p.setIssueDate(issueDate);
            p.setPaymentMethod(paymentMethodBox.getValue());
            p.setPaymentDate(paymentDate);
            p.setOrder(order);
            if(proforma == null) {
                p.setProformaNumber(OrderUtils.generateProformaNumber());
            } else {
                p.setProformaNumber(proforma.getProformaNumber());
            }
            proformasControllerApi.createProforma(p);

            stage.close();
            exportToPDF(p, true);
        }
    }

    void exportToPDF(Proforma p, boolean ask) {
        Optional<ButtonType> res= Optional.empty();
        order = p.getOrder();
        if(ask){
            Alert al = new Alert(Alert.AlertType.CONFIRMATION);
            al.setTitle("Export to PDF file");
            al.setHeaderText(null);
            al.setContentText("Do you want to export "+p.getProformaNumber()+" to PDF file?");
            al.getDialogPane().getStylesheets().add(Main.css);
            res = al.showAndWait();
        }
        if(!ask || res.isPresent() && res.get()==ButtonType.OK){
            FileChooser fc = new FileChooser();
            fc.setTitle("Proforma export");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
            fc.setInitialFileName(
                    "Proforma for "+
                            order.getClient().getName().replace("&"," and ")+
                            " no "+p.getProformaNumber().replace("/","-")+".pdf"
            );
            fc.setInitialDirectory(new File(SharedData.getConfig().getDefaultProformaPdfPath().isEmpty()?".": SharedData.getConfig().getDefaultProformaPdfPath()));
            Stage fStage = new Stage(StageStyle.DECORATED);
            fStage.setMaximized(false);
            File file = fc.showSaveDialog(fStage);

            LoadingDialog ld = new LoadingDialog("Exporting to pdf");
            Task<Boolean> eksportTask = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    if (file != null)
                        try {
                            return PdfGenerator.proforma(order, file.toPath().toString(), true);
                        } catch (IOException | DocumentException e1) {
                            e1.printStackTrace();
                        }
                    return true;
                }
            };
            eksportTask.setOnRunning(eve -> ld.show());
            eksportTask.setOnSucceeded(eve -> {
                ld.close();
                if(ordersController!=null)
                    ordersController.refresh();
                if(proformasController!=null)
                    proformasController.updateTable();
            });
            new Thread(eksportTask).start();

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Scene scene = new Scene(mainBox);
        scene.getStylesheets().add(Main.css);
        stage.setWidth(300);
        stage.setScene(scene);
        paymentMethodBox.getItems().addAll(Arrays.asList("cash", "card", "transfer"));
        paymentMethodBox.getSelectionModel().select(0);
        issueDatePicker.setValue(LocalDate.now());
        paymentDatePicker.setValue(LocalDate.now());
        saleDatePicker.setValue(LocalDate.now());
        setEvents();
    }
}
