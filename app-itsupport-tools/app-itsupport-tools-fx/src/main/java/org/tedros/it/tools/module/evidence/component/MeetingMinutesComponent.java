package org.tedros.it.tools.module.evidence.component;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.tedros.ai.service.AiTerosContext;
import org.tedros.ai.service.DocumentConverter;
import org.tedros.ai.service.DocumentConverter.ProcessedDocument;
import org.tedros.ai.service.IAiTerosService;
import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.presenter.view.ITView;
import org.tedros.common.model.TByteEntity;
import org.tedros.common.model.TFileEntity;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.component.ITComponent;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.THyperlink;
import org.tedros.fx.control.TLabel;
import org.tedros.fx.control.TMaskField;
import org.tedros.fx.domain.TLayoutType;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.layout.TFieldSet;
import org.tedros.fx.layout.TToolBar;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.fx.process.TProcess;
import org.tedros.fx.process.TTaskImpl;
import org.tedros.fx.property.TBytesLoader;
import org.tedros.integration.redmine.api.model.TIssueEvidenceInfo;
import org.tedros.integration.redmine.gateway.MeetingMinutesRedmineUpdateResult;
import org.tedros.integration.redmine.gateway.RedmineApiGateway;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.entity.MeetingAgenda;
import org.tedros.it.tools.entity.MeetingEvidence;
import org.tedros.it.tools.entity.MeetingMinutes;
import org.tedros.it.tools.entity.MeetingReferral;
import org.tedros.it.tools.module.evidence.model.MeetingMinutesMV;
import org.tedros.it.tools.module.evidence.report.process.MeetingMinutesReportProcess;
import org.tedros.it.tools.redmine.ai.function.RedmineApiPropertyUtil;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TLoggerUtil;
import org.tedros.util.TedrosFolder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class MeetingMinutesComponent extends StackPane implements ITComponent {

	private static final String FX_BACKGROUND_COLOR = "-fx-background-color: #ffec8e";
	private static final Logger LOGGER = TLoggerUtil.getLogger(MeetingMinutesComponent.class);
	private static final String MEETING_JSON_SYSTEM_PROMPT = """
			You extract structured meeting minutes from a transcription.
			Reply with ONLY a valid JSON object (no markdown fences) using this schema:
			{
			  "meetingPlace": "string",
			  "meetingDate": "string (prefer dd/MM/yyyy)",
			  "meetingStartTime": "string (HH:mm)",
			  "meetingFinishTime": "string (HH:mm)",
			  "meetingTopic": "string",
			  "participants": "string (comma-separated names)",
			  "meetingAgenda": [{"item":"01","description":"..."}],
			  "meetingReferrals": [{"item":"01","description":"...","responsable":"...","deadline":"..."}],
			  "nextMeetingPlace": "string",
			  "nextMeetingDate": "string",
			  "nextMeetingStartTime": "string",
			  "nextMeetingFinishTime": "string"
			}
			Fill item numbers as 01, 02, ... Infer missing times/dates when possible; otherwise use empty string.
			""";

	private MeetingMinutesMV mv;
	private File transcriptionFile;
	private TLabel lblTranscription;
	private TextField tfMeetingPlace;
	private TMaskField tfMeetingDate;
	private TMaskField tfMeetingStart;
	private TMaskField tfMeetingFinish;
	private TextArea taMeetingTopic;
	private TextArea taParticipants;
	private TextField tfNextPlace;
	private TMaskField tfNextDate;
	private TMaskField tfNextStart;
	private TMaskField tfNextFinish;
	private ListView<MeetingAgenda> lvAgenda;
	private ListView<MeetingReferral> lvReferrals;
	private ListView<MeetingEvidence> lvEvidences;
	private TextField tfIssueNumber;
	private TextField tfIssueTitle;
	private TextField tfActivityId;
	private TLabel lblStatus;
	private THyperlink linkPdfPath;
	
	
	private TerosFillService terosFillService;
	private MeetingMinutesReportProcess meetingMinutesReportProcess;
	private SimpleBooleanProperty progressIndicatorVisible = new SimpleBooleanProperty(false);
	private TRepository repo; 

	@Override
	@SuppressWarnings("rawtypes")
	public void tInitializeComponent(ITComponentDescriptor descriptor) {
		mv = (MeetingMinutesMV) descriptor.getModelView();
		ITView view = descriptor.getForm().gettPresenter().getView();
		view.gettProgressIndicator().bind(progressIndicatorVisible);
		repo = ((TDynaPresenter) ((TDynaView)view).gettPresenter()).getBehavior().getForm().gettObjectRepository();
		
		buildUi();
		bindFromMv();
		
		// Teros Fill Service
		terosFillService = new TerosFillService();
		terosFillService.setOnSucceeded(ev -> {
			String json = terosFillService.getValue();
			try {
				if (StringUtils.isNotBlank(terosFillService.getTranscriptionText())) {
					mv.getTranscription().set(terosFillService.getTranscriptionText());
				}
				applyAiJson(json);
				lblStatus.setText(TLanguage.getInstance().getString(ItToolsKey.MEETING_AI_DONE));
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
				lblStatus.setText(ex.getMessage());
				alert(AlertType.ERROR, ex.getMessage());
			}
		});
		terosFillService.setOnFailed(ev -> {
			Throwable ex = terosFillService.getException();
			lblStatus.setText(ex != null ? ex.getMessage() : "AI error");
			alert(AlertType.ERROR, lblStatus.getText());
		});
		
		// Report Process
		meetingMinutesReportProcess = new MeetingMinutesReportProcess();
		meetingMinutesReportProcess.setOnSucceeded(ev -> {
			TResult<String> res = meetingMinutesReportProcess.getValue();
			if (res != null && res.getState() == TState.SUCCESS) {
				String path = res.getValue() != null ? res.getValue() : res.getMessage();
				mv.getGeneratedPdfPath().set(path);
				linkPdfPath.setText(path);
				lblStatus.setText(TLanguage.getInstance().getString(ItToolsKey.MEETING_PDF_OK));
			} else {
				String msg = res != null ? res.getMessage() : "PDF error";
				lblStatus.setText(msg);
				alert(AlertType.ERROR, msg);
			}
		});
		meetingMinutesReportProcess.setOnFailed(ev -> {
			Throwable ex = meetingMinutesReportProcess.getException();
			alert(AlertType.ERROR, ex != null ? ex.getMessage() : "PDF error");
		});
		
		progressIndicatorVisible.bind(terosFillService.runningProperty()
				.or(meetingMinutesReportProcess.runningProperty()));
	}

	@Override
	public void tStopComponent() {
	}

	private void buildUi() {
		VBox root = new VBox(12);
		root.setPadding(new Insets(10));

		lblTranscription = new TLabel(TLanguage.getInstance().getString(ItToolsKey.MEETING_TRANSCRIPTION_NONE));
		TButton btnPick = new TButton(TLanguage.getInstance().getString(ItToolsKey.MEETING_SELECT_TRANSCRIPTION));
		btnPick.setOnAction(e -> pickTranscription());
		TButton btnSendAi = new TButton(TLanguage.getInstance().getString(ItToolsKey.SEND_TO_TEROS));
		btnSendAi.setOnAction(e -> sendToTeros());
		
		TToolBar transcriptionTollBar = new TToolBar();
		transcriptionTollBar.getItems().addAll(btnPick, btnSendAi);
		
		root.getChildren().add(section(TLanguage.getInstance().getString(ItToolsKey.MEETING_TRANSCRIPTION),
				transcriptionTollBar, lblTranscription));

		
		TFieldSet meetingMinutesFieldSet = new TFieldSet(TLayoutType.VBOX, TLanguage.getInstance().getString(ItToolsKey.LABEL_MEETING_MINUTES));	
		root.getChildren().add(meetingMinutesFieldSet);
		
		tfMeetingPlace = new TextField();
		tfMeetingDate = new TMaskField("99/99/9999");
		tfMeetingStart = new TMaskField("99:99");
		tfMeetingFinish = new TMaskField("99:99");
		taMeetingTopic = new TextArea();
		taMeetingTopic.setPrefRowCount(3);
		taMeetingTopic.setWrapText(true);
		taParticipants = new TextArea();
		taParticipants.setPrefRowCount(2);
		taParticipants.setWrapText(true);
		GridPane meetingGrid = formGrid(
				row(ItToolsKey.MEETING_PLACE, tfMeetingPlace, ItToolsKey.MEETING_DATE, tfMeetingDate),
				row(ItToolsKey.MEETING_START_TIME, tfMeetingStart, ItToolsKey.MEETING_FINISH_TIME, tfMeetingFinish));
		
		meetingMinutesFieldSet.tAddAllContent(section(TLanguage.getInstance().getString(ItToolsKey.MEETING_DATA), meetingGrid,
				labeled(ItToolsKey.MEETING_TOPIC, taMeetingTopic), labeled(ItToolsKey.MEETING_PARTICIPANTS, taParticipants)));

		lvAgenda = new ListView<>();
		lvAgenda.setPrefHeight(140);
		lvAgenda.setCellFactory(v -> agendaCell());
		TButton btnAddAgenda = new TButton("+");
		btnAddAgenda.setOnAction(e -> addAgendaItem());
		TButton btnRemAgenda = new TButton("-");
		btnRemAgenda.setOnAction(e -> {
			MeetingAgenda sel = lvAgenda.getSelectionModel().getSelectedItem();
			if (sel != null) {
				mv.getMeetingAgenda().remove(sel);
				renumberAgenda();
			}
		});
		
		TToolBar agendaTollBar = new TToolBar();
		agendaTollBar.getItems().addAll(btnAddAgenda, btnRemAgenda);
		
		meetingMinutesFieldSet.tAddAllContent(section(TLanguage.getInstance().getString(ItToolsKey.MEETING_AGENDA),
				agendaTollBar, lvAgenda));
				
		lvReferrals = new ListView<>();
		lvReferrals.setPrefHeight(160);
		lvReferrals.setCellFactory(v -> referralCell());
		TButton btnAddRef = new TButton("+");
		btnAddRef.setOnAction(e -> addReferralItem());
		TButton btnRemRef = new TButton("-");
		btnRemRef.setOnAction(e -> {
			MeetingReferral sel = lvReferrals.getSelectionModel().getSelectedItem();
			if (sel != null) {
				mv.getMeetingReferrals().remove(sel);
				renumberReferrals();
			}
		});
		
		TToolBar referralsTollBar = new TToolBar();
		referralsTollBar.getItems().addAll(btnAddRef, btnRemRef);
		
		meetingMinutesFieldSet.tAddAllContent(section(TLanguage.getInstance().getString(ItToolsKey.MEETING_REFERRALS),
				referralsTollBar, lvReferrals));
		
		tfNextPlace = new TextField();
		tfNextDate = new TMaskField("99/99/9999");
		tfNextStart = new TMaskField("99:99");
		tfNextFinish = new TMaskField("99:99");
		
		meetingMinutesFieldSet.tAddAllContent(section(TLanguage.getInstance().getString(ItToolsKey.MEETING_NEXT),
				formGrid(row(ItToolsKey.MEETING_PLACE, tfNextPlace, ItToolsKey.MEETING_DATE, tfNextDate),
						row(ItToolsKey.MEETING_START_TIME, tfNextStart, ItToolsKey.MEETING_FINISH_TIME, tfNextFinish))));
		
		lvEvidences = new ListView<>();
		lvEvidences.setPrefHeight(120);
		TButton btnAddEv = new TButton(TLanguage.getInstance().getString(ItToolsKey.MEETING_ADD_EVIDENCE));
		btnAddEv.setOnAction(e -> addEvidence());
		TButton btnRemEv = new TButton("-");
		btnRemEv.setOnAction(e -> {
			MeetingEvidence sel = lvEvidences.getSelectionModel().getSelectedItem();
			if (sel != null)
				mv.getEvidences().remove(sel);
		});
		
		TToolBar evidencesTollBar = new TToolBar();
		evidencesTollBar.getItems().addAll(btnAddEv, btnRemEv);
		
		meetingMinutesFieldSet.tAddAllContent(section(TLanguage.getInstance().getString(ItToolsKey.EVIDENCES),
				evidencesTollBar, lvEvidences));
		
		TButton btnPdf = new TButton(TLanguage.getInstance().getString(ItToolsKey.MEETING_GENERATE_FILE));
		btnPdf.setOnAction(e -> generatePdf());
		linkPdfPath = new THyperlink();
		
		EventHandler<ActionEvent> linkPdfPathEv = e -> {
			if(StringUtils.isBlank(linkPdfPath.getText()))
				return;
			try {
				TedrosContext.openDocument(linkPdfPath.getText());
			} catch (Exception e1) {
				LOGGER.error(e1.getMessage(), e1);
			}
		};
		repo.add("linkPdfPathEv", linkPdfPathEv);
		linkPdfPath.setOnAction(new WeakEventHandler<>(linkPdfPathEv));
		
		root.getChildren().add(section(TLanguage.getInstance().getString(ItToolsKey.MEETING_ACTIONS),
				new HBox(8, btnPdf), linkPdfPath));

		tfIssueNumber = new TextField();
		tfIssueTitle = new TextField();
		tfIssueTitle.setEditable(false);
		tfActivityId = new TextField("19");
		TButton btnSearch = new TButton(TLanguage.getInstance().getString(ItToolsKey.MEETING_SEARCH_ISSUE));
		btnSearch.setOnAction(e -> searchIssue());
		TButton btnUpdate = new TButton(TLanguage.getInstance().getString(ItToolsKey.MEETING_UPDATE_ISSUE));
		btnUpdate.setOnAction(e -> updateRedmineIssue());
		
		TToolBar redmineIssueTollBar = new TToolBar();
		redmineIssueTollBar.getItems().addAll(btnSearch, btnUpdate);
		
		root.getChildren().add(section(TLanguage.getInstance().getString(ItToolsKey.MEETING_REDMINE),
				formGrid(row(ItToolsKey.ISSUE_NUMBER, tfIssueNumber, ItToolsKey.MEETING_ACTIVITY_ID, tfActivityId)),
				labeled(ItToolsKey.MEETING_ISSUE_TITLE, tfIssueTitle), redmineIssueTollBar));

		lblStatus = new TLabel();
		root.getChildren().add(lblStatus);

		ScrollPane scroll = new ScrollPane(root);
		scroll.setFitToWidth(true);
		scroll.setStyle("-fx-background-color: transparent;");
		getChildren().add(scroll);
		VBox.setVgrow(scroll, Priority.ALWAYS);
	}

	private void bindFromMv() {
		bindText(tfMeetingPlace, mv.getMeetingPlace());
		bindText(tfMeetingDate, mv.getMeetingDate());
		bindText(tfMeetingStart, mv.getMeetingStartTime());
		bindText(tfMeetingFinish, mv.getMeetingFinishTime());
		bindArea(taMeetingTopic, mv.getMeetingTopic());
		bindArea(taParticipants, mv.getParticipants());
		bindText(tfNextPlace, mv.getNextMeetingPlace());
		bindText(tfNextDate, mv.getNextMeetingDate());
		bindText(tfNextStart, mv.getNextMeetingStartTime());
		bindText(tfNextFinish, mv.getNextMeetingFinishTime());
		bindText(tfIssueNumber, mv.getIssueNumber());
		bindText(tfIssueTitle, mv.getIssueTitle());
		if (mv.getActivityId().getValue() != null)
			tfActivityId.setText(String.valueOf(mv.getActivityId().getValue()));
		tfActivityId.textProperty().addListener((o, a, b) -> {
			try {
				mv.getActivityId().set(StringUtils.isBlank(b) ? 19 : Integer.parseInt(b.trim()));
			} catch (NumberFormatException ex) {
				mv.getActivityId().set(19);
			}
		});
		lvAgenda.setItems(mv.getMeetingAgenda());
		lvReferrals.setItems(mv.getMeetingReferrals());
		
		if(mv.getEvidences()!=null && !mv.getEvidences().isEmpty()) {
			mv.getEvidences().stream().forEach(i->{
				if(i.getFile()!=null && i.getFile().getByteEntity()!=null && i.getFile().getByteEntity().getBytes()==null) {
					try {
						TBytesLoader.loadBytes(i.getFile());
					} catch (TProcessException e) {
						LOGGER.error(e.getMessage(), e);
					}
				}
			});
		}		
		
		lvEvidences.setItems(mv.getEvidences());
		if (StringUtils.isNotBlank(mv.getTranscriptionFileName().get()))
			lblTranscription.setText(mv.getTranscriptionFileName().get());
		if (StringUtils.isNotBlank(mv.getGeneratedPdfPath().get()))
			linkPdfPath.setText(mv.getGeneratedPdfPath().get());
	}

	private void bindText(TextField field, javafx.beans.property.StringProperty prop) {
		field.setText(prop.get());
		field.textProperty().bindBidirectional(prop);
	}

	private void bindArea(TextArea field, javafx.beans.property.StringProperty prop) {
		field.setText(prop.get());
		field.textProperty().bindBidirectional(prop);
	}

	private void pickTranscription() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Transcription", "*.docx", "*.pdf", "*.txt"));
		File f = fc.showOpenDialog(getScene().getWindow());
		if (f != null) {
			transcriptionFile = f;
			mv.getTranscriptionFileName().set(f.getName());
			lblTranscription.setText(f.getAbsolutePath());
		}
	}

	private void sendToTeros() {
		if (transcriptionFile == null || !transcriptionFile.isFile()) {
			alert(AlertType.WARNING, TLanguage.getInstance().getString(ItToolsKey.MEETING_SELECT_TRANSCRIPTION_FIRST));
			return;
		}
		lblStatus.setText(TLanguage.getInstance().getString(ItToolsKey.MEETING_AI_PROCESSING));		
		terosFillService.startProcess();
	}

	private void applyAiJson(String raw) {
		String json = extractJson(raw);
		JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
		setIfPresent(obj, "meetingPlace", mv.getMeetingPlace());
		setIfPresent(obj, "meetingDate", mv.getMeetingDate());
		setIfPresent(obj, "meetingStartTime", mv.getMeetingStartTime());
		setIfPresent(obj, "meetingFinishTime", mv.getMeetingFinishTime());
		setIfPresent(obj, "meetingTopic", mv.getMeetingTopic());
		setIfPresent(obj, "participants", mv.getParticipants());
		setIfPresent(obj, "nextMeetingPlace", mv.getNextMeetingPlace());
		setIfPresent(obj, "nextMeetingDate", mv.getNextMeetingDate());
		setIfPresent(obj, "nextMeetingStartTime", mv.getNextMeetingStartTime());
		setIfPresent(obj, "nextMeetingFinishTime", mv.getNextMeetingFinishTime());

		mv.getMeetingAgenda().clear();
		if (obj.has("meetingAgenda") && obj.get("meetingAgenda").isJsonArray()) {
			JsonArray arr = obj.getAsJsonArray("meetingAgenda");
			int i = 1;
			for (JsonElement el : arr) {
				JsonObject a = el.getAsJsonObject();
				MeetingAgenda item = new MeetingAgenda();
				item.setItem(a.has("item") ? a.get("item").getAsString() : String.format("%02d", i));
				item.setDescription(a.has("description") ? a.get("description").getAsString() : "");
				mv.getMeetingAgenda().add(item);
				i++;
			}
		}
		mv.getMeetingReferrals().clear();
		if (obj.has("meetingReferrals") && obj.get("meetingReferrals").isJsonArray()) {
			JsonArray arr = obj.getAsJsonArray("meetingReferrals");
			int i = 1;
			for (JsonElement el : arr) {
				JsonObject a = el.getAsJsonObject();
				MeetingReferral item = new MeetingReferral();
				item.setItem(a.has("item") ? a.get("item").getAsString() : String.format("%02d", i));
				item.setDescription(a.has("description") ? a.get("description").getAsString() : "");
				item.setResponsable(a.has("responsable") ? a.get("responsable").getAsString() : "");
				item.setDeadline(a.has("deadline") ? a.get("deadline").getAsString() : "");
				mv.getMeetingReferrals().add(item);
				i++;
			}
		}
	}

	private static void setIfPresent(JsonObject obj, String key, javafx.beans.property.StringProperty prop) {
		if (obj.has(key) && !obj.get(key).isJsonNull())
			prop.set(obj.get(key).getAsString());
	}

	private static String extractJson(String raw) {
		if (raw == null)
			throw new IllegalArgumentException("Empty AI response");
		String s = raw.trim();
		if (s.startsWith("```")) {
			int start = s.indexOf('{');
			int end = s.lastIndexOf('}');
			if (start >= 0 && end > start)
				return s.substring(start, end + 1);
		}
		int start = s.indexOf('{');
		int end = s.lastIndexOf('}');
		if (start >= 0 && end > start)
			return s.substring(start, end + 1);
		return s;
	}

	private void addAgendaItem() {
		MeetingAgenda a = new MeetingAgenda();
		a.setItem(String.format("%02d", mv.getMeetingAgenda().size() + 1));
		a.setDescription("");
		mv.getMeetingAgenda().add(a);
	}

	private void addReferralItem() {
		MeetingReferral r = new MeetingReferral();
		r.setItem(String.format("%02d", mv.getMeetingReferrals().size() + 1));
		r.setDescription("");
		r.setResponsable("");
		r.setDeadline("");
		mv.getMeetingReferrals().add(r);
	}

	private void renumberAgenda() {
		int i = 1;
		for (MeetingAgenda a : mv.getMeetingAgenda()) {
			a.setItem(String.format("%02d", i++));
		}
		lvAgenda.refresh();
	}

	private void renumberReferrals() {
		int i = 1;
		for (MeetingReferral r : mv.getMeetingReferrals()) {
			r.setItem(String.format("%02d", i++));
		}
		lvReferrals.refresh();
	}

	private void addEvidence() {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(defaultEvidenceDir());
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
		List<File> files = fc.showOpenMultipleDialog(getScene().getWindow());
		if (files == null)
			return;
		for (File f : files) {
			try {
				byte[] bytes = Files.readAllBytes(f.toPath());
				TByteEntity be = new TByteEntity();
				be.setBytes(bytes);
				TFileEntity fe = new TFileEntity();
				fe.setFileName(f.getName());
				fe.setFileSize((long) bytes.length);
				String name = f.getName();
				int dot = name.lastIndexOf('.');
				fe.setFileExtension(dot > 0 ? name.substring(dot + 1) : "png");
				fe.setByteEntity(be);
				MeetingEvidence ev = new MeetingEvidence();
				ev.setName(f.getName());
				ev.setFilePath(f.getAbsolutePath());
				ev.setFile(fe);
				mv.getEvidences().add(ev);
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
	}

	private File defaultEvidenceDir() {
		String day = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
		File dir = new File(EvidenceScheduler.OUTPUT_DIR + File.separator + day);
		return dir.isDirectory() ? dir : new File(EvidenceScheduler.OUTPUT_DIR);
	}

	private void generatePdf() {
		syncEntityCollections();
		try {
			meetingMinutesReportProcess.exportPDF(mv.getEntity(), TedrosFolder.EXPORT_FOLDER.getFullPath());
			meetingMinutesReportProcess.startProcess();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			alert(AlertType.ERROR, ex.getMessage());
		}
	}

	private void searchIssue() {
		String num = tfIssueNumber.getText();
		if (StringUtils.isBlank(num))
			return;
		try {
			RedmineApiPropertyUtil util = RedmineApiPropertyUtil.getInstance();
			RedmineApiGateway gateway = new RedmineApiGateway(util.getRedmineUrl(), util.getRedmineKey());
			TIssueEvidenceInfo issue = gateway.getTIssueEvidenceInfo(Integer.valueOf(num.trim()));
			mv.getIssueNumber().set(num.trim());
			mv.getIssueTitle().set(issue.getSubject());
			lblStatus.setText(issue.getSubject());
		} catch (Exception ex) {
			LOGGER.warn(ex.getMessage(), ex);
			alert(AlertType.WARNING, ex.getMessage());
		}
	}

	private void updateRedmineIssue() {
		syncEntityCollections();
		String pdf = mv.getGeneratedPdfPath().get();
		if (StringUtils.isBlank(pdf) || !new File(pdf).isFile()) {
			alert(AlertType.WARNING, TLanguage.getInstance().getString(ItToolsKey.MEETING_GENERATE_PDF_FIRST));
			return;
		}
		if (StringUtils.isBlank(mv.getIssueNumber().get())) {
			alert(AlertType.WARNING, TLanguage.getInstance().getString(ItToolsKey.ISSUE_NUMBER));
			return;
		}
		try {
			byte[] pdfBytes = Files.readAllBytes(new File(pdf).toPath());
			Float hours = computeHours(mv.getMeetingStartTime().get(), mv.getMeetingFinishTime().get());
			Date spentOn = parseMeetingDate(mv.getMeetingDate().get());
			Integer activity = mv.getActivityId().getValue() != null ? mv.getActivityId().getValue() : 19;
			Integer issueId = Integer.valueOf(mv.getIssueNumber().get().trim());

			RedmineApiPropertyUtil util = RedmineApiPropertyUtil.getInstance();
			RedmineApiGateway gateway = new RedmineApiGateway(util.getRedmineUrl(), util.getRedmineKey());
			String fileName = new File(pdf).getName();
			MeetingMinutesRedmineUpdateResult result = gateway.updateIssueWithMeetingMinutes(issueId, null, activity,
					pdfBytes, fileName, "application/pdf", hours, spentOn, "Participação em Reunião",
					mv.getRedmineAttachmentId().get(), mv.getRedmineTimeEntryId().get());
			if (result.getAttachmentId() != null)
				mv.getRedmineAttachmentId().set(String.valueOf(result.getAttachmentId()));
			if (result.getTimeEntryId() != null)
				mv.getRedmineTimeEntryId().set(String.valueOf(result.getTimeEntryId()));
			lblStatus.setText(TLanguage.getInstance().getString(ItToolsKey.MEETING_REDMINE_UPDATED));
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			alert(AlertType.ERROR, ex.getMessage());
		}
	}

	private void syncEntityCollections() {
		MeetingMinutes entity = mv.getEntity();
		entity.setMeetingAgenda(new ArrayList<>(mv.getMeetingAgenda()));
		entity.setMeetingReferrals(new ArrayList<>(mv.getMeetingReferrals()));
		entity.setEvidences(new ArrayList<>(mv.getEvidences()));
	}

	private static Float computeHours(String start, String finish) {
		try {
			if (StringUtils.isBlank(start) || StringUtils.isBlank(finish))
				return 0.5f;
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
			Date s = fmt.parse(start.trim());
			Date f = fmt.parse(finish.trim());
			long diff = f.getTime() - s.getTime();
			if (diff <= 0)
				return 0.5f;
			return diff / (1000f * 60f * 60f);
		} catch (Exception e) {
			return 0.5f;
		}
	}

	private static Date parseMeetingDate(String value) {
		if (StringUtils.isBlank(value))
			return new Date();
		String[] patterns = { "dd/MM/yyyy", "yyyy-MM-dd", "dd-MM-yyyy" };
		for (String p : patterns) {
			try {
				return new SimpleDateFormat(p).parse(value.trim());
			} catch (Exception ignore) {
			}
		}
		return new Date();
	}

	private ListCell<MeetingAgenda> agendaCell() {
		return new ListCell<>() {
			private final TextField item = new TextField();
			private final TextArea desc = new TextArea();
			private final HBox box = new HBox(6, item, desc);
			{
				item.setPrefWidth(50);
				item.setStyle(FX_BACKGROUND_COLOR);
				item.setPromptText(TLanguage.getInstance().getString(ItToolsKey.MEETING_ITEM));
				desc.setWrapText(true); 
				desc.setPrefRowCount(2);				
				desc.setStyle(FX_BACKGROUND_COLOR);
				desc.setPromptText(TLanguage.getInstance().getString(TUsualKey.DESCRIPTION));				
				HBox.setHgrow(desc, Priority.ALWAYS);
			}

			@Override
			protected void updateItem(MeetingAgenda a, boolean empty) {
				super.updateItem(a, empty);
				if (empty || a == null) {
					setGraphic(null);
					return;
				}
				item.setText(a.getItem());
				desc.setText(a.getDescription());
				item.setOnAction(e -> a.setItem(item.getText()));
				item.focusedProperty().addListener((o, oldV, n) -> {
					if (!n)
						a.setItem(item.getText());
				});
				desc.focusedProperty().addListener((o, oldV, n) -> {
					if (!n)
						a.setDescription(desc.getText());
				});
				setGraphic(box);
			}
		};
	}

	private ListCell<MeetingReferral> referralCell() {
	    return new ListCell<>() {
	        private final TextField item = new TextField();
	        private final TextArea desc = new TextArea();
	        private final TextField resp = new TextField();
	        private final TextField deadline = new TextField();
	        private final HBox box = new HBox(6, item, desc, resp, deadline);

	        {
	            // Trava a largura do campo Item
	            item.setPrefWidth(40);
	            item.setMinWidth(Region.USE_PREF_SIZE);
	            item.setMaxWidth(Region.USE_PREF_SIZE);
	            item.setStyle(FX_BACKGROUND_COLOR);
	            item.setPromptText(TLanguage.getInstance().getString(ItToolsKey.MEETING_ITEM));
	            
	            // Trava a largura do campo Responsável
	            resp.setPrefWidth(120);
	            resp.setMinWidth(Region.USE_PREF_SIZE);
	            resp.setMaxWidth(Region.USE_PREF_SIZE);
	            resp.setStyle(FX_BACKGROUND_COLOR);
	            resp.setPromptText(TLanguage.getInstance().getString(TUsualKey.RESPONSABLE));
	            
	            // Trava a largura do campo Prazo
	            deadline.setPrefWidth(100);
	            deadline.setMinWidth(Region.USE_PREF_SIZE);
	            deadline.setMaxWidth(Region.USE_PREF_SIZE);
	            deadline.setStyle(FX_BACKGROUND_COLOR);
	            deadline.setPromptText(TLanguage.getInstance().getString(ItToolsKey.MEETING_DEADLINE));
	            
	            // Configura a Descrição para ser responsiva
	            desc.setPrefRowCount(2);
	            desc.setStyle(FX_BACKGROUND_COLOR);
	            desc.setPromptText(TLanguage.getInstance().getString(TUsualKey.DESCRIPTION));
	            
	            // 1. Permite que o TextArea encolha bastante antes de estourar a tela
	            desc.setMinWidth(50); 
	            
	            // 2. Opcional, mas recomendado: quebra o texto em vez de esconder
	            desc.setWrapText(true); 
	            
	            // 3. Diz ao HBox para dar todo o espaço horizontal excedente para o desc
	            HBox.setHgrow(desc, Priority.ALWAYS); 
	            
	            // 4. (Opcional) Garante que o HBox não ultrapasse a largura visível da ListCell
	            box.prefWidthProperty().bind(widthProperty().subtract(20)); // -20 compensa o scroll vertical
	        }

	        @Override
	        protected void updateItem(MeetingReferral r, boolean empty) {
	            super.updateItem(r, empty);
	            if (empty || r == null) {
	                setGraphic(null);
	                return;
	            }
	            
	            item.setText(r.getItem());
	            desc.setText(r.getDescription());
	            resp.setText(r.getResponsable());
	            deadline.setText(r.getDeadline());
	            
	            item.focusedProperty().addListener((o, a, n) -> {
	                if (!n) r.setItem(item.getText());
	            });
	            desc.focusedProperty().addListener((o, a, n) -> {
	                if (!n) r.setDescription(desc.getText());
	            });
	            resp.focusedProperty().addListener((o, a, n) -> {
	                if (!n) r.setResponsable(resp.getText());
	            });
	            deadline.focusedProperty().addListener((o, a, n) -> {
	                if (!n) r.setDeadline(deadline.getText());
	            });
	            
	            setGraphic(box);
	        }
	    };
	}

	private VBox section(String title, Node... nodes) {
		
		VBox box = new VBox(6);
		TLabel h = new TLabel(title);
		h.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");
		box.getChildren().add(h);
		box.getChildren().addAll(nodes);
		return box;
	}

	private VBox labeled(String key, Node node) {
		return new VBox(2, new TLabel(TLanguage.getInstance().getString(key)), node);
	}

	private GridPane formGrid(Object[]... rows) {
		GridPane grid = new GridPane();
		grid.setHgap(8);
		grid.setVgap(6);
		int r = 0;
		for (Object[] row : rows) {
			grid.add(new TLabel(TLanguage.getInstance().getString((String) row[0])), 0, r);
			grid.add((Node) row[1], 1, r);
			grid.add(new TLabel(TLanguage.getInstance().getString((String) row[2])), 2, r);
			grid.add((Node) row[3], 3, r);
			r++;
		}
		return grid;
	}

	private Object[] row(String k1, Node n1, String k2, Node n2) {
		return new Object[] { k1, n1, k2, n2 };
	}

	private void alert(AlertType type, String msg) {
		Platform.runLater(() -> {
			Alert a = new Alert(type, msg);
			a.showAndWait();
		});
	}

	private class TerosFillService extends TProcess<String> {
		
		private final IAiTerosService iaServ;
		private String prompt;
		private String transcriptionText;

		TerosFillService() {
			iaServ = AiTerosContext.newInstanceAiTerosService(MEETING_JSON_SYSTEM_PROMPT);
		}

		String getTranscriptionText() {
			return transcriptionText;
		}

		@Override
		protected TTaskImpl<String> createTask() {
			return new TTaskImpl<String>() {
				@Override
				protected String call() {
					
					try {
						byte[] bytes = Files.readAllBytes(transcriptionFile.toPath());
						ProcessedDocument doc = DocumentConverter.processFile(bytes, transcriptionFile.getName());
						transcriptionText = doc.textContent() != null ? doc.textContent() : "";
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
					
					prompt = "Extract the meeting minutes JSON from this transcription:\n\n" + transcriptionText;
					
					iaServ.cleanMessageHistory();					
					return iaServ.call(prompt, MEETING_JSON_SYSTEM_PROMPT);
				}

				@Override
				public String getServiceNameInfo() {
					return "MeetingMinutesTeros";
				}
			};
		}
	}
}
