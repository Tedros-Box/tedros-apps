/**
 * 
 */
package org.tedros.server.location.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.location.model.AdminArea;
import org.tedros.server.location.bo.AdminAreaImportBO;

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
