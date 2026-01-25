/**
 * 
 */
package org.tedros.services.module.service.ai;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.ToolCallResult;
import org.tedros.ai.function.model.Empty;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.server.result.TResult;
import org.tedros.services.ejb.controller.IServiceController;
import org.tedros.services.model.Service;
import org.tedros.util.TLoggerUtil;

/**
 * @author Davis Gordon
 *
 */
public class ListServicesFunction extends TFunction<Empty> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(ListServicesFunction.class);
	
	public static final String NAME = "list_services";
	public static final String DESCRIPTION = "List all provision services";

	public ListServicesFunction() {
		super(NAME, DESCRIPTION, Empty.class, 
		v->{
			LOGGER.info("Listing all services");
			
			try(TEjbServiceLocator loc = TEjbServiceLocator.getInstance()) {
				IServiceController serv = loc.lookup(IServiceController.JNDI_NAME);
				TResult<List<Service>> res = serv.listAll(TedrosContext.getLoggedUser().getAccessToken(), Service.class);
				if(res.getValue()!=null) {
					return ToolCallResult.builder()
							.message("Services retrieved successfully.")
							.result(Map.of(
				                    STATUS, SUCCESS,
				                    ACTION, "services_listed",
				                    SYSTEM_INSTRUCTION, "Services listed successfully. "
				                    		+ "Do not retry again. Proceed with the user's request.",
				                    "services", res.getValue()
				                ))
							.build();
				}
			}catch(Exception e) {
				LOGGER.error(e.getMessage(), e);
				return ToolCallResult.builder()
						.message("Error listing services: " + e.getMessage())
						.result(Map.of(
		                    STATUS, ERROR,
		                    ACTION, "services_list_error",
		                    ERROR_MESSAGE, e.getMessage()
		                ))
						.build();
			}
			
			
			return ToolCallResult.builder()
					.message("No services found.")
					.result(Map.of(
		                    STATUS, ERROR,
		                    ACTION, "no_services_found",
		                    ERROR_MESSAGE, "No services available in the system."
		                ))
					.build();
		});
	}

}
