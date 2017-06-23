package controller;

import entity.Task;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.MainModel;
import view.MainMenu;
import view.StatisticsForm;
import view.TaskForm;

import java.time.Duration;

public class MainController {
    private final MainModel mainModel;

    public MainController(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    public void switchTimer() {
        mainModel.getPlayButton().setOnAction(e -> {
            TreeItem<Task> treeItem = (TreeItem<Task>) MainModel.getTreeView().getSelectionModel().getSelectedItem();
            if (!treeItem.equals(MainModel.getTreeView().getRoot())) {
                Task task = treeItem.getValue();
                if (mainModel.getPlayButton().getId().equals("play")) {
                    if (task.getElapsedTaskDuration() == null) {
                        mainModel.setTimeElapsed(0);
                    } else {
                        mainModel.setTimeElapsed(task.getElapsedTaskDuration().getSeconds());
                    }
                    mainModel.getPlayButton().setId("pause");
                    Timeline timeline = new Timeline();
                    mainModel.setTimeline(timeline);
                    timeline.setCycleCount(1000000);
                    timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(1), ae -> {
                        mainModel.tick();
                        mainModel.setTimerText(mainModel.getTimeElapsed());
                        mainModel.getTimer().setText(mainModel.getTimerText());
                        alert();
                    }));
                    mainModel.getTimeline().play();
                    task.setElapsedTaskDuration(Duration.ofSeconds(mainModel.getTimeElapsed()));
                    treeItem.setValue(task);
                } else {
                    task.setElapsedTaskDuration(Duration.ofSeconds(mainModel.getTimeElapsed()));
                    task.setChanged(true);
                    treeItem.setValue(task);
                    checkAlert();
                    mainModel.getPlayButton().setId("play");
                    mainModel.getTimeline().pause();
                }
            }
        });
    }

    public void showAddTaskWindow() {
        mainModel.getAddButton().setOnAction(e -> {
            Stage dialog = new Stage();
            TaskForm taskForm = new TaskForm();
            taskForm.updateHeader("Add Task");
            taskForm.setButtons(taskForm.getTaskModel().getAddButton());

            dialog.setScene(taskForm.getScene());
            dialog.initOwner(MainMenu.getPrimaryStage());
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.showAndWait();
        });
    }

    public void showEditTaskWindow() {
        mainModel.getEditButton().setOnAction(e -> {
            Stage dialog = new Stage();
            TaskForm taskForm = new TaskForm();
            taskForm.updateHeader("Edit Task");
            taskForm.setButtons(taskForm.getTaskModel().getEditButton());

            TreeItem<Task> treeItem = (TreeItem<Task>) MainModel.getTreeView().getSelectionModel().getSelectedItem();
            if (!treeItem.equals(MainModel.getTreeView().getRoot())) {
                Task task = treeItem.getValue();
                taskForm.setTaskName(task.getTaskName());
                taskForm.setTaskDuration(task.getSuggestedTaskDuration().getSeconds());
                taskForm.setTaskComment(task.getComments());

                dialog.setScene(taskForm.getScene());
                dialog.initOwner(MainMenu.getPrimaryStage());
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.showAndWait();
            }
        });
    }

    public void removeTask() {
        mainModel.getRemoveButton().setOnAction(e -> {
            TreeItem<Task> treeItem = (TreeItem<Task>) MainModel.getTreeView().getSelectionModel().getSelectedItem();
            if (!treeItem.equals(MainModel.getTreeView().getRoot())) {
                treeItem.getValue().setChanged(true);
                treeItem.getValue().setForDeletion(true);
                MainController.updateUser();
                treeItem.getParent().getChildren().remove(treeItem);
            }
        });
    }

    public void completeTask() {
        mainModel.getCompleteButton().setOnAction(e -> {
            TreeItem<Task> treeItem = (TreeItem<Task>) MainModel.getTreeView().getSelectionModel().getSelectedItem();
            if (!treeItem.equals(MainModel.getTreeView().getRoot())) {
                if (treeItem.getGraphic() == null) {
                    treeItem.getValue().setFinished(true);
                    treeItem.getValue().setChanged(true);
                    treeItem.setGraphic(mainModel.createIcon());
                } else {
                    treeItem.getValue().setFinished(false);
                    treeItem.getValue().setChanged(true);
                    treeItem.setGraphic(null);
                }
                MainController.updateUser();
            }
        });
    }

    public void showStatisticsWindow() {
        mainModel.getStatisticsButton().setOnAction(e -> {
            Stage dialog = new Stage();
            StatisticsForm statisticsForm = new StatisticsForm();

            dialog.setScene(statisticsForm.getScene());
            dialog.initOwner(MainMenu.getPrimaryStage());
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.showAndWait();
        });
    }

    public void setTasksSuggestedTime() {
        MainModel.getTreeView().getSelectionModel().selectedItemProperty().addListener(observable -> {
            TreeItem<Task> treeItem = (TreeItem<Task>) MainModel.getTreeView().getSelectionModel().getSelectedItem();
            if (treeItem != null) {
                long time = treeItem.getValue().getSuggestedTaskDuration().getSeconds();
                mainModel.setTimerText(time);
                mainModel.getSuggestedTime().setText(mainModel.getTimerText());
                mainModel.getHeader().getChildren().remove(mainModel.getSuggestedTime());
                mainModel.getHeader().getChildren().add(mainModel.getSuggestedTime());
                mainModel.getPane().setTop(mainModel.getHeader());
            }
        });
    }

    public void setTasksElapsedTime() {
        MainModel.getTreeView().getSelectionModel().selectedItemProperty().addListener(observable -> {
            TreeItem<Task> treeItem = (TreeItem<Task>) MainModel.getTreeView().getSelectionModel().getSelectedItem();
            long time;
            if (treeItem != null) {
                if (treeItem.getValue().getElapsedTaskDuration() == null) {
                    time = 0;
                } else {
                    time = treeItem.getValue().getElapsedTaskDuration().getSeconds();
                }
                mainModel.setTimeElapsed(time);
                mainModel.setTimerText(time);
                mainModel.getTimer().setText(mainModel.getTimerText());
                mainModel.getHeader().getChildren().remove(mainModel.getTimer());
                mainModel.getHeader().getChildren().add(mainModel.getTimer());
                mainModel.getPane().setTop(mainModel.getHeader());
            }
        });
    }

    public void alert() {
        TreeItem<Task> treeItem = (TreeItem<Task>) MainModel.getTreeView().getSelectionModel().getSelectedItem();
        if (treeItem != null && !mainModel.getTimer().getId().equals("alert")) {
            if (mainModel.getTimeElapsed() >
                    treeItem.getValue().getSuggestedTaskDuration().getSeconds()) {
                mainModel.getTimer().setId("alert");
                new AudioClip(getClass().getResource("/sounds/beep.wav").toExternalForm()).play();
            }
        }
    }

    public void checkAlert() {
        MainModel.getTreeView().getSelectionModel().selectedItemProperty().addListener(observable -> {
            TreeItem<Task> treeItem = (TreeItem<Task>) MainModel.getTreeView().getSelectionModel().getSelectedItem();
            if (treeItem != null) {
                if (mainModel.getTimeElapsed() <=
                        treeItem.getValue().getSuggestedTaskDuration().getSeconds()) {
                    mainModel.getTimer().setId("");
                }
            }
        });
    }

    public static void updateUser() {
        MainModel.parseTreeView(MainModel.getTreeView().getRoot().getChildren(), MainMenu.getUser().getTaskList());
    }
}
