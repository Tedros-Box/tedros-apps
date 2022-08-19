/**
 * 
 */
package com.tedros.server.location.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.TImportFileEntityBO;
import com.tedros.ejb.base.service.TEjbImportService;
import com.tedros.location.model.AdminArea;
import com.tedros.server.location.bo.AdminAreaImportBO;

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
