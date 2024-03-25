package com.example.main_s2024.ViewsPack;

import com.example.main_s2024.Models.Report_db;
import com.example.main_s2024.Services.delete_from_db;
import com.example.main_s2024.Services.get_from_db;
import com.example.main_s2024.StageHandler.StageHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.net.URL;
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

    private get_from_db getFromDb;

    private delete_from_db deleteFromDb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportId.setCellValueFactory(new PropertyValueFactory<>("reportId"));
        systemLoadIdColumn.setCellValueFactory(new PropertyValueFactory<>("systemLoadId"));
        batteryInfoIdColumn.setCellValueFactory(new PropertyValueFactory<>("batteryInfoId"));
        cpuInfoIdColumn.setCellValueFactory(new PropertyValueFactory<>("cpuInfoId"));
        diskInfoIdColumn.setCellValueFactory(new PropertyValueFactory<>("diskInfoId"));
        systemInfoIdColumn.setCellValueFactory(new PropertyValueFactory<>("systemInfoId"));
        timeStampColumn.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));

        initializeDeleteButtonColumn();
        loadReportData();

        reportTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)) {
                Report_db selectedReport = reportTableView.getSelectionModel().getSelectedItem();
                if (selectedReport != null) {
                    System.out.println("DOUBLE ASS CLICKED");
                }
            }
        });
    }

    private void loadReportData() {
        getFromDb = new get_from_db();
        List<Report_db> reports = getFromDb.getAllReports();
        reportTableView.getItems().setAll(reports);
    }

    public void onBackButtonClicked(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        StageHandler stageHandler = new StageHandler(currentStage);

        stageHandler.hideStage();
    }

    private void initializeDeleteButtonColumn() {
        TableColumn<Report_db, Void> deleteButtonColumn = new TableColumn<>("Action");
        deleteButtonColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Report_db report = getTableView().getItems().get(getIndex());
                    deleteReport(report.getReportId());
                    reportTableView.getItems().remove(report);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
        reportTableView.getColumns().add(deleteButtonColumn);
    }

    private void deleteReport(int reportId) {
        deleteFromDb = new delete_from_db();
        deleteFromDb.deleteReportById(reportId);
    }


}
