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
import com.tedros.location.model.City;
import com.tedros.server.location.bo.CityImportBO;

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
