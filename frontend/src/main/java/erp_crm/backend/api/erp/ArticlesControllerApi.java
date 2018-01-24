package main.java.erp_crm.backend.api.erp;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.erp_crm.backend.api.ConnectionApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.erp.Article;
import main.java.erp_crm.frontend.login.Login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArticlesControllerApi {
    private ConnectionApi connection = Login.connection;
    private Gson gson = Converters.registerDateTime(new GsonBuilder()).create();


    public void refreshArticles(){
        List<Article> articles = new ArrayList<>();
        articles.addAll(
                Arrays.asList( gson.fromJson(connection.getObjects(ConnectionApi.ObjectType.ARTICLES), Article[].class) )
        );
        DBData.setArticles(articles);
    }

    public Integer createArticle(Article article){
        return connection.createObject(article.serialize(), ConnectionApi.ObjectType.ARTICLES);
    }


    public void deleteArticle(Article item) {
        connection.deleteObject(item.getId(), ConnectionApi.ObjectType.ARTICLES);
    }

    public void refreshUnits(){
        new UnitControllerApi().getUnits();
    }

    public void updateArticle(Integer id, Article article) {
        connection.updateObject(id, article.serialize(), ConnectionApi.ObjectType.ARTICLES);
    }
}
