package org.tedros.redminetools.module.ai.function;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class WebViewBridge {

	private final WebView webview;
	
	public WebViewBridge(WebView webview) {
		this.webview = webview;
		WebEngine we = this.webview.getEngine(); 
		we.setJavaScriptEnabled(true);
		JSObject window = (JSObject) we.executeScript("window");
		window.setMember("tedros", this);
	}
	
	public void run(String content) {
		getWebEngine().executeScript("appendAIResponse('"+content+"')");
	}
	
	private WebEngine getWebEngine() {
		return webview.getEngine();
	}
}
