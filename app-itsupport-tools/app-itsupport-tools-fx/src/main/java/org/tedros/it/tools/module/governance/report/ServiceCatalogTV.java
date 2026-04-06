package org.tedros.it.tools.module.governance.report;

import org.tedros.fx.model.TModelView;
import org.tedros.it.tools.model.ServiceCatalogReportItemModel;

import javafx.beans.property.SimpleStringProperty;

public class ServiceCatalogTV extends TModelView<ServiceCatalogReportItemModel>{
		
	private SimpleStringProperty name;
	
	public ServiceCatalogTV(ServiceCatalogReportItemModel model) {
		super(model);
		super.formatToString("%s", name);
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

}
