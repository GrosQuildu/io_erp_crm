package io.swagger.api.erp;

import io.swagger.ModelHelper;
import io.swagger.model.erp.Article;

import io.swagger.annotations.*;

import io.swagger.model.erp.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

@Controller
public class ArticlesApiController implements ArticlesApi {

    @Autowired
    ArticleRepository articleRepository;

    public ResponseEntity<Integer> createArticle(@ApiParam(value = "Article to create"  )  @Valid @RequestBody Article article) {
        article = articleRepository.save(article);
        return new ResponseEntity<Integer>(article.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteArticle(@ApiParam(value = "",required=true ) @PathVariable("articleId") Integer articleId) {
        articleRepository.delete(articleId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Article> getArticle(@ApiParam(value = "",required=true ) @PathVariable("articleId") Integer articleId) {
        Article article = articleRepository.findById(articleId);
        return new ResponseEntity<Article>(article, HttpStatus.OK);
    }

    public ResponseEntity<List<Article>> getArticles() {
        List<Article> articles = (List<Article>) articleRepository.findAll();
        return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateArticle(@ApiParam(value = "",required=true ) @PathVariable("articleId") Integer articleId,
        @ApiParam(value = "Article to create"  )  @Valid @RequestBody Article article) {
        if(article.getId() == 0)
            throw new Error("Not found");

        Article articleOld = articleRepository.findById(articleId);
        try {
            ModelHelper.combine(articleOld, article);
        } catch (Exception e) {
            throw new Error("Wrong object");
        }

        if(articleId == article.getId()) {
            article = articleRepository.save(article);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            throw new Error("Wrong id");
        }
    }

}
