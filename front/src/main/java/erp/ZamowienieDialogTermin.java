/*
package sample;


import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ZamowienieDialogTermin {
    private Label terminLabel = new Label("Termin realizacji");
    private ComboBox<Integer> terminyCombo = new ComboBox<>();
    private Label terminyContent = new Label("");
    private CheckBox innyTerminBox = new CheckBox("Inny termin");
    private TextArea innyTerminArea = new TextArea();
    private Label zaliczkaLabel = new Label("Zaliczka");
    private RadioButton zeroBtn = new RadioButton("0%");
    private RadioButton polBtn = new RadioButton("50%");
    private RadioButton caloscBtn = new RadioButton("100%");
    private RadioButton zlBtn = new RadioButton("zł");
    private RadioButton procBtn = new RadioButton("%");
    private CheckBox innaWartoscCheck = new CheckBox("Inna wartość");
    private TextField innaWartoscField = new TextField();
    private Label vatLabel = new Label("VAT");
    private TextField vatField = new TextField("23.0");
    private Label uwagiLabel = new Label("Uwagi");
    private TextArea uwagiArea = new TextArea();
    private Label warunkiLabel = new Label("Warunki");
    private TextArea warunkiArea = new TextArea();
    private VBox left = new VBox();
    private VBox right = new VBox();
    private HBox mainBox = new HBox();
    private ZamowienieDialog parent;
    private Boolean edit = false;
    private Label wartoscZaliczki = new Label("Wartość zaliczki: ");
    private Order order;
    private enum DefaultText {WARUNKI,UWAGI,TRESC}

    public ZamowienieDialogTermin(ZamowienieDialog parent){
        this.parent = parent;
        mainBox.setPadding(new Insets(10));
        mainBox.setSpacing(10);
        left.setSpacing(20);
        right.setSpacing(20);
        left.setPadding(new Insets(10));
        right.setPadding(new Insets(10));
        HBox terminBox = new HBox();
        terminBox.setSpacing(10);
        terminBox.getChildren().addAll(terminyCombo, terminyContent, innyTerminBox);
        HBox radioBox = new HBox();
        radioBox.setSpacing(10);
        radioBox.getChildren().addAll(zeroBtn, polBtn, caloscBtn);
        ToggleGroup procToggle = new ToggleGroup();
        zeroBtn.setToggleGroup(procToggle);
        polBtn.setToggleGroup(procToggle);
        caloscBtn.setToggleGroup(procToggle);
        ToggleGroup innyToggle = new ToggleGroup();
        zlBtn.setToggleGroup(innyToggle);
        zlBtn.setSelected(true);
        procBtn.setToggleGroup(innyToggle);
        HBox innaWartoscBox = new HBox();
        innaWartoscBox.setSpacing(10);
        innaWartoscBox.getChildren().addAll(innaWartoscField, zlBtn, procBtn);
        zeroBtn.setSelected(true);
        left.getChildren().addAll(terminLabel, terminBox,
                innyTerminArea, zaliczkaLabel,
                radioBox, innaWartoscCheck, innaWartoscBox, vatLabel, vatField, wartoscZaliczki
                );
        right.getChildren().addAll(uwagiLabel, uwagiArea, warunkiLabel, warunkiArea);
        mainBox.getChildren().addAll(left, right);
        terminyCombo.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14);
        terminyCombo.setValue(1);
        terminyContent.setText("tydzień");
        terminyCombo.setOnAction(ev -> setTerminyContent());
        innyTerminArea.setEditable(false);
        innyTerminBox.setOnAction(e -> {
            innyTerminArea.setEditable(innyTerminBox.isSelected());
            if(!innyTerminBox.isSelected()) innyTerminArea.setText("");
            updateParent();
        });
        innaWartoscField.setEditable(false);
        innaWartoscCheck.setOnAction(e -> {
            innaWartoscField.setEditable(innaWartoscCheck.isSelected());
            if(!innaWartoscCheck.isSelected()) innaWartoscField.setText("");
            updateParent();
        });
        if(!edit){
            uwagiArea.setText(getDefaultText(DefaultText.UWAGI));
            warunkiArea.setText(getDefaultText(DefaultText.WARUNKI));
        }
        zlBtn.setOnAction(e -> updateParent());
        procBtn.setOnAction(e -> updateParent());
        zeroBtn.setOnAction(e -> updateParent());
        polBtn.setOnAction(e -> updateParent());
        caloscBtn.setOnAction(e -> updateParent());
        vatField.setOnAction(e -> updateParent());
        innaWartoscField.setOnKeyReleased(e -> updateParent());
        uwagiArea.setOnKeyReleased(e -> updateParent());
        warunkiArea.setOnKeyReleased(e -> updateParent());
    }
    public ZamowienieDialogTermin(ZamowienieDialog parent, Order order){
        this(parent);
        this.order = order;
        edit=true;

        if(order.getZaliczkaProcent().length()==1){
            innaWartoscField.setText(order.getAdvance()+"");
            innaWartoscCheck.setSelected(true);
            zlBtn.setSelected(true);
            innaWartoscField.setEditable(true);
        } else if (order.getZaliczkaProcent().contains("50%")){
            polBtn.setSelected(true);
        } else if (order.getZaliczkaProcent().contains("100%")){
            caloscBtn.setSelected(true);
        } else if (order.getZaliczkaProcent().contains("%")) {
            innaWartoscField.setText(order.getZaliczkaProcent().replace("%",""));
            innaWartoscCheck.setSelected(true);
            procBtn.setSelected(true);
            innaWartoscField.setEditable(true);
        }
        wartoscZaliczki.setText("Wartość zaliczki: "+Utils.formatPln(order.getAdvance()));
        warunkiArea.setText(order.getWarunkiPlatnosci());
        uwagiArea.setText(order.getComments());
        if(order.getTerminRealizacji().matches("^[0-9]{1,2}\\styg\\w+$")) {
            terminyCombo.setValue(Integer.parseInt(order.getTerminRealizacji().substring(0, order.getTerminRealizacji().indexOf(" t"))));
            switch (terminyCombo.getValue()) {
                case 1:
                    terminyContent.setText("tydzień");
                    break;
                case 2:
                    terminyContent.setText("tygodnie");
                    break;
                case 3:
                    terminyContent.setText("tygodnie");
                    break;
                case 4:
                    terminyContent.setText("tygodnie");
                    break;
                case 5:
                    terminyContent.setText("tygodni");
                    break;
                case 6:
                    terminyContent.setText("tygodni");
                    break;
                case 7:
                    terminyContent.setText("tygodni");
                    break;
                case 8:
                    terminyContent.setText("tygodni");
                    break;
                case 9:
                    terminyContent.setText("tygodni");
                    break;
                case 10:
                    terminyContent.setText("tygodni");
                    break;
                case 11:
                    terminyContent.setText("tygodni");
                    break;
            }
            innyTerminBox.setSelected(false);
        } else {
            innyTerminBox.setSelected(true);
            innyTerminArea.setText(order.getTerminRealizacji());
            innyTerminBox.setDisable(false);
        }
        vatField.setText(order.getVat().toString());
    }
    private void updateParent(){
        String termin = "";
        if(!innyTerminBox.isSelected()){
            termin = terminyCombo.getValue() + " " + terminyContent.getText();
        } else {
            termin = innyTerminArea.getText();
        }
        String zaliczkaProcent = "";
        Float zaliczka = 0f;
        Boolean isProcent = false;

        if(innaWartoscCheck.isSelected()){
            if(zlBtn.isSelected()) {
                zaliczka = Float.parseFloat(innaWartoscField.getText().replace(",", "."));
                zaliczkaProcent = "0";
            }
            else if(procBtn.isSelected()) {
                zaliczkaProcent = innaWartoscField.getText() + "%";
                isProcent=true;
            }
        } else {
            if(zeroBtn.isSelected()){
                zaliczkaProcent="0%";
                zaliczka=0f;
                isProcent=false;
            } else if(polBtn.isSelected()){
                zaliczkaProcent="50%";
                isProcent=true;
            } else if(caloscBtn.isSelected()){
                zaliczkaProcent="100%";
                isProcent=true;
            }
        }
        parent.updateTermin(warunkiArea.getText(), uwagiArea.getText(), termin,
                isProcent,zaliczkaProcent, zaliczka, Float.parseFloat(vatField.getText().replace(",",".")));
        wartoscZaliczki.setText("Wartość zaliczki: "+Utils.formatPln(parent.getOrder().getAdvance()));
    }
    private String getDefaultText(DefaultText pole) {
        File file=null;
        if(pole== DefaultText.WARUNKI)
            file = new File(UstawieniaController.getUserDataDirectory()+"warunki.txt");
        else if(pole== DefaultText.UWAGI)
            file = new File(UstawieniaController.getUserDataDirectory()+"uwagi.txt");
        else if(pole== DefaultText.TRESC)
            file = new File(UstawieniaController.getUserDataDirectory()+"tresc.txt");
        Scanner sc;
        try {
            sc = new Scanner(file, "UTF-8");
            sc.useDelimiter("$^");
            String s="";
            if(sc.hasNext())
                s = sc.next();
            sc.close();
            return s;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
    private void setTerminyContent() {
        switch(terminyCombo.getValue()){
            case 1:
                terminyContent.setText("tydzień");
                break;
            case 2:
                terminyContent.setText("tygodnie");
                break;
            case 3:
                terminyContent.setText("tygodnie");
                break;
            case 4:
                terminyContent.setText("tygodnie");
                break;
            case 5:
                terminyContent.setText("tygodni");
                break;
            case 6:
                terminyContent.setText("tygodni");
                break;
            case 7:
                terminyContent.setText("tygodni");
                break;
            case 8:
                terminyContent.setText("tygodni");
                break;
            case 9:
                terminyContent.setText("tygodni");
                break;
            case 10:
                terminyContent.setText("tygodni");
                break;
            case 11:
                terminyContent.setText("tygodni");
                break;
            case 12:
                terminyContent.setText("tygodni");
                break;
            case 13:
                terminyContent.setText("tygodni");
                break;
            case 14:
                terminyContent.setText("tygodni");
                break;
        }
        updateParent();
    }

    public HBox getMainBox(){
        return this.mainBox;
    }
}
*/
