package org.tedros.it.tools.model;

import java.util.List;

import org.tedros.it.tools.entity.ServiceCatalog;
import org.tedros.it.tools.entity.ServiceGroup;
import org.tedros.server.model.ITReportItemModel;

public class ServiceCatalogReportItemModel implements ITReportItemModel<ServiceCatalog> {
	
	
	private static final long serialVersionUID = 8280475289819263874L;

	private ServiceCatalog model;
	private String name;
	private List<ServiceGroup> groups;
	
	public ServiceCatalogReportItemModel(ServiceCatalog model) {
		this.model = model;
		this.name = model.getName();
		this.groups = model.getGroups();
	}

	@Override
	public ServiceCatalog getModel() {
		return model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ServiceGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<ServiceGroup> groups) {
		this.groups = groups;
	}

	

}
