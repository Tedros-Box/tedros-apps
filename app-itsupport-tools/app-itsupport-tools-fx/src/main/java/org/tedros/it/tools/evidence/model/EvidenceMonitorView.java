package org.tedros.it.tools.evidence.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.tedros.it.tools.evidence.EvidenceCaptureListener;
import org.tedros.it.tools.evidence.EvidenceScheduler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

public class EvidenceMonitorView extends StackPane implements EvidenceCaptureListener {

    private EvidenceScheduler scheduler;
    private ObservableList<String> targetAppsList;
    private ListView<String> lvTargetApps;
    private VBox galleryBox;
    private Label lblStatus;
    private Button btnStartStop;
    private TextField tfOutputDir;

    public EvidenceMonitorView() {
        this.scheduler = new EvidenceScheduler();
        // Register ourselves as a listener
        this.scheduler.addListener(this);

        initUI();
    }

    private void initUI() {
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));

        // --- TOP: Controls ---
        HBox controlsBox = new HBox(10);
        controlsBox.setAlignment(Pos.CENTER_LEFT);
        controlsBox.setPadding(new Insets(0, 0, 10, 0));

        btnStartStop = new Button("Start Monitoring");
        btnStartStop.setStyle("-fx-font-weight: bold; -fx-base: #4CAF50;"); // Greenish
        btnStartStop.setOnAction(e -> toggleMonitoring());

        lblStatus = new Label("Status: Stopped");

        Button btnChooseDir = new Button("Output Folder...");
        tfOutputDir = new TextField(scheduler.getOutputDir());
        tfOutputDir.setEditable(false);
        tfOutputDir.setPrefWidth(300);

        btnChooseDir.setOnAction(e -> chooseDirectory());

        controlsBox.getChildren().addAll(btnStartStop, lblStatus, new Separator(), new Label("Save to:"), tfOutputDir,
                btnChooseDir);
        mainLayout.setTop(controlsBox);

        // --- LEFT: Target Apps & Settings ---
        VBox leftSidebar = new VBox(10);
        leftSidebar.setPrefWidth(250);
        leftSidebar.setPadding(new Insets(0, 10, 0, 0));

        Label lblApps = new Label("Target Applications");
        lblApps.setStyle("-fx-font-weight: bold;");

        targetAppsList = FXCollections.observableArrayList(scheduler.getTargetApps());
        lvTargetApps = new ListView<>(targetAppsList);
        VBox.setVgrow(lvTargetApps, Priority.ALWAYS);

        TextField tfNewApp = new TextField();
        tfNewApp.setPromptText("Add new app title...");

        HBox appButtons = new HBox(5);
        Button btnAdd = new Button("Add");
        Button btnRemove = new Button("Remove");

        btnAdd.setOnAction(e -> {
            String newVal = tfNewApp.getText().trim();
            if (!newVal.isEmpty() && !targetAppsList.contains(newVal)) {
                scheduler.addTargetApp(newVal);
                targetAppsList.add(newVal);
                tfNewApp.clear();
            }
        });

        btnRemove.setOnAction(e -> {
            String selected = lvTargetApps.getSelectionModel().getSelectedItem();
            if (selected != null) {
                scheduler.removeTargetApp(selected);
                targetAppsList.remove(selected);
            }
        });

        appButtons.getChildren().addAll(btnAdd, btnRemove);

        leftSidebar.getChildren().addAll(lblApps, tfNewApp, appButtons, lvTargetApps);
        mainLayout.setLeft(leftSidebar);

        // --- CENTER: Gallery of Captured Evidences ---
        VBox centerArea = new VBox(10);
        Label lblGallery = new Label("Captured Evidence Gallery (This Session)");
        lblGallery.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        galleryBox = new VBox(10); // Vertical stack of captured items
        ScrollPane scrollPane = new ScrollPane(galleryBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        centerArea.getChildren().addAll(lblGallery, scrollPane);

        mainLayout.setCenter(centerArea);

        this.getChildren().add(mainLayout);
    }

    private void toggleMonitoring() {
        if (scheduler.isRunning()) {
            scheduler.stopMonitoring();
            btnStartStop.setText("Start Monitoring");
            btnStartStop.setStyle("-fx-font-weight: bold; -fx-base: #4CAF50;");
            lblStatus.setText("Status: Stopped");
        } else {
            scheduler.startMonitoring();
            btnStartStop.setText("Stop Monitoring");
            btnStartStop.setStyle("-fx-font-weight: bold; -fx-base: #F44336;"); // Reddish
            lblStatus.setText("Status: Running...");
        }
    }

    private void chooseDirectory() {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Select Evidence Output Directory");
        if (new File(scheduler.getOutputDir()).exists()) {
            dc.setInitialDirectory(new File(scheduler.getOutputDir()));
        }

        File selected = dc.showDialog(this.getScene().getWindow());
        if (selected != null) {
            scheduler.setOutputDir(selected.getAbsolutePath());
            tfOutputDir.setText(selected.getAbsolutePath());
        }
    }

    @Override
    public void onCapture(String windowTitle, File evidenceFile) {
        // This method is called on JavaFX Thread thanks to Platform.runLater in
        // Scheduler
        addEvidenceToGallery(windowTitle, evidenceFile);

        // Update status label with flash
        String originalText = lblStatus.getText();
        lblStatus.setText("Attributes CAPTURED for " + windowTitle + "!");
        lblStatus.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");

        // Simple timer to revert status color (UI animation could be better but keeping
        // it simple)
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                javafx.application.Platform.runLater(() -> {
                    lblStatus.setText(originalText);
                    lblStatus.setStyle("-fx-text-fill: black;");
                });
            }
        }, 2000);
    }

    private void addEvidenceToGallery(String title, File file) {
        HBox item = new HBox(10);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-padding: 5; -fx-background-color: #f9f9f9;");
        item.setPrefHeight(100);

        try {
            // Thumbnail
            Image img = new Image(new FileInputStream(file));
            ImageView iv = new ImageView(img);
            iv.setPreserveRatio(true);
            iv.setFitHeight(80);
            iv.setFitWidth(120);

            VBox info = new VBox(5);
            Label lblTitle = new Label("App: " + title);
            lblTitle.setStyle("-fx-font-weight: bold;");
            Label lblPath = new Label(file.getName());
            Label lblTime = new Label(new java.util.Date().toString());

            info.getChildren().addAll(lblTitle, lblPath, lblTime);

            Button btnOpen = new Button("Open");
            btnOpen.setOnAction(e -> {
                try {
                    java.awt.Desktop.getDesktop().open(file);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            // Spacer
            HBox spacer = new HBox();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            item.getChildren().addAll(iv, info, spacer, btnOpen);

            // Add to top
            galleryBox.getChildren().add(0, item);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Ensure we stop the thread if the view is destroyed/removed potentially
    public void shutdown() {
        if (scheduler != null) {
            scheduler.stopMonitoring();
        }
    }
}
