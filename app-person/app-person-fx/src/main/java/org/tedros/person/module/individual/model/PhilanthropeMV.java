/**
 * 
 */
package org.tedros.person.module.individual.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TProcess;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
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
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.ejb.controller.IPersonStatusController;
import org.tedros.person.ejb.controller.IPersonTypeController;
import org.tedros.person.model.NaturalPersonMV;
import org.tedros.person.model.Philanthrope;
import org.tedros.person.model.PhilanthropeStatus;
import org.tedros.person.model.PhilanthropeType;
import org.tedros.server.query.TCompareOp;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(header = "", showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = IPersonController.JNDI_NAME, model=Philanthrope.class)
@TListViewPresenter(
		page=@TPage(serviceName = IPersonController.JNDI_NAME,
		query = @TQuery(entity=Philanthrope.class, condition= {
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME),
				@TCondition(field = "lastName", operator=TCompareOp.LIKE, label=TUsualKey.LAST_NAME)},
			orderBy= {@TOrder(label = TUsualKey.NAME, field = "name"),
					@TOrder(label = TUsualKey.LAST_NAME, field = "lastName")}
				),showSearch=true, showOrderBy=true),
		presenter=@TPresenter(decorator = @TDecorator(viewTitle=PersonKeys.VIEW_PHILANTHROPE,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.PHILANTHROPE_FORM_ID, appName = PersonKeys.APP_PERSON,
	moduleName = PersonKeys.MODULE_NATURAL_PERSON, viewName = PersonKeys.VIEW_PHILANTHROPE,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class PhilanthropeMV extends NaturalPersonMV<Philanthrope> {

	@TTabPane(tabs = { 
		@TTab( text = TUsualKey.MAIN_DATA, scroll=true, fields={"lastName","sex", "type", "address"}),
		@TTab(text = TUsualKey.DESCRIPTION, fields={"description"}),
		@TTab(text = TUsualKey.OBSERVATION, fields={"observation"}), 
		@TTab(text = TUsualKey.EVENTS,fields={"events"})
	})
	private SimpleLongProperty id;

	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(
	process=@TProcess(service = IPersonTypeController.JNDI_NAME, 
	modelView=PhilanthropeTypeMV.class, query=@TQuery(entity=PhilanthropeType.class)))
	@THBox(	spacing=10, fillHeight=true,
			pane=@TPane(children={"type", "status"}), 
	hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.NEVER), 
			@TPriority(field="status", priority=Priority.NEVER)}))
	private SimpleObjectProperty<PhilanthropeType> type;
	
	@TLabel(text=TUsualKey.STATUS)
	@TComboBoxField(
	process=@TProcess(service = IPersonStatusController.JNDI_NAME, 
	modelView=PhilanthropeStatusMV.class, query=@TQuery(entity=PhilanthropeStatus.class)))
	private SimpleObjectProperty<PhilanthropeStatus> status;
	
	public PhilanthropeMV(Philanthrope entity) {
		super(entity);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleObjectProperty<PhilanthropeType> getType() {
		return type;
	}

	public void setType(SimpleObjectProperty<PhilanthropeType> type) {
		this.type = type;
	}

	public SimpleObjectProperty<PhilanthropeStatus> getStatus() {
		return status;
	}

	public void setStatus(SimpleObjectProperty<PhilanthropeStatus> status) {
		this.status = status;
	}

}
