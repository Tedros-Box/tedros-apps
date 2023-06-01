/**
 * 
 */
package org.tedros.extension.server.cdi.eao;

import java.util.Collections;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import org.tedros.extension.model.Country;
import org.tedros.server.cdi.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class CountryEAO extends TGenericEAO<Country> {

	@Override
	public void afterFindAll(List<Country> lst) throws Exception {
		sort(lst);
	}
	
	@Override
	public void afterListAll(List<Country> lst) throws Exception {
		sort(lst);
	}
	
	@Override
	public void afterPageAll(List<Country> lst) throws Exception {
		sort(lst);
	}
	

	private void sort(List<Country> lst) {
		if(lst!=null)
			Collections.sort(lst, (a,b)->{
				return a.getName().compareTo(b.getName());
			});
	}
	
}
