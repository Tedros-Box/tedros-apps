package org.tedros.it.tools.module.gmud.component;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.presenter.view.ITView;
import org.tedros.api.presenter.view.TDetachViewType;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.component.ITComponent;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TDatePickerField;
import org.tedros.fx.control.TLabel;
import org.tedros.fx.control.TLongField;
import org.tedros.fx.control.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.domain.GmudStatus;
import org.tedros.it.tools.ejb.controller.IGmudReviewController;
import org.tedros.it.tools.entity.Gmud;
import org.tedros.it.tools.entity.GmudReview;
import org.tedros.it.tools.module.gmud.model.ReadGmudMV;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TJoinType;
import org.tedros.server.query.TSelect;
import org.tedros.server.result.TResult;
import org.tedros.util.TLoggerUtil;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class ReviewGmudComponent extends BorderPane implements ITComponent {

	private TLongField gmudNumber;
	private TextField promptTitle;
	private ComboBox<GmudStatus> cmbStatus;
	private TDatePickerField datePicker;
	private TextField txtProject;
	private TButton btnSearch;

	private TableView<GmudReview> tableView;
	private ObservableList<GmudReview> tableData;
	private Set<GmudReview> selectedReviews;
	private CheckBox cbSelectAll;

	private ComboBox<GmudStatus> cmbActionStatus;
	private TButton btnApply;

	GmudReviewProcess gmudReviewProcess;
	
	@SuppressWarnings("rawtypes")
	private ITView view;
	private SimpleBooleanProperty progressIndicatorVisible = new SimpleBooleanProperty(false);

	public ReviewGmudComponent() {
		this.selectedReviews = new HashSet<>();
		this.tableData = FXCollections.observableArrayList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void tInitializeComponent(ITComponentDescriptor descriptor) {
		view = descriptor.getForm().gettPresenter().getView();
		view.gettProgressIndicator().bind(progressIndicatorVisible);
		buildUI();
		
		gmudReviewProcess = new GmudReviewProcess();
		gmudReviewProcess.setOnFailed(e -> showAlert(TMessageType.ERROR, gmudReviewProcess.getException().getMessage()));
		gmudReviewProcess.setOnSucceeded(e -> {
			List<TResult> lst = (List<TResult>) gmudReviewProcess.getValue();
			TResult result =  lst.get(0);
			if(result.getValue()!=null) {
				List<GmudReview> reviews = (List<GmudReview>) result.getValue();
				tableData.setAll(reviews);
				selectedReviews.clear();
				cbSelectAll.setSelected(false);
			}
		});
		
		progressIndicatorVisible.bind(gmudReviewProcess.runningProperty());
		
	}

	@Override
	public void tStopComponent() {
		// Cleanup if needed
	}	

	private void buildUI() {
		this.setPadding(new Insets(10));
		this.setTop(createFilters());
		this.setCenter(createTable());
		this.setBottom(createActions());
	}

	private HBox createFilters() {
		TLanguage lang = TLanguage.getInstance();
		HBox hbox = new HBox(10);
		hbox.setPadding(new Insets(0, 0, 10, 0));
		hbox.setAlignment(Pos.CENTER_LEFT);
		
		gmudNumber = new TLongField();
		VBox vGmudNumber = new VBox(2, new TLabel(lang.getString(TUsualKey.NUMBER)), gmudNumber);

		promptTitle = new TextField();
		cmbStatus = new ComboBox<>();
		
		VBox vTitle = new VBox(2, new TLabel(lang.getString(TUsualKey.TITLE)), promptTitle);
		VBox vStatus = new VBox(2, new TLabel(lang.getString(TUsualKey.STATUS)), cmbStatus);
		cmbStatus.getItems().add(null);		
		cmbStatus.getItems().addAll(GmudStatus.values());
		cmbStatus.setConverter(new StringConverter<GmudStatus>() {
			@Override
			public String toString(GmudStatus object) {
				return object != null ? object.getDescription() : "";
			}

			@Override
			public GmudStatus fromString(String string) {
				return null;
			}
		});
		
		datePicker = new TDatePickerField();
		txtProject = new TextField();
				
		VBox vDate = new VBox(2, new TLabel(lang.getString(ItToolsKey.EXECUTION_DATE)), datePicker);
		VBox vProject = new VBox(2, new TLabel(lang.getString(ItToolsKey.GITLAB_PROJECT)), txtProject);

		btnSearch = new TButton(lang.getString(TUsualKey.SEARCH));
		btnSearch.setOnAction(e -> search());

		VBox vBtn = new VBox(2, new TLabel(""), btnSearch);
		vBtn.setAlignment(Pos.BOTTOM_LEFT);

		hbox.getChildren().addAll(vGmudNumber, vTitle, vStatus, vDate, vProject, vBtn);
		return hbox;
	}

	@SuppressWarnings("unchecked")
	private VBox createTable() {
		TLanguage lang = TLanguage.getInstance();
		
		tableView = new TableView<>();
		tableView.setItems(tableData);
		tableView.setEditable(true);
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// Selection Column
		TableColumn<GmudReview, Boolean> colSelect = new TableColumn<>();
		cbSelectAll = new CheckBox();
		cbSelectAll.setOnAction(e -> toggleSelectAll());
		colSelect.setGraphic(cbSelectAll);
		colSelect.setText(lang.getString(TUsualKey.SELECT));
		colSelect.setSortable(false);
		colSelect.setMinWidth(90);
		colSelect.setMaxWidth(90);
		colSelect.setPrefWidth(90);
		colSelect.setResizable(true);
		colSelect.setCellValueFactory(cell -> {
			GmudReview r = cell.getValue();
			SimpleBooleanProperty prop = new SimpleBooleanProperty(selectedReviews.contains(r));
			prop.addListener((obs, old, val) -> {
				if (val)
					selectedReviews.add(r);
				else
					selectedReviews.remove(r);
			});
			return prop;
		});
		colSelect.setCellFactory(col -> new TableCell<GmudReview, Boolean>() {
			private final CheckBox cb = new CheckBox();

			@Override
			protected void updateItem(Boolean item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setGraphic(null);
				} else {
					cb.setSelected(item);
					cb.selectedProperty().unbind(); // clear previous bindings
					cb.selectedProperty().addListener((obs, old, val) -> {
						// Reflection back to property is handled by CellValueFactory binding usually,
						// but here we are manually managing the set.
						// Actually, simpler to just listen to checkbox changes if value factory is
						// read-only or not providing writable prop effectively.
						// Let's bind bidirectional if possible, but booleanproperty created on fly
						// isn't bound to model.
						// So we manually update model (Set) from CellValueFactory listener.
						// To update CheckBox from Model, we rely on updateItem.
						// To update Model from CheckBox, we add listener.
					});
					// BINDING FIX:
					// The SimpleBooleanProperty in CellValueFactory reflects the Set state.
					// But changing the CheckBox should update the property?
					// No, the Property is creating a NEW instance every time.

					// BETTER APPROACH:
					// Just handle CheckBox events here to update Set.
					// And update CheckBox state from Set in updateItem.
					cb.setOnAction(e -> {
						GmudReview r = getTableView().getItems().get(getIndex());
						if (cb.isSelected())
							selectedReviews.add(r);
						else
							selectedReviews.remove(r);
					});
					setGraphic(cb);
				}
			}
		});

		// GMUD Number (ID)
		TableColumn<GmudReview, Long> colId = new TableColumn<>(lang.getString(TUsualKey.NUMBER));
		colId.setMinWidth(80);
        colId.setMaxWidth(90);
        colId.setPrefWidth(70);
        colId.setResizable(true);
		colId.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getGmud().getId()));

		// Title
		TableColumn<GmudReview, String> colTitle = new TableColumn<>(lang.getString(TUsualKey.TITLE));
		colTitle.setMinWidth(200); 
        colTitle.setMaxWidth(Double.MAX_VALUE); 
		colTitle.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getGmud().getTitle()));

		// Reviewer Name
		TableColumn<GmudReview, String> colReviewer = new TableColumn<>(lang.getString(ItToolsKey.REVIEWER));
		colReviewer.setMinWidth(200);
        colReviewer.setMaxWidth(250);
		colReviewer.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getReviewer().getName()));

		// GMUD Status
		TableColumn<GmudReview, String> colGStatus = new TableColumn<>(lang.getString(ItToolsKey.GMUD_STATUS));
		colGStatus.setMinWidth(100);
        colGStatus.setMaxWidth(120);
		colGStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getGmud().getStatus()));

		// Review Status
		TableColumn<GmudReview, String> colRStatus = new TableColumn<>(lang.getString(ItToolsKey.REVIEW_STATUS));
		colRStatus.setMinWidth(100);
		colRStatus.setMaxWidth(120);
		colRStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));

		// Type
		TableColumn<GmudReview, String> colType = new TableColumn<>(lang.getString(TUsualKey.TYPE));
		colType.setMinWidth(100);
        colType.setMaxWidth(120);
		colType.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getGmud().getType()));

		// Review Date
		TableColumn<GmudReview, String> colDate = new TableColumn<>(lang.getString(ItToolsKey.REVIEW_DATE));
		colDate.setMinWidth(180);
        colDate.setMaxWidth(220);
        colDate.setCellValueFactory(c -> {
        	Date dt = c.getValue().getGmud().getScheduledDate();
			return new SimpleStringProperty(dt != null ? DateFormat
					.getDateInstance(DateFormat.MEDIUM, TLanguage.getLocale())
					.format(dt) : "");
		});

		// Project Name
		TableColumn<GmudReview, String> colProject = new TableColumn<>(lang.getString(ItToolsKey.GITLAB_PROJECT));
		colProject.setMinWidth(100);
        colProject.setMaxWidth(220);
		colProject.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getGmud().getProjectName()));

		// Review Comment (Editable)
		TableColumn<GmudReview, String> colComment = new TableColumn<>(lang.getString(TUsualKey.COMMENT));
		colComment.setMinWidth(245);
		colComment.setMaxWidth(400);
		colComment.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getComments()));
		colComment.setCellFactory(TextFieldTableCell.forTableColumn());
		colComment.setOnEditCommit(e -> {
			GmudReview r = e.getRowValue();
			r.setComments(e.getNewValue());
			// Optionally save immediately or wait for "Apply"
		});

		tableView.getColumns().addAll(colSelect, colId, colTitle, colReviewer, colGStatus, colRStatus, colType, colDate,
				colProject, colComment);

		// Double click
		tableView.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2 && tableView.getSelectionModel().getSelectedItem() != null) {
				showGmudDetails(tableView.getSelectionModel().getSelectedItem().getGmud());
			}
		});

		VBox vbox = new VBox(tableView);
		VBox.setVgrow(tableView, Priority.ALWAYS);
		return vbox;
	}

	private HBox createActions() {
		TLanguage lang = TLanguage.getInstance();
		HBox hbox = new HBox(10);
		hbox.setPadding(new Insets(10, 0, 0, 0));
		hbox.setAlignment(Pos.CENTER_LEFT);

		cmbActionStatus = new ComboBox<>();
		cmbActionStatus.getItems().addAll(GmudStatus.values());
		cmbActionStatus.setPromptText(lang.getString(ItToolsKey.SELECT_STATUS_TO_APPLY));
		cmbActionStatus.setConverter(new StringConverter<GmudStatus>() {
			@Override
			public String toString(GmudStatus object) {
				return object != null ? object.getDescription() : "";
			}

			@Override
			public GmudStatus fromString(String string) {
				return null;
			}
		});

		btnApply = new TButton(lang.getString(ItToolsKey.APPLY_CHANGES));
		btnApply.setOnAction(e -> applyChanges());

		hbox.getChildren().addAll(new TLabel(lang.getString(ItToolsKey.UPDATE_STATUS)), cmbActionStatus, btnApply);
		return hbox;
	}

	private void toggleSelectAll() {
		if (cbSelectAll.isSelected()) {
			selectedReviews.addAll(tableData);
		} else {
			selectedReviews.clear();
		}
		tableView.refresh();
	}

	@SuppressWarnings("unchecked")
	private void search() {
		
		try {
			TSelect<GmudReview> select = new TSelect<>(GmudReview.class);
			select.addJoin(TJoinType.INNER, TSelect.ALIAS, "gmud", "g1");
			select.addJoin(TJoinType.INNER, TSelect.ALIAS, "reviewer", "r1");
			
			// Filters
			if (gmudNumber.getText() != null && !gmudNumber.getText().isEmpty()) {
				select.addAndCondition("g1", "id", TCompareOp.EQUAL, Long.parseLong(gmudNumber.getText()));
			}

			// Filters
			if (promptTitle.getText() != null && !promptTitle.getText().isEmpty()) {
				select.addAndCondition("g1", "title", TCompareOp.LIKE, "%" + promptTitle.getText() + "%");
			}
			if (cmbStatus.getValue() != null) {
				select.addAndCondition("g1", "status", TCompareOp.EQUAL, cmbStatus.getValue().getDescription()); 
			}
			if (datePicker.getValue() != null) {
				Date date = datePicker.getValue();
				select.addAndCondition("g1", "scheduledDate", TCompareOp.EQUAL, date);
			}
			if (txtProject.getText() != null && !txtProject.getText().isEmpty()) {
				select.addAndCondition("g1", "projectName", TCompareOp.LIKE, "%" + txtProject.getText() + "%");
			}

			// Current User Filter
			// Assuming TedrosContext is available and has getLoggedUser()
			select.addAndCondition("r1", "tedrosUserId", TCompareOp.EQUAL, TedrosContext.getLoggedUser().getId());

			gmudReviewProcess.search(select);
			gmudReviewProcess.startProcess();
			

		} catch (Exception e) {
			TLoggerUtil.error(this.getClass(), "Error during search", e);
			showAlert(TMessageType.ERROR, e.getMessage());
		}
	}

	private void applyChanges() {
		GmudStatus newStatus = cmbActionStatus.getValue();
		if (newStatus == null) {
			showAlert(TMessageType.WARNING, ItToolsKey.TEXT_SELECT_STATUS_TO_APPLY);
			return;
		}
		if (selectedReviews.isEmpty()) {
			showAlert(TMessageType.WARNING, ItToolsKey.TEXT_SELECT_ITEMS_TO_UPDATE);
			return;
		}

		try {
			// In a real scenario, we might batch update or iterate.
			// Implementing iterative update for now as bulk update method isn't specified.
			boolean allSuccess = true;
			for (GmudReview r : selectedReviews) {
				r.setStatus(newStatus.name()); // Assuming string status
				r.setReviewDate(new Date());
				/*TResult<GmudReview> res = controller.save(null, r); // Assuming save/update exists
				if (!res.getState().equals(TState.SUCCESS)) {
					allSuccess = false;
				}*/
			}
			if (allSuccess) {
				//showErrorAlert("Success", "Updated successfully", "Selected items have been updated.");
				search(); // Refresh
			} else {
				//showErrorAlert("Warning", "Partial Success", "Some items may not have been updated.");
				search();
			}
		} catch (Exception e) {
			TLoggerUtil.error(this.getClass(), "Error during apply changes", e);
			showAlert(TMessageType.ERROR, e.getMessage());
		}
	}

	private void showGmudDetails(Gmud gmud) {
		
		ObservableList<ReadGmudMV> l = FXCollections.observableArrayList(new ReadGmudMV(gmud));
		// 3) The View, note: we instantiate it but who decorates and behaves it is the
		// presenter in it.
		TDynaView<ReadGmudMV> v = new TDynaView<>(ReadGmudMV.class, l, false, TDetachViewType.NONE);
		v.tLoad();
		
		TText title = new TText(TLanguage.getInstance().getString(ItToolsKey.GMUD_DETAILS) + " "+ gmud.getTitle());
		title.settTextStyle(TTextStyle.LARGE);
		TButton btnClose = new TButton(TLanguage.getInstance().getString(TUsualKey.CLOSE));
		btnClose.setOnAction(e -> { 
			view.tHideModal();
			v.gettPresenter().invalidate();
		});
		
		BorderPane sp = new BorderPane();
		sp.getStyleClass().add("custom-popup");
		sp.setPadding(new Insets(10));
		sp.setTop(new HBox(10, title, btnClose));
		sp.setCenter(v);
		
		view.tShowModal(sp, false);
		
	}

	private void showAlert(TMessageType type, String content) {
		TMessage message = new TMessage(type, content);
		view.tShowModal(new TMessageBox(content), true); 
	}
	
	@SuppressWarnings("rawtypes")
	private class GmudReviewProcess extends TEntityProcess {
		@SuppressWarnings("unchecked")
		public GmudReviewProcess() {
			super(GmudReview.class, IGmudReviewController.JNDI_NAME);
		}
	}
		
}
