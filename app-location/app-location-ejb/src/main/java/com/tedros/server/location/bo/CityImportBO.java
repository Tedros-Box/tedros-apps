
package com.tedros.server.location.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.bo.TImportFileEntityBO;
import com.tedros.location.model.City;
import com.tedros.location.model.CityImport;
import com.tedros.server.base.bo.TLocBO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class CityImportBO extends TImportFileEntityBO<City> {

	@Inject
	private TLocBO<City> bo;
	
	@Override
	protected Class<City> getEntityClass() {
		return City.class;
	}

	@Override
	protected ITGenericBO<City> getBusinessObject() {
		return bo;
	}

	
	@Override
	public Class<CityImport> getImportModelClass() {
		return CityImport.class;
	}

}
