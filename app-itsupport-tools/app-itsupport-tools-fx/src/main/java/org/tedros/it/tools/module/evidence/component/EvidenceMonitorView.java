package org.tedros.it.tools.module.evidence.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.control.PopOver;
import org.tedros.fx.TFxKey;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TLabel;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.util.TDateUtil;
import org.tedros.util.TFileUtil;
import org.tedros.util.TLoggerUtil;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class EvidenceMonitorView extends StackPane implements EvidenceCaptureListener {
	
	private static final String FX_FONT_WEIGHT_BOLD = "-fx-font-weight: bold;";
	private static final String ADD_NEW_APP_TITLE = TLanguage.getInstance().getString(ItToolsKey.ADD_NEW_APP_TITLE);	
	private static final String START_MONITORING = TLanguage.getInstance().getString(ItToolsKey.START_MONITORING);
	private static final String STATUS_RUNNING = TLanguage.getInstance().getString(ItToolsKey.STATUS_RUNNING);
	private static final String STATUS_STOPPED = TLanguage.getInstance().getString(ItToolsKey.STATUS_STOPPED);
	private static final String STOP_MONITORING = TLanguage.getInstance().getString(ItToolsKey.STOP_MONITORING);
	private static final String TARGET_APPLICATIONS = TLanguage.getInstance().getString(ItToolsKey.TARGET_APPLICATIONS);
	private static final String UNKNOWN_APP = TLanguage.getInstance().getString(ItToolsKey.UNKNOWN_APP);
	private static final String SAVE_TO = TLanguage.getInstance().getString(ItToolsKey.SAVED_TO);
	private static final String OPEN = TLanguage.getInstance().getString(TFxKey.BUTTON_OPEN);

	private EvidenceScheduler scheduler;
	private ObservableList<String> targetAppsList;
	private ListView<String> lvTargetApps;
	
	// New components for Virtualized Gallery
	private ObservableList<EvidenceItem> evidenceList;
	private ListView<EvidenceItem> lvGallery;
	
	private TLabel lblStatus;
	private Button btnStartStop;
	private TLabel lblOutputDir;
	
	public EvidenceMonitorView() {
		this.scheduler = new EvidenceScheduler();
		// Register ourselves as a listener
		this.scheduler.addListener(this);
		
		initUI();
		this.loadTodayEvidence();
	}

	private void initUI() {
		BorderPane mainLayout = new BorderPane();
		mainLayout.setPadding(new Insets(10));
		
		// --- TOP: Controls ---
		HBox controlsBox = new HBox(10);
		controlsBox.setAlignment(Pos.CENTER_LEFT);
		controlsBox.setPadding(new Insets(0, 0, 10, 0));
		
		btnStartStop = new Button(START_MONITORING);
		btnStartStop.setStyle("-fx-font-weight: bold; -fx-base: #4CAF50;"); // Greenish
		btnStartStop.setOnAction(e -> toggleMonitoring());
		
		lblStatus = new TLabel(STATUS_STOPPED);
		
		lblOutputDir = new TLabel(scheduler.getOutputDir());
		lblOutputDir.setStyle(FX_FONT_WEIGHT_BOLD);
		lblOutputDir.setOnMouseEntered(e-> lblOutputDir.setCursor(Cursor.HAND));
		
		lblOutputDir.setOnMouseExited(e-> lblOutputDir.setCursor(Cursor.DEFAULT));
		
		lblOutputDir.setOnMouseClicked(e->{
			Thread thread = new Thread(() ->
			Platform.runLater(() ->{
                try {
					if(!TFileUtil.open(new File(scheduler.getOutputDir()))) {
						Label label = new Label(TLanguage.getInstance(null).getString("#{tedros.fxapi.message.os.not.support.operation}"));
						label.setId("t-label");
						label.setStyle(	"-fx-font: Arial; "+
										"-fx-font-size: 1.0em; "+
										"-fx-font-weight: bold; "+
										"-fx-font-smoothing-type:lcd; "+
										"-fx-text-fill: #000000; "+
										"-fx-padding: 2 5 5 2; ");
						
						PopOver p = new PopOver(label);
						p.show(lblOutputDir);
					}
				} catch (IOException ex) {
					TLoggerUtil.error(EvidenceMonitorView.class, ex.getMessage(), ex);
				}
	          }));
		thread.setDaemon(true);
		thread.start();
		});
		
		controlsBox.getChildren().addAll(btnStartStop, new Separator(), new TLabel(SAVE_TO), lblOutputDir);
		mainLayout.setTop(controlsBox);
		
		// --- LEFT: Target Apps & Settings ---
		VBox leftSidebar = new VBox(10);
		leftSidebar.setPrefWidth(250);
		leftSidebar.setPadding(new Insets(0, 10, 0, 0));
		
		TLabel lblApps = new TLabel(TARGET_APPLICATIONS);
		lblApps.setStyle(FX_FONT_WEIGHT_BOLD);
		
		targetAppsList = FXCollections.observableArrayList(scheduler.getTargetApps());
		lvTargetApps = new ListView<>(targetAppsList);
		VBox.setVgrow(lvTargetApps, Priority.ALWAYS);
		
		TextField tfNewApp = new TextField();
		tfNewApp.setPromptText(ADD_NEW_APP_TITLE);
		
		HBox appButtons = new HBox(5);
		TButton btnAdd = new TButton(TLanguage.getInstance().getString(TFxKey.BUTTON_ADD)); 
		TButton btnRemove = new TButton(TLanguage.getInstance().getString(TFxKey.BUTTON_REMOVE));
		
		btnAdd.setOnAction(e -> {
			String newVal = tfNewApp.getText().trim();
			if(!newVal.isEmpty() && !targetAppsList.contains(newVal)) {
				scheduler.addTargetApp(newVal);
				targetAppsList.add(newVal);
				tfNewApp.clear();
			}
		});
		
		btnRemove.setOnAction(e -> {
			String selected = lvTargetApps.getSelectionModel().getSelectedItem();
			if(selected != null) {
				scheduler.removeTargetApp(selected);
				targetAppsList.remove(selected);
			}
		});
		
		appButtons.getChildren().addAll(btnAdd, btnRemove);
		
		leftSidebar.getChildren().addAll(lblApps, tfNewApp, appButtons, lvTargetApps);
		mainLayout.setLeft(leftSidebar);
		
		// --- CENTER: Gallery of Captured Evidences (Virtualized) ---
		VBox centerArea = new VBox(10);
				
		evidenceList = FXCollections.observableArrayList();
		lvGallery = new ListView<>(evidenceList);
		VBox.setVgrow(lvGallery, Priority.ALWAYS);
		
		// Custom Cell Factory for efficient rendering
		lvGallery.setCellFactory(param -> new ListCell<EvidenceItem>() {
			private final ImageView imageView = new ImageView();
			private final Label lblTitle = new Label();
			private final Label lblPath = new Label();
			private final Label lblTime = new Label();
			private final TButton btnOpen = new TButton(OPEN);
			private final VBox infoBox = new VBox(5, lblTitle, lblPath, lblTime);
			private final HBox root = new HBox(10, imageView, infoBox); 
			private final HBox container = new HBox(root, new HBox() {{ HBox.setHgrow(this, Priority.ALWAYS); }}, btnOpen);

			{	
				root.setAlignment(Pos.CENTER_LEFT);
				container.setAlignment(Pos.CENTER_LEFT);
				container.setPadding(new Insets(5));
				
				imageView.setPreserveRatio(true);
				imageView.setFitHeight(80);
				imageView.setFitWidth(120);
				
				lblTitle.setStyle(FX_FONT_WEIGHT_BOLD);
				
				btnOpen.setOnAction(e -> {
					EvidenceItem item = getItem();
					if(item != null) {
						try {
							java.awt.Desktop.getDesktop().open(item.getFile());
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
			}

			@Override
			protected void updateItem(EvidenceItem item, boolean empty) {
				super.updateItem(item, empty);
				
				if (empty || item == null) {
					setText(null);
					setGraphic(null);
				} else {
					
					lblTitle.setText("App: " + item.getWindowTitle());
					lblPath.setText(item.getFile().getName());
					lblTime.setText(TDateUtil.formatMediumDateTime(item.getTimestamp(), TedrosContext.getLocale()));
					
					// Efficient Image Loading: Load thumbnail size only!
					try {
						// Load requested height=100, preserve ratio, smooth
						Image thumb = new Image(new FileInputStream(item.getFile()), 0, 100, true, true);
						imageView.setImage(thumb);
					} catch (FileNotFoundException e) {
						imageView.setImage(null); // Or placeholder
					}
					
					setGraphic(container);
				}
			}
		});
		
		centerArea.getChildren().addAll(lblStatus, lvGallery);
		
		mainLayout.setCenter(centerArea);
		
		this.getChildren().add(mainLayout);
	}

	private void toggleMonitoring() {
		if(scheduler.isRunning()) {
			scheduler.stopMonitoring();
			btnStartStop.setText(START_MONITORING);
			btnStartStop.setStyle("-fx-font-weight: bold; -fx-base: #4CAF50;");
			lblStatus.setText(STATUS_STOPPED);
		} else {
			scheduler.startMonitoring();
			btnStartStop.setText(STOP_MONITORING);
			btnStartStop.setStyle("-fx-font-weight: bold; -fx-base: #F44336;"); // Reddish
			lblStatus.setText(STATUS_RUNNING);
		}
	}
	

	@Override
	public void onCapture(String windowTitle, File evidenceFile) {
		// This method is called on JavaFX Thread thanks to Platform.runLater in Scheduler
		// Add to ObservableList, ListView updates automatically
		evidenceList.add(0, new EvidenceItem(windowTitle, evidenceFile));
		
		// Update status label with flash
		String originalText = lblStatus.getText();
		lblStatus.setText(TLanguage.getInstance().getFormatedString(ItToolsKey.ATTRIBUTES_CAPTURED, windowTitle));
		lblStatus.setStyle(FX_FONT_WEIGHT_BOLD);
		
		// Simple timer to revert status color (UI animation could be better but keeping it simple)
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				javafx.application.Platform.runLater(() -> {
					lblStatus.setText(originalText);
					lblStatus.setStyle("-fx-font-weight: normal;");
					//lblStatus.setStyle("-fx-text-fill: black;");
				});
			}
		}, 2000);
	}
	
	// Ensure we stop the thread if the view is destroyed/removed potentially
	public void shutdown() {
		if(scheduler != null) {
			scheduler.destroy();
		}
	}

	private void loadTodayEvidence() {
		try {
			String workingDayFolder = org.tedros.util.TDateUtil.format(new java.util.Date(), "yyyy-MM-dd");
			File dayDir = new File(scheduler.getOutputDir(), workingDayFolder);
			
			if (dayDir.exists() && dayDir.isDirectory()) {
				File[] files = dayDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
				if (files != null) {
					// Sort by last modified (newest first)
					java.util.Arrays.sort(files, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));
					
					for (File f : files) {
						String title = getWindowTitle(f);
						evidenceList.add(new EvidenceItem(title, f));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getWindowTitle(File f) {
		String title = UNKNOWN_APP;
		try {
			File metaFile = new File(f.getParentFile(), f.getName().replace(".png", ".properties"));
			if(metaFile.exists()) {
				java.util.Properties props = new java.util.Properties();
				try(FileInputStream fis = new FileInputStream(metaFile)){
					props.load(fis);
					title = props.getProperty("windowTitle", UNKNOWN_APP);
				}
			}
		} catch(Exception ex) {
			// Ignore metadata read errors
		}
		return title;
	}
}
