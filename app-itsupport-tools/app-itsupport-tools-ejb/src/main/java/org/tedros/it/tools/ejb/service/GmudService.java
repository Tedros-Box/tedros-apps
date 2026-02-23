package org.tedros.it.tools.ejb.service;

import org.tedros.it.tools.cdi.bo.GmudBO;
import org.tedros.it.tools.entity.Gmud;
import org.tedros.server.ejb.service.TEjbService;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

@LocalBean
@Stateless(name="GmudService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class GmudService  extends TEjbService<Gmud>  {

	@Inject
	private GmudBO bo;
	
	@Override
	public GmudBO getBussinesObject() {
		return bo;
	}

}
