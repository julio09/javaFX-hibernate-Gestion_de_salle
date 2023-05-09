package com.example.m1.Controller;

import com.example.m1.Entity.Prof;
import com.example.m1.Repository.ProfRepo;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class editProfController {

    private boolean update;

    Prof prof = new Prof();
    ProfRepo profRepo = new ProfRepo();

    @FXML
    private MFXTextField gradeF;

    @FXML
    private MFXTextField nomF;

    @FXML
    private MFXTextField prenomF;

    @FXML
    void update(ActionEvent event) {
        if (!nomF.getText().isBlank() && !prenomF.getText().isBlank() && !gradeF.getText().isBlank()){
            setedit();
        }
    }
    public void setedit(){

        if (update){
            profRepo.Update(prof.getId(), nomF.getText(),prenomF.getText(), gradeF.getText());
        }else {
            profRepo.createProf(prof);
        }
    }
    void setTextField(Long id, String nom, String prenom, String grade) {
        prof.setId(id);
        nomF.setText(nom);
        prenomF.setText(prenom);
        gradeF.setText(grade);
    }
    void setUpdate(boolean b){
        this.update = b;
    }

}
