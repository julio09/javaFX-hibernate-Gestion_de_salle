package com.example.m1.Repository;

import com.example.m1.Entity.Prof;
import com.example.m1.Entity.Salle;
import com.example.m1.util.HibernateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.List;

public class SalleRepo {
    public Salle getId(Long id){
        Salle salle = null;
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = session.beginTransaction();
            salle = session.get(Salle.class, id);
        } catch (Exception t) {
            if (tx != null) {
                tx.rollback();
            }
            t.printStackTrace();
        }

        return salle;
    }

    /**
     *
     * CREATION OU AJOUT DU PROPRIETE DANS LE TABLE PROF
     */

    public void createSalle(Salle salle){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.merge(salle);
            session.flush();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("");
        alert.setContentText("Un salle ajouter avec succer");
        alert.setOnShown(alert.getOnShowing());
        alert.show();
    }

    /**    LISTAGE  **/

    public ObservableList<Salle> Liste(){
        Transaction tx = null;
        Session session= HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        CriteriaBuilder builder= session.getCriteriaBuilder();
        CriteriaQuery<Salle> query= builder.createQuery(Salle.class);
        query.from(Salle.class);
        List<Salle> salleList = session.createQuery(query).getResultList();
        return  FXCollections.observableArrayList(salleList);
    }

    /**   SUPPRESSION **/
    public void delete(Long Id){
        Salle salle = getId(Id);
        Transaction Tx= null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            if (salle != null) {
                Tx = session.beginTransaction();
                session.remove(salle);
                Tx.commit();
            }
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Cette salle est supprimer dans la liste");
            alert.show();
        } catch (Exception t) {
            if (Tx != null) {
                Tx.rollback();
            }
            t.printStackTrace();
        }
    }

    /**    MODIFICATION **/

    public void Update(Long Id,String design) {
        Salle salle = getId(Id);
        Transaction Tx=null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            Tx = session.beginTransaction();
            salle.setId(Id);
            salle.setDesignation(design);
            session.merge(salle);
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
