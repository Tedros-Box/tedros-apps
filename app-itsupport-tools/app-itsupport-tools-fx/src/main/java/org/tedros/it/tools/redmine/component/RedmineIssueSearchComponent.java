package org.tedros.it.tools.redmine.component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TDatePickerField;
import org.tedros.fx.control.TLabel;
import org.tedros.it.tools.redmine.ai.model.FilterCondition;
import org.tedros.it.tools.redmine.ai.model.RedmineFilterField;
import org.tedros.it.tools.redmine.ai.model.RedmineIssueFilter;
import org.tedros.it.tools.redmine.api.model.TIssueEvidenceInfo;
import org.tedros.it.tools.redmine.api.model.TIssueStatus;
import org.tedros.it.tools.redmine.api.model.TRedmineUser;
import org.tedros.it.tools.redmine.gateway.RedmineApiGateway;
import org.tedros.util.TDateUtil;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RedmineIssueSearchComponent extends VBox {

    private RedmineApiGateway gateway;

    // Filters
    private TDatePickerField startDatePicker;
    private TDatePickerField endDatePicker;
    private ComboBox<TIssueStatus> statusComboBox;
    private ComboBox<TRedmineUser> assignedToComboBox;
    private ComboBox<TRedmineUser> authorComboBox;
    private TextField issueIdField;

    private TButton searchButton;
    private TButton sendForAnalysisButton;
    private ProgressIndicator progressIndicator;

    // Results
    private TableView<IssueViewModel> resultsTable;
    private ObservableList<IssueViewModel> issueData = FXCollections.observableArrayList();

    public RedmineIssueSearchComponent(RedmineApiGateway gateway) {
        this.gateway = gateway;
        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.setFillWidth(true);

        initializeUI();
        loadInitialData();
    }

    @SuppressWarnings("unchecked")
    private void initializeUI() {
        // 1. Top Section - Filters
        GridPane filtersGrid = new GridPane();
        filtersGrid.setHgap(10);
        filtersGrid.setVgap(10);
        filtersGrid.setPadding(new Insets(10));
        filtersGrid.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5; -fx-padding: 10;");

        // Date Range
        filtersGrid.add(new TLabel("Start Date:"), 0, 0);
        startDatePicker = new TDatePickerField();
        filtersGrid.add(startDatePicker, 1, 0);

        filtersGrid.add(new TLabel("End Date:"), 2, 0);
        endDatePicker = new TDatePickerField();
        filtersGrid.add(endDatePicker, 3, 0);

        // Status
        filtersGrid.add(new TLabel("Status:"), 0, 1);
        statusComboBox = new ComboBox<>();
        statusComboBox.setMaxWidth(Double.MAX_VALUE);
        filtersGrid.add(statusComboBox, 1, 1);

        // Assigned To
        filtersGrid.add(new TLabel("Assigned To:"), 2, 1);
        assignedToComboBox = new ComboBox<>();
        assignedToComboBox.setMaxWidth(Double.MAX_VALUE);
        filtersGrid.add(assignedToComboBox, 3, 1);

        // Author
        filtersGrid.add(new TLabel("Author:"), 0, 2);
        authorComboBox = new ComboBox<>();
        authorComboBox.setMaxWidth(Double.MAX_VALUE);
        filtersGrid.add(authorComboBox, 1, 2);

        // Spent Time
        // Issue Id
        filtersGrid.add(new TLabel("Issue Id:"), 2, 2);
        issueIdField = new TextField();
        issueIdField.setPromptText("Issue ID");
        filtersGrid.add(issueIdField, 3, 2);

        searchButton = new TButton("Search Issues");
        searchButton.setOnAction(e -> searchIssues());
        filtersGrid.add(searchButton, 4, 1);

        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
        progressIndicator.setMaxSize(20, 20);
        filtersGrid.add(progressIndicator, 4, 2);

        this.getChildren().add(filtersGrid);

        // 2. Center Section - Results Table
        resultsTable = new TableView<>();
        resultsTable.setEditable(true);
        resultsTable.setItems(issueData);
        VBox.setVgrow(resultsTable, Priority.ALWAYS);

        // --- IMPORTANTE: Define a política para preencher a largura total ---
        resultsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // --- COLUNAS ---

        // 1. Select (Tamanho fixo pequeno)
        TableColumn<IssueViewModel, Boolean> selectCol = new TableColumn<>("Select");
        selectCol.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        selectCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectCol));
        selectCol.setEditable(true);
        selectCol.setMinWidth(40);
        selectCol.setMaxWidth(40);
        selectCol.setPrefWidth(40);
        selectCol.setResizable(false); // Impede o usuário de redimensionar esta coluna

        // 2. ID (Tamanho fixo numérico)
        TableColumn<IssueViewModel, Long> idCol = new TableColumn<>("Issue ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setMinWidth(60);
        idCol.setMaxWidth(70);
        idCol.setPrefWidth(60);
        idCol.setResizable(false);

        // 3. Type (Tamanho controlado)
        TableColumn<IssueViewModel, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("trackerName"));
        typeCol.setMinWidth(100);
        typeCol.setMaxWidth(120); // Pode crescer um pouco, mas não muito

        // 4. Status (Tamanho controlado)
        TableColumn<IssueViewModel, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("statusName"));
        statusCol.setMinWidth(100);
        statusCol.setMaxWidth(120);

        // 5. Title (PRINCIPAL: Ocupa todo o espaço restante)
        TableColumn<IssueViewModel, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        titleCol.setMinWidth(200); // Garante que o texto não suma
        titleCol.setMaxWidth(Double.MAX_VALUE); // Permite crescer infinitamente

        // 6. % Done (Fixo pequeno)
        TableColumn<IssueViewModel, Integer> doneCol = new TableColumn<>("% Done");
        doneCol.setCellValueFactory(new PropertyValueFactory<>("doneRatio"));
        doneCol.setMinWidth(60);
        doneCol.setMaxWidth(60);
        doneCol.setResizable(false);

        // 7. Start Date (Fixo para caber a data)
        TableColumn<IssueViewModel, String> startCol = new TableColumn<>("Start Date");
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        startCol.setMinWidth(100);
        startCol.setMaxWidth(200);

        // 8. Due Date (Fixo para caber a data)
        TableColumn<IssueViewModel, String> dueCol = new TableColumn<>("Due Date");
        dueCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        dueCol.setMinWidth(100);
        dueCol.setMaxWidth(200);

        // 9. Assigned To (Fixo ou ligeiramente flexível)
        TableColumn<IssueViewModel, String> assignedCol = new TableColumn<>("Assigned To");
        assignedCol.setCellValueFactory(new PropertyValueFactory<>("assigneeName"));
        assignedCol.setMinWidth(100);
        assignedCol.setMaxWidth(250);

        // Adiciona todas as colunas
        resultsTable.getColumns().addAll(selectCol, idCol, typeCol, statusCol, titleCol, doneCol, startCol, dueCol,
                assignedCol);

        this.getChildren().add(resultsTable);

        // 3. Bottom Section - Actions
        HBox actionsBox = new HBox(10);
        actionsBox.setAlignment(Pos.CENTER_RIGHT);
        actionsBox.setPadding(new Insets(10, 0, 0, 0));

        sendForAnalysisButton = new TButton("Send for Analysis");
        sendForAnalysisButton.setStyle("-fx-font-weight: bold;");
        // Logic for this button is not required yet according to task

        actionsBox.getChildren().add(sendForAnalysisButton);
        this.getChildren().add(actionsBox);
    }

    private void loadInitialData() {
        progressIndicator.setVisible(true);
        CompletableFuture.runAsync(() -> {
            try {
                // Initialize gateway cache if needed (gateway logic)
                gateway.loadCustomFieldMetadata();

                List<TIssueStatus> statuses = gateway.listIssueStatuses();
                List<TRedmineUser> users = gateway.listUsers();

                Platform.runLater(() -> {
                    statusComboBox.getItems().setAll(statuses);
                    assignedToComboBox.getItems().setAll(users);
                    authorComboBox.getItems().setAll(users);
                });
            } catch (Exception e) {
                Platform.runLater(() -> showAlert("Error loading initial data", e.getMessage()));
            } finally {
                Platform.runLater(() -> progressIndicator.setVisible(false));
            }
        });
    }

    private void searchIssues() {
        progressIndicator.setVisible(true);
        searchButton.setDisable(true);
        issueData.clear();

        RedmineIssueFilter filter = buildFilter();

        CompletableFuture.runAsync(() -> {
            try {
                Map<String, FilterCondition> filtersMap = RedmineFilterField.fromObject(filter);
                List<TIssueEvidenceInfo> results = gateway.getIssuesByFilters(filtersMap);

                Platform.runLater(() -> {
                    if (results != null) {
                        for (TIssueEvidenceInfo info : results) {
                            issueData.add(new IssueViewModel(info));
                        }
                    }
                });
            } catch (Exception e) {
                Platform.runLater(() -> showAlert("Error searching issues", e.getMessage()));
            } finally {
                Platform.runLater(() -> {
                    progressIndicator.setVisible(false);
                    searchButton.setDisable(false);
                });
            }
        });
    }

    private RedmineIssueFilter buildFilter() {
        RedmineIssueFilter filter = new RedmineIssueFilter();

        // Date Range (Created On)
        if (startDatePicker.getValue() != null && endDatePicker.getValue() != null) {

            filter.setCreated_on(FilterCondition.betweenDates(TDateUtil.convertToLocalDate(startDatePicker.getValue()),
                    TDateUtil.convertToLocalDate(endDatePicker.getValue())));

        } else if (startDatePicker.getValue() != null) {
            // Only start date selected - assume '>= date'
            filter.setCreated_on(FilterCondition.greaterEqualThan(startDatePicker.getValue().toString()));
        } else if (endDatePicker.getValue() != null) {
            // Only end date selected - assume '<= date'
            filter.setCreated_on(FilterCondition.lessEqualThan(endDatePicker.getValue().toString()));
        }

        // Status
        if (statusComboBox.getValue() != null) {
            filter.setStatus_id(FilterCondition.equalsTo(String.valueOf(statusComboBox.getValue().getId())));
        }

        // Assigned To
        if (assignedToComboBox.getValue() != null) {
            filter.setAssigned_to_id(FilterCondition.equalsTo(String.valueOf(assignedToComboBox.getValue().getId())));
        }

        // Author
        if (authorComboBox.getValue() != null) {
            filter.setAuthor_id(FilterCondition.equalsTo(String.valueOf(authorComboBox.getValue().getId())));
        }

        // Issue Id
        if (issueIdField.getText() != null && !issueIdField.getText().trim().isEmpty()) {
            filter.setIssue_id(FilterCondition.equalsTo(issueIdField.getText().trim()));
        }

        return filter;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // ViewModel for TableView
    public static class IssueViewModel {
        private final BooleanProperty selected = new SimpleBooleanProperty(false);
        private final TIssueEvidenceInfo original;

        public IssueViewModel(TIssueEvidenceInfo original) {
            this.original = original;
        }

        public BooleanProperty selectedProperty() {
            return selected;
        }

        public boolean isSelected() {
            return selected.get();
        }

        public void setSelected(boolean val) {
            this.selected.set(val);
        }

        public Long getId() {
            return original.getId();
        }

        public String getTrackerName() {
            return original.getTrackerName();
        }

        public String getStatusName() {
            return original.getStatusName();
        }

        public String getSubject() {
            return original.getSubject();
        }

        public Integer getDoneRatio() {
            return original.getDoneRatio();
        }

        public String getStartDate() {
            return original.getStartDate();
        }

        public String getDueDate() {
            return original.getDueDate();
        }

        public String getAssigneeName() {
            return original.getAssigneeName();
        }

        public TIssueEvidenceInfo getOriginal() {
            return original;
        }
    }
}
