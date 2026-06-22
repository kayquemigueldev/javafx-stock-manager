module com.kayque.stockmanager.javafxstockmanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.kayque.stockmanager.javafxstockmanager to javafx.fxml;
    exports com.kayque.stockmanager.javafxstockmanager;
}