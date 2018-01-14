package main.java.erp.frontend.orders;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.erp.Main;
import main.java.erp.backend.api.erp.OrderControllerApi;
import main.java.erp.backend.model.common.Client;
import main.java.erp.backend.model.common.Employee;
import main.java.erp.backend.model.erp.Order;
import main.java.erp.backend.model.erp.OrderedArticle;
import main.java.erp.frontend.settings.SettingsController;
import org.joda.time.LocalDate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;

public class AddEditOrderController implements Initializable {
    public DatePicker realizationDeadlinePicker;
    private Stage stage = new Stage();
    public Button saveBtn;
    public Button cancelBtn;
    public Button addArticleBtn;
    public Button deleteArticleBtn;
    public TableView<OrderedArticle> articleTableView;
    public TableColumn articleColumn;
    public TableColumn amountColumn;
    public TableColumn netPriceColumn;
    public TableColumn weightColumn;
    public TextField deliveryCostField;
    public GridPane mainPane;
    public TextField stateField;
    public CheckBox isSignedBox;
    public CheckBox isPaid;
    public CheckBox isDone;
    public TextArea commentsArea;
    public TextArea conditionsArea;
    public TextArea deliveryAddressArea;
    public Text orderNumberText;
    public DatePicker orderDatePicker;
    public DatePicker realizationDatePicker;
    public Text employeeText;
    public TextField clientField;
    public Button chooseClientBtn;
    public Button clearClientBtn;
    public TextField advanceField;
    public TextField vatField;

    private OrdersController ordersController;
    private AddArticleToOrderController addArticleToOrderController;
    private AddClientToOrderController addClientToOrderController;
    private Order order;
    private Client client;
    private OrderControllerApi controller = new OrderControllerApi();
    //private OrderedArticlesApiController controllerOrderedArticle = new OrderedArticlesApiController();

    public AddEditOrderController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFiles/addArticleToOrder.fxml"));
            loader.load();
            addArticleToOrderController = loader.getController();
            addArticleToOrderController.setAddEditOrderController(this);


            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/addClientToOrder.fxml"));
            loader.load();
            addClientToOrderController = loader.getController();
            addClientToOrderController.setAddEditOrderController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOrdersController(OrdersController ordersController) {
        this.ordersController = ordersController;
    }

    private void setEvents() {
        chooseClientBtn.setOnAction(e -> addClientToOrderController.show());
        saveBtn.setOnAction(e -> {
            if(order!=null){
                fillOrder();
                controller.updateOrder(order.getId(), order);
                ordersController.refresh();
            } else {
                order = new Order();
                fillOrder();
                controller.createOrder(order);
                //controller.createClient(client);
            }
            ordersController.refresh();
            stage.close();
        });
        cancelBtn.setOnAction(e -> stage.close());
        addArticleBtn.setOnAction(e -> addArticleToOrderController.show());
        amountColumn.setCellFactory(column -> new TableCell<OrderedArticle, Integer>() {
            private TextField textField;

            @Override
            public void startEdit() {
                super.startEdit();
                if (textField == null) {
                    prepareTextField();
                }
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }

            @Override
            public void commitEdit(Integer item) {
                super.commitEdit(item);

                OrderedArticle object = (OrderedArticle) this.getTableRow().getItem();
                object.setAmount(item);
                //controllerOrderedArticle.updateOrderedArticle(object);

            }

            private void prepareTextField() {
                textField = new TextField();
                textField.setOnKeyPressed(e -> {
                    e.consume();
                    if (e.getCode().equals(ENTER)) {
                        commitEdit(Integer.parseInt(textField.getText()));
                    } else if (e.getCode() == ESCAPE) {
                        cancelEdit();
                    } else {
                        if (textField.getText().length()>0) {
                            textField.clear();
                        }
                        OrderedArticle object = (OrderedArticle) this.getTableRow().getItem();
                        textField.setText(object.getAmount().toString());
                    }
                });
                textField.setOnKeyReleased(Event::consume);
                textField.setOnKeyTyped(Event::consume);
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setText(getItem().toString());
                setGraphic(null);
            }

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText("");
                    setGraphic(null);
                } else {
                    if (isEditing()) {
                        if (textField != null) {
                            textField.setText(item.toString());
                        }
                        setText(null);
                        setGraphic(textField);
                    } else {
                        setGraphic(null);
                        setText(item.toString());
                    }
                }
            }
        });
    }

    private void fillOrder() {
        order.setAdvance(new BigDecimal(advanceField.getText()));
        order.setClient(client);
        order.setComments(commentsArea.getText());
        order.setConditions(conditionsArea.getText());
        order.setDeliveryAddress(deliveryAddressArea.getText());
        order.setDeliveryCost(new BigDecimal(deliveryCostField.getText()));
        order.setEmployee(new Employee());
        order.setIsDone(isDone.isSelected());
        order.setIsPaid(isPaid.isSelected()+"");
        order.setIsSigned(isSignedBox.isSelected());
        order.setOrderDate(LocalDate.fromDateFields(Date.valueOf(orderDatePicker.getValue())));
        order.setOrderNumber(orderNumberText.getText());
        order.setRealizationDate(LocalDate.fromDateFields(Date.valueOf(realizationDatePicker.getValue())));
        order.setRealizationDeadline(realizationDeadlinePicker.getValue().toString());
        order.setState(stateField.getText());
        order.setVat(0.23f);
        order.getOrderedArticles().clear();
        order.getOrderedArticles().addAll(articleTableView.getItems());
        for(OrderedArticle a : order.getOrderedArticles()){
            a.setOrder(order);
        }
    }

    public void show(){
        this.order = null;
        this.client = null;
        stage.setMaximized(true);
        orderNumberText.setText(OrderUtils.generateOrderNumber());
        stage.show();
    }
    public void show(Order order){
        fillFields(order);
        show();
    }

    private void fillFields(Order order) {
       this.order = order;
        advanceField.setText(order.getAdvance().toString());
        clientField.setText(order.getClient().getName());
        commentsArea.setText(order.getComments());
        conditionsArea.setText(order.getConditions());
        deliveryAddressArea.setText(order.getDeliveryAddress());
        deliveryCostField.setText(order.getDeliveryCost().toString());
        employeeText.setText(order.getEmployee().getName());
        isDone.setSelected(order.getIsDone());
        isPaid.setSelected(order.getIsPaid().toLowerCase().equals("true"));
        isSignedBox.setSelected(order.getIsSigned());
        orderNumberText.setText(order.getOrderNumber());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEvents();
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add(Main.css);
        stage.setWidth(300);
        stage.setScene(scene);
        orderDatePicker.setValue(java.time.LocalDate.now());
        realizationDatePicker.setValue(java.time.LocalDate.now());
        realizationDeadlinePicker.setValue(java.time.LocalDate.now());
    }

    public void addArticle(OrderedArticle orderedArticle) {

        articleTableView.getItems().add(orderedArticle);
    }


    private String getDefaultText(OrderUtils.DefaultText pole) {
        File file=null;
        if(pole== OrderUtils.DefaultText.CONDITIONS)
            file = new File(SettingsController.getUserDataDirectory()+"warunki.txt");
        else if(pole== OrderUtils.DefaultText.COMMENTS)
            file = new File(SettingsController.getUserDataDirectory()+"uwagi.txt");
        else if(pole== OrderUtils.DefaultText.MAIL_CONTENT)
            file = new File(SettingsController.getUserDataDirectory()+"tresc.txt");
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

    public void addClient(Client item) {
        client = item;
        clientField.setText(item.toString());
    }
}
