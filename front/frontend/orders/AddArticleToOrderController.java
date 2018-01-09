package main.java.erp.frontend.orders;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.erp.Main;
import main.java.erp.backend.model.erp.Article;
import main.java.erp.backend.model.erp.OrderedArticle;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class AddArticleToOrderController implements Initializable {
    public TextField amountField;
    private Stage stage = new Stage();
    public VBox mainBox;
    public TableView<Article> articleTableView;
    public TableColumn nameColumn;
    public TableColumn unitPriceColumn;
    public TableColumn weightColumn;
    public Button addBtn;
    public Button cancelBtn;
    private AddEditOrderController addEditOrderController;

    private void setEvents() {
        addBtn.setOnAction(e -> {
            Article item = articleTableView.getSelectionModel().getSelectedItem();
            if(item!=null){
                OrderedArticle orderedArticle = new OrderedArticle();
                orderedArticle.setArticle(item);
                orderedArticle.setAmount(Integer.parseInt(amountField.getText()));
                orderedArticle.setWeight(item.getWeight() * orderedArticle.getAmount());
                orderedArticle.setNetPrice(new BigDecimal(orderedArticle.getAmount() * item.getUnitPrice().floatValue()));
                addEditOrderController.addArticle(orderedArticle);

            }
            stage.close();
        });
        cancelBtn.setOnAction(e -> stage.close());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Scene scene = new Scene(mainBox);
        scene.getStylesheets().add(Main.css);
        stage.setScene(scene);
        setEvents();
    }

    public void setAddEditOrderController(AddEditOrderController addEditOrderController) {
        this.addEditOrderController = addEditOrderController;
    }

    public void show() {
        stage.show();
    }
}
