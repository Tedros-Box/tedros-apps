package org.tedros.it.tools.module.gmud.component;

import java.text.DateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.component.ITComponent;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TLabel;
import org.tedros.fx.control.TLongField;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.domain.GmudItemStatus;
import org.tedros.it.tools.domain.GmudStatus;
import org.tedros.it.tools.ejb.controller.IGmudController;
import org.tedros.it.tools.entity.Gmud;
import org.tedros.it.tools.entity.GmudItem;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TSelect;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TLoggerUtil;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class ExecuteGmudComponent extends BorderPane implements ITComponent {

	private TLongField gmudNumber;
	private TextField promptTitle;
	private TButton btnSearch;
	private TButton btnRefresh;

	private ListView<Gmud> listView;
	private ObservableList<Gmud> gmudList;

	private BorderPane detailPane;
	private ProgressBar progressBar;
	private TLabel lblProgress;
	private TableView<GmudItem> tableView;
	private ObservableList<GmudItem> tableData;

	private GmudProcess gmudProcess;
	private SaveGmudProcess saveGmudProcess;

	@SuppressWarnings("rawtypes")
	private ITView view;
	private SimpleBooleanProperty progressIndicatorVisible = new SimpleBooleanProperty(false);

	private Gmud selectedGmud;

	public ExecuteGmudComponent() {
		this.gmudList = FXCollections.observableArrayList();
		this.tableData = FXCollections.observableArrayList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void tInitializeComponent(ITComponentDescriptor descriptor) {
		view = descriptor.getForm().gettPresenter().getView();
		view.gettProgressIndicator().bind(progressIndicatorVisible);

		buildUI();

		gmudProcess = new GmudProcess();
		gmudProcess.setOnFailed(e -> showAlert(TMessageType.ERROR, gmudProcess.getException().getMessage()));
		gmudProcess.setOnSucceeded(e -> {
			List<TResult> lst = (List<TResult>) gmudProcess.getValue();
			if (lst != null && !lst.isEmpty()) {
				TResult result = lst.get(0);
				if (result.getValue() != null) {
					List<Gmud> gmuds = (List<Gmud>) result.getValue();
					gmudList.setAll(gmuds);
					if (!gmuds.isEmpty() && selectedGmud == null) {
						listView.getSelectionModel().selectFirst();
					} else if (!gmuds.isEmpty() && selectedGmud != null) {
						Long workingGmudId = selectedGmud.getId();
						clearDetail();
						for (Gmud g : gmudList) {
							if (g.getId().equals(workingGmudId)) {
								listView.getSelectionModel().select(g);
								break;
							}
						}
					} else {
						clearDetail();
					}
				}
			}
		});

		saveGmudProcess = new SaveGmudProcess();
		saveGmudProcess.setOnFailed(e -> showAlert(TMessageType.ERROR, saveGmudProcess.getException().getMessage()));
		saveGmudProcess.setOnSucceeded(e -> {
			List<TResult<Gmud>> lst = saveGmudProcess.getValue();
			if (!lst.isEmpty()) {
				lst.stream().forEach(r -> {
					if (r.getState() == TState.SUCCESS) {
						// Update the GMUD in the list
						Gmud savedGmud = r.getValue();
						updateGmudInList(savedGmud);
						// Refresh details if this is the selected one
						if (selectedGmud != null && selectedGmud.getId().equals(savedGmud.getId())) {
							updateDetail(savedGmud);
						}
					} else if (r.getState() == TState.ERROR) {
						showAlert(TMessageType.ERROR, r.getMessage());
					}else if (r.getState() == TState.WARNING) {
						showAlert(TMessageType.WARNING, r.getMessage());
					}else if (r.getState() == TState.OUTDATED) {
						showAlert(TMessageType.WARNING, TFxKey.MESSAGE_OUTDATE 
								+ ItToolsKey.TEXT_SCREEN_REFRESHED_RETRY_OPERATION);
						search();
					}
				});
			}
		});

		progressIndicatorVisible.bind(gmudProcess.runningProperty().or(saveGmudProcess.runningProperty()));

		// Initial search
		search();
	}

	@Override
	public void tStopComponent() {
		// Cleanup if needed
	}

	private void buildUI() {
		SplitPane splitPane = new SplitPane();
        splitPane.setStyle("-fx-background-color: transparent;");
		splitPane.getItems().addAll(createMasterView(), createDetailView());
		splitPane.setDividerPositions(0.3);

		this.setCenter(splitPane);
	}

	private VBox createMasterView() {
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(10));

		// Filters
		HBox filters = new HBox(5);
		filters.setAlignment(Pos.CENTER_LEFT);

		gmudNumber = new TLongField();
		gmudNumber.setPromptText(TLanguage.getInstance().getString(TUsualKey.NUMBER));
		gmudNumber.setPrefWidth(80);

		promptTitle = new TextField();
		promptTitle.setPromptText(TLanguage.getInstance().getString(TUsualKey.TITLE));
		HBox.setHgrow(promptTitle, Priority.ALWAYS);

		btnSearch = new TButton(TLanguage.getInstance().getString(TUsualKey.SEARCH));
		btnSearch.setOnAction(e -> search());

		btnRefresh = new TButton(TLanguage.getInstance().getString(TUsualKey.REFRESH));
		btnRefresh.setOnAction(e -> search());

		filters.getChildren().addAll(gmudNumber, promptTitle, btnSearch);

		// List
		listView = new ListView<>(gmudList);
		listView.setCellFactory(param -> new GmudListCell());
		listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal != null) {
				updateDetail(newVal);
			}
		});
		VBox.setVgrow(listView, Priority.ALWAYS);

		vbox.getChildren().addAll(filters, listView, btnRefresh);
		return vbox;
	}

	private BorderPane createDetailView() {
		detailPane = new BorderPane();
		detailPane.setPadding(new Insets(10));

		// Initially empty
		detailPane.setCenter(new TLabel(TLanguage.getInstance().getString(ItToolsKey.TEXT_SELECT_ITEMS_TO_UPDATE)));

		return detailPane;
	}

	private void clearDetail() {
		detailPane.setTop(null);
		detailPane.setCenter(new TLabel(TLanguage.getInstance().getString(ItToolsKey.TEXT_SELECT_ITEMS_TO_UPDATE)));
		selectedGmud = null;
		tableData.clear();
	}

	private void updateDetail(Gmud gmud) {
		selectedGmud = gmud;

		// Header
		VBox header = new VBox(5);
		header.setPadding(new Insets(0, 0, 10, 0));

		HBox titleRow = new HBox(10);
		titleRow.setAlignment(Pos.CENTER_LEFT);
		TLabel lblTitle = new TLabel(gmud.getTitle());
		lblTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
		
		titleRow.getChildren().addAll(lblTitle, getSpacer());

		HBox dateRow = new HBox(10);
		dateRow.setAlignment(Pos.CENTER_LEFT);
		String dateStr = gmud.getScheduledDate() != null
				? DateFormat.getDateInstance(DateFormat.MEDIUM, TLanguage.getLocale()).format(gmud.getScheduledDate())
				: "-";
		dateRow.getChildren()
				.add(new TLabel(TLanguage.getInstance().getString(ItToolsKey.EXECUTION_DATE) + ": " + dateStr));

		// Progress
		HBox progressRow = new HBox(5);
		progressRow.setAlignment(Pos.CENTER_LEFT);
		progressBar = new ProgressBar(0);
		progressBar.setPrefWidth(200);
		lblProgress = new TLabel("0%");
		progressRow.getChildren().addAll(new TLabel(TLanguage.getInstance().getString(TUsualKey.STATUS) + ":"),
				progressBar, lblProgress);

		header.getChildren().addAll(titleRow, dateRow, progressRow);

		// Table
		createItemTable();
		if (gmud.getExecutionPlan() != null) {

			// Defensive sort
			List<GmudItem> items = gmud.getExecutionPlan().stream()
					.sorted(Comparator.comparing(GmudItem::getStepOrder,
							Comparator.nullsLast(Comparator.naturalOrder())))
					.collect(Collectors.toList());

			tableData.setAll(items);
		} else {
			tableData.clear();
		}

		updateProgressState();

		detailPane.setTop(header);
		detailPane.setCenter(tableView);
	}

	private void updateProgressState() {
		if (tableData.isEmpty()) {
			progressBar.setProgress(0);
			lblProgress.setText("0%");
			return;
		}

		long total = tableData.size();
		long completed = tableData.stream()
				.filter(i -> GmudItemStatus.FINISHED.getDescription().equals(i.getStatus()))
				.count();

		double p = (double) completed / total;
		progressBar.setProgress(p);
		lblProgress.setText(String.format("%.0f%%", p * 100));			
	}

	@SuppressWarnings("unchecked")
	private void createItemTable() {
		TLanguage lang = TLanguage.getInstance();
		tableView = new TableView<>(tableData);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<GmudItem, Integer> colStep = new TableColumn<>(lang.getString(ItToolsKey.EXECUTION_ORDER));
		colStep.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getStepOrder()));
		colStep.setMaxWidth(60);
		colStep.setMinWidth(60);

		TableColumn<GmudItem, String> colAction = new TableColumn<>(lang.getString(TUsualKey.ACTION));
		colAction.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getAction()));

		TableColumn<GmudItem, String> colResp = new TableColumn<>(lang.getString(TUsualKey.RESPONSABLE));
		colResp.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getResponsible().getName()));

		TableColumn<GmudItem, String> colStatus = new TableColumn<>(lang.getString(TUsualKey.STATUS));
		colStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));

		TableColumn<GmudItem, GmudItem> colUpdate = new TableColumn<>(lang.getString(TUsualKey.EDIT));
		colUpdate.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue()));
		colUpdate.setCellFactory(param -> new StatusUpdateCell());
		
		tableView.getColumns().addAll(colStep, colAction, colResp, colStatus, colUpdate);
	}

	private class StatusUpdateCell extends TableCell<GmudItem, GmudItem> {
		private ComboBox<GmudItemStatus> cmb = new ComboBox<>();

		public StatusUpdateCell() {
			cmb.getItems().addAll(GmudItemStatus.values());
			cmb.setConverter(new StringConverter<GmudItemStatus>() {
				@Override
				public String toString(GmudItemStatus object) {
					return object != null ? object.getDescription() : "";
				}

				@Override
				public GmudItemStatus fromString(String string) {
					return null;
				}
			});

			cmb.setOnAction(e -> {
				GmudItem item = getItem();
				if (item != null && cmb.getValue() != null) {
					String newStatus = cmb.getValue().getDescription();
					if (!newStatus.equals(item.getStatus())) {
						if(newStatus.equals(GmudItemStatus.FINISHED.getDescription())) {
							// Additional check: only allow FINISHED if current status is IN_PROGRESS
							if(!item.getStatus().equals(GmudItemStatus.EXECUTING.getDescription())) {
								Platform.runLater(() ->
									showAlert(TMessageType.WARNING, TLanguage.getInstance()
											.getString(ItToolsKey.TEXT_ONLY_ITEMS_EXECUTING_CAN_BE_FINISHED))
								);
								// Revert selection
								cmb.setValue(GmudItemStatus.fromDescription(item.getStatus()));
								return;
							}
						}else if(newStatus.equals(GmudItemStatus.EXECUTING.getDescription())) {
							// Additional check: only allow IN_PROGRESS if current status is not FINISHED
							if(item.getStatus().equals(GmudItemStatus.FINISHED.getDescription())) {
								Platform.runLater(() ->
									showAlert(TMessageType.WARNING, TLanguage.getInstance()
											.getString(ItToolsKey.TEXT_CANNOT_REVERT_FINISHED_ITEMS_TO_EXECUTING))
									);
								// Revert selection
								cmb.setValue(GmudItemStatus.fromDescription(item.getStatus()));
								return;
							}
							if(!selectedGmud.getStatus().equals(GmudStatus.EXECUTING.getDescription())) {
								selectedGmud.setStatus(GmudStatus.EXECUTING.getDescription());
							}
						}else if(newStatus.equals(GmudItemStatus.FAILED.getDescription())) {
							
							if(item.getStatus().equals(GmudItemStatus.FINISHED.getDescription())) {
								Platform.runLater(() ->
									showAlert(TMessageType.WARNING, TLanguage.getInstance()
											.getString(ItToolsKey.TEXT_CANNOT_REVERT_FINISHED_ITEMS_TO_FAILED))
									);
								// Revert selection
								cmb.setValue(GmudItemStatus.fromDescription(item.getStatus()));
								return;
							}
							
							if(!item.getStatus().equals(GmudItemStatus.EXECUTING.getDescription())) {
								Platform.runLater(() ->
									showAlert(TMessageType.WARNING, TLanguage.getInstance()
											.getString(ItToolsKey.TEXT_ONLY_ITEMS_EXECUTING_CAN_BE_FAILED))
									);
								// Revert selection
								cmb.setValue(GmudItemStatus.fromDescription(item.getStatus()));
								return;
							}
							
							// If marking as FAILED, set GMUD status to FAILED as well
							selectedGmud.setStatus(GmudStatus.FAILED.getDescription());
						}
						
						item.setStatus(newStatus);
						saveGmudItems();
					}
				}
			});
		}

		@Override
		protected void updateItem(GmudItem item, boolean empty) {
			super.updateItem(item, empty);
			if (empty || item == null) {
				setGraphic(null);
			} else {
				// Check permissions
				boolean isResponsible = item.getResponsible().getTedrosUserId()
						.equals(TedrosContext.getLoggedUser().getId());
				cmb.setDisable(!isResponsible);

				// Set current value
				GmudItemStatus current = null;
				for (GmudItemStatus s : GmudItemStatus.values()) {
					if (s.getDescription().equals(item.getStatus())) {
						current = s;
						break;
					}
				}
				cmb.setValue(current);
				setGraphic(cmb);
			}
		}
		
		private void saveGmudItems() {
			// We save the whole GMUD entity to ensure consistency
			if (selectedGmud != null) {
				saveGmudProcess.save(selectedGmud);
				saveGmudProcess.startProcess();
			}
		}
	}	

	private void updateGmudInList(Gmud updated) {
		for (int i = 0; i < gmudList.size(); i++) {
			if (gmudList.get(i).getId().equals(updated.getId())) {
				gmudList.set(i, updated);
				return;
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void search() {
		try {
			// Select GMUDs that are APPROVED or EXECUTING
			TSelect<Gmud> select = new TSelect<>(Gmud.class);
			// Status Filter (OR condition)
			// select.addOrCondition ... but TSelect might not support complex nested OR
			// depending on version.
			// Assuming we filter by IN or multiple TSelects, but standard way:
			// Let's rely on restrictions.
			// Note: TSelect usually does AND. For status IN ...
			// Let's construct a restriction if possible or just fetch both statuses logic
			// likely handled in query construction or multiple conditions.
			// Simple approach: TSelect with dynamic query construction often supports 'IN'
			// via custom logic or just separate conditions.
			// Given TSelect API shown in precedents, let's use:
			// (Status = APPROVED OR Status = EXECUTING)

			// Actually simpler: Just filter by approved/executing in the query if possible.
			// If TSelect doesn't support convenient OR, we might need two queries or a
			// custom JPQL.
			// Assuming standard Tedros behavior allowed:
			
			select.addAndCondition("status", TCompareOp.EQUAL,
					GmudStatus.APPROVED.getDescription());
			select.addOrCondition("status", TCompareOp.EQUAL,
					GmudStatus.EXECUTING.getDescription());
			
			if (gmudNumber.getValue() != null) {
				select.addAndCondition("id", TCompareOp.EQUAL, gmudNumber.getValue());
			}

			if (promptTitle.getText() != null && !promptTitle.getText().isEmpty()) {
				select.addAndCondition("title", TCompareOp.LIKE, "%" + promptTitle.getText() + "%");
			}

			// Optional: We could join with GmudItem to filter only my GMUDs, but
			// requirements say "Optional optimization".
			// For now, let's show all active GMUDs so the team can see what's happening.

			// Ordering
			select.addOrderBy("scheduledDate"); // ASC

			gmudProcess.search(select);
			gmudProcess.startProcess();

		} catch (Exception e) {
			TLoggerUtil.error(this.getClass(), "Error searching GMUDs", e);
			showAlert(TMessageType.ERROR, e.getMessage());
		}
	}

	private void showAlert(TMessageType type, String content) {
		TMessage message = new TMessage(type, content);
		view.tShowModal(new TMessageBox(message), true);
	}

	private javafx.scene.Node getSpacer() {
		javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		return spacer;
	}

	// Inner Classes for Processes

	@SuppressWarnings("rawtypes")
	private class GmudProcess extends TEntityProcess {
		@SuppressWarnings("unchecked")
		public GmudProcess() {
			super(Gmud.class, IGmudController.JNDI_NAME);
		}
	}

	private class SaveGmudProcess extends TEntityProcess<Gmud> {
		public SaveGmudProcess() {
			super(Gmud.class, IGmudController.JNDI_NAME);
		}
	}

	// Cell Factory for ListView
	private class GmudListCell extends ListCell<Gmud> {
		
		@Override
		protected void updateItem(Gmud item, boolean empty) {
			super.updateItem(item, empty);
			if (empty || item == null) {
				setGraphic(null);
			} else {
				VBox v = new VBox(2);
				Label lblId = new Label("#" + item.getId() + " - " + item.getTitle());
				lblId.setStyle("-fx-font-weight: bold;");

				Label lblProject = new Label(item.getProjectName());
				lblProject.setStyle("-fx-font-size: 10px;");

				Label lblStatus = new Label(item.getStatus());
				String color = GmudStatus.EXECUTING.getDescription().equals(item.getStatus()) ? "blue" : "green";
				lblStatus.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 11px; -fx-font-weight: bold;");
				
				v.getChildren().addAll(lblId, lblProject, lblStatus);
				setGraphic(v);
			}
		}
	}
}
