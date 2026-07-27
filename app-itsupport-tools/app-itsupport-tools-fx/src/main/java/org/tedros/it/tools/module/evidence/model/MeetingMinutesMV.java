package org.tedros.it.tools.module.evidence.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.model.TFormatter;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.component.TComponent;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.ejb.controller.IMeetingMinutesController;
import org.tedros.it.tools.entity.MeetingAgenda;
import org.tedros.it.tools.entity.MeetingEvidence;
import org.tedros.it.tools.entity.MeetingMinutes;
import org.tedros.it.tools.entity.MeetingReferral;
import org.tedros.it.tools.module.evidence.component.MeetingMinutesComponent;
import org.tedros.server.query.TCompareOp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

@TForm(header = "", showBreadcrumBar = false, scroll = true)
@TEjbService(serviceName = IMeetingMinutesController.JNDI_NAME, model = MeetingMinutes.class, filterByLoggedUser = true)
@TListViewPresenter(page = @TPage(serviceName = IMeetingMinutesController.JNDI_NAME, filterByLoggedUser = true, 
	query = @TQuery(entity = MeetingMinutes.class, condition = {
		@TCondition(field = "meetingTopic", operator = TCompareOp.LIKE, label = ItToolsKey.MEETING_TOPIC),
		@TCondition(field = "meetingDate", operator = TCompareOp.EQUAL, label = ItToolsKey.MEETING_DATE),
		@TCondition(field = "issueNumber", operator = TCompareOp.EQUAL, label = ItToolsKey.ISSUE_NUMBER) }, orderBy = {
				@TOrder(label = ItToolsKey.MEETING_DATE, field = "meetingDate"),
				@TOrder(label = ItToolsKey.ISSUE_NUMBER, field = "issueNumber") }), showSearch = true, showOrderBy = true), 
	presenter = @TPresenter(decorator = @TDecorator(viewTitle = ItToolsKey.VIEW_MEETING_MINUTES, buildModesRadioButton = false), 
							behavior  = @TBehavior(saveOnlyChangedModels = false)))
@TSecurity(id = DomainApp.MEETING_MINUTES_FORM_ID, appName = ItToolsKey.APP_ITSUPPORT, moduleName = ItToolsKey.MODULE_ITSUPPORT_EVIDENCE, 
			viewName = ItToolsKey.VIEW_MEETING_MINUTES, allowedAccesses = {
		TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.SAVE, TAuthorizationType.DELETE,
		TAuthorizationType.NEW })
public class MeetingMinutesMV extends TEntityModelView<MeetingMinutes> {

	@TFieldBox(node = @TNode(parse = true, id = "no-effect-id"))
	@TComponent(type = MeetingMinutesComponent.class)
	private SimpleStringProperty component;

	private SimpleLongProperty id;
	private SimpleStringProperty meetingPlace;
	private SimpleStringProperty meetingDate;
	private SimpleStringProperty meetingStartTime;
	private SimpleStringProperty meetingFinishTime;
	private SimpleStringProperty meetingTopic;
	private SimpleStringProperty participants;
	private SimpleStringProperty nextMeetingPlace;
	private SimpleStringProperty nextMeetingDate;
	private SimpleStringProperty nextMeetingStartTime;
	private SimpleStringProperty nextMeetingFinishTime;
	private SimpleStringProperty issueNumber;
	private SimpleStringProperty issueTitle;
	private SimpleIntegerProperty activityId;
	private SimpleStringProperty redmineAttachmentId;
	private SimpleStringProperty redmineTimeEntryId;
	private SimpleStringProperty transcriptionFileName;
	private SimpleStringProperty transcription;
	private SimpleStringProperty generatedPdfPath;

	@TGenericType(model = MeetingAgenda.class)
	private ITObservableList<MeetingAgenda> meetingAgenda;

	@TGenericType(model = MeetingReferral.class)
	private ITObservableList<MeetingReferral> meetingReferrals;

	@TGenericType(model = MeetingEvidence.class)
	private ITObservableList<MeetingEvidence> evidences;

	public MeetingMinutesMV(MeetingMinutes entity) {
		super(entity);
		super.formatToString(TFormatter.create().add("[%s] ", meetingDate).add("%s", meetingTopic));
		if (entity != null && entity.isNew() && activityId.getValue() == null) {
			activityId.setValue(19);
		}
	}

	@Override
	public SimpleLongProperty getId() {
		return id;
	}

	@Override
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getComponent() {
		return component;
	}

	public void setComponent(SimpleStringProperty component) {
		this.component = component;
	}

	public SimpleStringProperty getMeetingPlace() {
		return meetingPlace;
	}

