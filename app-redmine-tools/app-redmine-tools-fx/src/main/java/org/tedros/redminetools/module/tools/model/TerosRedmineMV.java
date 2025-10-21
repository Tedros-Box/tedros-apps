package org.tedros.redminetools.module.tools.model;

import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.web.TWebEngine;
import org.tedros.fx.annotation.scene.web.TWebView;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.redminetools.start.TConstant;

import javafx.beans.property.SimpleStringProperty;

@TPresenter(model=TerosRedmineModel.class,
decorator=@TDecorator(type=TViewDecorator.class, viewTitle="Teros AI HTML Response"),
behavior=@TBehavior(type=TViewBehavior.class))
public class TerosRedmineMV extends TModelView<TerosRedmineModel> {
	
	//private final static double HEIGHT = 500;
	
	@TWebView(engine = @TWebEngine(load = TWebEngine.MODULE_FOLDER+"/"+TConstant.UUI+"/teros_ia_response.html"
))
	private SimpleStringProperty webContent;

	public TerosRedmineMV(TerosRedmineModel model) {
		super(model);
	}

	public SimpleStringProperty getWebContent() {
		return webContent;
	}

	public void setWebContent(SimpleStringProperty webContent) {
		this.webContent = webContent;
	}
	
	

}
