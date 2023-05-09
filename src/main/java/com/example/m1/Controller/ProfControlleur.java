package com.example.m1.Controller;

import com.example.m1.Entity.Prof;
import com.example.m1.HelloApplication;
import com.example.m1.Repository.ProfRepo;
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

public class ProfControlleur {

    @FXML
    private MFXTextField gradeF;

    @FXML
    private MFXTextField nomF;

    @FXML
    private MFXTextField prenomF;

    @FXML
    private TableColumn<Prof, String> action;

    @FXML
    private TableColumn<Prof, String> codeprof;

    @FXML
    private TableColumn<Prof, String> grade;

    @FXML
    private TableColumn<Prof, String> nom;

    @FXML
    private TableColumn<Prof, String> prenom;

    @FXML
    private TableView<Prof> table;

    @FXML
    private MFXTextField rs;

    ProfRepo profRepo = new ProfRepo();
    Prof prof = new Prof();

    ObservableList<Prof> profs = profRepo.Liste();



    public void initialize(){
        profObservableList();
        filtre();
    }
    public void filtre(){
        FilteredList<Prof> filteredList = new FilteredList<>(profs, b ->true);
        rs.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(prof -> {
            if(newValue.isEmpty() || newValue.isBlank()){
                return true;
            }
            String rech = newValue.toLowerCase();
            if(prof.getNom().toLowerCase().contains(rech)){
                return true;
            }else if (prof.getPrenom().toLowerCase().contains(rech)){
                return true;
            }else return prof.getGrade().toLowerCase().contains(rech);
        }));
        SortedList<Prof> listExit = new SortedList<>(filteredList);
        listExit.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(listExit);
    }

    @FXML
    void ajout(ActionEvent event) {
        if(!nomF.getText().isBlank() && !prenomF.getText().isBlank() && !gradeF.getText().isBlank()){
            ajout();
            refreshTable();
            filtre();
        }
    }

    public void profObservableList(){
        codeprof.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        grade.setCellValueFactory(new PropertyValueFactory<>("Grade"));
        Callback<TableColumn<Prof, String>, TableCell<Prof, String>> cellFoctory = (TableColumn<Prof, String> param) -> new TableCell<>() {
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
                            prof = table.getSelectionModel().getSelectedItem();
                            profRepo.delete(prof.getId());
                            table.setItems(profs);
                        }
                        refreshTable();
                        filtre();

                    });
                    editIcon.setOnMouseClicked((MouseEvent event) -> {

                        prof = table.getSelectionModel().getSelectedItem();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(HelloApplication.class.getResource("modal.fxml"));
                        try {
                            loader.load();
                        } catch (IOException ex) {
                            Logger.getLogger(HelloApplication.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        editProfController edit = loader.getController();
                        edit.setUpdate(true);
                        edit.setTextField(prof.getId(), prof.getNom(), prof.getPrenom(), prof.getGrade());
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
        table.setItems(profs);
    }
    public void refreshTable(){
        try {
            profs.clear();
            ObservableList<Prof> produits = profRepo.Liste();
            for (Prof produit: produits){
                profs.add(produit);
                table.setItems(produits);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void ajout() {
        prof.setNom(nomF.getText());
        prof.setPrenom(prenomF.getText());
        prof.setGrade(gradeF.getText());
        profRepo.createProf(prof);
    }
}
