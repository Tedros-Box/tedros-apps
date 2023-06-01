/**
 * 
 */
package org.tedros.extension.server.ejb.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

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
