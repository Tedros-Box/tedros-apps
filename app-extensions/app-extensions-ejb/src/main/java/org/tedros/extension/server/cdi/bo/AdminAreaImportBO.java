
package org.tedros.extension.server.cdi.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.extension.model.AdminArea;
import org.tedros.extension.model.AdminAreaImport;
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
