/**
 * 
 */
package org.tedros.server.location.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.location.model.Country;
import org.tedros.server.cdi.bo.TImportFileEntityBO;
import org.tedros.server.ejb.service.TEjbImportService;
import org.tedros.server.location.bo.CountryImportBO;

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
