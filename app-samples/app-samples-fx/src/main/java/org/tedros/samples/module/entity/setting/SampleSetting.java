/**
 * 
 */
package org.tedros.samples.module.entity.setting;

import java.util.List;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.core.TLanguage;
import org.tedros.core.repository.TRepository;
import org.tedros.extension.ejb.controller.IAdminAreaController;
import org.tedros.extension.model.AdminArea;
import org.tedros.extension.model.Country;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.control.TItem;
import org.tedros.fx.form.TSetting;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.samples.module.entity.model.SettingSampleMV;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TSelect;
import org.tedros.server.result.TResult;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.concurrent.Worker.State;
import javafx.scene.control.ComboBox;

/**
 * @author Davis Gordon
 *
 */
public class SampleSetting extends TSetting {

	private TRepository repo;
	
	/**
	 * @param descriptor
	 */
	public SampleSetting(ITComponentDescriptor descriptor) {
		super(descriptor);
		this.repo = new TRepository();
	}

	/* (non-Javadoc)
	 * @see org.tedros.api.form.ITSetting#dispose()
	 */
	@Override
	public void dispose() {
		this.repo.clear();
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.form.TSetting#run()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void run() {
		SettingSampleMV mv = super.getModelView();
		ChangeListener<Country> chl = (a,o,n)->{
			ComboBox admAreaCbx = super.getControl("adminArea");
			admAreaCbx.getItems().clear();
			admAreaCbx.getItems().add(new TItem(TLanguage.getInstance()
					.getString(TUsualKey.SELECT), null));
			mv.getAdminArea().setValue(null);
			loadAdminArea(n);
		};
		repo.add("chl", chl);
		mv.getCountry().addListener(new WeakChangeListener<>(chl));
		loadAdminArea(mv.getCountry().getValue());

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadAdminArea(Country n) {
		if(n instanceof Country) {
			ComboBox<AdminArea> admAreaCbx = super.getControl("adminArea");
			TEntityProcess p = 
			new TEntityProcess(AdminArea.class, IAdminAreaController.JNDI_NAME) {};
			p.stateProperty().addListener((obs,old,state)->{
				if(state.equals(State.SUCCEEDED)) {
					List<TResult<List<AdminArea>>> l = (List<TResult<List<AdminArea>>>) p.getValue();
					if(!l.isEmpty()) {
						TResult<List<AdminArea>> r = l.get(0);
						if(r.getValue()!=null)
							admAreaCbx.getItems().addAll(r.getValue());
					}
				}
			});
			TSelect sel = new TSelect(AdminArea.class);
			sel.addAndCondition(TSelect.ALIAS, "countryIso2Code", TCompareOp.EQUAL, n.getIso2Code());
			sel.addOrderBy("name");
			p.search(sel);
			p.startProcess();
		}
	}

}
