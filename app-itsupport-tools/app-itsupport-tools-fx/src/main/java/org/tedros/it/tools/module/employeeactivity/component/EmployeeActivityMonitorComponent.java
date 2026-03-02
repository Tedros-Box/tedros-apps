package org.tedros.it.tools.module.employeeactivity.component;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class EmployeeActivityMonitorComponent extends VBox implements ITComponent {

    private ComboBox<Employee> employeeComboBox;
    private TDatePickerField startDatePicker;
    private TDatePickerField endDatePicker;

    private TableView<ProductivityActivityDTO> resultsTable;
    private ObservableList<ProductivityActivityDTO> activityData = FXCollections.observableArrayList();

    // Gráfico de Janelas (Horizontal)
    private StackedBarChart<Number, String> barChart;
    private XYChart.Series<Number, String> activeSeries;
    private XYChart.Series<Number, String> inactiveSeries;

    // Novo Gráfico por Hora (Vertical)
    private StackedBarChart<String, Number> hourlyChart;
    private XYChart.Series<String, Number> hourlyActiveSeries;
    private XYChart.Series<String, Number> hourlyInactiveSeries;

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
            populateHourlyChart(results);
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

        // Build Chart (Horizontal) - Resumo de Janelas
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(lang.getString(ItToolsKey.COUNT));

        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setLabel(lang.getString(ItToolsKey.WINDOW_TITLE));

        barChart = new StackedBarChart<>(xAxis, yAxis);
        barChart.setTitle(lang.getString(ItToolsKey.ACTIVITY_PER_ACTIVE_WINDOW));
        barChart.setAnimated(false); 

        activeSeries = new XYChart.Series<>();
        activeSeries.setName(lang.getString(ItToolsKey.EMPLOYEE_ACTIVITY));

        inactiveSeries = new XYChart.Series<>();
        inactiveSeries.setName(lang.getString(ItToolsKey.EMPLOYEE_INACTIVITY));

        barChart.getData().addAll(inactiveSeries, activeSeries);

        // Build Hourly Chart (Vertical) - Novo Gráfico por Hora
        CategoryAxis xAxisHourly = new CategoryAxis();
        xAxisHourly.setLabel(lang.getString(ItToolsKey.HOUR));

        NumberAxis yAxisHourly = new NumberAxis();
        yAxisHourly.setLabel(lang.getString(ItToolsKey.COUNT));

        hourlyChart = new StackedBarChart<>(xAxisHourly, yAxisHourly);
        hourlyChart.setTitle(lang.getString(ItToolsKey.ACTIVITY_PER_HOUR));
        hourlyChart.setAnimated(false);

        hourlyActiveSeries = new XYChart.Series<>();
        hourlyActiveSeries.setName(lang.getString(ItToolsKey.EMPLOYEE_ACTIVITY));

        hourlyInactiveSeries = new XYChart.Series<>();
        hourlyInactiveSeries.setName(lang.getString(ItToolsKey.EMPLOYEE_INACTIVITY));

        hourlyChart.getData().addAll(hourlyInactiveSeries, hourlyActiveSeries);

        // Montando as Abas
        TabPane tabPane = new TabPane();
        
        Tab resultsTab = new Tab(lang.getString(TUsualKey.RESULT), resultsTable);
        resultsTab.setClosable(false);
        
        Tab perActivityTab = new Tab(lang.getString(ItToolsKey.PER_ACTIVE_WINDOW), barChart);
        perActivityTab.setClosable(false);

        Tab perHourTab = new Tab(lang.getString(ItToolsKey.PER_HOUR), hourlyChart);
        perHourTab.setClosable(false);

        tabPane.getTabs().addAll(
        		resultsTab,
        		perActivityTab,
                perHourTab);

        VBox.setVgrow(tabPane, Priority.ALWAYS);

        this.getChildren().addAll(filtersGrid, tabPane);
    }

    private void loadEmployees() {
        LoadEmployeeService employeeService = new LoadEmployeeService();
        employeeService.setOnSucceeded(e -> {
            List<Employee> employees = employeeService.getValue();
            if (employees != null) {
            	employees.sort((a, b) -> a.toString().compareToIgnoreCase(b.toString()));
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

        // Limpa os dados de todos os gráficos
        activityData.clear();
        inactiveSeries.getData().clear();
        activeSeries.getData().clear();
        hourlyActiveSeries.getData().clear();
        hourlyInactiveSeries.getData().clear();

        rawDataService.setParameters(userId, start, end);
        summaryService.setParameters(userId, start, end);
        rawDataService.startProcess();
    }

    // Método para processar o gráfico de horas baseado na lista bruta
    private void populateHourlyChart(List<ProductivityActivityDTO> rawData) {
        if (rawData == null || rawData.isEmpty()) return;

        // Arrays para armazenar a contagem de cada uma das 24 horas (0-23)
        long[] activeCount = new long[24];
        long[] inactiveCount = new long[24];

        for (ProductivityActivityDTO dto : rawData) {
            if (dto.getTimestamp() != null) {
                int hour = dto.getTimestamp().getHour();
                long mouse = dto.getMouseEventCount();
                long keyboard = dto.getKeyboardEventCount();

                // Se houver qualquer evento de teclado ou mouse, conta como registro ativo
                if (mouse > 0 || keyboard > 0) {
                    activeCount[hour]++;
                } else {
                    inactiveCount[hour]++;
                }
            }
        }

        List<XYChart.Data<String, Number>> activeList = new ArrayList<>();
        List<XYChart.Data<String, Number>> inactiveList = new ArrayList<>();

        for (int i = 0; i < 24; i++) {
            String hourLabel = String.format("%02dh", i);
            activeList.add(new XYChart.Data<>(hourLabel, activeCount[i]));
            inactiveList.add(new XYChart.Data<>(hourLabel, inactiveCount[i]));
        }

        TLanguage lang = TLanguage.getInstance();
        setupHourlyTooltip(activeList, lang.getString(ItToolsKey.EMPLOYEE_ACTIVITY));
        setupHourlyTooltip(inactiveList, lang.getString(ItToolsKey.EMPLOYEE_INACTIVITY));

        hourlyActiveSeries.getData().addAll(activeList);
        hourlyInactiveSeries.getData().addAll(inactiveList);
    }

    private void setupHourlyTooltip(List<XYChart.Data<String, Number>> dataList, String typeLabel) {
        for (XYChart.Data<String, Number> data : dataList) {
            data.nodeProperty().addListener((observable, oldNode, newNode) -> {
                if (newNode != null) {
                    String tooltipText = String.format("%s: %s%n%s: %s", TLanguage.getInstance().getString(ItToolsKey.HOUR), 
                    		data.getXValue(), typeLabel, data.getYValue());
                    Tooltip tooltip = new Tooltip(tooltipText);
                    Tooltip.install(newNode, tooltip);
                }
            });
        }
    }

    private void populateChart(List<ActivitySummaryDTO> summary) {
        if (summary == null || summary.isEmpty()) return;

        List<ActivitySummaryDTO> sortedSummary = new ArrayList<>(summary);
        sortedSummary.sort((a, b) -> {
            long totalA = (a.activeCount()) + (a.inactiveCount());
            long totalB = (b.activeCount()) + (b.inactiveCount());
            return Long.compare(totalB, totalA);
        });

        int limit = 15; 
        long otherActiveCount = 0;
        long otherInactiveCount = 0;
        boolean hasOthers = false;

        List<XYChart.Data<Number, String>> activeDataList = new ArrayList<>();
        List<XYChart.Data<Number, String>> inactiveDataList = new ArrayList<>();

        for (int i = 0; i < sortedSummary.size(); i++) {
            ActivitySummaryDTO dto = sortedSummary.get(i);
            long active = dto.activeCount();
            long inactive = dto.inactiveCount();
            String fullTitle = dto.windowTitle() != null ? dto.windowTitle() : "Unknown";

            if (i < limit) {
                String displayTitle = fullTitle;
                if (displayTitle.length() > 40) { 
                    displayTitle = displayTitle.substring(0, 40) + "...";
                }
                
                XYChart.Data<Number, String> activeData = new XYChart.Data<>(active, displayTitle);
                activeData.setExtraValue(fullTitle); 
                activeDataList.add(activeData);

                XYChart.Data<Number, String> inactiveData = new XYChart.Data<>(inactive, displayTitle);
                inactiveData.setExtraValue(fullTitle); 
                inactiveDataList.add(inactiveData);
            } else {
                hasOthers = true;
                otherActiveCount += active;
                otherInactiveCount += inactive;
            }
        }

        if (hasOthers) {
            String othersTitle = TLanguage.getInstance().getString(ItToolsKey.OTHER_WINDOWS);
            
            XYChart.Data<Number, String> activeData = new XYChart.Data<>(otherActiveCount, othersTitle);
            activeData.setExtraValue(othersTitle);
            activeDataList.add(activeData);

            XYChart.Data<Number, String> inactiveData = new XYChart.Data<>(otherInactiveCount, othersTitle);
            inactiveData.setExtraValue(othersTitle);
            inactiveDataList.add(inactiveData);
        }

        Collections.reverse(activeDataList);
        Collections.reverse(inactiveDataList);

        TLanguage lang = TLanguage.getInstance();
        setupTooltip(activeDataList, lang.getString(ItToolsKey.EMPLOYEE_ACTIVITY));
        setupTooltip(inactiveDataList, lang.getString(ItToolsKey.EMPLOYEE_INACTIVITY));

        activeSeries.getData().addAll(activeDataList);
        inactiveSeries.getData().addAll(inactiveDataList);
    }

    private void setupTooltip(List<XYChart.Data<Number, String>> dataList, String typeLabel) {
        for (XYChart.Data<Number, String> data : dataList) {
            data.nodeProperty().addListener((observable, oldNode, newNode) -> {
                if (newNode != null) {
                    String fullTitle = data.getExtraValue() != null ? data.getExtraValue().toString() : data.getYValue();
                    String tooltipText = String.format("%s\n%s: %s", fullTitle, typeLabel, data.getXValue());
                    
                    Tooltip tooltip = new Tooltip(tooltipText);
                    Tooltip.install(newNode, tooltip);
                }
            });
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