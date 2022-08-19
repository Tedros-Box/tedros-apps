/**
 * 
 */
package com.tedros.server.location.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.location.model.Place;
import com.tedros.location.report.model.PlaceReportModel;
import com.tedros.server.location.bo.PlaceBO;

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
