/**
 * 
 */
package org.tedros.person.module.individual.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.form.TDetailForm;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.ejb.controller.INaturalPersonController;
import org.tedros.person.ejb.controller.IPersonStatusController;
import org.tedros.person.ejb.controller.IPersonTypeController;
import org.tedros.person.model.NaturalPerson;
import org.tedros.person.model.NaturalPersonMV;
import org.tedros.person.model.NaturalStatus;
import org.tedros.person.model.NaturalStatusMV;
import org.tedros.person.model.NaturalType;
import org.tedros.person.model.NaturalTypeMV;
import org.tedros.server.query.TCompareOp;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = INaturalPersonController.JNDI_NAME, model=NaturalPerson.class)
@TListViewPresenter(
		page=@TPage(serviceName = INaturalPersonController.JNDI_NAME,
		query = @TQuery(entity=NaturalPerson.class, condition= {
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME),
				@TCondition(field = "lastName", operator=TCompareOp.LIKE, label=TUsualKey.LAST_NAME)},
			orderBy= {@TOrder(label = TUsualKey.NAME, field = "name"),
					@TOrder(label = TUsualKey.LAST_NAME, field = "lastName")}
				),showSearch=true, showOrderBy=true),
		presenter=@TPresenter(decorator = @TDecorator(viewTitle=PersonKeys.VIEW_NATURAL_PERSON,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.NATURAL_PERSON_FORM_ID, appName = PersonKeys.APP_PERSON,
	moduleName = PersonKeys.MODULE_NATURAL_PERSON, viewName = PersonKeys.VIEW_NATURAL_PERSON,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class IndividualMV extends NaturalPersonMV<NaturalPerson> {

	@TTabPane(tabs = { 
		@TTab( text = TUsualKey.MAIN_DATA,
			content = @TContent(detailForm=@TDetailForm(fields={"lastName","sex", "type", "address"}))),
		@TTab(text = TUsualKey.DESCRIPTION, 
			content = @TContent(detailForm=@TDetailForm(fields={"description"}))),
		@TTab(text = TUsualKey.OBSERVATION, 
			content = @TContent(detailForm=@TDetailForm(fields={"observation"}))), 
		@TTab(text = TUsualKey.EVENTS,
			content = @TContent(detailForm=@TDetailForm(fields={"events"})))
	})
	private SimpleLongProperty id;

	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(
	optionsList=@TOptionsList(serviceName = IPersonTypeController.JNDI_NAME, 
	optionModelViewClass=NaturalTypeMV.class,
	entityClass=NaturalType.class))
	@THBox(	spacing=10, fillHeight=true,
			pane=@TPane(children={"type", "status"}), 
	hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.NEVER), 
			@TPriority(field="status", priority=Priority.NEVER)}))
	private SimpleObjectProperty<NaturalType> type;
	
	@TLabel(text=TUsualKey.STATUS)
	@TComboBoxField(
	optionsList=@TOptionsList(serviceName = IPersonStatusController.JNDI_NAME, 
	optionModelViewClass=NaturalStatusMV.class,
	entityClass=NaturalStatus.class))
	private SimpleObjectProperty<NaturalStatus> status;
	
	public IndividualMV(NaturalPerson entity) {
		super(entity);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleObjectProperty<NaturalType> getType() {
		return type;
	}

	public void setType(SimpleObjectProperty<NaturalType> type) {
		this.type = type;
	}

	public SimpleObjectProperty<NaturalStatus> getStatus() {
		return status;
	}

	public void setStatus(SimpleObjectProperty<NaturalStatus> status) {
		this.status = status;
	}
}
