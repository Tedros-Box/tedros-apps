
package org.tedros.extension.server.cdi.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.extension.model.City;
import org.tedros.extension.model.CityImport;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.cdi.bo.TImportFileEntityBO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class CityImportBO extends TImportFileEntityBO<City> {

	@Inject
	private TExtensionBO<City> bo;
	
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
