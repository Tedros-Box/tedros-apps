package org.tedros.it.tools.cdi.bo;

import org.tedros.it.tools.cdi.eao.GmudEao;
import org.tedros.it.tools.entity.Gmud;
import org.tedros.server.cdi.bo.TGenericBO;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class GmudBO extends TGenericBO<Gmud> {
	
	@Inject
	private GmudEao eao;
	
	@Override
	public GmudEao getEao() {
		return eao;
	}

}
