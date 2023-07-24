/**
 * 
 */
package org.tedros.samples.module.entity;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.sample.domain.DomainApp;
import org.tedros.sample.entity.SampleA;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.module.entity.model.CustomActionMV;
import org.tedros.samples.module.entity.model.CustomDecoratorMV;
import org.tedros.samples.module.entity.model.IntegratedMV;
import org.tedros.samples.module.entity.model.MasterDetailMV;
import org.tedros.samples.module.entity.model.PageSearchMV;
import org.tedros.samples.module.entity.model.SettingSampleMV;

/**
 * @author Davis Gordon
 *
 */
@TView(title="Entity Samples",
	items = { 
		@TItem(title = "Edit master/detail entity", 
		model=SampleA.class, modelView=MasterDetailMV.class),
		@TItem(title = "Page and search entities", 
		modelView=PageSearchMV.class),
		@TItem(title = "Integrated entity sample", 
		modelView=IntegratedMV.class),
		@TItem(title = "Custom action sample", 
		modelView=CustomActionMV.class),
		@TItem(title = "Setting sample", 
		modelView=SettingSampleMV.class),
		@TItem(title = "Custom decorator sample", 
		modelView=CustomDecoratorMV.class)    
})
@TSecurity(id=DomainApp.SAMPLE_A_MODULE_ID, 
appName = SmplsKey.APP_SAMPLES, 
moduleName = "Samples", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class EntitySampleModule extends TModule {

}
