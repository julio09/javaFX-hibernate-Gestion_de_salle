package com.example.m1.Repository;

import com.example.m1.Entity.Prof;
import com.example.m1.Entity.Salle;
import com.example.m1.util.HibernateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.List;

public class ProfRepo {

    /**    RECHEERCHE UN ID DANS LE TABLE PROF(FINDBYID) **/

    public Prof getId(Long id){
        Prof prof = null;
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = session.beginTransaction();
            prof = session.get(Prof.class, id);
        } catch (Exception t) {
            if (tx != null) {
                tx.rollback();
            }
            t.printStackTrace();
        }

        return prof;
    }

    /**
     *
     * CREATION OU AJOUT DU PROPRIETE DANS LE TABLE PROF
     */
    public void createProf(Prof prof){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.getTransaction().begin();
            session.merge(prof);
            session.flush();
            session.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informartion");
        alert.setHeaderText("succer");
        alert.setContentText("Un professeur ajouter avec succer");
        alert.setOnShown(alert.getOnShowing());
        alert.show();
    }

    /**    LISTAGE  **/

    public ObservableList<Prof> Liste(){
        Session session= HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        CriteriaBuilder builder= session.getCriteriaBuilder();
        CriteriaQuery<Prof> query= builder.createQuery(Prof.class);
        query.from(Prof.class);
        List<Prof> CategorieList = session.createQuery(query).getResultList();
        session.close();
        return  FXCollections.observableArrayList(CategorieList);
    }

    /**   SUPPRESSION **/
    public void delete(Long Id){
        Prof prof = getId(Id);
        Transaction Tx= null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            if (prof != null) {
                Tx = session.beginTransaction();
                session.remove(prof);
                Tx.commit();
            }
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Cette professeur est supprimer dans la liste");
            alert.show();
        } catch (Exception t) {
            if (Tx != null) {
                Tx.rollback();
            }
            t.printStackTrace();
        }
    }

    /**    MODIFICATION **/

    public void Update(Long Id,String nom,String prenom,String grade) {
        Prof prof = getId(Id);
        Transaction Tx=null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            Tx = session.beginTransaction();
            prof.setNom(nom);
            prof.setPrenom(prenom);
            prof.setGrade(grade);
            session.merge(prof);
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
