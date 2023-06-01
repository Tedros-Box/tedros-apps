package org.tedros.extension.server.ejb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.extension.domain.DomainApp;
import org.tedros.extension.ejb.controller.IPlaceReportController;
import org.tedros.extension.model.Place;
import org.tedros.extension.report.model.PlaceItemModel;
import org.tedros.extension.report.model.PlaceReportModel;
import org.tedros.extension.server.ejb.service.PlaceService;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TActionPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TMethodPolicie;
import org.tedros.server.security.TMethodSecurity;
import org.tedros.server.security.TSecurityInterceptor;

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
			return new TResult<>(TState.SUCCESS, m);
		}catch(Exception e){
			return new TResult<>(TState.ERROR, e.getMessage());
		}
	}

	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}

}
