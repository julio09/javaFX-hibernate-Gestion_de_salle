package com.example.m1.Controller;

import com.example.m1.Entity.Prof;
import com.example.m1.Entity.Salle;
import com.example.m1.HelloApplication;
import com.example.m1.Repository.SalleRepo;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SalleController {

    @FXML
    private TableColumn<Salle, String> des;

    @FXML
    private TableColumn<Salle, String> action;

    @FXML
    private MFXTextField desF;

    @FXML
    private TableColumn<Salle, Long> id;

    @FXML
    private MFXTextField rs;

    @FXML
    private TableView<Salle> table;

    Salle salle = new Salle();
    SalleRepo salleRepo = new SalleRepo();

    ObservableList<Salle> salles = salleRepo.Liste();

    public void initialize(){
        SalleObservableList();
        filtre();
    }
    public void filtre(){
        FilteredList<Salle> filteredList = new FilteredList<>(salles, b ->true);
        rs.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(salle -> {
            if(newValue.isEmpty() || newValue.isBlank()){
                return true;
            }
            String rech = newValue.toLowerCase();
            if(salle.getDesignation().toLowerCase().contains(rech)){
                return true;
            }else return salle.getId().toString().contains(rech);
        }));
        SortedList<Salle> listExit = new SortedList<>(filteredList);
        listExit.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(listExit);
    }
    @FXML
    void ajout(ActionEvent event) {
        if (!desF.getText().isBlank()){
            ajout();
            refreshTable();
            filtre();
        }
    }
    public void SalleObservableList(){
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        des.setCellValueFactory(new PropertyValueFactory<>("designation"));
        Callback<TableColumn<Salle, String>, TableCell<Salle, String>> cellFoctory = (TableColumn<Salle, String> param) -> new TableCell<>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                //that cell created only on non-empty rows
                if (empty) {
                    setGraphic(null);
                    setText(null);

                } else {


                    ImageView editIcon = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/ICON/edit.png"))));
                    editIcon.setFitWidth(35);
                    editIcon.setFitHeight(30);
                    ImageView deleteIcon = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/ICON/delete.png"))));
                    deleteIcon.setFitWidth(35);
                    deleteIcon.setFitHeight(30);



                    deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("Est tu vraiment sur d'effacer cette salle?");
                        alert.setTitle("Suppression");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){
                            salle = table.getSelectionModel().getSelectedItem();
                            salleRepo.delete(salle.getId());
                            table.setItems(salles);
                        }
                        refreshTable();
                        filtre();

                    });
                    editIcon.setOnMouseClicked((MouseEvent event) -> {

                        salle = table.getSelectionModel().getSelectedItem();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(HelloApplication.class.getResource("modal1.fxml"));
                        try {
                            loader.load();
                        } catch (IOException ex) {
                            Logger.getLogger(HelloApplication.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        editSalle edit = loader.getController();
                        edit.setUpdate(true);
                        edit.setTextField(salle.getId(), salle.getDesignation());
                        Parent parent = loader.getRoot();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(parent));
                        stage.initStyle(StageStyle.UTILITY);
                        stage.showAndWait();
                        refreshTable();
                        filtre();
                    });

                    HBox managebtn = new HBox(editIcon, deleteIcon);
                    managebtn.setStyle("-fx-alignment:center");
                    HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                    HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                    setGraphic(managebtn);

                    setText(null);

                }
            }

        };
        action.setCellFactory(cellFoctory);
        table.setItems(salles);
    }
    public void refreshTable(){
        try {
            salles.clear();
            ObservableList<Salle> salles1 = salleRepo.Liste();
            for (Salle salle: salles1){
                salles.add(salle);
                table.setItems(salles);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void ajout() {
        salle.setDesignation(desF.getText());
        salleRepo.createSalle(salle);
    }
}
