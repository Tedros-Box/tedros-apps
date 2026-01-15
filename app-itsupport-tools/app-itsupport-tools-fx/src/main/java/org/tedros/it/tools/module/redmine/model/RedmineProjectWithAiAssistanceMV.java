package org.tedros.it.tools.module.redmine.model;

import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TVBox;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.it.tools.entity.RedmineProjectWithAiAssistance;
import org.tedros.it.tools.module.redmine.setting.RedmineProjectWithAiAssistanceSettings;

import javafx.beans.property.SimpleStringProperty;

@TSetting(RedmineProjectWithAiAssistanceSettings.class)
@TPresenter(model=RedmineProjectWithAiAssistance.class,
decorator=@TDecorator(type=TViewDecorator.class, viewTitle="Filtrar projetos do Redmine com assistência de IA."),
behavior=@TBehavior(type=TViewBehavior.class))
public class RedmineProjectWithAiAssistanceMV extends TEntityModelView<RedmineProjectWithAiAssistance> {
	
	@TVBox(pane=@TPane(children={"header"}))
	@TText(textStyle = TTextStyle.LARGE, text="Filtrar projetos do Redmine com assistência de IA.")
	@TFieldBox(node=@TNode(parse = true, id=TFieldBox.TITLE))
	private SimpleStringProperty header;
	

	public RedmineProjectWithAiAssistanceMV(RedmineProjectWithAiAssistance entity) {
		super(entity);
	}

}
