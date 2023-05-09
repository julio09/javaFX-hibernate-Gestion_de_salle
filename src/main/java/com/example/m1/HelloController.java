package com.example.m1;

import com.example.m1.Controller.ProfControlleur;
import com.example.m1.Entity.Prof;
import com.example.m1.Repository.ProfRepo;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {

    @FXML
    private MFXTextField grade;

    @FXML
    private MFXTextField nom;

    @FXML
    private MFXTextField prenom;


    ProfRepo profRepo = new ProfRepo();
    Prof prof = new Prof();
    ProfControlleur profControlleur = new ProfControlleur();

    private void ajout() {
        prof.setNom(nom.getText());
        prof.setPrenom(prenom.getText());
        prof.setGrade(grade.getText());
        profRepo.createProf(prof);
    }

    @FXML
    void save(ActionEvent event) {
        if(!nom.getText().isBlank() && !prenom.getText().isBlank() && !grade.getText().isBlank()){
            ajout();
            profControlleur.refreshTable();
        }

    }
}