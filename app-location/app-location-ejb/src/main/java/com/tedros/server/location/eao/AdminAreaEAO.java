/**
 * 
 */
package com.tedros.server.location.eao;

import java.util.Collections;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import com.tedros.ejb.base.eao.TGenericEAO;
import com.tedros.location.model.AdminArea;
import com.tedros.location.model.Country;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class AdminAreaEAO extends TGenericEAO<AdminArea> {

	@SuppressWarnings("unchecked")
	public List<AdminArea> filter(Country country){
		String query = "select distinct e from AdminArea e "
				+ "where e.countryIso2Code = :code "
				+ "order by e.name";
		
		Query qry = super.getEntityManager().createQuery(query);
		
		qry.setParameter("code", country.getIso2Code());
		
		return qry.getResultList();
	}
	
	@Override
	public void afterFindAll(List<AdminArea> lst) throws Exception {
		sort(lst);
	}
	
	@Override
	public void afterListAll(List<AdminArea> lst) throws Exception {
		sort(lst);
	}
	
	@Override
	public void afterPageAll(List<AdminArea> lst) throws Exception {
		sort(lst);
	}
	

	private void sort(List<AdminArea> lst) {
		if(lst!=null)
			Collections.sort(lst, (a,b)->{
				return a.getName().compareTo(b.getName());
			});
	}
	
}
