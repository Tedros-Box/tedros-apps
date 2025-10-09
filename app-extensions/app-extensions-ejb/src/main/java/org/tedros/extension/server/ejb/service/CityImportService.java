/**
 * 
 */
package org.tedros.extension.server.ejb.service;

import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.extension.model.City;
import org.tedros.extension.server.cdi.bo.CityImportBO;
import org.tedros.server.cdi.bo.TImportFileEntityBO;
import org.tedros.server.ejb.service.TEjbImportService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="CityImportService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CityImportService extends TEjbImportService<City> {

	@Inject
	private CityImportBO bo;
	
	@Override
	public TImportFileEntityBO<City> getBusinessObject() {
		return bo;
	}

}
