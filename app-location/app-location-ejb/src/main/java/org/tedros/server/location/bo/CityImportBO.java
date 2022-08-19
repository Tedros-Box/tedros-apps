
package org.tedros.server.location.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.location.model.City;
import org.tedros.location.model.CityImport;
import org.tedros.server.base.bo.TLocBO;

import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.cdi.bo.TImportFileEntityBO;

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
