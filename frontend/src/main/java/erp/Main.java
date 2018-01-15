package main.java.erp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import main.java.erp.frontend.login.Login;

import java.lang.reflect.Field;
import java.nio.charset.Charset;


public class Main extends Application {
    Stage primaryStage;
    Login l;
    public static String css = Main.class.getResource("/style.css").toExternalForm();
    @Override
    public void start(Stage primaryStage) throws Exception{
        l  = new Login(primaryStage);
        System.setProperty("file.encoding","UTF-8");
        Field charset = Charset.class.getDeclaredField("defaultCharset");
        charset.setAccessible(true);
        charset.set(null,null);
        this.primaryStage=primaryStage;
        l.show();
    }



    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void stop() throws Exception
    {
        super.stop();
        Platform.exit();
        System.exit(0);
    }
}

