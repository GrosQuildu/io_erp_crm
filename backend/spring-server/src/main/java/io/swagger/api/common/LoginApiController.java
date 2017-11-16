package io.swagger.api.common;

import io.swagger.model.common.Token;
import io.swagger.model.User;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

@Controller
public class LoginApiController implements LoginApi {



    public ResponseEntity<Token> login(@ApiParam(value = ""  )  @Valid @RequestBody User user) {
        // do some magic!
        return new ResponseEntity<Token>(HttpStatus.OK);
    }

}
