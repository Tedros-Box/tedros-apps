package org.tedros.it.tools.module.employeeactivity.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.component.TComponent;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.model.ProductivityActivityDTO;
import org.tedros.it.tools.module.employeeactivity.component.EmployeeActivityMonitorComponent;

import javafx.beans.property.SimpleObjectProperty;

@TPresenter(model=ProductivityActivityDTO.class,
decorator=@TDecorator(type=TViewDecorator.class, viewTitle=ItToolsKey.VIEW_EMPLOYEE_ACTIVITY_MONITORING),
behavior=@TBehavior(type=TViewBehavior.class))
@TSecurity(id=DomainApp.MONITOR_EMPLOYEE_ACTIVITY_FORM_ID, appName = ItToolsKey.APP_ITSUPPORT,
moduleName = ItToolsKey.MODULE_ITSUPPORT_EMPLOYEE_ACTIVITY, viewName = ItToolsKey.VIEW_EMPLOYEE_ACTIVITY_MONITORING,
allowedAccesses={TAuthorizationType.VIEW_ACCESS})
public class EmployeeActivityMV extends TModelView<ProductivityActivityDTO>{

	@TFieldBox(node = @TNode(parse = true, id="component"))
	@TComponent(type = EmployeeActivityMonitorComponent.class)
	private SimpleObjectProperty<Object> component;
	
	
	public EmployeeActivityMV(ProductivityActivityDTO entity) {
		super(entity);
	}
}
