/**
 * 
 */
package org.tedros.person.start;

import java.util.List;

import org.tedros.core.security.model.TUser;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.person.ejb.controller.INaturalPersonController;
import org.tedros.person.model.NaturalPerson;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;

import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;

/**
 * @author Davis Gordon
 *
 */
public final class TPersonHelper {
	
	private TPersonHelper() {
		
	}
	
	public static TPersonHelper create() {
		return new TPersonHelper();
	}

	public void loadPersonsAssociatedWith(TUser user, ObservableList<NaturalPerson> persons) {
		TEntityProcess<NaturalPerson> p = 
				new TEntityProcess<NaturalPerson>(NaturalPerson.class, INaturalPersonController.JNDI_NAME) {};
		p.stateProperty().addListener((a,o,n)->{
			if(n.equals(State.SUCCEEDED)) {
				List<TResult<NaturalPerson>> l = p.getValue();
				if(!l.isEmpty()) {
					l.forEach(r->{
						if(r.getState().equals(TState.SUCCESS))
							persons.add(r.getValue());
					});
				}else
					persons.add(null);
			}
		});
		NaturalPerson u = new NaturalPerson();
		u.setTedrosUserId(user.getId());
		p.find(u);
		p.startProcess();
	}
}
