package org.tedros.redminetools.module.tools;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.redminetools.model.RedmineProject;
import org.tedros.redminetools.module.tools.model.RedmineProjectMV;

@TView(title="Tools",
items = {
	@TItem(title="Projetos", description="Lista todos os projetos",
	model = RedmineProject.class, modelView=RedmineProjectMV.class)
})
public class RedmineToolsModule extends TModule {

}
