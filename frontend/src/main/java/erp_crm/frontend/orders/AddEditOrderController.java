package main.java.erp_crm.frontend.orders;

import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.erp_crm.Main;
import main.java.erp_crm.backend.SharedData;
import main.java.erp_crm.backend.api.erp.OrdersApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.common.Client;
import main.java.erp_crm.backend.model.erp.DeliveryCost;
import main.java.erp_crm.backend.model.erp.Order;
import main.java.erp_crm.backend.model.erp.OrderedArticle;
import org.joda.time.LocalDate;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;

public class AddEditOrderController implements Initializable {
    public DatePicker realizationDeadlinePicker;
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

    private Stage stage = new Stage();
    private OrdersController ordersController;
    private AddArticleToOrderController addArticleToOrderController;
    private AddClientToOrderController addClientToOrderController;
    private OrdersApi controller = new OrdersApi();


    private Order order;
    private Client client;



    private void loadControllers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFiles/erp/addArticleToOrder.fxml"));
            loader.load();
            addArticleToOrderController = loader.getController();
            addArticleToOrderController.setAddEditOrderController(this);


            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/erp/addClientToOrder.fxml"));
            loader.load();
            addClientToOrderController = loader.getController();
            addClientToOrderController.setAddEditOrderController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String calculateDeliveryCost() {
        float weight = 0f;
        for(OrderedArticle i : articleTableView.getItems()){
            weight+=i.getWeight();
        }
        for(DeliveryCost cost : DBData.getDeliveryCosts()){
            if(cost.getWeightFrom() < weight && cost.getWeightTo() >= weight){
                return cost.getPrice().toString();
            }
        }
        return 0f+"";
    }

    public void setOrdersController(OrdersController ordersController) {
        this.ordersController = ordersController;
    }

    private void setEvents() {
        chooseClientBtn.setOnAction(e -> addClientToOrderController.show());
        saveBtn.setOnAction(e -> {
            save();
            close();
        });
        cancelBtn.setOnAction(e -> close());
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

    private void save() {
        if(order!=null){
            fillOrder();
            controller.updateOrder(order.getId(), order);
            ordersController.refresh();
        } else {
            order = new Order();
            fillOrder();
            controller.createOrder(order);
        }
        ordersController.refresh();
    }

    private void close() {
        this.order = null;
        this.client = null;
        stage.close();
    }

    private void fillOrder() {
        order.setAdvance(new BigDecimal(advanceField.getText()));
        order.setClient(client);
        order.setComments(commentsArea.getText());
        order.setConditions(conditionsArea.getText());
        order.setDeliveryAddress(deliveryAddressArea.getText());
        order.setDeliveryCost(new BigDecimal(deliveryCostField.getText()));
        order.setEmployee(DBData.getLoggedUser());
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
    }

    public void show(){
        initializeFields();
        orderNumberText.setText(OrderUtils.generateOrderNumber());
        stage.show();
    }
    public void show(Order order){
        initializeFields();
        this.order = order;
        this.client = order.getClient();
        fillFields();
        stage.show();
    }

    private void fillFields() {
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
        vatField.setText(order.getVat().toString());
        stateField.setText(order.getState());
        articleTableView.getItems().setAll(order.getOrderedArticles());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage.setMaximized(true);
        loadControllers();
        setEvents();
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add(Main.css);
        stage.setWidth(300);
        stage.setScene(scene);
        initializeFields();

        articleTableView.getItems().addListener((ListChangeListener<OrderedArticle>) change -> {

            while (change.next()) {
                deliveryCostField.setText(calculateDeliveryCost());
            }

        });
    }

    private void initializeFields() {
        this.order = null;
        this.client = null;
        articleTableView.getItems().clear();

        advanceField.setText("");
        clientField.setText("");
        deliveryAddressArea.setText("");
        deliveryCostField.setText("");
        employeeText.setText("");
        orderNumberText.setText("");
        vatField.setText("");
        stateField.setText("");


        isDone.setSelected(false);
        isPaid.setSelected(false);
        isSignedBox.setSelected(false);

        orderDatePicker.setValue(java.time.LocalDate.now());
        realizationDatePicker.setValue(java.time.LocalDate.now());
        realizationDeadlinePicker.setValue(java.time.LocalDate.now());

        conditionsArea.setText(SharedData.getConfig().getOrdersConditions() != null ? SharedData.getConfig().getOrdersConditions() : "");
        commentsArea.setText(SharedData.getConfig().getOrdersComments() != null ? SharedData.getConfig().getOrdersComments() : "");

    }

    public void addArticle(OrderedArticle orderedArticle) {
        articleTableView.getItems().add(orderedArticle);
    }



    public void addClient(Client item) {
        client = item;
        clientField.setText(item.toString());
    }
}
