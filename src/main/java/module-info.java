module com.kayque.stockmanager.javafxstockmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.kayque.stockmanager.javafxstockmanager to javafx.fxml;
    exports com.kayque.stockmanager.javafxstockmanager;
}