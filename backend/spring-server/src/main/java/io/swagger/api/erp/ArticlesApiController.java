package io.swagger.api.erp;

import io.swagger.ModelHelper;
import io.swagger.api.ApiException;
import io.swagger.model.common.Unit;
import io.swagger.model.common.UnitRepository;
import io.swagger.model.erp.Article;

import io.swagger.annotations.*;

import io.swagger.model.erp.ArticleRepository;
import io.swagger.model.erp.OrderedArticle;
import io.swagger.model.erp.OrderedArticleRepository;
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

    /** Dependent:
        * orderedArticles
     * Depends on:
        * unit
     */
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UnitRepository unitRepository;
    @Autowired
    OrderedArticleRepository orderedArticleRepository;

    private Article getArticleHelper(Integer id) {
        Article article = articleRepository.findById(id);
        if(article == null)
            throw new Error("Article not found");
        return article;
    }

    public ResponseEntity<Integer> createArticle(@ApiParam(value = "Article to create"  )  @Valid @RequestBody Article article) {
        Unit unit = unitRepository.findById(article.getUnit().getId());
        if(unit == null)
            throw new Error("Unit not found");
        article.setUnit(unit);

        article = articleRepository.save(article);
        return new ResponseEntity<Integer>(article.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteArticle(@ApiParam(value = "",required=true ) @PathVariable("articleId") Integer articleId) {
        getArticleHelper(articleId);

        Integer orderedArticlesAssigned = orderedArticleRepository.findAllByArticleId(articleId).size();
        if(orderedArticlesAssigned != 0)
            throw new Error(orderedArticlesAssigned + " ordered articles are assigned to this article");

        articleRepository.delete(articleId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Article> getArticle(@ApiParam(value = "",required=true ) @PathVariable("articleId") Integer articleId) {
        getArticleHelper(articleId);
        Article article = articleRepository.findById(articleId);
        return new ResponseEntity<Article>(article, HttpStatus.OK);
    }

    public ResponseEntity<List<Article>> getArticles() {
        List<Article> articles = (List<Article>) articleRepository.findAll();
        return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateArticle(@ApiParam(value = "",required=true ) @PathVariable("articleId") Integer articleId,
        @ApiParam(value = "Article to create"  )  @Valid @RequestBody Article article) {
        if(articleId != article.getId())
            throw new Error("Wrong article id");

        Article articleOld = getArticleHelper(articleId);
        try {
            ModelHelper.combine(articleOld, article);
        } catch (Exception e) {
            throw new Error("Wrong article object");
        }

        Unit unit = unitRepository.findById(article.getUnit().getId());
        if(unit == null)
            throw new Error("Unit not found");
        article.setUnit(unit);

        article = articleRepository.save(article);
        return new ResponseEntity<Void>(HttpStatus.OK);

    }

}
