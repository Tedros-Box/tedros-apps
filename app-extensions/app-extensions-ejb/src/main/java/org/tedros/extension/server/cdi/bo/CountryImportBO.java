
package org.tedros.extension.server.cdi.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.extension.model.Country;
import org.tedros.extension.model.CountryImport;
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
