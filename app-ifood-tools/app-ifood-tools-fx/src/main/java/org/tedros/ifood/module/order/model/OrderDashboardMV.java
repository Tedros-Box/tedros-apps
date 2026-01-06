package org.tedros.ifood.module.order.model;

import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TVBox;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;

import javafx.beans.property.SimpleStringProperty;

@TForm(scroll=false)
@TSetting(OrderDashboardSettings.class)
@TPresenter(model=OrderDashboardModel.class,
	decorator=@TDecorator(type=TViewDecorator.class, viewTitle="Order Dashboard"),
	behavior=@TBehavior(type=TViewBehavior.class))
/*@TSecurity(id=DomainApp.MESSAGE_VIEWER_FORM_ID,
appName=ToolsKey.APP_TOOLS, moduleName=ToolsKey.MODULE_AI, viewName=ToolsKey.VIEW_AI_CHAT_MESSAGE_VIEWER,
allowedAccesses={	TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,  
	   				TAuthorizationType.NEW, TAuthorizationType.SAVE, TAuthorizationType.DELETE})*/
public class OrderDashboardMV extends TModelView<OrderDashboardModel> {
		
	@TText(text="Order Dashboard", textStyle = TTextStyle.MEDIUM)
	@TVBox(pane=@TPane(children={"webContent"}), spacing=10)
	private SimpleStringProperty webContent;

	public OrderDashboardMV(OrderDashboardModel model) {
		super(model);
	}

	public SimpleStringProperty getWebContent() {
		return webContent;
	}

	public void setWebContent(SimpleStringProperty webContent) {
		this.webContent = webContent;
	}
	
	

}
