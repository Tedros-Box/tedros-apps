/**
 * 
 */
package org.tedros.samples.module.control.model;

import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.THTMLEditor;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.web.TWebEngine;
import org.tedros.fx.annotation.scene.web.TWebView;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
@TPresenter(model=WebModel.class,
	decorator=@TDecorator(type=TViewDecorator.class, viewTitle="Web components"),
	behavior=@TBehavior(type=TViewBehavior.class))
public class WebControlMV extends TModelView<WebModel> {
	
	private final static double HEIGHT = 500;
	
	@TText(textStyle = TTextStyle.LARGE, text="Samples of web components")
	@TFieldBox(node=@TNode(parse = true, id=TFieldBox.TITLE))
	private SimpleStringProperty header;
	
	@TWebView(engine = @TWebEngine(load="https://docs.oracle.com/javase/8/javafx/get-started-tutorial/jfx-overview.htm"),
			maxHeight=HEIGHT)
	@TTabPane(tabs = { 
		@TTab(fields = { "web" }, text = "Web View"),
		@TTab(fields = { "content" }, text = "Html Editor") 
	})
	private SimpleStringProperty web;

	@THTMLEditor(showActionsToolBar=true,
		control=@TControl(parse = true, maxHeight=HEIGHT, minHeight=HEIGHT))
	private SimpleStringProperty content;


	public WebControlMV(WebModel model) {
		super(model);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
