
package com.tedros.server.location.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.bo.TImportFileEntityBO;
import com.tedros.location.model.Country;
import com.tedros.location.model.CountryImport;
import com.tedros.server.base.bo.TEntityBO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class CountryImportBO extends TImportFileEntityBO<Country> {

	@Inject
	private TEntityBO<Country> bo;
	
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
