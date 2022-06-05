module com.fuzzy.fuzzyexpertsystemstool {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
//    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
//    requires javax.persistence;
//    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires redis.clients.jedis;
    requires com.google.gson;
//    requires javax.persistence;

    opens com.fuzzy.fuzzyexpertsystemstool to javafx.fxml;
    opens com.fuzzy.fuzzyexpertsystemstool.dbmodel to javafx.base, com.google.gson;
    exports com.fuzzy.fuzzyexpertsystemstool;
}