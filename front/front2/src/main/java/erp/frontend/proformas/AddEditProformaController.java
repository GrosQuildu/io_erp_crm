package main.java.erp.frontend.proformas;

import com.lowagie.text.DocumentException;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.erp.Main;
import main.java.erp.backend.SharedData;
import main.java.erp.backend.api.erp.ProformasApiController;
import main.java.erp.backend.model.erp.Order;
import main.java.erp.backend.model.erp.Proforma;
import main.java.erp.backend.pdf.PdfGenerator;
import main.java.erp.frontend.loading.LoadingDialog;
import main.java.erp.frontend.orders.OrderUtils;
import main.java.erp.frontend.orders.OrdersController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddEditProformaController implements Initializable {
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

    public AddEditProformaController(){
        setEvents();
    }

    public void show(Order order){
        this.order = order;
        stage.show();
    }
    public void show(Proforma proforma){
        this.proforma = proforma;
        issueDatePicker.setValue(LocalDate.of(proforma.getIssueDate().getYear(), proforma.getIssueDate().getMonthOfYear(), proforma.getIssueDate().getDayOfMonth()));
        saleDatePicker.setValue(LocalDate.of(proforma.getSaleDate().getYear(), proforma.getSaleDate().getMonthOfYear(), proforma.getSaleDate().getDayOfMonth()));
        paymentDatePicker.setValue(LocalDate.of(proforma.getPaymentDate().getYear(), proforma.getPaymentDate().getMonthOfYear(), proforma.getPaymentDate().getDayOfMonth()));
        paymentMethodBox.setValue(proforma.getPaymentMethod());
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
            Order tmp = new Order(); //pobranie z bazy wg id
            p.setSaleDate(new org.joda.time.LocalDate(saleDatePicker.getValue()));
            p.setIssueDate(new org.joda.time.LocalDate(issueDatePicker.getValue()));
            p.setPaymentMethod(paymentMethodBox.getValue());
            p.setPaymentDate(new org.joda.time.LocalDate(paymentDatePicker.getValue()));
            p.setOrder(tmp);
            if(proforma == null) {
                p.setProformaNumber(OrderUtils.generateProformaNumber(tmp.getOrderNumber()));
            } else {
                p.setProformaNumber(proforma.getProformaNumber());
            }
            //zapis proformy


            stage.close();
            exportToPDF(p, true);
        }
    }

    public void exportToPDF(Proforma p, boolean ask) {
        Optional<ButtonType> res= Optional.empty();
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
            String numerZamowienia=order.getOrderNumber();
            fc.setInitialFileName(
                    "Proforma for "+
                            order.getClient().getName().replace("&"," and ")+
                            " no "+p.getProformaNumber().replace("/","-")+".pdf"
            );
            fc.setInitialDirectory(new File(SharedData.getProformyPath().isEmpty()?".": SharedData.getProformyPath()));
            Stage fStage = new Stage(StageStyle.DECORATED);
            fStage.setMaximized(false);
            File file = fc.showSaveDialog(fStage);

            LoadingDialog ld = new LoadingDialog("Exporting to pdf");
            Task<Boolean> eksportTask = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    if (file != null)
                        try {
                            return PdfGenerator.proforma(numerZamowienia, file.toPath().toString(), true);
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
                    ordersController.updateTable();
                if(proformasController!=null)
                    proformasController.updateTable();
            });
            new Thread(eksportTask).start();

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
