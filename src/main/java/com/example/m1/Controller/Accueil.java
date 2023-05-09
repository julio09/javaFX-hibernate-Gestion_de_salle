package com.example.m1.Controller;

import com.example.m1.util.HibernateUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Accueil {


    @FXML
    private BorderPane pane;

    @FXML
    void occuperRoute(ActionEvent event) throws IOException {
        loadFXML(getClass().getResource("/com/example/m1/occuper.fxml"));
    }

    @FXML
    void profRoute(ActionEvent event) throws IOException {
        loadFXML(getClass().getResource("/com/example/m1/prof.fxml"));
    }

    @FXML
    void salleRoute(ActionEvent event) throws IOException {
        loadFXML(getClass().getResource("/com/example/m1/salle.fxml"));
    }

    Parent profP ;
    public void initialize() throws IOException {
        HibernateUtil.getSessionFactory().getCurrentSession();
        loadFXML(getClass().getResource("/com/example/m1/prof.fxml"));
    }

    public Parent profAcceuil() throws IOException {

       Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/m1/prof.fxml")));
        return parent;
    }


    private void loadFXML(URL url) throws IOException {
        FXMLLoader loader = new FXMLLoader(url);
        pane.setCenter(loader.load());
    }
}
