package com.example.m1.Controller;

import com.example.m1.Entity.Occuper;
import com.example.m1.Entity.Prof;
import com.example.m1.Entity.Salle;
import com.example.m1.Repository.OccuperRepo;
import com.example.m1.Repository.ProfRepo;
import com.example.m1.Repository.SalleRepo;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class editOccup {

    @FXML
    private MFXDatePicker dateF;

    @FXML
    private MFXComboBox<Prof> profF;

    @FXML
    private MFXComboBox<Salle> salleF;

    private boolean update;

    Occuper occuper = new Occuper();
    OccuperRepo occuperRepo = new OccuperRepo();

    ProfRepo profRepo = new ProfRepo();
    SalleRepo salleRepo = new SalleRepo();

    ObservableList<Prof> profs = profRepo.Liste();
    ObservableList<Salle> salles = salleRepo.Liste();

    public void comboBOx(){
        profF.setItems(profs);
        salleF.setItems(salles);
    }
    public void initialize(){
        comboBOx();
    }

    @FXML
    void update(ActionEvent event) {
        if (!profF.getText().isBlank() && !salleF.getText().isBlank() && !dateF.getText().isBlank()){
            setedit();
        }

    }
    public void setedit(){
        if (update){
            occuperRepo.Update(occuper.getId(), salleF.getSelectedItem(), profF.getSelectedItem(),dateF.getText());
        }else {
            occuperRepo.createOccup(occuper);
        }
    }
    void setTextField(Long id, String prof,String salle, String date) {
        occuper.setId(id);
        profF.setText(prof);
        salleF.setText(salle);
        dateF.setText(date);
    }
    void setUpdate(boolean b) {
        this.update = b;
    }

}