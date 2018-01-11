package main.java.erp.frontend.articles;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.erp.Main;
import main.java.erp.backend.api.ConnectionApi;
import main.java.erp.backend.api.erp.ArticlesApiController;
import main.java.erp.backend.api.erp.ArticlesControllerApi;
import main.java.erp.backend.model.common.Unit;
import main.java.erp.backend.model.erp.Article;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class AddEditArticleController implements Initializable {
    public VBox mainBox;
    public TextField nameField;
    private Stage stage = new Stage();
    private Scene scene;
    public TextField availabilityField;
    public ComboBox<Unit> unitComboBox;
    public TextField unitPriceField;
    public Button saveBtn;
    public Button cancelBtn;
    public TextField weightField;
    private Article article;
    private ArticlesControllerApi controller = new ArticlesControllerApi();
    private ArticlesController articlesController;



    private void setEvents() {
        saveBtn.setOnAction(e -> {
            if(article!=null){
                fillArticle();
                //controller.updateArticle(article.getId(), article);

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
        Unit unit = new Unit();
        unit.setId(1);
        unit.setName("Sztuk");
        unit.setNameShort("szt.");
        article.setUnit(unit);
        article.setUnitPrice(new BigDecimal(unitPriceField.getText()));
        article.setWeight(new BigDecimal(weightField.getText()).floatValue());
    }

    public void show(){
        this.article = null;
        stage.show();
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
        scene = new Scene(mainBox);
        scene.getStylesheets().add(Main.css);
        stage.setWidth(300);
        stage.setScene(scene);
    }

    public void setArticlesController(ArticlesController articlesController) {
        this.articlesController = articlesController;
    }

}
