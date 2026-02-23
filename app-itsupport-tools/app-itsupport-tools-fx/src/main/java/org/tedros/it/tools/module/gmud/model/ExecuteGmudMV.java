package org.tedros.it.tools.module.gmud.model;

import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.component.TComponent;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.it.tools.module.gmud.component.ExecuteGmudComponent;

import javafx.beans.property.SimpleObjectProperty;

@TPresenter(model=ExecuteGmudModel.class,
decorator=@TDecorator(type=TViewDecorator.class, viewTitle="Executar GMUD"),
behavior=@TBehavior(type=TViewBehavior.class))

/*@TSecurity(id=DomainApp.CHANGE_MANAGER_GMUD_APPROVE_FORM_ID, appName = ItToolsKey.APP_ITSUPPORT,
	moduleName = ItToolsKey.MODULE_ITSUPPORT_GMUD, viewName = "Revisar GMUD",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.READ, TAuthorizationType.SAVE})*/
public class ExecuteGmudMV extends TModelView<ExecuteGmudModel> {
	
	@TFieldBox(node = @TNode(parse = true, id="component"))
	@TComponent(type = ExecuteGmudComponent.class)
	private SimpleObjectProperty<Object> component;
	
	public ExecuteGmudMV(ExecuteGmudModel model) {
		super(model);
	}

	public SimpleObjectProperty<Object> getComponent() {
		return component;
	}

	public void setComponent(SimpleObjectProperty<Object> component) {
		this.component = component;
	}

}
