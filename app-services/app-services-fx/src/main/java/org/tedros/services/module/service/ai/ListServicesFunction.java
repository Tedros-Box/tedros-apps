/**
 * 
 */
package org.tedros.services.module.service.ai;

import java.util.List;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Empty;
import org.tedros.ai.function.model.Response;
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
				if(res.getValue()!=null)
					return new Response(SUSCESS_MESSAGE, res.getValue());
			}catch(Exception e) {
				LOGGER.error(e.getMessage(), e);
				return new Response(EXCEPTION_MESSAGE + e.getMessage());
			}
			
			
			return new Response(NO_DATA_FOUND_MESSAGE);
		});
	}

}
