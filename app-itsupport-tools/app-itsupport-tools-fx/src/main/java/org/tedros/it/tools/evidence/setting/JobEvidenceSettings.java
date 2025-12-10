package org.tedros.it.tools.evidence.setting;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.security.model.TUser;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.TAutoCompleteEntity;
import org.tedros.fx.form.TSetting;
import org.tedros.it.tools.entity.JobEvidenceItem;
import org.tedros.it.tools.evidence.model.EvidenceSelectorView;
import org.tedros.it.tools.evidence.model.JobEvidenceMV;
import org.tedros.person.ejb.controller.IEmployeeController;
import org.tedros.person.model.Employee;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TLoggerUtil;

import javafx.scene.layout.VBox;


public class JobEvidenceSettings extends TSetting {
	
	private static final Logger log = TLoggerUtil.getLogger(JobEvidenceSettings.class);
	
    public JobEvidenceSettings(ITComponentDescriptor descriptor) {
        super(descriptor);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void run() {
    	
    	JobEvidenceMV mv = super.getModelView();    	
    	ITObservableList<JobEvidenceItem> items = mv.getItems(); 
    	VBox node = super.getLayout("itemsHeader");
    	node.getChildren().add(new EvidenceSelectorView(items));
    	
    	try(TEjbServiceLocator locator = TEjbServiceLocator.getInstance()) {    		
    		try {
    			TUser user = TedrosContext.getLoggedUser();
				IEmployeeController serv = locator.lookup(IEmployeeController.JNDI_NAME);
				Employee entity = new Employee();
				entity.setTedrosUserId(user.getId());
				TResult<Employee> res = serv.find(user.getAccessToken(), entity);
				if(res.getState().equals(TState.SUCCESS) && res.getValue()!=null) {
					entity = res.getValue();
					TAutoCompleteEntity control = super.getControl("employee");
					control.tSelectedItemProperty().set(entity);
				}
				
			} catch (NamingException e) {
				log.error(e.getMessage(), e);
			}
    	}
    }
}
