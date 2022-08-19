/**
 * 
 */
package org.tedros.server.location.eao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.tedros.location.model.Place;
import org.tedros.location.report.model.PlaceReportModel;

import org.tedros.server.cdi.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class PlaceEAO extends TGenericEAO<Place> {

	@SuppressWarnings("unchecked")
	public List<Place> filterBy(PlaceReportModel m){
		
		StringBuffer sbf = new StringBuffer("select distinct e from Place e ");
		sbf.append("join e.type t ");
		sbf.append("join e.address a ");
		sbf.append("join a.country c ");
		sbf.append("left join a.adminArea aa ");
		sbf.append("left join a.city ct ");
		sbf.append("left join a.streetType st ");
		sbf.append(" where 1=1 ");
		
		if(StringUtils.isNotBlank(m.getTitle()))
			sbf.append("and lower(e.title) like :title ");
		
		if(m.getType()!=null)
			sbf.append("and t.id = :type ");
		
		if(m.getCountry()!=null)
			sbf.append("and c.id = :country ");
		
		if(m.getAdminArea()!=null)
			sbf.append("and aa.id = :admArea ");
		
		if(m.getCity()!=null)
			sbf.append("and ct.id = :city ");
		
		if(m.getStreetType()!=null)
			sbf.append("and st.id = :streetType ");
		
		if(StringUtils.isNotBlank(m.getPublicPlace()))
			sbf.append("and lower(a.publicPlace) like :publicPlace ");
		
		if(StringUtils.isNotBlank(m.getCode()))
			sbf.append("and a.code = :code ");
		
		
		if(StringUtils.isNotBlank(m.getOrderBy())) {
			sbf.append("order by "+m.getOrderBy());
			if(StringUtils.isNotBlank(m.getOrderType()))
				sbf.append(" "+m.getOrderType());
		}
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		
		if(StringUtils.isNotBlank(m.getTitle()))
			qry.setParameter("title", "%"+m.getTitle().toLowerCase()+"%");
		
		if(m.getType()!=null)
			qry.setParameter("type", m.getType().getId());

		if(m.getCountry()!=null)
			qry.setParameter("country", m.getCountry().getId());

		if(m.getAdminArea()!=null)
			qry.setParameter("admArea", m.getAdminArea().getId());

		if(m.getCity()!=null)
			qry.setParameter("city", m.getCity().getId());

		if(m.getStreetType()!=null)
			qry.setParameter("streetType", m.getStreetType().getId());
		
		if(StringUtils.isNotBlank(m.getPublicPlace()))
			qry.setParameter("publicPlace", "%"+m.getPublicPlace().toLowerCase()+"%");
		
		if(StringUtils.isNotBlank(m.getCode()))
			qry.setParameter("code", m.getCode());
		
		return qry.getResultList();
	}
	
}