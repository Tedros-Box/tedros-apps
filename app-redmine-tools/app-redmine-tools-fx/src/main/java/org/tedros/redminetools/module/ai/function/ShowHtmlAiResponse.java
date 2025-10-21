package org.tedros.redminetools.module.ai.function;

import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.api.form.ITFieldBox;
import org.tedros.api.form.ITModelForm;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.extension.model.GeoMap;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.redminetools.module.tools.model.TerosRedmineMV;

import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class ShowHtmlAiResponse extends TFunction<HtmlContent> {
	
	private static final String PROMPT = """
			Shows and append a given html response in a javafx.scene.web.WebView component.
						
			Important:
			The view 'Teros AI Response' must be opened before call this function to show any html content. 
			Use 'list_all_view_path' to find out the view path and after you can call the view using the 'call_view'
			function.   
			""";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ShowHtmlAiResponse() {
		super("show_html_content", PROMPT, HtmlContent.class, 
				v->{
					TedrosAppManager manager = TedrosAppManager.getInstance();
					
					ITView<TDynaPresenter<TerosRedmineMV>> vw = manager.getCurrentView();
					TDynaPresenter<TerosRedmineMV> p = vw.gettPresenter();
					 
					Platform.runLater(()->{						
						
						ITModelForm form = ((TViewBehavior) p.getBehavior()).getForm();
						
						WebViewBridge webViewBridge = form.gettObjectRepository().get("webviewbridge");
						
						if(webViewBridge==null) {							
							ITFieldBox fdbox = form.gettFieldBox("webContent");
							WebView wv = (WebView) fdbox.gettControl();
							webViewBridge = new WebViewBridge(wv);
							form.gettObjectRepository().add("webviewbridge", webViewBridge);
						}
						
						webViewBridge.run(v.htmlContent());
						
					});
					
					return new Response("Successful"); 
				});
	}	

}
