/**
 * 
 */
package org.tedros.sample.server.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.extension.domain.DomainApp;
import org.tedros.extension.model.Country;
import org.tedros.sample.ejb.controller.ICountryChartSampleController;
import org.tedros.sample.server.ejb.service.SmplsService;
import org.tedros.server.controller.TParam;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.model.ITChartModel;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="ICountryChartSampleController")
@TBeanSecurity(@TBeanPolicie(id=DomainApp.COUNTRY_FORM_ID, 
policie= {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS}))
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CountryChartSampleController extends TSecureEjbController<Country>
implements ICountryChartSampleController, ITSecurity {

	@EJB
	private SmplsService<Country> serv;
	
	@EJB
	private ITSecurityController securityController;

	@Override
	public ITEjbService<Country> getService() {
		return serv;
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}
	/* (non-Javadoc)
	 * @see org.tedros.server.controller.ITEjbChartController#process(org.tedros.server.security.TAccessToken, org.tedros.server.controller.TParam[])
	 */
	@Override
	public TResult<? extends ITChartModel> process(TAccessToken token, TParam... params) {
		try {
			/*//TSelect 
			
			//List<TProfile> p = serv.listAll(TProfile.class);
			//List<TUser> u = uServ.listAll(TUser.class);
			List<Country> p = serv.;
			
			TChartModel<Long, Long> cm = new TChartModel<>();
			//TChartSerie<String,Long> s = new TChartSerie<>(null);
			//cm.addSerie(s);
			p.stream().forEach(e->{
				TChartSerie<Long,Long> s = new TChartSerie<>(e);
				cm.addSerie(s);
				s.addData(RandomUtils.nextLong(0, 100), RandomUtils.nextLong(0, 100));
				s.addData(RandomUtils.nextLong(0, 100), RandomUtils.nextLong(0, 100));
				s.addData(RandomUtils.nextLong(0, 100), RandomUtils.nextLong(0, 100));
			});*/
			return new TResult<>(TState.SUCCESS);
		} catch (Exception e) {
			return super.processException(token, null, e);
		}
	}

}
