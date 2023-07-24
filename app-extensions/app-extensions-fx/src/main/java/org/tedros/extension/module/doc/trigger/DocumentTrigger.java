/**
 * 
 */
package org.tedros.extension.module.doc.trigger;

import java.util.Date;
import java.util.List;

import org.tedros.api.form.ITModelForm;
import org.tedros.core.context.TViewDescriptor;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.model.ITEntityModelView;
import org.tedros.extension.model.Document;
import org.tedros.extension.model.ModalDocumentMV;
import org.tedros.fx.control.trigger.TTrigger;
import org.tedros.fx.form.TFieldBox;
import org.tedros.server.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
public class DocumentTrigger extends TTrigger<List<ModalDocumentMV>> {

	public DocumentTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public void run(TEvent event, List<ModalDocumentMV> l, List<ModalDocumentMV> oldValue) {
		
		if(l!=null) {
			ITModelForm f = super.getForm();
			ITEntityModelView mv = (ITEntityModelView) f.gettModelView();
			TEntity e = (TEntity) mv.getModel();
			TViewDescriptor vds = TedrosAppManager.getInstance().getViewDescriptor(mv.getClass(), e.getClass());
			l.forEach(m->{
				Document d = m.getEntity();
				if(d.isNew()) {
					d.setIntegratedDate(new Date());
					//d.setIntegratedEntityId(e.getId());
					d.setIntegratedEntity(e.getClass().getName());
					d.setIntegratedModelView(mv.getClass().getName());
					d.setIntegratedModulePath(vds.getModuleDescriptor().getPathKeys());
					d.setIntegratedViewName(vds.getTitle());
					d.setIntegratedAppUUID(vds.getModuleDescriptor().getApplicationUUID());
				}
			});
		}
	}
}
