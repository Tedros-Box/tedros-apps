package org.tedros.it.tools.module.gmud;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.entity.Gmud;
import org.tedros.it.tools.module.gmud.model.EditGmudMV;
import org.tedros.it.tools.module.gmud.model.ExecuteGmudMV;
import org.tedros.it.tools.module.gmud.model.ExecuteGmudModel;
import org.tedros.it.tools.module.gmud.model.ReviewGmudMV;
import org.tedros.it.tools.module.gmud.model.ReviewGmudModel;

@TView(items = {
		@TItem(title =  ItToolsKey.VIEW_GMUD_EDIT, description = ItToolsKey.VIEW_GMUD_EDIT_DESC, 
			modelView = EditGmudMV.class, model = Gmud.class),
		@TItem(title =  "Revisar GMUD", //description = ItToolsKey.VIEW_GMUD_EDIT_DESC, 
		modelView = ReviewGmudMV.class, model = ReviewGmudModel.class),
		@TItem(title =  "Executar GMUD", //description = ItToolsKey.VIEW_GMUD_EDIT_DESC, 
		modelView = ExecuteGmudMV.class, model = ExecuteGmudModel.class)})
public class GmudModule extends TModule {

}
