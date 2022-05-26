package com.tedros.server.report.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessPolicie;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TActionPolicie;
import com.tedros.ejb.base.security.TBeanPolicie;
import com.tedros.ejb.base.security.TBeanSecurity;
import com.tedros.ejb.base.security.TMethodPolicie;
import com.tedros.ejb.base.security.TMethodSecurity;
import com.tedros.ejb.base.security.TSecurityInterceptor;
import com.tedros.ejb.controller.IPlaceReportController;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.model.Place;
import com.tedros.location.report.model.PlaceItemModel;
import com.tedros.location.report.model.PlaceReportModel;
import com.tedros.server.location.service.PlaceService;

@TSecurityInterceptor

@TBeanSecurity({@TBeanPolicie(id = DomainApp.PLACE_REPORT_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@Stateless(name="IPlaceReportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class PlaceReportController implements IPlaceReportController, ITSecurity {

	@EJB
	private PlaceService serv;
	
	@EJB
	private ITSecurityController security;
	
	public PlaceReportController() {
	}

	@Override	
	@TMethodSecurity({
		@TMethodPolicie(policie = {TActionPolicie.SEARCH})})
	public TResult<PlaceReportModel> process(TAccessToken token, PlaceReportModel m) {
		try{
			List<Place> lst = serv.filterBy(m);
			if(lst!=null){
				List<PlaceItemModel> itens = new ArrayList<>();
				for(Place a : lst){
					itens.add(new PlaceItemModel(a));
				}
				m.setResult(itens);
			}
			return new TResult<>(EnumResult.SUCESS, m);
		}catch(Exception e){
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}

	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}

}
