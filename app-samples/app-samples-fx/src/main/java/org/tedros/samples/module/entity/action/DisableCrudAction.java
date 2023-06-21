/**
 * 
 */
package org.tedros.samples.module.entity.action;

import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.entity.behavior.TMasterCrudViewBehavior;
import org.tedros.sample.entity.SampleB;
import org.tedros.samples.module.entity.model.CustomActionMV;

/**
 * @author Davis Gordon
 *
 */
public class DisableCrudAction extends TPresenterAction {

	public DisableCrudAction() {
		super(TActionType.SAVE, TActionType.DELETE);
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.control.action.TPresenterAction#runBefore()
	 */
	@Override
	public boolean runBefore() {
		TDynaPresenter<CustomActionMV> p = super.getPresenter();
		TMasterCrudViewBehavior<CustomActionMV, SampleB> b = 
				(TMasterCrudViewBehavior<CustomActionMV, SampleB>) p.getBehavior();
		
		b.addMessage(new TMessage(TMessageType.INFO, "The save and delete event disabled!"));
		
		return false;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.control.action.TPresenterAction#runAfter()
	 */
	@Override
	public void runAfter() {
		
	}

}
