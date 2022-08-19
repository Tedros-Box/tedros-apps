
package org.tedros.server.location.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.location.model.Country;
import org.tedros.location.model.CountryImport;

import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.cdi.bo.TImportFileEntityBO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class CountryImportBO extends TImportFileEntityBO<Country> {

	@Inject
	private CountryBO bo;
	
	@Override
	protected Class<Country> getEntityClass() {
		return Country.class;
	}

	@Override
	protected ITGenericBO<Country> getBusinessObject() {
		return bo;
	}

	
	@Override
	public Class<CountryImport> getImportModelClass() {
		return CountryImport.class;
	}

}
