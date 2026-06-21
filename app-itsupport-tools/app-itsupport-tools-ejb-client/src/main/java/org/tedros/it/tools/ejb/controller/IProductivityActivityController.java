/**
 * 
 */
package org.tedros.it.tools.ejb.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.tedros.it.tools.model.ActivitySummaryDTO;
import org.tedros.it.tools.model.ProductivityActivityDTO;
import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.security.TAccessToken;

import jakarta.ejb.Remote;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IProductivityActivityController extends ITSecureEjbController<ProductivityActivityDTO> {

	static final String JNDI_NAME = "IProductivityActivityControllerRemote";

	TResult<ProductivityActivityDTO> saveActivity(TAccessToken token, List<ProductivityActivityDTO> activities);

	TResult<List<ProductivityActivityDTO>> findUserIdAndDateRange(TAccessToken token, Long userId, LocalDateTime start,
			LocalDateTime end);

	TResult<List<ActivitySummaryDTO>> getWindowUsageSummary(TAccessToken token, Long userId, LocalDateTime start,
			LocalDateTime end);

}
