/**
 * 
 */
package org.tedros.server.location.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.location.model.Place;
import org.tedros.location.report.model.PlaceReportModel;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;
import org.tedros.server.location.bo.PlaceBO;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class PlaceService extends TEjbService<Place> {

	@Inject
	private PlaceBO bo;
	
	@Override
	public ITGenericBO<Place> getBussinesObject() {
		return bo;
	}
	

	public List<Place> filterBy(PlaceReportModel m){
		return bo.filterBy(m);
	}

}
