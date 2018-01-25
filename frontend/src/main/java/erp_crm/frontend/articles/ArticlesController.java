package main.java.erp_crm.frontend.articles;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.erp_crm.backend.api.erp.ArticlesApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.common.Unit;
import main.java.erp_crm.backend.model.erp.Article;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ArticlesController implements Initializable{
    public Button addArticleBtn;
    public Button editArticleBtn;
    public Button deleteArticleBtn;
    public TableView<Article> articleTable;
    public TableColumn<Article, String> nameColumn;
    public TableColumn<Article, Float> availabilityColumn;
    public TableColumn<Article, Unit> unitColumn;
    public TableColumn<Article, Float> unitPriceColumn;
    public TableColumn<Article, Float> weightColumn;


    private ArticlesApi controller = new ArticlesApi();
    private AddEditArticleController addEditArticleController;



    private void loadControllers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFiles/erp/addEditArticle.fxml"));
            loader.load();
            addEditArticleController = loader.getController();
            addEditArticleController.setArticlesController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadControllers();
        setColumns();
        refresh();
        setEvents();
        Bindings.bindContent(articleTable.getItems(), DBData.getArticles());
    }

    private void setColumns() {
        articleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));

        unitColumn.setCellFactory(column -> new TableCell<Article, Unit>() {
            @Override
            protected void updateItem(Unit item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.getName());
                }
            }
        });

    }

    private void setEvents() {
        deleteArticleBtn.setOnAction(e -> deleteArticle());
        addArticleBtn.setOnAction(e -> addArticle());
        editArticleBtn.setOnAction(e -> editArticle());
    }

    private void deleteArticle() {
        Article toDelete = articleTable.getSelectionModel().getSelectedItem();
        if(toDelete != null) {
            controller.deleteArticle(toDelete);
            refresh();
        }
    }

    private void addArticle() {
        addEditArticleController.show();
    }

    private void editArticle() {
        Article selected = articleTable.getSelectionModel().getSelectedItem();
        if(selected != null){
            addEditArticleController.show(selected);
        }
    }

    void refresh() {
        controller.refreshArticles();
        controller.refreshUnits();
    }
}