	public void setMeetingPlace(SimpleStringProperty meetingPlace) {
		this.meetingPlace = meetingPlace;
	}

	public SimpleStringProperty getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(SimpleStringProperty meetingDate) {
		this.meetingDate = meetingDate;
	}

	public SimpleStringProperty getMeetingStartTime() {
		return meetingStartTime;
	}

	public void setMeetingStartTime(SimpleStringProperty meetingStartTime) {
		this.meetingStartTime = meetingStartTime;
	}

	public SimpleStringProperty getMeetingFinishTime() {
		return meetingFinishTime;
	}

	public void setMeetingFinishTime(SimpleStringProperty meetingFinishTime) {
		this.meetingFinishTime = meetingFinishTime;
	}

	public SimpleStringProperty getMeetingTopic() {
		return meetingTopic;
	}

	public void setMeetingTopic(SimpleStringProperty meetingTopic) {
		this.meetingTopic = meetingTopic;
	}

	public SimpleStringProperty getParticipants() {
		return participants;
	}

	public void setParticipants(SimpleStringProperty participants) {
		this.participants = participants;
	}

	public SimpleStringProperty getNextMeetingPlace() {
		return nextMeetingPlace;
	}

	public void setNextMeetingPlace(SimpleStringProperty nextMeetingPlace) {
		this.nextMeetingPlace = nextMeetingPlace;
	}

	public SimpleStringProperty getNextMeetingDate() {
		return nextMeetingDate;
	}

	public void setNextMeetingDate(SimpleStringProperty nextMeetingDate) {
		this.nextMeetingDate = nextMeetingDate;
	}

	public SimpleStringProperty getNextMeetingStartTime() {
		return nextMeetingStartTime;
	}

	public void setNextMeetingStartTime(SimpleStringProperty nextMeetingStartTime) {
		this.nextMeetingStartTime = nextMeetingStartTime;
	}

	public SimpleStringProperty getNextMeetingFinishTime() {
		return nextMeetingFinishTime;
	}

	public void setNextMeetingFinishTime(SimpleStringProperty nextMeetingFinishTime) {
		this.nextMeetingFinishTime = nextMeetingFinishTime;
	}

	public SimpleStringProperty getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(SimpleStringProperty issueNumber) {
		this.issueNumber = issueNumber;
	}

	public SimpleStringProperty getIssueTitle() {
		return issueTitle;
	}

	public void setIssueTitle(SimpleStringProperty issueTitle) {
		this.issueTitle = issueTitle;
	}

	public SimpleIntegerProperty getActivityId() {
		return activityId;
	}

	public void setActivityId(SimpleIntegerProperty activityId) {
		this.activityId = activityId;
	}

	public SimpleStringProperty getRedmineAttachmentId() {
		return redmineAttachmentId;
	}

	public void setRedmineAttachmentId(SimpleStringProperty redmineAttachmentId) {
		this.redmineAttachmentId = redmineAttachmentId;
	}

	public SimpleStringProperty getRedmineTimeEntryId() {
		return redmineTimeEntryId;
	}

	public void setRedmineTimeEntryId(SimpleStringProperty redmineTimeEntryId) {
		this.redmineTimeEntryId = redmineTimeEntryId;
	}

	public SimpleStringProperty getTranscriptionFileName() {
		return transcriptionFileName;
	}

	public void setTranscriptionFileName(SimpleStringProperty transcriptionFileName) {
		this.transcriptionFileName = transcriptionFileName;
	}

	public SimpleStringProperty getTranscription() {
		return transcription;
	}

	public void setTranscription(SimpleStringProperty transcription) {
		this.transcription = transcription;
	}

	public SimpleStringProperty getGeneratedPdfPath() {
		return generatedPdfPath;
	}

	public void setGeneratedPdfPath(SimpleStringProperty generatedPdfPath) {
		this.generatedPdfPath = generatedPdfPath;
	}

	public ITObservableList<MeetingAgenda> getMeetingAgenda() {
		return meetingAgenda;
	}

	public void setMeetingAgenda(ITObservableList<MeetingAgenda> meetingAgenda) {
		this.meetingAgenda = meetingAgenda;
	}

	public ITObservableList<MeetingReferral> getMeetingReferrals() {
		return meetingReferrals;
	}

	public void setMeetingReferrals(ITObservableList<MeetingReferral> meetingReferrals) {
		this.meetingReferrals = meetingReferrals;
	}

	public ITObservableList<MeetingEvidence> getEvidences() {
		return evidences;
	}

	public void setEvidences(ITObservableList<MeetingEvidence> evidences) {
		this.evidences = evidences;
	}
}
