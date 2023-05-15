/**
 * 
 */
package org.tedros.sample.server.cdi.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.sample.entity.Sale;
import org.tedros.sample.server.cdi.eao.SmplsEAO;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;

/**
 * The CDI business object 
 * 
 * @author Davis
 *
 */
@RequestScoped
public class SaleBO extends TGenericBO<Sale> {

	@Inject
	private SmplsEAO<Sale> eao;
	
	
	@Override
	public ITGenericEAO<Sale> getEao() {
		return eao;
	}

	@Override
	public Sale save(Sale e) throws Exception {
		
		if(e.isNew()) {
			e.getItems().forEach(i->{
				
			});
		}
		
		return super.save(e);
	}
	
	
	
	
	
	
	
}
