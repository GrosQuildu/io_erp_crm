package main.java.erp.frontend.articles;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.erp.backend.api.ConnectionApi;
import main.java.erp.backend.api.erp.ArticlesControllerApi;
import main.java.erp.backend.model.erp.Article;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ArticlesController implements Initializable{
    public TableColumn weightColumn;
    @FXML
    private Button addArticleBtn;
    @FXML
    private Button editArticleBtn;
    @FXML
    private Button deleteArticleBtn;
    @FXML
    private TableView<Article> articleTable;
    @FXML
    private TableColumn<Article, String> nameColumn;
    @FXML
    private TableColumn<Article, Float> availabilityColumn;
    @FXML
    private TableColumn<Article, String> unitColumn;
    @FXML
    private TableColumn<Article, Float> unitPriceColumn;
    private ArticlesControllerApi controller = new ArticlesControllerApi();
    private AddEditArticleController addEditArticleController;
    private FXMLLoader loader;


    public ArticlesController(){

        try {
            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/addEditArticle.fxml"));
            loader.load();
            addEditArticleController = loader.getController();
            addEditArticleController.setArticlesController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        articleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        refresh();
        setEvents();
    }

    private void setEvents() {
        deleteArticleBtn.setOnAction(e -> {
            Article toDelete = articleTable.getSelectionModel().getSelectedItem();
            if(toDelete != null) {
               //controller.deleteArticle(toDelete.getId());
                refresh();
            }
        });
        addArticleBtn.setOnAction(e -> addEditArticleController.show());
        editArticleBtn.setOnAction(e -> {
            Article selected = articleTable.getSelectionModel().getSelectedItem();
            if(selected != null){
                addEditArticleController.show(selected);
            }
        });
    }

    void refresh() {
       articleTable.getItems().setAll(controller.getArticles());
    }
}
