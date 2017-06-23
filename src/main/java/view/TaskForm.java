package view;

import controller.TaskController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import menu.TimeTaskClient;
import model.TaskModel;

public class TaskForm {
    private Scene taskScene;
    private TaskModel taskModel;
    private TaskController taskController;

    public TaskForm() {
        this.taskModel = new TaskModel();
        this.taskController = new TaskController(taskModel);

        taskController.addTask();
        taskController.editTask();
        taskController.cancelTaskItem();

        taskScene = new Scene(taskModel.getGrid(), 600, 350);
        taskScene.getStylesheets().add(TimeTaskClient.class.getResource("/css/Form.css").toExternalForm());
    }

    public Scene getScene() {
        return taskScene;
    }

    public TaskModel getTaskModel() {
        return taskModel;
    }

    public void updateHeader(String header) {
        Text sceneTitle = new Text(header);
        sceneTitle.setId("task-title");
        HBox title = new HBox(10);
        title.setAlignment(Pos.CENTER);
        title.getChildren().add(sceneTitle);
        taskModel.getGrid().add(title, 0, 1, 2, 1);
    }

    public void setTaskName(String name) {
        TextField taskName = new TextField(name);
        taskModel.setTaskName(taskName);
        taskModel.getGrid().add(taskName, 1, 2);
    }

    public void setTaskDuration(long time) {
        taskModel.setTaskDuration(time);
    }

    public void setTaskComment(String comment) {
        TextArea textArea = new TextArea(comment);
        textArea.setWrapText(true);
        taskModel.setTaskComment(textArea);
        taskModel.getGrid().add(textArea, 1, 4);
    }

    public void setButtons(Button action) {
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(action, taskModel.getCancelButton());
        taskModel.getGrid().add(hbBtn, 1, 5);
        taskController.addTask();
        taskController.editTask();
        taskController.cancelTaskItem();
    }
}
