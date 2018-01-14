package main.java.erp.backend.api.erp;

import com.google.gson.Gson;
import main.java.erp.backend.api.ConnectionApi;
import main.java.erp.backend.model.DBData;
import main.java.erp.backend.model.common.Unit;
import main.java.erp.backend.model.erp.Article;
import main.java.erp.frontend.login.Login;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArticlesControllerApi {
    private ConnectionApi connection = Login.connection;
    private Gson gson = new Gson();


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
