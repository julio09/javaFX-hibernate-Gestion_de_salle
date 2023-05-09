package com.example.m1.Controller;

import com.example.m1.Entity.Salle;
import com.example.m1.Repository.SalleRepo;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class editSalle {

    @FXML
    private MFXTextField desF;

    private boolean update;

    SalleRepo salleRepo = new SalleRepo();

    Salle salle = new Salle();

    @FXML
    void update(ActionEvent event) {
        if (!desF.getText().isBlank()){
            setedit();
        }
    }
    public void setedit(){

        if (update){
            salleRepo.Update(salle.getId(), desF.getText());
        }else {
            salleRepo.createSalle(salle);
        }
    }
    void setTextField(Long id, String designation) {
        salle.setId(id);
        desF.setText(designation);
    }
    void setUpdate(boolean b) {
        this.update = b;
    }
}
