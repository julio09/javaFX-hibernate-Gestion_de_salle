package com.example.m1.Repository;

import com.example.m1.Entity.Occuper;
import com.example.m1.Entity.Prof;
import com.example.m1.Entity.Salle;
import com.example.m1.util.HibernateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OccuperRepo {
    public Occuper getId(Long id){
        Occuper occuper = null;
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = session.beginTransaction();
            occuper = session.get(Occuper.class, id);
        } catch (Exception t) {
            if (tx != null) {
                tx.rollback();
            }
            t.printStackTrace();
        }

        return occuper;
    }

    /**
     *
     * CREATION OU AJOUT DU PROPRIETE DANS LE TABLE PROF
     */
    public void createOccup(Occuper occuper){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();
        session.merge(occuper);
        session.flush();
        session.close();
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle("");
         alert.setHeaderText("");
         alert.setContentText("Un Occupation ajouter avec succer");
         alert.setOnShown(alert.getOnShowing());
         alert.show();
    }

    /**    LISTAGE  **/
    Occuper occuper = new Occuper();

    public ObservableList<Occuper> Liste(){
        Session session= HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder= session.getCriteriaBuilder();
        CriteriaQuery<Occuper> query= builder.createQuery(Occuper.class);
        query.from(Occuper.class);
        List<Occuper> occuperList = session.createQuery(query).getResultList();
        Hibernate.initialize(occuper.getProf());
        Hibernate.unproxy(occuper.getSalle());
        return  FXCollections.observableArrayList(occuperList);
    }

    /**   SUPPRESSION **/
    public void delete(Long Id){
        Occuper occuper1 = getId(Id);
        Transaction Tx= null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            if (occuper1 != null) {
                Tx = session.beginTransaction();
                session.remove(occuper1);
                Tx.commit();
            }
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Cette occupation est supprimer dans la liste");
            alert.show();
        } catch (Exception t) {
            if (Tx != null) {
                Tx.rollback();
            }
            t.printStackTrace();
        }
    }

    /**    MODIFICATION **/

    public void Update(Long Id, Salle salle, Prof Prof, String date) {
        Occuper occuper = getId(Id);
        Transaction Tx=null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            Tx = session.beginTransaction();
            occuper.setProf(Prof);
            occuper.setSalle(salle);
            occuper.setDate(date);
            session.merge(occuper);
            Alert alert = new Alert(Alert.AlertType.WARNING);
             alert.setContentText("Modification avec succées");
             alert.setHeaderText(null);
             alert.show();
            Tx.commit();
        } catch (Exception t) {
            if (Tx != null) {
                Tx.rollback();
            }
            t.printStackTrace();
        }
    }
}
