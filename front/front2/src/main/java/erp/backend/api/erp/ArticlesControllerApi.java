package main.java.erp.backend.api.erp;

import com.google.gson.Gson;
import main.java.erp.backend.api.ConnectionApi;
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


    public List<Article> getArticles(){
        List<Article> articles = new ArrayList<>();
        articles.addAll(
                Arrays.asList( gson.fromJson(connection.getObjects(ConnectionApi.ObjectType.ARTICLES), Article[].class) )
        );
        return articles;
    }

    public Integer createArticle(Article article){
        System.out.println(article.serialize());
        return connection.createObject(article.serialize(), ConnectionApi.ObjectType.ARTICLES);
    }
}
