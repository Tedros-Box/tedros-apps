package org.tedros.it.tools.module.redmine.model;

import static org.tedros.core.annotation.security.TAuthorizationType.VIEW_ACCESS;

import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.component.TComponent;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.entity.RedmineProjectWithAiAssistance;
import org.tedros.it.tools.redmine.component.RedmineIssueSearchComponent;

import javafx.beans.property.SimpleObjectProperty;

@TPresenter(model=RedmineProjectWithAiAssistance.class,
decorator=@TDecorator(type=TViewDecorator.class, viewTitle=ItToolsKey.VIEW_REDMINE_SEARCH_ISSUES_TO_TEROS),
behavior=@TBehavior(type=TViewBehavior.class))
@TSecurity(	id=DomainApp.REDMINE_TOOLS_FORM_ID, appName = ItToolsKey.APP_ITSUPPORT,
moduleName = ItToolsKey.MODULE_ITSUPPORT_REDMINE, viewName = ItToolsKey.VIEW_REDMINE_SEARCH_ISSUES_TO_TEROS,
allowedAccesses={VIEW_ACCESS})
public class RedmineProjectWithAiAssistanceMV extends TEntityModelView<RedmineProjectWithAiAssistance> {
	
	@TFieldBox(node = @TNode(parse = true, id="component"))
	@TComponent(type = RedmineIssueSearchComponent.class)
	private SimpleObjectProperty<Object> component;
	

	public RedmineProjectWithAiAssistanceMV(RedmineProjectWithAiAssistance entity) {
		super(entity);
	}

}
