
package org.tedros.server.location.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.location.model.AdminArea;
import org.tedros.location.model.AdminAreaImport;

import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.cdi.bo.TImportFileEntityBO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class AdminAreaImportBO extends TImportFileEntityBO<AdminArea> {

	@Inject
	private AdminAreaBO bo;
	
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
