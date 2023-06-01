/**
 * 
 */
package org.tedros.extension.server.ejb.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.extension.model.AdminArea;
import org.tedros.extension.model.Country;
import org.tedros.extension.server.cdi.bo.AdminAreaBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class AdminAreaService extends TEjbService<AdminArea> {

	@Inject
	private AdminAreaBO bo;
	
	@Override
	public ITGenericBO<AdminArea> getBussinesObject() {
		return bo;
	}
	
	public List<AdminArea> filter(Country country){
		return bo.filter(country);
	}

}
