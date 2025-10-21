package org.tedros.redminetools.module.tools;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.redminetools.module.tools.model.TerosRedmineMV;
import org.tedros.redminetools.module.tools.model.TerosRedmineModel;

@TView(title="Teros AI Response",
items = {
	@TItem(title="Teros AI Response", description="Exibe respostas do Teros em HTML",
	model = TerosRedmineModel.class, modelView=TerosRedmineMV.class)
})
public class RedmineToolsModule extends TModule {

}
