package org.tedros.redminetools.module.tools.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TShowField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.redminetools.domain.DomainApp;
import org.tedros.redminetools.ejb.controller.IRedmineProjectController;
import org.tedros.redminetools.model.TProject;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

@TForm(header = "", showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = IRedmineProjectController.JNDI_NAME, model=TProject.class)
@TListViewPresenter(
		page=@TPage(serviceName = IRedmineProjectController.JNDI_NAME,
		query = @TQuery(entity = TProject.class),showSearch=true, showOrderBy=true),
		presenter=@TPresenter(decorator = @TDecorator(viewTitle="Projetos",
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false, saveOnlyChangedModels = false, saveAllModels = false)))
@TSecurity(id=DomainApp.REDMINE_PROJECT_FORM_ID, appName = "Redmine Tools",
	moduleName = "Tools", viewName = "Projeto",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class RedmineProjectMV extends TEntityModelView<TProject> {
	
	@TText(textStyle = TTextStyle.LARGE)
	@TFieldBox(node=@TNode(parse = true, id=TFieldBox.TITLE))
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.ID_NUMBER)
	@TShowField
	@TFlowPane(hgap=20,
	pane=@TPane(children={"id","identifier","homepage"}))
	private SimpleLongProperty id;
	
	@TLabel(text="Identificador")
	@TShowField
	private SimpleStringProperty identifier;
	
	@TLabel(text="Home page")
	@TShowField
	private SimpleStringProperty homepage;	
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TShowField
	private SimpleStringProperty description;
	
	public RedmineProjectMV(TProject model) {
		super(model);
		super.formatToString("%s", name);
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getIdentifier() {
		return identifier;
	}

	public void setIdentifier(SimpleStringProperty identifier) {
		this.identifier = identifier;
	}

	public SimpleStringProperty getHomepage() {
		return homepage;
	}

	public void setHomepage(SimpleStringProperty homepage) {
		this.homepage = homepage;
	}

	public SimpleStringProperty getDescription() {
		return description;
	}

	public void setDescription(SimpleStringProperty description) {
		this.description = description;
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}
	
}
