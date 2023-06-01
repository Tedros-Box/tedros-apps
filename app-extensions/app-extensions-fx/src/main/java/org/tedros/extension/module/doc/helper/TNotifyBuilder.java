/**
 * 
 */
package org.tedros.extension.module.doc.helper;

import java.util.Date;

import org.tedros.core.context.TModuleContext;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.notify.model.TNotify;
import org.tedros.extension.ExtKey;
import org.tedros.extension.model.ContactType;
import org.tedros.extension.model.Document;
import org.tedros.extension.module.doc.DocumentModule;
import org.tedros.extension.module.doc.model.DocumentMV;
import org.tedros.fx.builder.TGenericBuilder;

/**
 * @author Davis Gordon
 *
 */
public class TNotifyBuilder extends TGenericBuilder<TNotify> {

	@Override
	public TNotify build() {
		
		DocumentMV mv = (DocumentMV) super.getComponentDescriptor().getModelView();
		Document d = mv.getModel();
		
		StringBuilder to = new StringBuilder("");
		if(d.getContacts()!=null)
			d.getContacts().stream().filter(p->{
				return p.getType()!=null && p.getType().equals(ContactType.EMAIL);
			}).forEach(c->{
				if(!to.toString().isEmpty())
					to.append(", ");
				to.append(c.getValue());
			});
		
		TNotify n = new TNotify();
		n.setTo(to.toString());
		
		if(d.getFile()!=null)
			n.setFile(d.getFile());
		
		if(d.getContent()!=null)
			n.setContent(d.getContent());
		
		if(d.getName()!=null)
			n.setSubject(d.getName());
		
		TModuleContext ct = TedrosAppManager.getInstance().getModuleContext(DocumentModule.class);
		
		n.setIntegratedAppUUID(ct.getModuleDescriptor().getApplicationUUID());
		n.setIntegratedModulePath(ct.getModuleDescriptor().getPathKeys());
		n.setIntegratedViewName(ExtKey.APP_EXTS);
		n.setIntegratedDate(new Date());
		n.setIntegratedEntityId(d.getId());
		n.setIntegratedModelView(mv.getClass().getName());
		return n;
	}

}
