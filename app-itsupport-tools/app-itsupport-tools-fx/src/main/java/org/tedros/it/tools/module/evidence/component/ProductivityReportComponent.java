package org.tedros.it.tools.module.evidence.component;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.message.TMessageType;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.component.ITComponent;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TDatePickerField;
import org.tedros.fx.control.TLabel;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.process.TProcess;
import org.tedros.fx.process.TTaskImpl;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.ejb.controller.IProductivityActivityController;
import org.tedros.it.tools.model.ActivitySummaryDTO;
import org.tedros.it.tools.model.ProductivityActivityDTO;
import org.tedros.person.ejb.controller.IEmployeeController;
import org.tedros.person.model.Employee;
import org.tedros.server.result.TResult;
import org.tedros.util.TDateUtil;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ProductivityReportComponent extends VBox implements ITComponent {

    private ComboBox<Employee> employeeComboBox;
    private TDatePickerField startDatePicker;
    private TDatePickerField endDatePicker;

    private TableView<ProductivityActivityDTO> resultsTable;
    private ObservableList<ProductivityActivityDTO> activityData = FXCollections.observableArrayList();

    private StackedBarChart<String, Number> barChart;
    private XYChart.Series<String, Number> activeSeries;
    private XYChart.Series<String, Number> inactiveSeries;

    @SuppressWarnings("rawtypes")
    private ITView view;
    private SimpleBooleanProperty progressIndicatorVisible = new SimpleBooleanProperty(false);

    private SearchRawDataService rawDataService;
    private SearchSummaryService summaryService;

    @Override
    public void tInitializeComponent(ITComponentDescriptor descriptor) {
        view = descriptor.getForm().gettPresenter().getView();
        view.gettProgressIndicator().bind(progressIndicatorVisible);

        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.setFillWidth(true);

        initializeUI();
        loadEmployees();

        rawDataService = new SearchRawDataService();
        summaryService = new SearchSummaryService();

        rawDataService.setOnSucceeded(e -> {
            List<ProductivityActivityDTO> results = rawDataService.getValue();
            activityData.setAll(results);
            summaryService.startProcess();
        });

        summaryService.setOnSucceeded(e -> {
            List<ActivitySummaryDTO> summary = summaryService.getValue();
            populateChart(summary);
        });

        rawDataService
                .setOnFailed(e -> showAlert("Failed to load raw data: " + rawDataService.getException().getMessage()));

        summaryService.setOnFailed(
                e -> showAlert("Failed to load summary data: " + summaryService.getException().getMessage()));

        progressIndicatorVisible.bind(rawDataService.runningProperty()
                .or(summaryService.runningProperty()));
    }

    @SuppressWarnings("unchecked")
    private void initializeUI() {
        TLanguage lang = TLanguage.getInstance();

        GridPane filtersGrid = new GridPane();
        filtersGrid.setHgap(10);
        filtersGrid.setVgap(10);
        filtersGrid.setPadding(new Insets(10));
        filtersGrid.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5; -fx-padding: 10;");

        employeeComboBox = new ComboBox<>();
        employeeComboBox.setMaxWidth(Double.MAX_VALUE);
        filtersGrid.add(new TLabel(lang.getString(TUsualKey.EMPLOYEE)), 0, 0);
        filtersGrid.add(employeeComboBox, 1, 0);

        startDatePicker = new TDatePickerField();
        filtersGrid.add(new TLabel(lang.getString(TUsualKey.BEGIN_DATE)), 2, 0);
        filtersGrid.add(startDatePicker, 3, 0);

        endDatePicker = new TDatePickerField();
        filtersGrid.add(new TLabel(lang.getString(TUsualKey.END_DATE)), 4, 0);
        filtersGrid.add(endDatePicker, 5, 0);

        TButton searchButton = new TButton(lang.getString(TUsualKey.SEARCH));
        searchButton.setOnAction(e -> doSearch());
        filtersGrid.add(searchButton, 6, 0);

        // Build Table (Left)
        resultsTable = new TableView<>();
        resultsTable.setItems(activityData);
        resultsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<ProductivityActivityDTO, String> timestampCol = new TableColumn<>(lang.getString(TUsualKey.DATE));
        timestampCol.setMinWidth(180);
        timestampCol.setMaxWidth(220);
        timestampCol.setCellValueFactory(c -> {
        	LocalDateTime localDateTime = c.getValue().getTimestamp();
        	Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
			return new SimpleStringProperty(date != null ? DateFormat
					.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, TLanguage.getLocale())
					.format(date) : "");
		});

        TableColumn<ProductivityActivityDTO, String> windowCol = new TableColumn<>(lang.getString(ItToolsKey.WINDOW_TITLE));
        windowCol.setCellValueFactory(new PropertyValueFactory<>("activeWindowTitle"));
        windowCol.setMinWidth(500); 
        windowCol.setMaxWidth(Double.MAX_VALUE);

        TableColumn<ProductivityActivityDTO, Long> mouseCol = new TableColumn<>(lang.getString(ItToolsKey.MOUSE_EVENTS));
        mouseCol.setCellValueFactory(new PropertyValueFactory<>("mouseEventCount"));
        mouseCol.setMinWidth(100);
        mouseCol.setMaxWidth(120);
        mouseCol.setPrefWidth(110);

        TableColumn<ProductivityActivityDTO, Long> keyboardCol = new TableColumn<>(lang.getString(ItToolsKey.KEYBOARD_EVENTS));
        keyboardCol.setCellValueFactory(new PropertyValueFactory<>("keyboardEventCount"));
        keyboardCol.setMinWidth(100);
        keyboardCol.setMaxWidth(120);
        keyboardCol.setPrefWidth(110);

        resultsTable.getColumns().addAll(timestampCol, windowCol, mouseCol, keyboardCol);

        // Build Chart (Right)
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel(lang.getString(ItToolsKey.WINDOW_TITLE));

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(lang.getString(ItToolsKey.COUNT));

        barChart = new StackedBarChart<>(xAxis, yAxis);
        barChart.setTitle(lang.getString(ItToolsKey.ACTIVITY_SUMMARY));
        barChart.setAnimated(false); 

        activeSeries = new XYChart.Series<>();
        activeSeries.setName(lang.getString(ItToolsKey.ACTIVITY));

        inactiveSeries = new XYChart.Series<>();
        inactiveSeries.setName(lang.getString(ItToolsKey.INACTIVITY));

        barChart.getData().addAll(inactiveSeries, activeSeries);

        TabPane tabPane = new TabPane();
        
        Tab resultsTab = new Tab(lang.getString(TUsualKey.RESULT), resultsTable);
        resultsTab.setClosable(false);
        
        Tab summaryTab = new Tab(lang.getString(TUsualKey.SUMMARY), barChart);
        summaryTab.setClosable(false);

        tabPane.getTabs().addAll(
        		resultsTab,
        		summaryTab);

        VBox.setVgrow(tabPane, Priority.ALWAYS);

        this.getChildren().addAll(filtersGrid, tabPane);
    }

    private void loadEmployees() {
        LoadEmployeeService employeeService = new LoadEmployeeService();
        employeeService.setOnSucceeded(e -> {
            List<Employee> employees = employeeService.getValue();
            if (employees != null) {
                employeeComboBox.getItems().setAll(employees);
            }
        });
        employeeService.setOnFailed(
                e -> showAlert("Failed to load employees: " + employeeService.getException().getMessage()));
        view.gettProgressIndicator().bind(employeeService.runningProperty());
        employeeService.startProcess();
    }

    private void doSearch() {
        if (!validateFilters()) {
            return;
        }

        Employee emp = employeeComboBox.getValue();
        LocalDateTime start = TDateUtil.convertToLocalDate(startDatePicker.getValue()).atStartOfDay();
        LocalDateTime end = TDateUtil.convertToLocalDate(endDatePicker.getValue()).atTime(23, 59, 59);

        Long userId = emp.getTedrosUserId();

        activityData.clear();
        inactiveSeries.getData().clear();
        activeSeries.getData().clear();

        rawDataService.setParameters(userId, start, end);
        summaryService.setParameters(userId, start, end);
        rawDataService.startProcess();
    }

    private void populateChart(List<ActivitySummaryDTO> summary) {
        for (ActivitySummaryDTO dto : summary) {
            String title = dto.windowTitle() != null ? dto.windowTitle() : "Unknown";
            // Trim long titles for the chart axis
            if (title.length() > 30) {
                title = title.substring(0, 30) + "...";
            }
            activeSeries.getData().add(new XYChart.Data<>(title, dto.activeCount()));
            inactiveSeries.getData().add(new XYChart.Data<>(title, dto.inactiveCount()));
        }
    }

    private boolean validateFilters() {
    	TLanguage lang = TLanguage.getInstance();
        if (employeeComboBox.getValue() == null) {
            showAlert(lang.getString(ItToolsKey.TEXT_SELECT_EMPLOYEE));
            return false;
        }
        if (startDatePicker.getValue() == null) {
            showAlert(lang.getString(ItToolsKey.TEXT_SELECT_START_DATE));
            return false;
        }
        if (endDatePicker.getValue() == null) {
            showAlert(lang.getString(ItToolsKey.TEXT_SELECT_END_DATE));
            return false;
        }
        if (endDatePicker.getValue().before(startDatePicker.getValue())) {
            showAlert(lang.getString(ItToolsKey.TEXT_END_DATE_CANNOT_BE_BEFORE_START_DATE));
            return false;
        }
        return true;
    }

    private void showAlert(String content) {
        view.tShowModal(new TMessageBox(content, TMessageType.ERROR), true);
    }

    @Override
    public void tStopComponent() {
        // Stop background processes if necessary
    	if (rawDataService.isRunning()) {
    		rawDataService.cancel();
    	}
    	
    	if (summaryService.isRunning()) {
			summaryService.cancel();
		}
    }

    private class LoadEmployeeService extends TProcess<List<Employee>> {
        @Override
        protected TTaskImpl<List<Employee>> createTask() {
            return new TTaskImpl<List<Employee>>() {
                @Override
                protected List<Employee> call() throws Exception {
                    try (TEjbServiceLocator locator = TEjbServiceLocator.getInstance()) {
                        IEmployeeController controller = locator.lookup(IEmployeeController.JNDI_NAME);
                        TResult<List<Employee>> result = controller
                                .listAll(TedrosContext.getLoggedUser().getAccessToken(), Employee.class);
                        if (result.isSuccess()) {
                            return result.getValue();
                        } else {
                            throw new RuntimeException("Error loading employees.");
                        }
                    }
                }

                @Override
                public String getServiceNameInfo() {
                    return "LoadEmployeeService";
                }
            };
        }
    }

    private class SearchRawDataService extends TProcess<List<ProductivityActivityDTO>> {
        private Long userId;
        private LocalDateTime start;
        private LocalDateTime end;

        public void setParameters(Long userId, LocalDateTime start, LocalDateTime end) {
            this.userId = userId;
            this.start = start;
            this.end = end;
        }

        @Override
        protected TTaskImpl<List<ProductivityActivityDTO>> createTask() {
            return new TTaskImpl<List<ProductivityActivityDTO>>() {
                @Override
                protected List<ProductivityActivityDTO> call() throws Exception {
                    try (TEjbServiceLocator locator = TEjbServiceLocator.getInstance()) {
                        IProductivityActivityController controller = locator
                                .lookup(IProductivityActivityController.JNDI_NAME);
                        TResult<List<ProductivityActivityDTO>> result = controller.findUserIdAndDateRange(
                                TedrosContext.getLoggedUser().getAccessToken(), userId, start, end);
                        if (result.isSuccess()) {
                            return result.getValue();
                        } else {
                            throw new RuntimeException("Error loading activities.");
                        }
                    }
                }

                @Override
                public String getServiceNameInfo() {
                    return "SearchRawDataService";
                }
            };
        }
    }

    private class SearchSummaryService extends TProcess<List<ActivitySummaryDTO>> {
        private Long userId;
        private LocalDateTime start;
        private LocalDateTime end;

        public void setParameters(Long userId, LocalDateTime start, LocalDateTime end) {
            this.userId = userId;
            this.start = start;
            this.end = end;
        }

        @Override
        protected TTaskImpl<List<ActivitySummaryDTO>> createTask() {
            return new TTaskImpl<List<ActivitySummaryDTO>>() {
                @Override
                protected List<ActivitySummaryDTO> call() throws Exception {
                    try (TEjbServiceLocator locator = TEjbServiceLocator.getInstance()) {
                        IProductivityActivityController controller = locator
                                .lookup(IProductivityActivityController.JNDI_NAME);
                        TResult<List<ActivitySummaryDTO>> result = controller.getWindowUsageSummary(
                                TedrosContext.getLoggedUser().getAccessToken(), userId, start, end);
                        if (result.isSuccess()) {
                            return result.getValue();
                        } else {
                            throw new RuntimeException("Error loading summary.");
                        }
                    }
                }

                @Override
                public String getServiceNameInfo() {
                    return "SearchSummaryService";
                }
            };
        }
    }

}
