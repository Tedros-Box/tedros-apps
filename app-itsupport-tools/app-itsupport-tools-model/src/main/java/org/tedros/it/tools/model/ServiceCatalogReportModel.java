package org.tedros.it.tools.model;

import org.tedros.server.model.TReportModel;

public class ServiceCatalogReportModel extends TReportModel<ServiceCatalogReportItemModel> {

	private static final long serialVersionUID = 6773104942871316575L;
	
	private String name;
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
