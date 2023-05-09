package com.example.m1.Controller;

import com.example.m1.Entity.Occuper;
import com.example.m1.Entity.Prof;
import com.example.m1.Entity.Salle;
import com.example.m1.HelloApplication;
import com.example.m1.Repository.OccuperRepo;
import com.example.m1.Repository.ProfRepo;
import com.example.m1.Repository.SalleRepo;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OccuperControlleur {

    @FXML
    private TableColumn<Occuper, String> action;

    @FXML
    private TableColumn<Occuper, String> date;

    @FXML
    private MFXDatePicker dateF;

    @FXML
    private TableColumn<Occuper, String> id;

    @FXML
    private TableColumn<Occuper, String> prof;

    @FXML
    private MFXComboBox<Prof> profF;

    @FXML
    private MFXTextField rs;

    @FXML
    private TableColumn<Occuper, String> salle;

    @FXML
    private MFXComboBox<Salle> salleF;

    @FXML
    private TableView<Occuper> table;

    ProfRepo profRepo = new ProfRepo();
    SalleRepo salleRepo = new SalleRepo();

    Occuper occuper = new Occuper();

    OccuperRepo occuperRepo = new OccuperRepo();

    ObservableList<Prof> profs = profRepo.Liste();
    ObservableList<Salle> salles = salleRepo.Liste();

    ObservableList<Occuper> occupers = occuperRepo.Liste();

    public void initialize(){
        ObservableList();
        comboBOx();
        filtre();
    }
    public void comboBOx(){
        profF.setItems(profs);
        salleF.setItems(salles);
    }

    @FXML
    void ajout(ActionEvent event) {
        if (!profF.getText().isBlank() && !salleF.getText().isBlank() && !dateF.getText().isBlank()){
            ajout();
            refreshTable();
            filtre();
        }
    }


    public void filtre(){
        FilteredList<Occuper> filteredList = new FilteredList<>(occupers, b ->true);
        rs.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(occuper -> {
            if(newValue.isEmpty() || newValue.isBlank()){
                return true;
            }
            String rech = newValue.toLowerCase();
            if(occuper.getProf().getNom().toLowerCase().contains(rech)){
                return true;
            }else if (occuper.getSalle().getDesignation().toLowerCase().contains(rech)){
                return true;
            }else return occuper.getDate().toLowerCase().contains(rech);
        }));
        SortedList<Occuper> listExit = new SortedList<>(filteredList);
        listExit.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(listExit);
    }

    public void ObservableList(){
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        prof.setCellValueFactory(new PropertyValueFactory<>("prof"));
        salle.setCellValueFactory(new PropertyValueFactory<>("salle"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        Callback<TableColumn<Occuper, String>, TableCell<Occuper, String>> cellFoctory = (TableColumn<Occuper, String> param) -> new TableCell<>() {
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
                        alert.setHeaderText("Est tu vraiment sur d'effacer cette proffesseur?");
                        alert.setTitle("Suppression");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){
                            occuper = table.getSelectionModel().getSelectedItem();
                            occuperRepo.delete(occuper.getId());
                            table.setItems(occupers);
                        }
                        refreshTable();
                        filtre();

                    });
                    editIcon.setOnMouseClicked((MouseEvent event) -> {

                        occuper = table.getSelectionModel().getSelectedItem();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(HelloApplication.class.getResource("modal2.fxml"));
                        try {
                            loader.load();
                        } catch (IOException ex) {
                            Logger.getLogger(HelloApplication.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        editOccup edit = loader.getController();
                        edit.setUpdate(true);
                        edit.setTextField(occuper.getId(), occuper.getProf().getNom(), occuper.getSalle().getDesignation(), occuper.getDate());
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
        table.setItems(occupers);
    }
    public void refreshTable(){
        try {
            occupers.clear();
            ObservableList<Occuper> produits = occuperRepo.Liste();
            for (Occuper occuper1: produits){
                occupers.add(occuper1);
                table.setItems(occupers);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void ajout() {
        occuper.setProf(profF.getSelectedItem());
        occuper.setSalle(salleF.getSelectedItem());
        occuper.setDate(dateF.getText());
        occuperRepo.createOccup(occuper);
    }


}
