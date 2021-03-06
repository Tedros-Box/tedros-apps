/**
 * 
 */
package com.tedros.docs.module.builder;

import java.util.Date;

import com.tedros.core.context.TModuleContext;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.notify.model.TNotify;
import com.tedros.docs.TDocsKey;
import com.tedros.docs.model.Document;
import com.tedros.docs.module.DocumentModule;
import com.tedros.docs.module.model.DocumentMV;
import com.tedros.extension.model.ContactType;
import com.tedros.fxapi.builder.TGenericBuilder;

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
		n.setIntegratedViewName(TDocsKey.APP_DOCS);
		n.setIntegratedDate(new Date());
		n.setIntegratedEntityId(d.getId());
		n.setIntegratedModelView(mv.getClass().getName());
		return n;
	}

}
