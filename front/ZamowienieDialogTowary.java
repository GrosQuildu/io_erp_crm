/*
package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ZamowienieDialogTowary {
    private HBox mainBox = new HBox();
    private VBox left = new VBox();
    private VBox right = new VBox();
    private TableView<OrderedArticle> towaryTable = new TableView<>();
    private TableColumn<OrderedArticle, Integer> numerColumn = new TableColumn<>("Lp.");
    private TableColumn<OrderedArticle, String> opisColumn = new TableColumn<>("Opis");
    private TableColumn<OrderedArticle, Float> iloscColumn = new TableColumn<>("Ilość");
    private TableColumn<OrderedArticle, Float> cenaJednostkowaColumn = new TableColumn<>("Cena jedn.");
    private TableColumn<OrderedArticle, Float> nettoColumn = new TableColumn<>("Netto");
    private TableColumn<OrderedArticle, Float> wagaColumn = new TableColumn<>("Waga");
    private ZamowienieDialog parent;
    private TabPane dodajEdytujTowarPane = new TabPane();
    private Tab dodajTab = new Tab("Dodaj towar");
    private Tab edytujTab = new Tab("Edytuj towar");
    private DodajEdytujTowar dodajTowarBox = new DodajEdytujTowar();
    private DodajEdytujTowar edytujTowarBox = new DodajEdytujTowar();
    private Button usunBtn = new Button("Usuń");
    private ObservableList<OrderedArticle> dane = FXCollections.observableArrayList();
    private Label warning = new Label("");
    Order order = new Order();
    public ZamowienieDialogTowary(ZamowienieDialog parent, Order order){
        this.parent = parent;
        dodajTab.setClosable(false);
        edytujTab.setClosable(false);
        dodajTab.setContent(dodajTowarBox);
        edytujTab.setContent(edytujTowarBox);
        dodajEdytujTowarPane.getSelectionModel().select(dodajTab);
        dodajEdytujTowarPane.getTabs().addAll(dodajTab, edytujTab);
        numerColumn.setCellValueFactory(new PropertyValueFactory<>("lp"));
        numerColumn.setMinWidth(30);
        numerColumn.setMaxWidth(30);
        opisColumn.setCellValueFactory(new PropertyValueFactory<>("opis"));
        opisColumn.setMinWidth(100);
        iloscColumn.setCellValueFactory(new PropertyValueFactory<>("ilosc"));
        cenaJednostkowaColumn.setCellValueFactory(new PropertyValueFactory<>("cenaJednostkowa"));
        nettoColumn.setCellValueFactory(new PropertyValueFactory<>("netto"));
        wagaColumn.setCellValueFactory(new PropertyValueFactory<>("waga"));
        towaryTable.getColumns().addAll(numerColumn,opisColumn,iloscColumn,cenaJednostkowaColumn,wagaColumn,nettoColumn);
        towaryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        left.getChildren().addAll(towaryTable, warning, usunBtn);
        right.getChildren().addAll(dodajEdytujTowarPane);
        left.setSpacing(10);
        right.setSpacing(10);
        left.setPadding(new Insets(10));
        right.setPadding(new Insets(10));
        HBox.setHgrow(left, Priority.ALWAYS);
        HBox.setHgrow(right, Priority.ALWAYS);
        mainBox.getChildren().addAll(left, right);
        usunBtn.setOnAction(e -> usun());
        if(order != null){
            //pobranie towarów z bazy
            towaryTable.getItems().setAll(dane);
        }
    }

    private void usun() {
        OrderedArticle toRemove = towaryTable.getSelectionModel().getSelectedItem();
        if(toRemove!=null){
            towaryTable.getItems().remove(toRemove);
        }
        updateParent();
    }
    private void updateParent(){
        String towary;
        StringBuilder sb = new StringBuilder();
        Float netto = 0f;
        Float waga = 0f;
        for(OrderedArticle i : towaryTable.getItems()){
            sb.append(i.getId()).append(",");
            netto+=i.getNetPrice();
            waga+=i.getWeight();
        }
        towary=sb.toString();
        towary=towary.substring(0,towary.lastIndexOf(','));
        parent.updateTowary(towary, netto, waga);
    }
    public HBox getMainBox(){
        return this.mainBox;
    }
    class DodajEdytujTowar extends VBox {
        private RadioButton zListyBtn = new RadioButton("Wybór z listy");
        private RadioButton reczneBtn = new RadioButton("Wpisywanie ręczne");
        private ToggleGroup zListyReczneGroup = new ToggleGroup();
        private TextField opisField = new TextField("");
        private TextField nettoField = new TextField("");
        private TextField wagaField = new TextField("");
        private TextField cenaJednostkowaField = new TextField("");
        private TextField iloscField = new TextField();
        private Label opisLabel = new Label("Opis produktu");
        private Label nettoLabel = new Label("Wartość netto");
        private Label wagaLabel = new Label("Waga");
        private Label iloscLabel = new Label("Ilość");
        private Label cenaJednostkowaLabel = new Label("Cena jednostkowa");
        private ComboBox<String> lista = new ComboBox<>();
        private HBox topBox = new HBox();
        private Button zapiszBtn = new Button("Zapisz");
        private Boolean edit=false;
        public DodajEdytujTowar(){
            super();
            this.setSpacing(10);
            this.setPadding(new Insets(10));
            topBox.setSpacing(10);
            VBox radioBox = new VBox();
            radioBox.setSpacing(10);
            zListyBtn.setToggleGroup(zListyReczneGroup);
            zListyBtn.setSelected(true);
            reczneBtn.setToggleGroup(zListyReczneGroup);

            radioBox.getChildren().addAll(zListyBtn, reczneBtn);
            HBox.setHgrow(lista, Priority.ALWAYS);
            topBox.getChildren().addAll(radioBox, lista);
            this.getChildren().addAll(topBox,
                    opisLabel, opisField,
                    iloscLabel, iloscField,
                    cenaJednostkowaLabel, cenaJednostkowaField,
                    wagaLabel, wagaField,
                    nettoLabel, nettoField,
                    zapiszBtn);
            setEvents();
        }

        private void setEvents() {
            lista.setOnAction(evv -> setFields());
            zapiszBtn.setOnAction(e -> dodajEdytuj());
            iloscField.setOnKeyReleased(e -> {
                Utils.blockLetters(e, iloscField);
                setFieldsFromIlosc();
            });
            wagaField.setOnKeyReleased(e -> {
                Utils.blockLetters(e, wagaField);
            });
            nettoField.setOnKeyReleased(e -> {
                Utils.blockLetters(e, nettoField);
            });
            cenaJednostkowaField.setOnKeyReleased(e -> {
                Utils.blockLetters(e, cenaJednostkowaField);
                setFieldsFromCenaJednostkowa();
            });
            reczneBtn.setOnAction(e -> {
                if(reczneBtn.isSelected()){
                    opisField.setVisible(true);
                    wagaField.setVisible(true);
                    cenaJednostkowaField.setVisible(true);
                    iloscField.setVisible(true);
                    nettoField.setVisible(true);
                }
            });
        }

        private void setFieldsFromCenaJednostkowa() {
            if(!iloscField.getText().isEmpty() && !cenaJednostkowaField.getText().isEmpty()){
                Float net = Float.parseFloat(iloscField.getText().replace(",","."))*Float.parseFloat(cenaJednostkowaField.getText().replace(",","."));
                nettoField.setText(net.toString());
            }

            if(lista.getValue().contains("Dostawa")){
                nettoField.setText(cenaJednostkowaField.getText());
            }
        }
        private void setFieldsFromIlosc() {
            if(iloscField.getText()!=null && !iloscField.getText().isEmpty()) {
                if (lista.getSelectionModel().getSelectedItem().contains("Okleina")) {
                    Float w = Float.parseFloat(iloscField.getText().replace(",", ".")) / 2;
                    w = Math.round(w * 100f) / 100f;
                    wagaField.setText(w.toString());

                } else if (lista.getSelectionModel().getSelectedItem().contains("kg") && !wagaField.getText().isEmpty()) {
                    Float w = Float.parseFloat(iloscField.getText().replace(",", "."));
                    Float waga = lista.getSelectionModel().getSelectedItem().contains("5 kg") ? 5f : 10f;
                    w = Math.round(w * 100f * waga) / 100f;
                    wagaField.setText(w.toString());
                }
            }
            if(!iloscField.getText().isEmpty() && !cenaJednostkowaField.getText().isEmpty()) {
                Float net = Float.parseFloat(iloscField.getText().replace(",", ".")) * Float.parseFloat(cenaJednostkowaField.getText().replace(",", "."));
                nettoField.setText(net.toString());
            }
        }

        private void dodajEdytuj() {
            OrderedArticle tmp = new OrderedArticle();
            Float cenaJednostkowa = Float.parseFloat(cenaJednostkowaField.getText().replace(",",".").matches("[0-9]+[.][0-9]+|[0-9]+") ? cenaJednostkowaField.getText().replace(",",".") : "0.0");
            tmp.setUnitPrice(cenaJednostkowa);
            if(cenaJednostkowa<0){
                showAlert("cena jednostkowa");
            }
            Float waga = Float.parseFloat(wagaField.getText().replace(",",".").matches("[0-9]+[.][0-9]+|[0-9]+")
                    ? wagaField.getText().replace(",",".") : "0.0");
            tmp.setWeight(waga);
            if(waga<0){
                showAlert("waga");
            }
            Float ilosc = Float.parseFloat(iloscField.getText().replace(",", ".").matches("[0-9]+[.][0-9]+|[0-9]+")
                    ? iloscField.getText().replace(",", ".") : "0.0");
            tmp.setAmount(ilosc);
            if(ilosc<0){
                showAlert("ilość");
            }
            Float netto = Float.parseFloat(nettoField.getText().replace(",",".").matches("[0-9]+[.][0-9]+|[0-9]+")
                    ? nettoField.getText().replace(",",".") : "0.0");
            tmp.setNetPrice(netto);
            if(netto<0){
                showAlert("netto");
            }
            if(reczneBtn.isSelected()){
                tmp.setDescription(opisField.getText().trim());
            } else tmp.setDescription(lista.getValue().trim() + " " + opisField.getText().trim());
            if(edit){
                tmp.setId(towaryTable.getSelectionModel().getSelectedItem().getId());
                //aktualizacja w bazie
                int index = towaryTable.getSelectionModel().getSelectedIndex();
                towaryTable.getItems().remove(index);
                //dodawanie towaru
            } else {
                //dodawanie towaru
                clearFields();
            }
            sort();
        }
        private void sort(){
            updateParent();
        }
        private void clearFields() {
            iloscField.setText("");
            wagaField.setText("");
            opisField.setText("");
            nettoField.setText("");
            cenaJednostkowaField.setText("");
        }

        private void showAlert(String tekst) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Błąd!");
            al.setHeaderText(null);
            al.setContentText("Podano złą wartość w polu " + tekst + ".");
            al.show();
        }

        private void setFields() {
            String s=lista.getValue();
            wagaField.setText("");
            iloscField.setText("");
            nettoField.setText("");
            cenaJednostkowaField.setText("");
            warning.setText("");
            wagaField.setVisible(true);
            wagaLabel.setVisible(true);
            if(s.equals("Okleina ścienna") || s.contains("Klej Vesstige Heavy")){
                opisField.setVisible(true);
                opisLabel.setVisible(true);
                iloscField.setVisible(true);
                if(s.contains("5 kg")) wagaField.setText("5");
                else if (s.contains("10 kg")) wagaField.setText("10");
            } else {
                opisField.setVisible(false);
                opisLabel.setVisible(false);
            }
            if(s.contains("Okleina")){
                iloscField.setVisible(true);
                iloscLabel.setVisible(true);
            } else if (s.contains("Montaż")){
                wagaField.setVisible(false);
                wagaLabel.setVisible(false);
                iloscField.setVisible(true);
                iloscLabel.setVisible(true);
            } else if (s.contains("Dostawa")){
                wagaField.setVisible(false);
                wagaLabel.setVisible(false);
                iloscField.setVisible(false);
                iloscLabel.setVisible(false);
                warning.setText("Uwaga! Poniżej 5000 zł dostawa naliczana jest automatycznie.");
            } else if (s.contains("Klej") || s.contains("Grunt")){
                iloscField.setVisible(true);
                iloscLabel.setVisible(true);
                if(s.contains("5 kg")) wagaField.setText("5");
                else if(s.contains("10 kg")) wagaField.setText("10");
            }
            if(s.contains("V DIGITAL") || s.contains("Montaż") || s.contains("Dostawa")) {
                opisField.setText("");
            }
        }

        public DodajEdytujTowar(String towary){
            this();
            //pobranie towarów z bazy
        }


    }
}
*/
