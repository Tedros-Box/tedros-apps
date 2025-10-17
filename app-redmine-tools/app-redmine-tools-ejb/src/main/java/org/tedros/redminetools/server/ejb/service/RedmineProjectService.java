/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.tedros.redminetools.server.ejb.service;

import org.tedros.redminetools.model.TProject;
import org.tedros.redminetools.server.cdi.bo.RedmineProjectBO;
import org.tedros.server.ejb.service.TEjbService;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

/**
 * The transact service bean 
 *
 * @author Davis Dun
 *
 */
@LocalBean
@Stateless(name="RedmineProjectService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class RedmineProjectService extends TEjbService<TProject>  {
	
	@Inject
	private RedmineProjectBO bo;
	
	@Override
	public RedmineProjectBO getBussinesObject() {
		return bo;
	}
	

}
