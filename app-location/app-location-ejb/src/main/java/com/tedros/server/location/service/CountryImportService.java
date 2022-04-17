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
import com.tedros.location.model.Country;
import com.tedros.server.location.bo.CountryImportBO;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="CountryImportService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CountryImportService extends TEjbImportService<Country> {

	@Inject
	private CountryImportBO bo;
	
	@Override
	public TImportFileEntityBO<Country> getBusinessObject() {
		return bo;
	}

}
