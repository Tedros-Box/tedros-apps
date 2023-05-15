/**
 * 
 */
package org.tedros.person.trigger;

import java.util.List;

import org.tedros.core.controller.TUserController;
import org.tedros.core.security.model.TUser;
import org.tedros.fx.control.trigger.TTrigger;
import org.tedros.fx.form.TFieldBox;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.person.model.NaturalPerson;
import org.tedros.person.model.NaturalPersonMV;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;

import javafx.concurrent.Worker.State;

/**
 * @author Davis Gordon
 *
 */
public class TedrosUserTrigger extends TTrigger<TUser> {

	public TedrosUserTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run(TEvent event, TUser e, TUser old) {
		NaturalPersonMV<? extends NaturalPerson> mv = (NaturalPersonMV<? extends NaturalPerson>) super.getForm().gettModelView();
		if(event.equals(TEvent.BUILD) & mv.getEntity().getTedrosUserId()!=null) {
			TEntityProcess<TUser> p = 
					new TEntityProcess<TUser>(TUser.class, TUserController.JNDI_NAME) {};
			p.stateProperty().addListener((a,o,n)->{
				if(n.equals(State.SUCCEEDED)) {
					List<TResult<TUser>> l = p.getValue();
					if(!l.isEmpty()) {
						TResult<TUser> r = l.get(0);
						if(r.getState().equals(TState.SUCCESS)) {
							mv.getTedrosUser().setValue(r.getValue());
						}
					}
				}
			});
			TUser u = new TUser();
			u.setId(mv.getEntity().getTedrosUserId());
			p.findById(u);
			p.startProcess();
		}else {
			mv.getEntity().setTedrosUserId(e!=null ? e.getId() : null);
		}
	}

}
