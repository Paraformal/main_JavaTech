package com.example.main_s2024.ViewsPack;

import com.example.main_s2024.Models.*;
import com.example.main_s2024.Services.delete_from_db;
import com.example.main_s2024.Services.get_from_db;
import com.example.main_s2024.StageHandler.StageHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class ReportView implements Initializable {

    @FXML
    private TableView<Report_db> reportTableView;

    @FXML
    private TableColumn<Report_db, Integer> reportId;

    @FXML
    private TableColumn<Report_db, Integer> systemLoadIdColumn;

    @FXML
    private TableColumn<Report_db, Integer> batteryInfoIdColumn;

    @FXML
    private TableColumn<Report_db, Integer> cpuInfoIdColumn;

    @FXML
    private TableColumn<Report_db, Integer> diskInfoIdColumn;

    @FXML
    private TableColumn<Report_db, Integer> systemInfoIdColumn;

    @FXML
    private TableColumn<Report_db, Date> timeStampColumn;

    @FXML
    private TableColumn<Report_db, String> systemIdColumn;

    private get_from_db getFromDb;

    private delete_from_db deleteFromDb;

    private String systemId = "50-2F-9B-9E-EA-8F";

    private boolean isAdmin = true;


    public ReportView() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportId.setCellValueFactory(new PropertyValueFactory<>("reportId"));
        systemLoadIdColumn.setCellValueFactory(new PropertyValueFactory<>("systemLoadId"));
        batteryInfoIdColumn.setCellValueFactory(new PropertyValueFactory<>("batteryInfoId"));
        cpuInfoIdColumn.setCellValueFactory(new PropertyValueFactory<>("cpuInfoId"));
        diskInfoIdColumn.setCellValueFactory(new PropertyValueFactory<>("diskInfoId"));
        systemInfoIdColumn.setCellValueFactory(new PropertyValueFactory<>("systemInfoId"));
        systemIdColumn.setCellValueFactory(new PropertyValueFactory<>("systemId"));
        timeStampColumn.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));

        initializeActionColumn();
        loadReportData();


        reportTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)) {
                Report_db selectedReport = reportTableView.getSelectionModel().getSelectedItem();
                if (selectedReport != null) {
                    openReportDetailsView(event);
                }
            }
        });
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    private void loadReportData() {
        System.out.println("NIGGAAA IAM HERE BROOOOO " + isAdmin);
        if (!isAdmin) {
            getFromDb = new get_from_db();
            try {
                List<Report_db> reports = getFromDb.getAllReportsById(this.systemId);
                reportTableView.getItems().setAll(reports);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            getFromDb = new get_from_db();
            List<Report_db> reports = getFromDb.getAllReports();
            reportTableView.getItems().setAll(reports);
        }
    }

    public void onBackButtonClicked(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        StageHandler stageHandler = new StageHandler(currentStage);

        stageHandler.hideStage();
    }

    private void initializeActionColumn() {
        TableColumn<Report_db, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");
            private final Button saveButton = new Button("Save");
            private final HBox container = new HBox(10);

            {
                deleteButton.setOnAction(event -> {
                    Report_db report = getTableView().getItems().get(getIndex());
                    deleteReport(report.getReportId());
                    getTableView().getItems().remove(report);
                });

                saveButton.setOnAction(event -> {
                    Report_db report = getTableView().getItems().get(getIndex());
                    saveReport(report.getReportId());
                });

                container.getChildren().addAll(deleteButton, saveButton);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(container);
                }
            }
        });
        reportTableView.getColumns().add(actionColumn);
    }

    private void saveReport(int reportId) {
        get_from_db getFromDb1 = new get_from_db();
        Report_db report = getFromDb1.getReportById(reportId);
        BatteryInfo batteryInfo = getFromDb1.getBatteryInfoById(report.getBatteryInfoId());
        CpuInfo cpuInfo = getFromDb1.getCpuInfoById(report.getCpuInfoId());
        DiskInfo diskInfo = getFromDb1.getDiskInfoById(report.getDiskInfoId());
        SystemLoad systemLoad = getFromDb1.getSystemLoadById(report.getSystemLoadId());
        SystemInfo systemInfo = getFromDb1.getSystemInfoById(report.getSystemInfoId());

        String reportData = "Report ID: " + report.getReportId() + "\n" +
                "Time Stamp: " + report.getTimeStamp() + "\n" +
                "Battery Info: " + batteryInfo.toString() + "\n" +
                "CPU Info: " + cpuInfo.toString() + "\n" +
                "Disk Info: " + diskInfo.toString() + "\n" +
                "System Load: " + systemLoad.toString() + "\n" +
                "System Info: " + systemInfo.toString() + "\n";

        String fileName = "saved_report_" + report.getReportId() + ".txt";
        try {
            Files.write(Paths.get(fileName), reportData.getBytes(), StandardOpenOption.CREATE);
            System.out.println("Report saved to " + fileName);
            //Alert view saved successfully
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Report Saved");
            alert.setHeaderText("Report saved successfully");
            alert.setContentText("Report saved to " + fileName);
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteReport(int reportId) {
        deleteFromDb = new delete_from_db();
        deleteFromDb.deleteReportById(reportId);
    }

    public void openReportDetailsView(MouseEvent event) {
        try {
            Report_db selectedReport = reportTableView.getSelectionModel().getSelectedItem();
            if (selectedReport != null) {
                int report_id = selectedReport.getReportId();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/main_s2024/report_details_view.fxml"));
                Parent root = loader.load();

                ReportDetailsView controller = loader.getController();
                controller.setProjectId(report_id);

                Stage currentStage = (Stage) reportTableView.getScene().getWindow();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
                currentStage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setIsAdmin(boolean isAdmin) {
        System.out.println("FINNALY DONE" + isAdmin);
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

}
