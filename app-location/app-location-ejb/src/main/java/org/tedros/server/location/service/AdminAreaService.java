/**
 * 
 */
package org.tedros.server.location.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.location.model.AdminArea;
import org.tedros.location.model.Country;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;
import org.tedros.server.location.bo.AdminAreaBO;

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
