/**
 * 
 */
package org.tedros.person.module.individual.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.form.TDetailForm;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.view.TOption;
import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.ejb.controller.INaturalPersonController;
import org.tedros.person.model.NaturalPerson;
import org.tedros.person.model.NaturalPersonMV;

import javafx.beans.property.SimpleLongProperty;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = INaturalPersonController.JNDI_NAME, model=NaturalPerson.class)
@TListViewPresenter(
		paginator=@TPaginator(entityClass = NaturalPerson.class, serviceName = INaturalPersonController.JNDI_NAME,
		show=true, showSearchField=true, searchFieldName="name", 
		orderBy = {	@TOption(text = TUsualKey.NAME , value = "name"),
					@TOption(text = TUsualKey.LAST_NAME, value = "lastName")}),
		presenter=@TPresenter(decorator = @TDecorator(viewTitle=PersonKeys.VIEW_NATURAL_PERSON,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.NATURAL_PERSON_FORM_ID, appName = PersonKeys.APP_PERSON,
	moduleName = PersonKeys.MODULE_NATURAL_PERSON, viewName = PersonKeys.VIEW_NATURAL_PERSON,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class IndividualMV extends NaturalPersonMV<NaturalPerson> {

	@TTabPane(tabs = { 
		@TTab( text = TUsualKey.MAIN_DATA,
			content = @TContent(detailForm=@TDetailForm(fields={"lastName","sex", "address"}))),
		@TTab(text = TUsualKey.DESCRIPTION, 
			content = @TContent(detailForm=@TDetailForm(fields={"description"}))),
		@TTab(text = TUsualKey.OBSERVATION, 
			content = @TContent(detailForm=@TDetailForm(fields={"observation"}))), 
		@TTab(text = TUsualKey.EVENTS,
			content = @TContent(detailForm=@TDetailForm(fields={"events"})))
	})
	private SimpleLongProperty id;
	
	public IndividualMV(NaturalPerson entity) {
		super(entity);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}
}
