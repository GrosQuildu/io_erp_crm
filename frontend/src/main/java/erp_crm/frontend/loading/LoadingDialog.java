package main.java.erp_crm.frontend.loading;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;

public class LoadingDialog {
    VBox pane = new VBox();
    Scene scene = new Scene(pane);
    Stage stage = new Stage(StageStyle.UNDECORATED);
    Thread thread;
    private String komunikat;
    HashMap<String,Integer> map = new HashMap<>();
    public LoadingDialog(String komunikat){
        this.komunikat=komunikat;
        map.put(komunikat+"...",1);
        map.put(komunikat+"..",2);
        map.put(komunikat+".",3);
        map.put(komunikat,4);
    }
    public void show(){
        Label msg = new Label(komunikat+"...");
        msg.setAlignment(Pos.CENTER);
        msg.setStyle("-fx-text-fill:#ffffff;");
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.getChildren().add(msg);
        pane.setFillWidth(true);
        pane.setPadding(new Insets(20));
        pane.setStyle("-fx-background-color: #284B63;");
        stage.setScene(scene);
        stage.setWidth(320);
        stage.setHeight(100);
        stage.setResizable(false);



        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                while (!isCancelled()) {
                    Platform.runLater(() -> {
                        switch(map.get(msg.getText())){
                            case 1:
                                msg.setText(komunikat);
                                break;
                            case 2:
                                msg.setText(komunikat+"...");
                                break;
                            case 3:
                                msg.setText(komunikat+"..");
                                break;
                            case 4:
                                msg.setText(komunikat+".");
                                break;
                        }
                    });
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {
                    }
                }
                return false;
            }
        };
        thread = new Thread(task);

        thread.start();

        stage.show();
    }

    public void close(){
        stage.close();
        thread.interrupt();
    }
}


/*
LoadingDialog ld = new LoadingDialog("");
Task<Boolean> task = new Task<Boolean>(){
@Override
protected Boolean call() throws Exception {
    return ;
}
};
task.setOnRunning(event -> ld.show());
task.setOnSucceeded(event -> {
ld.close();
stage.close();
});
new Thread(task).start();
* */