package org.tedros.it.tools.evidence.component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import org.tedros.common.model.TByteEntity;
import org.tedros.common.model.TFileEntity;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.collections.TFXCollections;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TDatePickerField;
import org.tedros.fx.control.TLabel;
import org.tedros.fx.control.TMaskField;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.entity.JobEvidenceItem;
import org.tedros.util.TDateUtil;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class EvidenceSelectorView extends StackPane {

    // Wrapper for search results (transient)
    private static class SelectableEvidence {
        File file;
        String appName;
        long timestamp;

        BooleanProperty selected = new SimpleBooleanProperty(false);

        public SelectableEvidence(File file, String appName, long timestamp) {
            this.file = file;
            this.appName = appName;
            this.timestamp = timestamp;
        }
    }
    
    private TDatePickerField datePicker;
    private TextField tfAppName;
    private TMaskField tfStartTime; // Format HH:mm
    private TMaskField tfEndTime; // Format HH:mm

    // Search Results
    private ObservableList<SelectableEvidence> evidenceList;
    private ListView<SelectableEvidence> listViewSearch;

    // Selected Items (Final List)
    private ITObservableList<JobEvidenceItem> selectedItems;
    private ListView<JobEvidenceItem> listViewSelected;

    private EvidenceScheduler scheduler;

    public EvidenceSelectorView() {
        this(null);
    }

    public EvidenceSelectorView(ITObservableList<JobEvidenceItem> initialItems) {
        this.scheduler = new EvidenceScheduler();
        this.selectedItems = TFXCollections.iTObservableList();

        if (initialItems != null) {
            this.selectedItems = initialItems;
        }

        initUI();
    }

    public ITObservableList<JobEvidenceItem> tEvidenceItemProperty() {
        return selectedItems;
    }

    private void initUI() {
    	
    	VBox root = new VBox(10);
    	
        this.setPadding(new Insets(10));

        // --- TOP ZONE: Filters ---
        VBox topContainer = new VBox(5);
        HBox filters = new HBox(10);
        filters.setAlignment(Pos.CENTER_LEFT);

        datePicker = new TDatePickerField(new Date());
        datePicker.setLocale(TedrosContext.getLocale());
        
        tfAppName = new TextField();
        tfAppName.setPromptText(TLanguage.getInstance().getString(ItToolsKey.TARGET_APPLICATIONS));

        tfStartTime = new TMaskField("99:99", "08:00");
        tfStartTime.setPrefWidth(60);
        tfEndTime = new TMaskField("99:99", "18:00");
        tfEndTime.setPrefWidth(60);

        TButton btnSearch = new TButton(TLanguage.getInstance().getString(TFxKey.BUTTON_SEARCH));
        btnSearch.setOnAction(e -> doSearch());

        filters.getChildren().addAll(
                new Label(TLanguage.getInstance().getString(TUsualKey.DATE)+":"), datePicker,
                new Label("App:"), tfAppName,
                new Label(TLanguage.getInstance().getString(ItToolsKey.TIME)+":"), tfStartTime, new Label("-"), tfEndTime,
                btnSearch);

        topContainer.getChildren().addAll(filters, new Separator());
        root.getChildren().add(topContainer);

        // --- CENTER ZONE: Split Pane (Search Result | Selected Items) ---
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.setStyle("-fx-background-color: transparent;");
        
        // 1. Search Results List (TOP HALF)
        evidenceList = FXCollections.observableArrayList();
        listViewSearch = new ListView<>(evidenceList);
        listViewSearch.setCellFactory(param -> new SearchResultCell());

        VBox searchBox = new VBox(5);
        HBox searchHeader = new HBox(10);
        searchHeader.setAlignment(Pos.CENTER_LEFT);
        searchHeader.setPadding(new Insets(5));
        searchHeader.setStyle("-fx-background-color: transparent; -fx-border-color: lightgray; -fx-border-width: 0 0 1 0;");
        
        TButton btnAdd = new TButton(TLanguage.getInstance().getString(TFxKey.BUTTON_ADD)); // Down arrow
        btnAdd.setOnAction(e -> addSelectedItems());

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        searchHeader.getChildren().addAll(new TLabel(TLanguage.getInstance().getString(ItToolsKey.SEARCH_RESULTS)), spacer, btnAdd);

        searchBox.getChildren().addAll(searchHeader, listViewSearch);
        VBox.setVgrow(listViewSearch, Priority.ALWAYS);

        // 2. Selected Items List (BOTTOM HALF)
        listViewSelected = new ListView<>(selectedItems);
        listViewSelected.setCellFactory(param -> new SelectedItemCell());
        TLabel lblSelected = new TLabel(TLanguage.getInstance().getString(ItToolsKey.SELECTED_EVIDENCES));
        VBox selectedBox = new VBox(5, lblSelected, listViewSelected);
        selectedBox.setStyle("-fx-background-color: transparent; -fx-border-color: lightgray; -fx-border-width: 0 0 1 0;");
        VBox.setVgrow(listViewSelected, Priority.ALWAYS);
        VBox.setMargin(lblSelected, new Insets(11,5,5,5));

        splitPane.getItems().addAll(searchBox, selectedBox);
        splitPane.setDividerPositions(0.5);

        VBox.setVgrow(splitPane, Priority.ALWAYS);
        root.getChildren().add(splitPane);
        
        super.getChildren().add(root);

        // Initial search
        doSearch();
    }

    private void addSelectedItems() {
        for (SelectableEvidence item : evidenceList) {
            if (item.selected.get()) {
                // Logic: Add to selectedItems list
                JobEvidenceItem newItem = new JobEvidenceItem();
                newItem.setFilePath(item.file.getAbsolutePath());
                newItem.setName(item.appName);
                newItem.setEvidenceDate(new Date(item.timestamp));
                
                try(InputStream fis = new FileInputStream(item.file)) {
					byte[] fileBytes = fis.readAllBytes();
					TByteEntity byteEntity = new TByteEntity();
					byteEntity.setBytes(fileBytes);
					
					TFileEntity fileEntity = new TFileEntity();
					fileEntity.setFileName(item.file.getName());
					fileEntity.setByteEntity(byteEntity);					
					newItem.setFile(fileEntity);
					selectedItems.add(newItem);
				} catch (Exception e) {
					e.printStackTrace();
				}                

                // UX: Uncheck item or show it as added?
                // Unchecking is clearer that it was processed.
                item.selected.setValue(false);
            }
        }
    }
    

    // --- INNER CLASSES: CELLS ---

    // Cell for Search Results
    private class SearchResultCell extends ListCell<SelectableEvidence> {
        private final CheckBox chkSelect = new CheckBox();
        private final ImageView imageView = new ImageView();
        private final Label lblInfo = new Label();
        
        private final TButton btnOpen = new TButton(TLanguage.getInstance().getString(TFxKey.BUTTON_OPEN));
        private final HBox root = new HBox(10, chkSelect, imageView, new VBox(5, lblInfo, btnOpen));

        {
            root.setAlignment(Pos.CENTER_LEFT);
            root.setPadding(new Insets(5));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(60);
            imageView.setFitWidth(100);
            
            chkSelect.selectedProperty().addListener((obs, old, val) -> {
                SelectableEvidence item = getItem();
                if (item != null)
                    item.selected.set(val);
            });
            
            btnOpen.setOnAction(e -> {
                SelectableEvidence item = getItem();
                if (item != null)
                    openFile(item.file);
            });
        }

        @Override
        protected void updateItem(SelectableEvidence item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                chkSelect.setSelected(item.selected.get());
                lblInfo.setText(TDateUtil.format(new Date(item.timestamp), "HH:mm") + " - " + item.appName);
                setThumbnail(imageView, item.file);
                setGraphic(root);
            }
        }
        
        private void setThumbnail(ImageView iv, File f) {
            try {
                iv.setImage(new Image(new FileInputStream(f), 0, 100, true, true));
            } catch (Exception e) {
                iv.setImage(null);
            }
        }
    }

    // Cell for Selected Items
    private class SelectedItemCell extends ListCell<JobEvidenceItem> {
        private final ImageView imageView = new ImageView();
        private final Label lblInfo = new Label();
        private final TextArea txtDesc = new TextArea();
        private final TButton btnRemove = new TButton(TLanguage.getInstance().getString(TFxKey.BUTTON_REMOVE));
        private final HBox root = new HBox(10, new VBox(5, imageView, btnRemove), new VBox(5, lblInfo, txtDesc));

        {
            root.setPadding(new Insets(5));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(60);
            imageView.setFitWidth(100);
            txtDesc.setPromptText(TLanguage.getInstance().getString(ItToolsKey.PROMPT_EDIT_DESCRIPTION));
            txtDesc.setPrefHeight(50);
            txtDesc.setWrapText(true);

            txtDesc.textProperty().addListener((obs, old, val) -> {
                JobEvidenceItem item = getItem();
                if (item != null)
                    item.setDescription(val);
            });

            btnRemove.setOnAction(e -> {
                JobEvidenceItem item = getItem();
                if (item != null)
                    selectedItems.remove(item);
            });
        }

        @Override
        protected void updateItem(JobEvidenceItem item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
            	setThumbnail(imageView, item.getFile());
            	lblInfo.setText(TDateUtil.format(item.getEvidenceDate(), "HH:mm") + " - " + item.getName());
                txtDesc.setText(item.getDescription());
                setGraphic(root);
            }
        }
        
        private void setThumbnail(ImageView iv, TFileEntity fileEntity) {
            try {
                iv.setImage(new Image(new ByteArrayInputStream(fileEntity.getByteEntity().getBytes()), 0, 100, true, true));
            } catch (Exception e) {
                iv.setImage(null);
            }
        }
    }

    private void openFile(File f) {
        try {
            java.awt.Desktop.getDesktop().open(f);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void doSearch() {
        evidenceList.clear();
        try {
            Date date = datePicker.getValue();
            if (date == null)
                return;

            String dateStr = TDateUtil.format(date, "yyyy-MM-dd");
            File dayDir = new File(scheduler.getOutputDir(), dateStr);

            if (!dayDir.exists() || !dayDir.isDirectory())
                return;

            String appFilter = tfAppName.getText().toLowerCase().trim();
            long startMs = parseTime(dateStr, tfStartTime.getText(), true);
            long endMs = parseTime(dateStr, tfEndTime.getText(), false);

            File[] imageFiles = dayDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
            if (imageFiles == null)
                return;

            Arrays.sort(imageFiles, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));

            String unknownApp = TLanguage.getInstance().getString(ItToolsKey.UNKNOWN_APP);
            
            for (File img : imageFiles) {
                String appName = unknownApp;
                long ts = img.lastModified();
                File meta = new File(img.getParentFile(), img.getName().replace(".png", ".properties"));
                if (meta.exists()) {
                    try (FileInputStream fis = new FileInputStream(meta)) {
                        Properties props = new Properties();
                        props.load(fis);
                        appName = props.getProperty("windowTitle", unknownApp);
                        String tsStr = props.getProperty("timestamp");
                        if (tsStr != null)
                            ts = Long.parseLong(tsStr);
                    } catch (Exception ex) {
                    }
                }

                if (!appFilter.isEmpty() && !appName.toLowerCase().contains(appFilter))
                    continue;
                if (ts < startMs || ts > endMs)
                    continue;

                evidenceList.add(new SelectableEvidence(img, appName, ts));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long parseTime(String dateStr, String timeStr, boolean start) {
        try {
            String fullStr = dateStr + " " + timeStr;
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.parse(fullStr).getTime();
        } catch (Exception e) {
            return start ? Long.MIN_VALUE : Long.MAX_VALUE;
        }
    }    
    
}
