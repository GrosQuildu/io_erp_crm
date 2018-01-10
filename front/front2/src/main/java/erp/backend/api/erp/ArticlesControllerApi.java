package main.java.erp.backend.api.erp;

import main.java.erp.backend.api.common.ServerConfiguration;
import main.java.erp.backend.model.erp.Article;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArticlesControllerApi {
    //RestTemplate restTemplate = new RestTemplate();
    ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
    private HttpHeaders httpHeaders = new HttpHeaders();

    OAuth2RestTemplate restTemplate;
    private String baseUrl = ServerConfiguration.address + ":" + ServerConfiguration.port + "/" + ServerConfiguration.baseUrl + "/erp/articles";

    public ArticlesControllerApi(){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("accept", "application/json");
        resourceDetails.setAccessTokenUri("http://javafx-client:R3GMPX2DQHD1234VFG929ABW@localhost:8080/api/oauth/token");
        resourceDetails.setClientId("javafx-client");
        resourceDetails.setClientSecret("R3GMPX2DQHD1234VFG929ABW");
        resourceDetails.setGrantType("password");
        resourceDetails.setUsername("admin@io_erp_crm.com");
        resourceDetails.setPassword("O9HKN2NJZ20T");
        String parameters = "grant_type=password&username=admin@io_erp_crm.com&password=O9HKN2NJZ20T";
        HttpEntity<String> request = new HttpEntity<>(parameters, headers);
        restTemplate = new OAuth2RestTemplate(resourceDetails, new DefaultOAuth2ClientContext());
        HttpEntity<String> token = restTemplate.postForEntity(resourceDetails.getAccessTokenUri(), request, String.class);
    }

    public List<Article> getArticles(){
        List<Article> articles = new ArrayList<>();
        articles.addAll(
                Arrays.asList(restTemplate.getForEntity(baseUrl, Article[].class, Collections.emptyMap()).getBody())
        );
        return articles;
    }
}
