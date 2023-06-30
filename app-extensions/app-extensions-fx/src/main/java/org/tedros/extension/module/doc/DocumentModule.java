/**
 * 
 */
package org.tedros.extension.module.doc;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.extension.ExtKey;
import org.tedros.extension.domain.DomainApp;
import org.tedros.extension.model.Document;
import org.tedros.extension.model.DocumentStatus;
import org.tedros.extension.model.DocumentType;
import org.tedros.extension.module.doc.model.DocumentMV;
import org.tedros.extension.module.doc.model.DocumentStatusMV;
import org.tedros.extension.module.doc.model.DocumentTypeMV;
/**
 * @author Davis Gordon
 *
 */
@TView(title=ExtKey.VIEW_GROUP_DOCS,
items = { 
	@TItem(title = ExtKey.VIEW_DOCS, 
	description=ExtKey.VIEW_DOCS_DESC,
	modelView=DocumentMV.class, model=Document.class),
	@TItem(title = ExtKey.VIEW_DOCS_TYPE, 
	description=ExtKey.VIEW_DOCS_TYPE_DESC,
	modelView=DocumentTypeMV.class, model=DocumentType.class),
	@TItem(title = ExtKey.VIEW_DOCS_STATE, 
	description=ExtKey.VIEW_DOCS_STATE_DESC,
	modelView=DocumentStatusMV.class, model=DocumentStatus.class)
})
@TSecurity(	id=DomainApp.DOCUMENT_MODULE_ID, appName = ExtKey.APP_EXTS, 
		 moduleName = ExtKey.MODULE_DOCS, 
		 allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class DocumentModule extends TModule {

}
