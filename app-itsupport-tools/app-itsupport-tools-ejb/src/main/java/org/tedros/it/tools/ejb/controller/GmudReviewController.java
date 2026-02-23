/**
 * 
 */
package org.tedros.it.tools.ejb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.domain.GmudReviewStatus;
import org.tedros.it.tools.domain.GmudStatus;
import org.tedros.it.tools.ejb.service.ItSupportToolsService;
import org.tedros.it.tools.entity.Gmud;
import org.tedros.it.tools.entity.GmudReview;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

/**
 * The controller bean
 * 
 * @author Davis Dun
 *
 */
@TSecurityInterceptor
@Stateless(name="IGmudReviewController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.CHANGE_MANAGER_GMUD_APPROVE_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class GmudReviewController extends TSecureEjbController<GmudReview> implements IGmudReviewController, ITSecurity  {

	@EJB
	private ItSupportToolsService<Gmud> gmudServ;
	
	@EJB
	private ItSupportToolsService<GmudReview> serv;
		
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<GmudReview> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
	
	@Override
	public TResult<GmudReview> save(TAccessToken token, GmudReview entity) {
		
		try {
			Gmud gmud = gmudServ.findById(entity.getGmud());
			
			if(gmud.getStatus().equals(GmudStatus.EXECUTING.getDescription()) || 
					gmud.getStatus().equals(GmudStatus.FINISHED.getDescription())) {
				return new TResult<>(TResult.TState.WARNING, 
						"GMUD "+GmudStatus.EXECUTING.getDescription()+" "
						+ "ou "+GmudStatus.FINISHED.getDescription()+" não pode ser revisada.");
			}
						
			Map<String, Integer> reviewerCount = new HashMap<>();
			reviewerCount.put(GmudReviewStatus.APPROVED.getDescription(), 0); 
			reviewerCount.put(GmudReviewStatus.REJECTED.getDescription(), 0);
			reviewerCount.put(GmudReviewStatus.PENDING.getDescription(), 0);
			
			List<GmudReview> list = gmud.getReviews();
			list.stream().forEach(rev -> {
				GmudReview r = rev.getId().equals(entity.getId())? entity : rev;
				String status = r.getStatus();
				reviewerCount.put(status, reviewerCount.get(status)+1);
			});
			
			int approved = reviewerCount.get(GmudReviewStatus.APPROVED.getDescription());
			int rejected = reviewerCount.get(GmudReviewStatus.REJECTED.getDescription());
			int pending = reviewerCount.get(GmudReviewStatus.PENDING.getDescription());
			
			gmud.getReviews().stream().forEach(r -> {
				if(r.getId().equals(entity.getId())) {
					r.setStatus(entity.getStatus());
					r.setComments(entity.getComments());
					r.setReviewDate(entity.getReviewDate());
				}
			});
			
			if(rejected > 0) {
				gmud.setStatus(GmudStatus.REJECTED.getDescription());
				gmud = gmudServ.save(gmud);
			} else if(approved == list.size()) {
				gmud.setStatus(GmudStatus.APPROVED.getDescription());
				gmud = gmudServ.save(gmud);
			} else if(pending > 0) {
				gmud.setStatus(GmudStatus.ANALYSIS.getDescription());
				gmud = gmudServ.save(gmud);
			}
			
			return new TResult<>(TState.SUCCESS, gmud.getReviews().stream()
					.filter(r -> r.getId().equals(entity.getId())).findFirst().get());
			
		} catch (Exception e) {
			return super.processException(token, entity, e);
		}
	}
	
	@Override
	public TResult<GmudReview> remove(TAccessToken token, GmudReview entity) {
		try {
			Gmud gmud = gmudServ.find(entity.getGmud());
			
			if(gmud.getStatus().equals(GmudStatus.FINISHED.getDescription())) {
				return new TResult<>(TResult.TState.WARNING, 
						"GMUD finalizada não pode ser alterada.");
			}
			
			return super.remove(token, entity);
			
		} catch (Exception e) {
			return super.processException(token, entity, e);
		}
	}
}
