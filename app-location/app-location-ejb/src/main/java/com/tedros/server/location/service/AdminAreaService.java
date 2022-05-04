/**
 * 
 */
package com.tedros.server.location.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.location.model.AdminArea;
import com.tedros.location.model.Country;
import com.tedros.server.location.bo.AdminAreaBO;

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
