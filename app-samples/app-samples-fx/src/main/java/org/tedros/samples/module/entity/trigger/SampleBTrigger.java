/**
 * 
 */
package org.tedros.samples.module.entity.trigger;

import java.util.Date;
import java.util.List;

import org.tedros.core.context.TModuleContext;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.fx.control.trigger.TTrigger;
import org.tedros.fx.form.TFieldBox;
import org.tedros.sample.entity.SampleA;
import org.tedros.sample.entity.SampleB;
import org.tedros.samples.module.entity.EntitySampleModule;
import org.tedros.samples.module.entity.model.MasterDetailMV;
import org.tedros.samples.module.entity.model.SampleBDetailMV;

/**
 * @author Davis Gordon
 *
 */
public class SampleBTrigger extends TTrigger<List<SampleBDetailMV>> {


	public SampleBTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
	}

	@Override
	public void run(TEvent event, List<SampleBDetailMV> newValue, List<SampleBDetailMV> oldValue) {
		if(event.equals(TEvent.ADDED)) {
			MasterDetailMV mv = (MasterDetailMV) super.getForm().gettModelView();
			SampleA sample = mv.getEntity();
			TModuleContext ct = TedrosAppManager.getInstance().getModuleContext(EntitySampleModule.class);
			newValue.forEach(i->{
				SampleB e = i.getEntity();
				e.setIntegratedAppUUID(ct.getModuleDescriptor().getApplicationUUID());
				e.setIntegratedModulePath(ct.getModuleDescriptor().getPathKeys());
				e.setIntegratedViewName("Edit a master/detail entity");
				e.setIntegratedDate(new Date());
				e.setIntegratedEntityId(sample.getId());
				e.setIntegratedModelView(mv.getClass().getName());
			});
		}
		
	}

}
