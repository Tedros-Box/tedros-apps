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
import org.tedros.it.tools.module.gmud.component.ReviewGmudComponent;

import javafx.beans.property.SimpleObjectProperty;

@TPresenter(model=ReviewGmudModel.class,
decorator=@TDecorator(type=TViewDecorator.class, viewTitle="Revisar GMUD"),
behavior=@TBehavior(type=TViewBehavior.class))
public class ReviewGmudMV extends TModelView<ReviewGmudModel> {
	
	@TFieldBox(node = @TNode(parse = true, id="component"))
	@TComponent(type = ReviewGmudComponent.class)
	private SimpleObjectProperty<Object> component;
	
	public ReviewGmudMV(ReviewGmudModel model) {
		super(model);
	}

	public SimpleObjectProperty<Object> getComponent() {
		return component;
	}

	public void setComponent(SimpleObjectProperty<Object> component) {
		this.component = component;
	}

}
