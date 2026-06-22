module com.kayque.stockmanager.javafxstockmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires jbcrypt;
    requires itextpdf;

    opens com.kayque.stockmanager.javafxstockmanager to javafx.fxml;
    opens com.kayque.stockmanager.javafxstockmanager.controller to javafx.fxml;

    exports com.kayque.stockmanager.javafxstockmanager;
    exports com.kayque.stockmanager.javafxstockmanager.controller;
    exports com.kayque.stockmanager.javafxstockmanager.model;
    exports com.kayque.stockmanager.javafxstockmanager.dao;
    exports com.kayque.stockmanager.javafxstockmanager.database;
    exports com.kayque.stockmanager.javafxstockmanager.security;
    exports com.kayque.stockmanager.javafxstockmanager.util;
}