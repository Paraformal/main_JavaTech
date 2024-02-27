module com.example.main_s2024 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires dotenv.java;
    requires org.slf4j;

    opens com.example.main_s2024 to javafx.fxml;
    exports com.example.main_s2024;
    exports com.example.main_s2024.DbHandler;
    opens com.example.main_s2024.DbHandler to javafx.fxml;
}