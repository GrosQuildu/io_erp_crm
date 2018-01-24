package main.java.erp_crm.frontend.orders;

import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.erp_crm.Main;
import main.java.erp_crm.backend.api.erp.ArticlesControllerApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.erp.Article;
import main.java.erp_crm.backend.model.erp.OrderedArticle;

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
    private ArticlesControllerApi articlesControllerApi = new ArticlesControllerApi();

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
        setColumns();
        articlesControllerApi.refreshArticles();
        setEvents();

        Bindings.bindContent(articleTableView.getItems(), DBData.getArticles());
    }

    private void setColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
    }

    public void setAddEditOrderController(AddEditOrderController addEditOrderController) {
        this.addEditOrderController = addEditOrderController;
    }

    public void show() {
        stage.show();
    }
}