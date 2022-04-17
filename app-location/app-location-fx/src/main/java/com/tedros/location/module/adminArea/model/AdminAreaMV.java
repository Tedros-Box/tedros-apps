/**
 * 
 */
package com.tedros.location.module.adminArea.model;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.model.AdminArea;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "#{form.keep.update}", showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = "IAdminAreaControllerRemote", model=AdminArea.class)
@TListViewPresenter(listViewMinWidth=350, listViewMaxWidth=350,
	paginator=@TPaginator(entityClass = AdminArea.class, serviceName = "IAdminAreaControllerRemote",
			show=true, showSearchField=true, searchFieldName="name", 
			orderBy = {	@TOption(text = "#{label.country.code}", value = "countryIso2Code"), 
						@TOption(text = "#{label.name}", value = "name")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="#{view.admin.area}", buildImportButton=true),
	behavior=@TBehavior(importModelViewClass=AdminAreaImportMV.class, runNewActionAfterSave=true)))
@TSecurity(	id=DomainApp.ADMIN_AREA_FORM_ID, 
	appName = "#{app.location.name}", moduleName = "#{module.administrative}", viewName = "#{view.admin.area}",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class AdminAreaMV extends TEntityModelView<AdminArea> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty display;
	
	@TReaderHtml
	@TLabel(text="#{label.country.code} (ISO2)")
	@TTextField(maxLength=2, required = true, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"countryIso2Code", "name", "iso2Code"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="countryIso2Code", priority=Priority.NEVER), 
			@TPriority(field="iso2Code", priority=Priority.NEVER), 
			@TPriority(field="name", priority=Priority.ALWAYS)}))
	private SimpleStringProperty countryIso2Code;
			
	@TReaderHtml
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, required = true)
	private SimpleStringProperty name;
			
	@TReaderHtml
	@TLabel(text="#{label.capital}")
	@TTextField(maxLength=120, required = false)
	private SimpleStringProperty iso2Code;
			
	
	public AdminAreaMV(AdminArea e) {
		super(e);
		this.buildDisplayText();
		this.setDisplayText(e.getCountryIso2Code(), e.getName());
	}
	
	@Override
	public void reload(AdminArea e) {
		super.reload(e);
		this.buildDisplayText();
		this.setDisplayText(e.getCountryIso2Code(), e.getName());
	}
	
	private void buildDisplayText() {
		ChangeListener<String> cchl = super.getListenerRepository().get("countryChl");
		if(cchl==null) {
			cchl = (a,o,n)->{
				this.setDisplayText(n, this.name.getValue());
			};
		}else
			super.removeListener("countryChl");
		super.getListenerRepository().add("countryChl", cchl);
		this.countryIso2Code.addListener(cchl);
		
		ChangeListener<String> nchl = super.getListenerRepository().get("nameChl");
		if(nchl==null) {
			nchl = (a,o,n)->{
				this.setDisplayText(this.countryIso2Code.getValue(), n);
			};
		}else
			super.removeListener("nameChl");
		super.getListenerRepository().add("nameChl", nchl);
		this.name.addListener(nchl);
	}
	
	private void setDisplayText(String country, String name) {
		String s = (StringUtils.isNotBlank(country) ? "["+country+"] " : "") 
				+ (StringUtils.isNotBlank(name) ? name : "");
		if(this.display==null)
			this.display = new SimpleStringProperty();
		this.display.setValue(s);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return display;
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

	public SimpleStringProperty getDisplay() {
		return display;
	}

	public void setDisplay(SimpleStringProperty display) {
		this.display = display;
	}

}
