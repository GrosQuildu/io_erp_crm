/**
 * NOTE: This class is auto generated by the swagger code generator program (2.2.3).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api.erp;

import io.swagger.model.erp.Article;
import io.swagger.model.Error;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

@Api(value = "articles", description = "the articles API")
public interface ArticlesApi {

    @ApiOperation(value = "Create new article", notes = "", response = Integer.class, authorizations = {
        @Authorization(value = "Authorization")
    }, tags={ "ERP - articles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "", response = Integer.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/erp/articles",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Integer> createArticle(@ApiParam(value = "Article to create"  )  @RequestBody Article article);


    @ApiOperation(value = "Delete article", notes = "", response = Void.class, authorizations = {
        @Authorization(value = "Authorization")
    }, tags={ "ERP - articles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Deleted", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/erp/articles/{articleId}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteArticle(@ApiParam(value = "",required=true ) @PathVariable("articleId") Integer articleId);


    @ApiOperation(value = "Returns Article", notes = "", response = Article.class, authorizations = {
        @Authorization(value = "Authorization")
    }, tags={ "ERP - articles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "", response = Article.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/erp/articles/{articleId}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<Article> getArticle(@ApiParam(value = "",required=true ) @PathVariable("articleId") Integer articleId);


    @ApiOperation(value = "Returns list of Articles", notes = "", response = Article.class, responseContainer = "List", authorizations = {
        @Authorization(value = "Authorization")
    }, tags={ "ERP - articles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "", response = Article.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/erp/articles",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<Article>> getArticles();


    @ApiOperation(value = "Update existing article", notes = "", response = Void.class, authorizations = {
        @Authorization(value = "Authorization")
    }, tags={ "ERP - articles", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Updated", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/erp/articles/{articleId}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> updateArticle(@ApiParam(value = "",required=true ) @PathVariable("articleId") Integer articleId,@ApiParam(value = "Article to create"  )  @RequestBody Article article);

}
