package controller;

import entity.Task;
import java.time.Duration;
import java.time.LocalDateTime;

import javafx.scene.control.TreeItem;
import model.MainModel;
import model.TaskModel;
import view.MainMenu;

public class TaskController {
    private TaskModel taskModel;

    public TaskController(TaskModel taskModel) {
        this.taskModel = taskModel;
    }

    public void addTask() {
        taskModel.getAddButton().setOnAction(e -> {
            long userId = MainMenu.getUser().getId();
            String taskName = taskModel.getTaskName().getText();
            LocalDateTime creationTime = LocalDateTime.now();
            Duration taskDuration = Duration.ofSeconds(taskModel.getTaskDuration());
            String taskComment = taskModel.getTaskComment().getText();

            TreeItem<Task> treeItem = (TreeItem<Task>) MainModel.getTreeView().getSelectionModel().getSelectedItem();
            Task task = new Task(userId, taskName, creationTime, taskDuration, treeItem.getValue(), taskComment);
            task.setChanged(true);
            treeItem.getChildren().add(new TreeItem<>(task));
            taskModel.getAddButton().getScene().getWindow().hide();

            MainController.updateUser();
        });
    }

    public void editTask() {
        taskModel.getEditButton().setOnAction(e -> {
            TreeItem<Task> treeItem = (TreeItem<Task>) MainModel.getTreeView().getSelectionModel().getSelectedItem();
            Task task = treeItem.getValue();
            task.setTaskName(taskModel.getTaskName().getText());
            task.setSuggestedTaskDuration(Duration.ofSeconds(taskModel.getTaskDuration()));
            task.setComments(taskModel.getTaskComment().getText());
            task.setChanged(true);
            MainModel.getTreeView().refresh();
            taskModel.getEditButton().getScene().getWindow().hide();
            MainModel.getTreeView().getSelectionModel().select(null);
            MainModel.getTreeView().getSelectionModel().select(treeItem);

            MainController.updateUser();
        });
    }

    public void cancelTaskItem() {
        taskModel.getCancelButton().setOnAction(e -> taskModel.getCancelButton().getScene().getWindow().hide());
    }
}
