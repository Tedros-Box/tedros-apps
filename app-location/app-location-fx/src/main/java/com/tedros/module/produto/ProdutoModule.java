package com.tedros.module.produto;

import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.module.produto.model.ProdutoModelView;

@TSecurity(	id="TLOCAT_PRODUTO_MODULE", appName = "#{myapp.name}", 
moduleName = "#{module.adm}", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class ProdutoModule extends TModule {

	@Override
	public void tStart() {
		tShowView(new TDynaView<>( ProdutoModelView.class));
	}

}
