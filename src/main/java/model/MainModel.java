package model;

import entity.Task;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.Duration;
import java.util.List;

public class MainModel {
    private static TreeView treeView;
    private BorderPane pane;
    private HBox header;
    private ToggleButton playButton;
    private Button addButton;
    private Button editButton;
    private Button removeButton;
    private Button completeButton;
    private Button statisticsButton;
    private Label timer;
    private Label suggestedTime;
    private StringProperty timerText = new SimpleStringProperty();
    private Timeline timeline;
    private long timeElapsed;

    public MainModel() {
        pane = new BorderPane();

        header = new HBox();
        header.setSpacing(25);
        header.setId("header");
        playButton = new ToggleButton();
        playButton.setId("play");
        timer = new Label();
        timer.setTooltip(new Tooltip("Elapsed time"));
        timer.getStyleClass().add("timer");
        suggestedTime = new Label();
        suggestedTime.setTooltip(new Tooltip("Suggested time"));
        suggestedTime.getStyleClass().add("timer");
        setTimerText(0);
        timer.setText(getTimerText());
        suggestedTime.setText(getTimerText());
        playButton.setSelected(false);
        header.getChildren().addAll(playButton, timer, suggestedTime);

        VBox mainSection = new VBox();
        mainSection.setId("main");

        TreeItem<Task> rootNode =
                new TreeItem<>(new Task("My tasks", Duration.ZERO, null));
        treeView = new TreeView<>(rootNode);
        treeView.prefHeightProperty().bind(mainSection.heightProperty());
        treeView.setEditable(true);

        mainSection.getChildren().add(treeView);

        VBox sideBar = new VBox();
        sideBar.setId("sidebar");
        sideBar.setSpacing(12);
        addButton = new Button("Add");
        editButton = new Button("Edit");
        removeButton = new Button("Remove");
        completeButton = new Button("Complete");
        statisticsButton = new Button("Stats");
        sideBar.getChildren().addAll(addButton, editButton, removeButton, completeButton, statisticsButton);

        VBox leftBar = new VBox();
        leftBar.setId("leftbar");

        HBox footer = new HBox();
        footer.setId("footer");

        pane.setTop(header);
        pane.setCenter(mainSection);
        pane.setRight(sideBar);
        pane.setBottom(footer);
        pane.setLeft(leftBar);
    }

    public static TreeView getTreeView() {
        return treeView;
    }

    public static void setTreeView(TreeView treeView) {
        MainModel.treeView = treeView;
    }

    public BorderPane getPane() {
        return pane;
    }

    public void setPane(BorderPane pane) {
        this.pane = pane;
    }

    public HBox getHeader() {
        return header;
    }

    public void setHeader(HBox header) {
        this.header = header;
    }

    public ToggleButton getPlayButton() {
        return playButton;
    }

    public void setPlayButton(ToggleButton playButton) {
        this.playButton = playButton;
    }

    public Button getAddButton() {
        return addButton;
    }

    public void setAddButton(Button addButton) {
        this.addButton = addButton;
    }

    public Button getEditButton() {
        return editButton;
    }

    public void setEditButton(Button editButton) {
        this.editButton = editButton;
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    public void setRemoveButton(Button removeButton) {
        this.removeButton = removeButton;
    }

    public Button getCompleteButton() {
        return completeButton;
    }

    public void setCompleteButton(Button completeButton) {
        this.completeButton = completeButton;
    }

    public Button getStatisticsButton() {
        return statisticsButton;
    }

    public void setStatisticsButton(Button statisticsButton) {
        this.statisticsButton = statisticsButton;
    }

    public Label getTimer() {
        return timer;
    }

    public void setTimer(Label timer) {
        this.timer = timer;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public Label getSuggestedTime() {
        return suggestedTime;
    }

    public void setSuggestedTime(Label suggestedTime) {
        this.suggestedTime = suggestedTime;
    }

    public Node createIcon() {
        return new ImageView(new Image(getClass().getResourceAsStream("/icons/CheckMarkCircle.png")));
    }

    public static void parseTreeView(List<TreeItem<Task>> root, List<Task> parent) {
        for (TreeItem<Task> item: root) {
            Task task = item.getValue();
            if (!item.getChildren().isEmpty()) {
                parseTreeView(item.getChildren(), task.getSubTaskList());
            }
            if (!parent.contains(task)) {
                parent.add(task);
            }
        }
    }



    public void loadTreeView(List<Task> tasks, TreeItem<Task> parent) {
        for (Task task: tasks) {
            TreeItem<Task> item = new TreeItem<>(task);
            if (task.isFinished()) {
                item.setGraphic(createIcon());
            }
            parent.getChildren().add(item);
            if (task.getSubTaskList().size() != 0) {
                loadTreeView(task.getSubTaskList(), item);
            }
        }
    }



    public void setTimerText(long remainingSeconds) {
        long minutes = remainingSeconds / 60;
        long seconds = remainingSeconds % 60;
        this.timerText.set(String.format("%02d:%02d", minutes, seconds));
    }

    public String getTimerText() {
        return timerText.get();
    }

    public StringProperty timerTextProperty() {
        return timerText;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public void tick() {
        timeElapsed++;
    }
}
