/**
 * 
 */
package com.tedros.server.location.bo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.location.model.Place;
import com.tedros.location.report.model.PlaceReportModel;
import com.tedros.server.location.eao.PlaceEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class PlaceBO extends TGenericBO<Place> {

	@Inject
	private PlaceEAO eao;
	
	@Override
	public ITGenericEAO<Place> getEao() {
		return eao;
	}

	public List<Place> filterBy(PlaceReportModel m){
		return eao.filterBy(m);
	}
	
}
