package org.tedros.redminetools.module.tools.model;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TShowField;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.redminetools.model.RedmineProject;

import javafx.beans.property.SimpleStringProperty;

@TPresenter(model=RedmineProject.class,
decorator=@TDecorator(type=TViewDecorator.class, viewTitle="Projetos"),
behavior=@TBehavior(type=TViewBehavior.class))
public class RedmineProjectMV extends TModelView<RedmineProject> {
	
	@TText(textStyle = TTextStyle.LARGE)
	@TFieldBox(node=@TNode(parse = true, id=TFieldBox.TITLE))
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.ID_NUMBER)
	@TShowField
	@TFlowPane(hgap=20,
	pane=@TPane(children={"id","identifier","homepage"}))
	private SimpleStringProperty id;
	
	@TLabel(text="Identificador")
	@TShowField
	private SimpleStringProperty identifier;
	
	@TLabel(text="Home page")
	@TShowField
	private SimpleStringProperty homepage;	
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TShowField
	private SimpleStringProperty description;
	
	public RedmineProjectMV(RedmineProject model) {
		super(model);
		super.formatToString("%s", name);
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getId() {
		return id;
	}

	public void setId(SimpleStringProperty id) {
		this.id = id;
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
	
}
