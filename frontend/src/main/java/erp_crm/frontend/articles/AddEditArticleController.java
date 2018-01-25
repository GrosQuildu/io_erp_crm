package main.java.erp_crm.frontend.articles;

import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.erp_crm.Main;
import main.java.erp_crm.backend.api.erp.ArticlesApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.common.Unit;
import main.java.erp_crm.backend.model.erp.Article;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class AddEditArticleController implements Initializable {
    private Stage stage = new Stage();
    public VBox mainBox;
    public TextField nameField;
    public TextField availabilityField;
    public ComboBox<Unit> unitComboBox;
    public TextField unitPriceField;
    public Button saveBtn;
    public Button cancelBtn;
    public TextField weightField;


    private Article article;
    private ArticlesApi controller = new ArticlesApi();
    private ArticlesController articlesController;



    private void setEvents() {
        saveBtn.setOnAction(e -> {
            if(article!=null){
                fillArticle();
                controller.updateArticle(article.getId(), article);

            } else {
                article = new Article();
                fillArticle();
                controller.createArticle(article);
            }
            articlesController.refresh();
            stage.close();
        });
        cancelBtn.setOnAction(e -> stage.close());
    }

    private void fillArticle() {
        article.setAvailability(Integer.parseInt(availabilityField.getText()));
        article.setUnit(unitComboBox.getValue());
        article.setName(nameField.getText());
        article.setUnitPrice(new BigDecimal(unitPriceField.getText()));
        article.setWeight(new BigDecimal(weightField.getText()).floatValue());
    }

    public void show(){
        this.article = null;
        initializeFields();
        if(unitComboBox.getItems().size()>0) unitComboBox.getSelectionModel().select(0);
        stage.show();
    }

    private void initializeFields() {
        availabilityField.setText("0.00");
        if(unitComboBox.getItems().size()>0) {
            unitComboBox.getSelectionModel().select(0);
        }
        nameField.setText("");
        unitPriceField.setText("0.00");
        weightField.setText("0");
    }

    public void show(Article article){
        show();
        fillFields(article);
    }

    private void fillFields(Article article) {
        this.article = article;
        nameField.setText(article.getName());
        availabilityField.setText(article.getAvailability().toString());
        unitComboBox.setValue(article.getUnit());
        unitPriceField.setText(article.getUnitPrice().toString());
        weightField.setText(article.getWeight().toString());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEvents();
        Scene scene = new Scene(mainBox);
        scene.getStylesheets().add(Main.css);
        stage.setWidth(300);
        stage.setScene(scene);
        Bindings.bindContent(unitComboBox.getItems(), DBData.getUnits());
        controller.refreshUnits();
        initializeFields();
    }

    public void setArticlesController(ArticlesController articlesController) {
        this.articlesController = articlesController;
    }

}
