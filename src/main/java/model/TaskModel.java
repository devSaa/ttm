package model;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

public class TaskModel {
    private GridPane grid;
    private HBox hbTime;
    private TextField taskName;
    private TextField taskDurationHours;
    private TextField taskDurationMinutes;
    private TextArea taskComment;
    private Text hint;
    private Button addButton;
    private Button editButton;
    private Button cancelButton;

    public TaskModel() {
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(25);
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(25);
        col2.setPercentWidth(60);
        grid.getColumnConstraints().addAll(col1, col2);

        Label userName = new Label("Task Name:");
        grid.add(userName, 0, 2);
        taskName = new TextField();
        grid.add(taskName, 1, 2);

        Label taskDurationLabel = new Label("Task Duration:");
        grid.add(taskDurationLabel, 0, 3);
        hbTime = new HBox(10);
        taskDurationHours = new TextField();
        taskDurationHours.setAlignment(Pos.BASELINE_RIGHT);
        taskDurationHours.setPrefWidth(40);
        Label colon = new Label(":");
        taskDurationMinutes = new TextField();
        taskDurationMinutes.setAlignment(Pos.BASELINE_RIGHT);
        taskDurationMinutes.setPrefWidth(40);
        hbTime.getChildren().addAll(taskDurationHours, colon, taskDurationMinutes);
        grid.add(hbTime, 1, 3);

        Label taskCommentLabel = new Label("Task Comment:");
        grid.add(taskCommentLabel, 0, 4);
        taskComment = new TextArea();
        taskComment.setWrapText(true);
        grid.add(taskComment, 1, 4);

        addButton = new Button("Add");
        editButton = new Button("Edit");
        cancelButton = new Button("Cancel");

        hint = new Text();
        hint.setId("warning");
        HBox hbMessage = new HBox(10);
        hbMessage.setAlignment(Pos.BOTTOM_RIGHT);
        hbMessage.getChildren().add(hint);
        grid.add(hbMessage, 0, 6, 2, 1);
    }

    public GridPane getGrid() {
        return grid;
    }

    public void setGrid(GridPane grid) {
        this.grid = grid;
    }

    public HBox getHbTime() {
        return hbTime;
    }

    public void setHbTime(HBox hbTime) {
        this.hbTime = hbTime;
    }

    public TextField getTaskName() {
        return taskName;
    }

    public void setTaskName(TextField taskName) {
        this.taskName = taskName;
    }

    public TextField getTaskDurationHours() {
        return taskDurationHours;
    }

    public void setTaskDurationHours(TextField taskDurationHours) {
        this.taskDurationHours = taskDurationHours;
    }

    public TextField getTaskDurationMinutes() {
        return taskDurationMinutes;
    }

    public void setTaskDurationMinutes(TextField taskDurationMinutes) {
        this.taskDurationMinutes = taskDurationMinutes;
    }

    public long getTaskDuration() {
        if (taskDurationHours.getText().equals("")) {
            taskDurationHours.setText("0");
        }
        if (taskDurationMinutes.getText().equals("")) {
            taskDurationMinutes.setText("0");
        }
        return Long.parseLong(taskDurationHours.getText()) * 60 + Long.parseLong(taskDurationMinutes.getText());
    }

    public void setTaskDuration(long taskDuration) {
        taskDurationHours.setText(String.valueOf(taskDuration / 60));
        taskDurationMinutes.setText(String.valueOf(taskDuration % 60));
    }

    public TextArea getTaskComment() {
        return taskComment;
    }

    public void setTaskComment(TextArea taskComment) {
        this.taskComment = taskComment;
    }

    public Text getHint() {
        return hint;
    }

    public void setHint(Text hint) {
        this.hint = hint;
    }

    public Button getAddButton() {
        return addButton;
    }

    public void setAddButton(Button addButton) {
        this.addButton = addButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(Button cancelButton) {
        this.cancelButton = cancelButton;
    }

    public Button getEditButton() {
        return editButton;
    }

    public void setEditButton(Button editButton) {
        this.editButton = editButton;
    }
}
