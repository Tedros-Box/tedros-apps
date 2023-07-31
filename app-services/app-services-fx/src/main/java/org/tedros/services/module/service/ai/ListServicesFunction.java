/**
 * 
 */
package org.tedros.services.module.service.ai;

import java.util.List;

import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Empty;
import org.tedros.ai.function.model.Response;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.server.result.TResult;
import org.tedros.services.ejb.controller.IServiceController;
import org.tedros.services.model.Service;

/**
 * @author Davis Gordon
 *
 */
public class ListServicesFunction extends TFunction<Empty> {

	public ListServicesFunction() {
		super("list_services", "List all provision services", Empty.class, 
		v->{
			ServiceLocator loc = ServiceLocator.getInstance();
			try {
				IServiceController serv = loc.lookup(IServiceController.JNDI_NAME);
				TResult<List<Service>> res = serv.listAll(TedrosContext.getLoggedUser().getAccessToken(), Service.class);
				if(res.getValue()!=null)
					return new Response("Service list", res.getValue());
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				loc.close();
			}
			
			
			return new Response("No data found!");
		});
	}

}
