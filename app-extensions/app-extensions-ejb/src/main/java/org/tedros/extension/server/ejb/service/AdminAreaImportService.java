/**
 * 
 */
package org.tedros.extension.server.ejb.service;

import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.extension.model.AdminArea;
import org.tedros.extension.server.cdi.bo.AdminAreaImportBO;
import org.tedros.server.cdi.bo.TImportFileEntityBO;
import org.tedros.server.ejb.service.TEjbImportService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="AdminAreaImportService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class AdminAreaImportService extends TEjbImportService<AdminArea> {

	@Inject
	private AdminAreaImportBO bo;
	
	@Override
	public TImportFileEntityBO<AdminArea> getBusinessObject() {
		return bo;
	}

}
