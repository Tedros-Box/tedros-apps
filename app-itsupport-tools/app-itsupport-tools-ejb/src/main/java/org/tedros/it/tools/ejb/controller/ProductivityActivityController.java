/**
 * 
 */
package org.tedros.it.tools.ejb.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.tedros.it.tools.ejb.mongo.ProductivityActivityRepository;
import org.tedros.it.tools.ejb.service.ItSupportToolsService;
import org.tedros.it.tools.model.ProductivityActivityDTO;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessToken;
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
@Stateless(name="IProductivityActivityController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ProductivityActivityController extends TSecureEjbController<ProductivityActivityDTO> implements IProductivityActivityController, ITSecurity  {

	@EJB
	private ItSupportToolsService<ProductivityActivityDTO> serv;
	
	@EJB
	private ProductivityActivityRepository repository;
		
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<ProductivityActivityDTO> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
	
	public TResult<ProductivityActivityDTO> saveActivity(TAccessToken token, List<ProductivityActivityDTO> activities) {
		try {			
			for (ProductivityActivityDTO activity : activities)
				serv.save(activity);			
			return new TResult<>(TState.SUCCESS);			
		} catch (Exception e) {
			return super.processException(token, null, e);
		}
	}
	
	public TResult<List<ProductivityActivityDTO>> findUserIdAndDateRange(TAccessToken token, Long userId, LocalDateTime start, LocalDateTime end) {
		try {
			
			List<ProductivityActivityDTO> activities = repository.findUserIdAndDateRange(userId, start, end);
			return new TResult<>(TState.SUCCESS, activities);
			
		} catch (Exception e) {
			return super.processException(token, null, e);
		}
	}
	
	
	
	
}
