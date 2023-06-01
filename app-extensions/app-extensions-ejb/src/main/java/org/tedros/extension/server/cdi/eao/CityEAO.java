/**
 * 
 */
package org.tedros.extension.server.cdi.eao;

import java.util.Collections;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.tedros.extension.model.AdminArea;
import org.tedros.extension.model.City;
import org.tedros.extension.model.Country;
import org.tedros.server.cdi.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class CityEAO extends TGenericEAO<City> {

	@SuppressWarnings("unchecked")
	public List<City> filter(Country country, AdminArea adminArea){
		String query = "select distinct e from City e "
				+ "where 1=1 "
				+ (country!=null ? "and e.countryIso2Code = :code " : "")
				+ (adminArea!=null ? "and e.adminArea = :adminArea " : "")
				+ "order by e.name";
		
		Query qry = super.getEntityManager().createQuery(query);
		
		if(country!=null)
			qry.setParameter("code", country.getIso2Code());
		
		if(adminArea!=null)
			qry.setParameter("adminArea", adminArea.getName());
		
		return qry.getResultList();
	}
	
	@Override
	public void afterFindAll(List<City> lst) throws Exception {
		sort(lst);
	}
	
	@Override
	public void afterListAll(List<City> lst) throws Exception {
		sort(lst);
	}
	
	@Override
	public void afterPageAll(List<City> lst) throws Exception {
		sort(lst);
	}
	

	private void sort(List<City> lst) {
		if(lst!=null)
			Collections.sort(lst, (a,b)->{
				return a.getName().compareTo(b.getName());
			});
	}
	
}
