module com.example.m1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires MaterialFX;
    requires fontawesomefx;


    opens com.example.m1 to javafx.fxml;
    opens com.example.m1.Controller to javafx.fxml;
    opens com.example.m1.Entity to org.hibernate.orm.core;


    exports com.example.m1;
    exports com.example.m1.Controller;
    exports com.example.m1.Entity;
    exports com.example.m1.Repository;
}