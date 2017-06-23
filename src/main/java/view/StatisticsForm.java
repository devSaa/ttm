package view;

import controller.StatisticsController;
import entity.Task;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import menu.TimeTaskClient;
import model.StatisticsModel;

import java.util.List;
import java.util.Objects;


public class StatisticsForm {
    private Scene statisticsForm;
    private StatisticsModel statisticsModel;
    private StatisticsController statisticsController;
    private Button statsCountAllSubTask = new Button("All Sub Task");
    private Button statsCountAllFinishTask = new Button("All Finish Task");
    private Button statsCountAllProcessTask = new Button("All Process Task");

    public StatisticsForm() {
        statisticsModel = new StatisticsModel();
        statisticsController = new StatisticsController(statisticsModel);

        statisticsController.init();

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Root tasks");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total counts sub tasks");

        BarChart barChart = new BarChart(xAxis, yAxis);
        XYChart.Series dataSeries = new XYChart.Series();
        List<Task> rootTasks = MainMenu.getUser().getTaskList();
        HBox hBox = new HBox();
        hBox.getChildren().addAll(statsCountAllSubTask, statsCountAllProcessTask, statsCountAllFinishTask);

        statsCountAllSubTask.setOnAction(e -> {
            createBarChar(rootTasks, dataSeries, barChart, "sub");
        });

        statsCountAllFinishTask.setOnAction(e -> {
            createBarChar(rootTasks, dataSeries, barChart, "finished");
        });

        statsCountAllProcessTask.setOnAction(e -> {
            createBarChar(rootTasks, dataSeries, barChart, "process");
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox);
        borderPane.setCenter(barChart);

        statisticsForm = new Scene(borderPane, 1000, 600);
        statisticsForm.getStylesheets().add(
                TimeTaskClient.class.getResource("/css/Form.css").toExternalForm());
    }

    public BarChart createBarChar(List<Task> rootTasks, XYChart.Series dataSeries, BarChart barChart, String flag) {
        int count = 0;
        for (Task rootTask : rootTasks) {
            if (rootTask.isForDeletion() != true) {
                dataSeries.getData().add(new XYChart.Data(rootTask.getTaskName(), parseTasksList(rootTask, count, flag)));

//                if (Objects.equals(flag, "finished") && rootTask.isFinished()) {
//                    dataSeries.getData().addAll(new XYChart.Data(rootTask.getTaskName(), parseTasksList(rootTask, count, flag)));
//                } else if (Objects.equals(flag, "process") && rootTask.isFinished() == false) {
//                    dataSeries.getData().addAll(new XYChart.Data(rootTask.getTaskName(), parseTasksList(rootTask, count, flag)));
//                } else if (Objects.equals(flag, "sub")) {
//                    dataSeries.getData().addAll(new XYChart.Data(rootTask.getTaskName(), parseTasksList(rootTask, count, flag)));
//                }
            }
        }
        barChart.getData().add(dataSeries);
        barChart.setPrefWidth(1000.0);
        barChart.setPrefHeight(500.0);
        return barChart;
    }

    public int parseTasksList(Task task, int count, String flag) {
        for (Task subTask : task.getSubTaskList()) {
            if (subTask.isForDeletion() != true) {
                if (Objects.equals(flag, "finished") && subTask.isFinished()) {
                    count++;
                    if (!subTask.getSubTaskList().isEmpty()) {
                        parseTasksList(subTask, count, flag);
                    }
                } else if (Objects.equals(flag, "process") && subTask.isFinished() == false) {
                    count++;
                    if (subTask.getSubTaskList().isEmpty() != true) {
                        parseTasksList(subTask, count, flag);
                    }
                } else if (Objects.equals(flag, "sub")) {
                    count++;
                    if (!subTask.getSubTaskList().isEmpty()) {
                        parseTasksList(subTask, count, flag);
                    }
                }
            }
        }
        return count;
    }

    public Scene getScene() {
        return statisticsForm;
    }
}