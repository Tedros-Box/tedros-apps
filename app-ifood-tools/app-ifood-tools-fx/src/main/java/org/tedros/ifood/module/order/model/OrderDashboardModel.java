package org.tedros.ifood.module.order.model;

import org.tedros.server.model.ITModel;

public class OrderDashboardModel implements ITModel {

	private static final long serialVersionUID = 1L;
	
	private String webContent;

	public String getWebContent() {
		return webContent;
	}

	public void setWebContent(String webContent) {
		this.webContent = webContent;
	}

}
