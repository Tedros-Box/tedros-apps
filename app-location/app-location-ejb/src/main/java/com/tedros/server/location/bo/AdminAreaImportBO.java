
package com.tedros.server.location.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.bo.TImportFileEntityBO;
import com.tedros.location.model.AdminArea;
import com.tedros.location.model.AdminAreaImport;
import com.tedros.server.base.bo.TEntityBO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class AdminAreaImportBO extends TImportFileEntityBO<AdminArea> {

	@Inject
	private TEntityBO<AdminArea> bo;
	
	@Override
	protected Class<AdminArea> getEntityClass() {
		return AdminArea.class;
	}

	@Override
	protected ITGenericBO<AdminArea> getBusinessObject() {
		return bo;
	}

	
	@Override
	public Class<AdminAreaImport> getImportModelClass() {
		return AdminAreaImport.class;
	}

}
