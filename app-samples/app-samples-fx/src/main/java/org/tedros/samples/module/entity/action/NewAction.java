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
public class NewAction extends TPresenterAction {

	private long begin;
	
	public NewAction() {
		super(TActionType.NEW);
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.control.action.TPresenterAction#runBefore()
	 */
	@Override
	public boolean runBefore() {
		begin = System.nanoTime();
		TDynaPresenter<CustomActionMV> p = super.getPresenter();
		TMasterCrudViewBehavior<CustomActionMV, SampleB> b = 
				(TMasterCrudViewBehavior<CustomActionMV, SampleB>) p.getBehavior();
		
		b.addMessage(new TMessage(TMessageType.INFO, "Shows message before the new action!"));
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.control.action.TPresenterAction#runAfter()
	 */
	@Override
	public void runAfter() {
		TDynaPresenter<CustomActionMV> p = super.getPresenter();
		TMasterCrudViewBehavior<CustomActionMV, SampleB> b = 
				(TMasterCrudViewBehavior<CustomActionMV, SampleB>) p.getBehavior();
		long duration = System.nanoTime() - begin;
		b.addMessage(new TMessage(TMessageType.INFO, "Shows message after the new action!\n"
				+ "The interval between messages was: "+(duration/1000000)+"ms"));
		

	}

}
