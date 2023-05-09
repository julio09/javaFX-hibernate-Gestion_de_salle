package com.example.m1;

import com.example.m1.Entity.Occuper;
import com.example.m1.Entity.Prof;
import com.example.m1.Entity.Salle;
import com.example.m1.Repository.OccuperRepo;
import com.example.m1.Repository.ProfRepo;
import com.example.m1.Repository.SalleRepo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Accueil.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1213, 816);
        stage.setTitle("Accueil");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

