/**
 * 
 */
package org.tedros.extension.server.ejb.service;

import java.util.List;

import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.extension.model.Place;
import org.tedros.extension.report.model.PlaceReportModel;
import org.tedros.extension.server.cdi.bo.PlaceBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

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
