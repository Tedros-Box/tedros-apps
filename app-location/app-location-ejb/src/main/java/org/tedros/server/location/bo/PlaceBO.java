/**
 * 
 */
package org.tedros.server.location.bo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.location.model.Place;
import org.tedros.location.report.model.PlaceReportModel;
import org.tedros.server.location.eao.PlaceEAO;

import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;

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
