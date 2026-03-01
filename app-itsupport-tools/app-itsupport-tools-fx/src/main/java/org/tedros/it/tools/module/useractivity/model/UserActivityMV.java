package org.tedros.it.tools.module.useractivity.model;

import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.component.TComponent;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.it.tools.model.ProductivityActivityDTO;
import org.tedros.it.tools.module.evidence.component.ProductivityReportComponent;

import javafx.beans.property.SimpleObjectProperty;

@TPresenter(model=ProductivityActivityDTO.class,
decorator=@TDecorator(type=TViewDecorator.class, viewTitle="User Activity Report"),
behavior=@TBehavior(type=TViewBehavior.class))

public class UserActivityMV extends TModelView<ProductivityActivityDTO>{

	@TFieldBox(node = @TNode(parse = true, id="component"))
	@TComponent(type = ProductivityReportComponent.class)
	private SimpleObjectProperty<Object> component;
	
	
	public UserActivityMV(ProductivityActivityDTO entity) {
		super(entity);
	}
}
