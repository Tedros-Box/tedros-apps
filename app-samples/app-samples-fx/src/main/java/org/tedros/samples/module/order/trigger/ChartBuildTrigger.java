/**
 * 
 */
package org.tedros.samples.module.order.trigger;

import org.tedros.fx.control.TDetailField;
import org.tedros.fx.control.trigger.TTrigger;
import org.tedros.fx.form.TFieldBox;

import javafx.beans.Observable;

/**
 * @author Davis Gordon
 *
 */
public class ChartBuildTrigger extends TTrigger<Object> {

	public ChartBuildTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(TEvent event, Object newValue, Object oldValue) {
		if(event.equals(TEvent.BUILD))
			return;
		
		TFieldBox target = super.getTarget();
		TDetailField df = (TDetailField) target.gettControl();
		df.tClearAction();
		df.tNewAction();
	}

}
