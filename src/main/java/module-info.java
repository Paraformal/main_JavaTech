module com.example.main_s2024 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires dotenv.java;
    requires org.slf4j;
    requires com.github.oshi;
    requires sigar;
    requires org.json;
    requires com.sun.jna;
    requires okhttp3;
    requires com.google.gson;

    opens com.example.main_s2024 to javafx.fxml;
    exports com.example.main_s2024;
    exports com.example.main_s2024.DbHandler;
    opens com.example.main_s2024.DbHandler to javafx.fxml;
    exports com.example.main_s2024.StageHandler;
    opens com.example.main_s2024.StageHandler to javafx.fxml;
    exports com.example.main_s2024.Utils;
    opens com.example.main_s2024.Utils to javafx.fxml;
    exports com.example.main_s2024.DataPack to com.sun.jna;
    exports com.example.main_s2024.ViewsPack;
    opens com.example.main_s2024.ViewsPack to javafx.fxml;
    opens com.example.main_s2024.Models to javafx.base;

}