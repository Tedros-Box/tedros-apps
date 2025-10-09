/**
 * 
 */
package org.tedros.extension.server.ejb.service;

import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.extension.model.Country;
import org.tedros.extension.server.cdi.bo.CountryImportBO;
import org.tedros.server.cdi.bo.TImportFileEntityBO;
import org.tedros.server.ejb.service.TEjbImportService;

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
