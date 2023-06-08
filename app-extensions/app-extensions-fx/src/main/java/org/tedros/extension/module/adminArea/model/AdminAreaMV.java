/**
 * 
 */
package org.tedros.extension.module.adminArea.model;

import static org.tedros.core.annotation.security.TAuthorizationType.DELETE;
import static org.tedros.core.annotation.security.TAuthorizationType.EDIT;
import static org.tedros.core.annotation.security.TAuthorizationType.NEW;
import static org.tedros.core.annotation.security.TAuthorizationType.SAVE;
import static org.tedros.core.annotation.security.TAuthorizationType.VIEW_ACCESS;

import org.tedros.core.annotation.security.TSecurity;
import org.tedros.extension.LocatKey;
import org.tedros.extension.domain.DomainApp;
import org.tedros.extension.ejb.controller.IAdminAreaController;
import org.tedros.extension.model.AdminArea;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTextField;
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
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.server.query.TCompareOp;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = LocatKey.FORM_KEEP_UPDATE, showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = IAdminAreaController.JNDI_NAME, model=AdminArea.class)
@TListViewPresenter(listViewMinWidth=350, listViewMaxWidth=350,
	page=@TPage(serviceName = IAdminAreaController.JNDI_NAME,
		query = @TQuery(entity=AdminArea.class, condition= {
				@TCondition(field = "countryIso2Code", operator=TCompareOp.EQUAL, label=TUsualKey.COUNTRY_CODE),
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME)},
			orderBy= {@TOrder(label = TUsualKey.COUNTRY_CODE, field = "countryIso2Code"),
					@TOrder(label = TUsualKey.NAME, field = "name")}
				),showSearch=true, showOrderBy=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=LocatKey.VIEW_ADMIN_AREA, buildImportButton=true),
	behavior=@TBehavior(importModelViewClass=AdminAreaImportMV.class, runNewActionAfterSave=true)))
@TSecurity(	id=DomainApp.ADMIN_AREA_FORM_ID, appName = LocatKey.APP_LOCATION_NAME,
moduleName = LocatKey.MODULE_ADMINISTRATIVE, viewName = LocatKey.VIEW_ADMIN_AREA,
allowedAccesses={VIEW_ACCESS, EDIT, SAVE, DELETE, NEW})
public class AdminAreaMV extends TEntityModelView<AdminArea> {
	
	@TLabel(text=TUsualKey.COUNTRY_CODE+" (ISO2)")
	@TTextField(maxLength=2, required = true, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"countryIso2Code", "name", "iso2Code"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="countryIso2Code", priority=Priority.NEVER), 
			@TPriority(field="iso2Code", priority=Priority.NEVER), 
			@TPriority(field="name", priority=Priority.ALWAYS)}))
	private SimpleStringProperty countryIso2Code;
			
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=60, required = true)
	private SimpleStringProperty name;
			
	@TLabel(text=TUsualKey.CAPITAL)
	@TTextField(maxLength=120, required = false)
	private SimpleStringProperty iso2Code;
			
	
	public AdminAreaMV(AdminArea e) {
		super(e);
		this.formatToString("[%s] %s", this.countryIso2Code, this.name);
	}

	public SimpleStringProperty getCountryIso2Code() {
		return countryIso2Code;
	}

	public void setCountryIso2Code(SimpleStringProperty countryIso2Code) {
		this.countryIso2Code = countryIso2Code;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getIso2Code() {
		return iso2Code;
	}

	public void setIso2Code(SimpleStringProperty iso2Code) {
		this.iso2Code = iso2Code;
	}

}
