package org.tedros.it.tools.redmine.component;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.tedros.ai.service.AiServiceProvider;
import org.tedros.ai.service.AiTerosServiceFactory;
import org.tedros.ai.service.IAiTerosService;
import org.tedros.ai.web.TerosWebViewBridge;
import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.component.ITComponent;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TDatePickerField;
import org.tedros.fx.control.TLabel;
import org.tedros.fx.layout.TToolBar;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.process.TProcess;
import org.tedros.fx.process.TTaskImpl;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.redmine.ai.function.RedmineApiPropertyUtil;
import org.tedros.it.tools.redmine.ai.model.FilterCondition;
import org.tedros.it.tools.redmine.ai.model.FilterType;
import org.tedros.it.tools.redmine.ai.model.RedmineFilterField;
import org.tedros.it.tools.redmine.ai.model.RedmineIssueFilter;
import org.tedros.it.tools.redmine.api.model.TIssueEvidenceInfo;
import org.tedros.it.tools.redmine.api.model.TIssueStatus;
import org.tedros.it.tools.redmine.api.model.TRedmineUser;
import org.tedros.it.tools.redmine.gateway.RedmineApiGateway;
import org.tedros.util.TDateUtil;
import org.tedros.util.TLoggerUtil;
import org.tedros.util.TedrosFolder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class RedmineIssueSearchComponent extends VBox implements ITComponent{

    private static final String SYSTEM_PROMPT = """
            	Your are a Redmine AI Assistant integrated into the Teros system.
            	Reply using HTML format only.

               STRICT PROTOCOLS:
	           1. Tone: Professional and direct. NO EMOTICONS or EMOJIS allowed.
	           2. Data: Use only provided/system data. Do NOT invent data.
	           3. Missing Info: If data is missing, explicitly ask for specific fields and provide a placeholder example.
	           4. No Images: Do NOT generate <img> tags or reference external images.
	           5. Responses language. Always respond in the user's language. The primary languages are Portuguese and English. Detect the language of the user's input and match it.

               CRITICAL OUTPUT RULES (Follow Strictly):
               1. **RAW HTML ONLY**: You must return a raw HTML string.
               2. **NO ESCAPING**: Do NOT escape HTML tags. For example, return "<div>" (CORRECT), never "&lt;div&gt;" (WRONG).
               3. **NO MARKDOWN**: Do NOT wrap the output in Markdown code blocks (e.g., ```html ... ```). Do NOT use backticks.
               4. **NO DOCUMENT TAGS**: Do NOT include <html>, <head>, <body>, or <script>.
               5. **MERMAID DIAGRAMS**: To insert diagrams, use exactly: <div class="mermaid">...syntax...</div>.

               Content Guidelines:
               - Use standard HTML5 tags (div, h3, p, ul, table, strong, span).
               - Use inline CSS for styling to ensure it looks good on a dark background.
               - Ensure the content is safe for insertion via 'innerHTML'.

               Example of CORRECT response:
               <div style='padding:10px;'><h3>Title</h3><p>Content here.</p></div>

               Example of WRONG response (Do NOT do this):
               ```html\n&lt;div&gt;...&lt;/div&gt;\n```
               """;

    // Filters
    private TDatePickerField startDatePicker;
    private TDatePickerField endDatePicker;
    private ComboBox<TIssueStatus> statusComboBox;
    private ComboBox<TRedmineUser> assignedToComboBox;
    private ComboBox<TRedmineUser> authorComboBox;
    private TextField issueIdField;

    private TButton searchButton;
    private TButton sendForAnalysisButton;
    private SimpleBooleanProperty progressIndicatorVisible = new SimpleBooleanProperty(false);

    // Results
    private Accordion accordion;
    private TitledPane filterPane;
    private TitledPane aiPane;
    private TableView<IssueViewModel> resultsTable;
    private ObservableList<IssueViewModel> issueData = FXCollections.observableArrayList();
    private TButton addToAnalysisButton;

    // AI Analysis
    private ObservableList<IssueViewModel> selectedIssueData = FXCollections.observableArrayList();
    private TableView<IssueViewModel> selectedTable;
    private TButton removeSelectedButton;
    private TButton clearAllButton;
    private TextArea aiPromptField;
    private WebView aiResponseWebView;
    private RedmineApiGateway gateway;
    private TerosWebViewBridge webViewBridge;
    private TerosService terosServ;
    private RedmineService redmineServ;
    
    @SuppressWarnings("rawtypes")
	private ITView view;
    
    @Override
	public void tInitializeComponent(ITComponentDescriptor descriptor) {
    	view = descriptor.getForm().gettPresenter().getView();
    	view.gettProgressIndicator().bind(progressIndicatorVisible);
    	
        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.setFillWidth(true);
        initializeUI();
        Platform.runLater(() -> {
            try {
            	RedmineApiPropertyUtil propertyUtil = RedmineApiPropertyUtil.getInstance();
            	gateway = new RedmineApiGateway(propertyUtil.getRedmineUrl(), propertyUtil.getRedmineKey());

                loadInitialData();
            	
            	terosServ = new TerosService();
            	terosServ.onFailedProperty().addListener((obs, oldVal, newVal) -> Platform
                		.runLater(() -> Platform.runLater(() -> showAlert(terosServ.getException().getMessage())))); 
            	terosServ.setOnSucceeded(e -> {
            		String htmlMessage = terosServ.getValue();
					webViewBridge.run(htmlMessage);
					accordion.setExpandedPane(aiPane);
            	});
            	
                redmineServ = new RedmineService(gateway);
                redmineServ.onFailedProperty().addListener((obs, oldVal, newVal) -> Platform
                		.runLater(() -> showAlert(redmineServ.getException().getMessage())));               
                redmineServ.setOnSucceeded(e -> {
	       			List<TIssueEvidenceInfo> results = redmineServ.getValue();
	       			if (results != null) {
	       				accordion.setExpandedPane(filterPane);
	       				results.stream().map(IssueViewModel::new).forEach(issueData::add);
	       			}
	       		});
               
                progressIndicatorVisible.bind(terosServ.runningProperty().or(redmineServ.runningProperty()));
                
            	webViewBridge = new TerosWebViewBridge(aiResponseWebView);
            	aiResponseWebView.getEngine()
                        .load("file:" + TedrosFolder.MODULE_FOLDER.getFullPath() + "TCORE_19780222" + File.separator
                                + "teros_ia_response.html");
                aiResponseWebView.setZoom(0.78);
            } catch (Exception e) {
                TLoggerUtil.error(RedmineIssueSearchComponent.class, "Failed to load AI response HTML template.", e);
            }
		});
                
    }
    
    private void initializeUI() {
    	
    	TLanguage lang = TLanguage.getInstance();
    	
        // 1. Top Section - Filters
        GridPane filtersGrid = new GridPane();
        filtersGrid.setHgap(10);
        filtersGrid.setVgap(10);
        filtersGrid.setPadding(new Insets(10));
        filtersGrid.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5; -fx-padding: 10;");

        // Date Range
        filtersGrid.add(new TLabel(lang.getString(TUsualKey.BEGIN_DATE)), 0, 0);
        startDatePicker = new TDatePickerField();
        filtersGrid.add(startDatePicker, 1, 0);

        filtersGrid.add(new TLabel(lang.getString(TUsualKey.END_DATE)), 2, 0);
        endDatePicker = new TDatePickerField();
        filtersGrid.add(endDatePicker, 3, 0);

        // Status
        filtersGrid.add(new TLabel(lang.getString(TUsualKey.STATUS)), 0, 1);
        statusComboBox = new ComboBox<>();
        statusComboBox.setMaxWidth(Double.MAX_VALUE);
        filtersGrid.add(statusComboBox, 1, 1);

        // Assigned To
        filtersGrid.add(new TLabel(lang.getString(ItToolsKey.ASSIGNED_TO)), 2, 1);
        assignedToComboBox = new ComboBox<>();
        assignedToComboBox.setMaxWidth(Double.MAX_VALUE);
        filtersGrid.add(assignedToComboBox, 3, 1);

        // Author
        filtersGrid.add(new TLabel(lang.getString(TUsualKey.AUTOR)), 0, 2);
        authorComboBox = new ComboBox<>();
        authorComboBox.setMaxWidth(Double.MAX_VALUE);
        filtersGrid.add(authorComboBox, 1, 2);

        // Spent Time
        // Issue Id
        filtersGrid.add(new TLabel(lang.getString(ItToolsKey.ISSUE_ID)), 2, 2);
        issueIdField = new TextField();
        issueIdField.setPromptText(lang.getString(ItToolsKey.ISSUE_ID));
        filtersGrid.add(issueIdField, 3, 2);

        searchButton = new TButton(lang.getString(ItToolsKey.SEACH_FOR_ISSUE));
        searchButton.setOnAction(e -> searchIssues());
        filtersGrid.add(searchButton, 4, 1);

        buildResultTableView();
        buildSelectedTableView();

        // 3. Layout Restructuring with Accordion
        accordion = new Accordion();
        VBox.setVgrow(accordion, Priority.ALWAYS);

        // Pane 1: Search Results
        addToAnalysisButton = new TButton(lang.getString(ItToolsKey.ADD_TO_ANALYSIS));
        addToAnalysisButton.setOnAction(e -> addToAnalysis());

        VBox resultsBox = new VBox(10, filtersGrid, resultsTable, addToAnalysisButton);
        resultsBox.setPadding(new Insets(10));
        resultsBox.setFillWidth(true);
        VBox.setVgrow(resultsTable, Priority.ALWAYS);
        
        filterPane = new TitledPane(lang.getString(TUsualKey.FILTERS), resultsBox);
        

        // Pane 2: AI Analysis
        aiPromptField = new TextArea();
        aiPromptField.setPromptText(lang.getString(ItToolsKey.DESCRIBE_IA_INSTRUCTIONS));
        aiPromptField.setPrefRowCount(4);

        removeSelectedButton = new TButton(lang.getString(ItToolsKey.REMOVE_SELECTED));
        removeSelectedButton.setOnAction(e -> removeFromAnalysis());

        clearAllButton = new TButton(lang.getString(ItToolsKey.CLEAR_ALL));
        clearAllButton.setOnAction(e -> selectedIssueData.clear());

        TToolBar selectionButtons = new TToolBar(removeSelectedButton, clearAllButton);

        sendForAnalysisButton = new TButton(lang.getString(ItToolsKey.SEND_TO_TEROS));
        sendForAnalysisButton.setStyle("-fx-font-weight: bold;");
        sendForAnalysisButton.setOnAction(e -> performAiAnalysis());

        aiResponseWebView = new WebView();
        aiResponseWebView.setMaxHeight(350);
        VBox selectedItensBox = new VBox(10,
                new TLabel(lang.getString(ItToolsKey.SELECTED_ITEMS_TO_ANALYSE)),
                selectedTable,
                selectionButtons);
        selectedItensBox.setPadding(new Insets(10));
        VBox.setVgrow(selectedTable, Priority.ALWAYS);
        
        VBox aiBox = new VBox(10,
                new TLabel(lang.getString(ItToolsKey.TEROS_INSTRUCTIONS)),
                aiPromptField,
                sendForAnalysisButton,
                aiResponseWebView);
        aiBox.setPadding(new Insets(10));

        aiPane = new TitledPane(lang.getString(ItToolsKey.ISSUE_ANALYSIS_WITH_TEROS), new HBox(10, selectedItensBox, aiBox));
        

        accordion.getPanes().addAll(filterPane, aiPane);
        this.getChildren().add(accordion);
        accordion.setExpandedPane(filterPane);
    }

    @SuppressWarnings("unchecked")
	private void buildResultTableView() {
    	TLanguage lang = TLanguage.getInstance();
    	
        // 2. Center Section - Results Table
        resultsTable = new TableView<>();
        resultsTable.setEditable(true);
        resultsTable.setItems(issueData);
        VBox.setVgrow(resultsTable, Priority.ALWAYS);

        // --- IMPORTANTE: Define a política para preencher a largura total ---
        resultsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // --- COLUNAS ---

        // 1. Select (Tamanho fixo pequeno)
        TableColumn<IssueViewModel, Boolean> selectCol = new TableColumn<>();
        CheckBox selectAllCb = new CheckBox();
        selectAllCb.setOnAction(e -> {
            boolean isSelected = selectAllCb.isSelected();
            issueData.forEach(item -> item.setSelected(isSelected));
        });
        selectCol.setGraphic(selectAllCb);
        selectCol.setText(lang.getString(TUsualKey.SELECT));
        selectCol.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        selectCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectCol));
        selectCol.setEditable(true);
        selectCol.setMinWidth(90);
        selectCol.setMaxWidth(90);
        selectCol.setPrefWidth(90);
        selectCol.setResizable(true); // Impede o usuário de redimensionar esta coluna

        // 2. ID (Tamanho fixo numérico)
        TableColumn<IssueViewModel, Long> idCol = new TableColumn<>(lang.getString(ItToolsKey.ISSUE_ID));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setMinWidth(80);
        idCol.setMaxWidth(90);
        idCol.setPrefWidth(70);
        idCol.setResizable(true);

        // 3. Type (Tamanho controlado)
        TableColumn<IssueViewModel, String> typeCol = new TableColumn<>(lang.getString(TUsualKey.TYPE));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("trackerName"));
        typeCol.setMinWidth(100);
        typeCol.setMaxWidth(120); // Pode crescer um pouco, mas não muito

        // 4. Status (Tamanho controlado)
        TableColumn<IssueViewModel, String> statusCol = new TableColumn<>(lang.getString(TUsualKey.STATUS));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("statusName"));
        statusCol.setMinWidth(100);
        statusCol.setMaxWidth(120);

        // 5. Title (PRINCIPAL: Ocupa todo o espaço restante)
        TableColumn<IssueViewModel, String> titleCol = new TableColumn<>(lang.getString(TUsualKey.TITLE));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        titleCol.setMinWidth(200); // Garante que o texto não suma
        titleCol.setMaxWidth(Double.MAX_VALUE); // Permite crescer infinitamente

        // 6. % Done (Fixo pequeno)
        TableColumn<IssueViewModel, Integer> doneCol = new TableColumn<>(lang.getString(ItToolsKey.PERCENTAGE_DONE));
        doneCol.setCellValueFactory(new PropertyValueFactory<>("doneRatio"));
        doneCol.setMinWidth(90);
        doneCol.setMaxWidth(90);
        doneCol.setResizable(true);

        // 7. Start Date (Fixo para caber a data)
        TableColumn<IssueViewModel, String> startCol = new TableColumn<>(lang.getString(TUsualKey.BEGIN_DATE));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        startCol.setMinWidth(100);
        startCol.setMaxWidth(200);

        // 8. Due Date (Fixo para caber a data)
        TableColumn<IssueViewModel, String> dueCol = new TableColumn<>(lang.getString(ItToolsKey.DUE_DATE));
        dueCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        dueCol.setMinWidth(100);
        dueCol.setMaxWidth(200);

        // 9. Assigned To (Fixo ou ligeiramente flexível)
        TableColumn<IssueViewModel, String> assignedCol = new TableColumn<>(lang.getString(ItToolsKey.ASSIGNED_TO));
        assignedCol.setCellValueFactory(new PropertyValueFactory<>("assigneeName"));
        assignedCol.setMinWidth(200);
        assignedCol.setMaxWidth(250);

        // Adiciona todas as colunas
        resultsTable.getColumns().addAll(selectCol, idCol, typeCol, statusCol, titleCol, doneCol, startCol, dueCol,
                assignedCol);

        this.getChildren().add(resultsTable);
    }

    @SuppressWarnings("unchecked")
	private void buildSelectedTableView() {
    	
    	TLanguage lang = TLanguage.getInstance();
    	
        selectedTable = new TableView<>();
        selectedTable.setItems(selectedIssueData);
        selectedTable.setPrefHeight(200);
        selectedTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // 2. ID (Tamanho fixo numérico)
        TableColumn<IssueViewModel, Long> idCol = new TableColumn<>(lang.getString(ItToolsKey.ISSUE_ID));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setMinWidth(90);
        idCol.setMaxWidth(90);
        idCol.setPrefWidth(90);
        idCol.setResizable(true);

        // 3. Type (Tamanho controlado)
        TableColumn<IssueViewModel, String> typeCol = new TableColumn<>(lang.getString(TUsualKey.TYPE));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("trackerName"));
        typeCol.setMinWidth(100);
        typeCol.setMaxWidth(120); // Pode crescer um pouco, mas não muito

        // 4. Status (Tamanho controlado)
        TableColumn<IssueViewModel, String> statusCol = new TableColumn<>(lang.getString(TUsualKey.STATUS));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("statusName"));
        statusCol.setMinWidth(100);
        statusCol.setMaxWidth(120);

        // 5. Title (PRINCIPAL: Ocupa todo o espaço restante)
        TableColumn<IssueViewModel, String> titleCol = new TableColumn<>(lang.getString(TUsualKey.TITLE));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        titleCol.setMinWidth(200); // Garante que o texto não suma
        titleCol.setMaxWidth(Double.MAX_VALUE); // Permite crescer infinitamente

        selectedTable.getColumns().addAll(idCol, typeCol, statusCol, titleCol);
    }

    private void addToAnalysis() {
        List<IssueViewModel> selectedInResults = issueData.stream()
                .filter(IssueViewModel::isSelected)
                .collect(Collectors.toList());

        if (selectedInResults.isEmpty()) {
            showAlert(TLanguage.getInstance().getString(ItToolsKey.TEXT_SELECT_ITEMS_FIRST));
            return;
        }

        accordion.setExpandedPane(aiPane);
        
        for (IssueViewModel item : selectedInResults) {
            // Prevent duplicates based on ID
            boolean exists = selectedIssueData.stream().anyMatch(existing -> existing.getId().equals(item.getId()));
            if (!exists) {
                // We add the same ViewModel instance.
                // If we wanted independent selection state in the second table,
                // we might clone it, but here it's fine.
                selectedIssueData.add(item);
            }
        }
    }

    private void removeFromAnalysis() {
        IssueViewModel selected = selectedTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selectedIssueData.remove(selected);
        }
    }

    private void performAiAnalysis() {
        if (selectedIssueData.isEmpty()) {
        	accordion.setExpandedPane(filterPane);
        	showAlert(TLanguage.getInstance().getString(ItToolsKey.TEXT_ADD_ISSUES_TO_ANALYSIS));
            return;
        }

        List<TIssueEvidenceInfo> selectedIssues = selectedIssueData.stream()
                .map(IssueViewModel::getOriginal)
                .collect(Collectors.toList());

        String userPrompt = aiPromptField.getText();
        
        if(userPrompt == null || userPrompt.trim().isEmpty()) {
        	showAlert(TLanguage.getInstance().getString(ItToolsKey.TEXT_PROVIDE_INSTRUCTIONS));
			return;
		}

        CompletableFuture.runAsync(() -> {
            if (selectedIssues.size() > 3) {
                int count = 1;
                for (TIssueEvidenceInfo issue : selectedIssues) {
                    callTerosService(List.of(issue), userPrompt,
                            "the user selected " + selectedIssues.size()
                                    + " issues and to prevent overload we are sending one by one. this one is the number "
                                    + count + " of " + selectedIssues.size() + ". Reply using HTML format only.");
                    count++;
                }
            } else {
                callTerosService(selectedIssues, userPrompt, null);
            }
        });

    }

    private void callTerosService(List<TIssueEvidenceInfo> selectedIssues, String userPrompt, String systemPrompt) {
        String contextData = toJson(selectedIssues);
        String fullPromptWithIssues = (userPrompt != null ? userPrompt : "") + "\n\nData:\n" + contextData;        
        terosServ.prompt = fullPromptWithIssues;
        terosServ.systemPrompt = systemPrompt != null ? systemPrompt : SYSTEM_PROMPT;
        terosServ.startProcess();
    }

    private String toJson(List<TIssueEvidenceInfo> issues) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(issues);
        } catch (Exception e) {
            return issues.toString();
        }
    }

    private void loadInitialData() {
        
    	Service<Void> loadService = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						// Initialize gateway cache if needed (gateway logic)
		                gateway.loadCustomFieldMetadata();
		                
		                String all = "-";
		                
		                TIssueStatus allStatus = new TIssueStatus();
		                allStatus.setName(all);
		                
		                TRedmineUser allUsers = new TRedmineUser();
		                allUsers.setLogin(all);

		                List<TIssueStatus> statuses = new ArrayList<>(gateway.listIssueStatuses());
		                List<TRedmineUser> users = new ArrayList<>(gateway.listUsers());
		                
		                statuses.add(0, allStatus);
		                users.add(0, allUsers);
		                
	                    statusComboBox.getItems().setAll(statuses);
	                    assignedToComboBox.getItems().setAll(users);
	                    authorComboBox.getItems().setAll(users);
		                
						return null;
					}
				};
			}
    		
    	};
    	
    	progressIndicatorVisible.bind(loadService.runningProperty());
    	
    	loadService.setOnFailed(e -> showAlert("Error loading initial data"+": "+loadService.getException().getMessage()));
    	loadService.start();
    }

    private void searchIssues() {
        issueData.clear();
        RedmineIssueFilter filter = buildFilter();
        redmineServ.filter = filter;
        redmineServ.startProcess();
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
        if (statusComboBox.getValue() != null && statusComboBox.getValue().getId() != null) {
            filter.setStatus_id(FilterCondition.equalsTo(String.valueOf(statusComboBox.getValue().getId())));
        }

        // Assigned To
        if (assignedToComboBox.getValue() != null && assignedToComboBox.getValue().getId() != null) {
            filter.setAssigned_to_id(FilterCondition.equalsTo(String.valueOf(assignedToComboBox.getValue().getId())));
        }

        // Author
        if (authorComboBox.getValue() != null && authorComboBox.getValue().getId() != null) {
            filter.setAuthor_id(FilterCondition.equalsTo(String.valueOf(authorComboBox.getValue().getId())));
        }

        // Issue Id
        if (issueIdField.getText() != null && !issueIdField.getText().trim().isEmpty()) {
            String[] ids = Arrays.stream(issueIdField.getText().trim().split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toArray(String[]::new);
            if (ids.length > 0) {
                filter.setIssue_id(new FilterCondition(FilterType.TEXT, "=", ids));
            }
        }

        return filter;
    }

    private void showAlert(String content) {
    	view.tShowModal(new TMessageBox(content), true); 
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
    
    private static class TerosService extends TProcess<String> {
    	
		private String prompt;
		private String systemPrompt;
		private IAiTerosService iaServ;
		
		public TerosService() {
			String apiKey = TedrosContext.getAiApiKey();
            String aiModel = TedrosContext.getAiModel();
            AiServiceProvider aiProvider = TedrosContext.getAiServiceProvider();
            iaServ = AiTerosServiceFactory.newInstance(apiKey, aiModel, SYSTEM_PROMPT, aiProvider);
		}

		@Override
		protected TTaskImpl<String> createTask() {
			return new TTaskImpl<String>() {
				@Override
				protected String call() throws Exception {
					iaServ.cleanMessageHistory();
					return iaServ.call(prompt, systemPrompt);
				}

				@Override
				public String getServiceNameInfo() {
					return null;
				}
			};
		}
    }
    
    private static class RedmineService extends TProcess<List<TIssueEvidenceInfo>> {
    	
    	RedmineIssueFilter filter;
    	private RedmineApiGateway gateway;
    	
    	public RedmineService(RedmineApiGateway gateway) {
    		this.gateway = gateway;	
    	}

    	@Override
		protected TTaskImpl<List<TIssueEvidenceInfo>> createTask() {
			return new TTaskImpl<List<TIssueEvidenceInfo>>() {
				@Override
				protected List<TIssueEvidenceInfo> call() throws Exception {
					Map<String, FilterCondition> filtersMap = RedmineFilterField.fromObject(filter);
	                return gateway.getIssuesByFilters(filtersMap);
				}

				@Override
				public String getServiceNameInfo() {
					return null;
				}
			};
		}
    }	
}
