package org.tedros.it.tools.evidence.model;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.collections.TFXCollections;
import org.tedros.fx.control.TButton;
import org.tedros.it.tools.entity.JobEvidenceItem;
import org.tedros.it.tools.evidence.EvidenceScheduler;
import org.tedros.util.TDateUtil;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class EvidenceSelectorView extends BorderPane {

    // Wrapper for search results (transient)
    private static class SelectableEvidence {
        File file;
        String appName;
        long timestamp;

        BooleanProperty selected = new SimpleBooleanProperty(false);
        StringProperty description = new SimpleStringProperty("");

        public SelectableEvidence(File file, String appName, long timestamp) {
            this.file = file;
            this.appName = appName;
            this.timestamp = timestamp;
        }
    }

    private DatePicker datePicker;
    private TextField tfAppName;
    private TextField tfStartTime; // Format HH:mm
    private TextField tfEndTime; // Format HH:mm

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
        this.setPadding(new Insets(10));

        // --- TOP ZONE: Filters ---
        VBox topContainer = new VBox(5);
        HBox filters = new HBox(10);
        filters.setAlignment(Pos.CENTER_LEFT);

        datePicker = new DatePicker(java.time.LocalDate.now());
        tfAppName = new TextField();
        tfAppName.setPromptText("App Name...");

        tfStartTime = new TextField("08:00");
        tfStartTime.setPrefWidth(60);
        tfEndTime = new TextField("18:00");
        tfEndTime.setPrefWidth(60);

        TButton btnSearch = new TButton("Search");
        btnSearch.setOnAction(e -> doSearch());

        filters.getChildren().addAll(
                new Label("Date:"), datePicker,
                new Label("App:"), tfAppName,
                new Label("Time:"), tfStartTime, new Label("-"), tfEndTime,
                btnSearch);

        topContainer.getChildren().addAll(filters, new Separator());
        this.setTop(topContainer);

        // --- CENTER ZONE: Split Pane (Search Result | Selected Items) ---
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(javafx.geometry.Orientation.VERTICAL);

        // 1. Search Results List (TOP HALF)
        evidenceList = FXCollections.observableArrayList();
        listViewSearch = new ListView<>(evidenceList);
        listViewSearch.setCellFactory(param -> new SearchResultCell());

        VBox searchBox = new VBox(5);
        HBox searchHeader = new HBox(10);
        searchHeader.setAlignment(Pos.CENTER_LEFT);

        TButton btnAdd = new TButton("Add Selected \u2193"); // Down arrow
        btnAdd.setOnAction(e -> addSelectedItems());

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        searchHeader.getChildren().addAll(new Label("1. Search Results"), spacer, btnAdd);

        searchBox.getChildren().addAll(searchHeader, listViewSearch);
        VBox.setVgrow(listViewSearch, Priority.ALWAYS);

        // 2. Selected Items List (BOTTOM HALF)
        listViewSelected = new ListView<>(selectedItems);
        listViewSelected.setCellFactory(param -> new SelectedItemCell());

        VBox selectedBox = new VBox(5, new Label("2. Selected Evidence (Final List)"), listViewSelected);
        VBox.setVgrow(listViewSelected, Priority.ALWAYS);

        splitPane.getItems().addAll(searchBox, selectedBox);
        splitPane.setDividerPositions(0.5);

        this.setCenter(splitPane);

        // Initial search
        doSearch();
    }

    private void addSelectedItems() {
        for (SelectableEvidence item : evidenceList) {
            if (item.selected.get()) {
                // Logic: Add to selectedItems list
                JobEvidenceItem newItem = new JobEvidenceItem();
                newItem.setDescription(item.description.get());

                // TODO: Here we should map the file properly.
                // Since we don't have transient fields in JobEvidenceItem for File object,
                // we assume usage of files list or just description for now.
                // Or maybe the user will implement file upload later.
                newItem.setFiles(new ArrayList<>());

                selectedItems.add(newItem);

                // UX: Uncheck item or show it as added?
                // Unchecking is clearer that it was processed.
                item.selected.set(false);
            }
        }
    }

    // --- INNER CLASSES: CELLS ---

    // Cell for Search Results
    private class SearchResultCell extends ListCell<SelectableEvidence> {
        private final CheckBox chkSelect = new CheckBox();
        private final ImageView imageView = new ImageView();
        private final Label lblInfo = new Label();
        private final TextArea txtDesc = new TextArea();
        private final TButton btnOpen = new TButton("Open");
        private final HBox root = new HBox(10, chkSelect, imageView, new VBox(5, lblInfo, txtDesc), btnOpen);

        {
            root.setAlignment(Pos.CENTER_LEFT);
            root.setPadding(new Insets(5));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(60);
            imageView.setFitWidth(100);

            txtDesc.setPromptText("Description...");
            txtDesc.setPrefHeight(50);
            txtDesc.setPrefWidth(200);
            txtDesc.setWrapText(true);

            chkSelect.selectedProperty().addListener((obs, old, val) -> {
                SelectableEvidence item = getItem();
                if (item != null)
                    item.selected.set(val);
            });
            txtDesc.textProperty().addListener((obs, old, val) -> {
                SelectableEvidence item = getItem();
                if (item != null)
                    item.description.set(val);
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
                txtDesc.setText(item.description.get());
                lblInfo.setText(TDateUtil.format(new Date(item.timestamp), "HH:mm") + " - " + item.appName);

                setThumbnail(imageView, item.file);

                setGraphic(root);
            }
        }
    }

    // Cell for Selected Items
    private class SelectedItemCell extends ListCell<JobEvidenceItem> {
        private final TextArea txtDesc = new TextArea();
        private final TButton btnRemove = new TButton("Remove");
        private final VBox root = new VBox(5, new Label("Evidence Item"), txtDesc, btnRemove);

        {
            root.setPadding(new Insets(5));
            txtDesc.setPromptText("Edit Description...");
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
                // Note: We don't have easy access to the image/app name here unless
                // JobEvidenceItem stored it. Displaying description is primary.
                txtDesc.setText(item.getDescription());
                setGraphic(root);
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

    private void setThumbnail(ImageView iv, File f) {
        try {
            iv.setImage(new Image(new FileInputStream(f), 0, 100, true, true));
        } catch (Exception e) {
            iv.setImage(null);
        }
    }

    private void doSearch() {
        evidenceList.clear();
        try {
            java.time.LocalDate ld = datePicker.getValue();
            if (ld == null)
                return;

            String dateStr = ld.toString();
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

            for (File img : imageFiles) {
                String appName = "Unknown";
                long ts = img.lastModified();
                File meta = new File(img.getParentFile(), img.getName().replace(".png", ".properties"));
                if (meta.exists()) {
                    try (FileInputStream fis = new FileInputStream(meta)) {
                        Properties props = new Properties();
                        props.load(fis);
                        appName = props.getProperty("windowTitle", "Unknown");
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
