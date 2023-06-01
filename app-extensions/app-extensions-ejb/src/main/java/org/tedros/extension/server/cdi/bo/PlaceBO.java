/**
 * 
 */
package org.tedros.extension.server.cdi.bo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.extension.model.Place;
import org.tedros.extension.report.model.PlaceReportModel;
import org.tedros.extension.server.cdi.eao.PlaceEAO;
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
