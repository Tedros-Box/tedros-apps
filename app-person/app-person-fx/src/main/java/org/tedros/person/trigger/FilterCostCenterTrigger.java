/**
 * 
 */
package org.tedros.person.trigger;

import java.util.List;

import org.tedros.core.TLanguage;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.control.TComboBoxField;
import org.tedros.fx.control.TItem;
import org.tedros.fx.control.trigger.TTrigger;
import org.tedros.fx.form.TFieldBox;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.person.ejb.controller.ICostCenterController;
import org.tedros.person.model.CostCenter;
import org.tedros.person.model.ICostCenterAccounting;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.module.company.table.CostCenterTV;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;

import javafx.beans.property.Property;
import javafx.concurrent.Worker.State;

/**
 * @author Davis Gordon
 *
 */
public class FilterCostCenterTrigger extends TTrigger<LegalPerson> {

	public FilterCostCenterTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void run(TEvent event, LegalPerson value, LegalPerson old) {
		TComboBoxField cmb = (TComboBoxField) super.getTarget().gettControl();
		if(value!=null) {
			TModelView mv =  (TModelView) super.getForm().gettModelView();
			TEntityProcess<CostCenter> p = 
					new TEntityProcess<CostCenter>(CostCenter.class, ICostCenterController.JNDI_NAME) {};
			p.stateProperty().addListener((a,o,n)->{
				if(n.equals(State.SUCCEEDED)) {
					List l = p.getValue();
					if(!l.isEmpty()) {
						TResult<List<CostCenter>> r = (TResult<List<CostCenter>>) l.get(0);
						if(r.getState().equals(TState.SUCCESS)) {
							cmb.getItems().clear();
							if(!r.getValue().isEmpty()) {
								cmb.getItems()
								.add(new TItem(TLanguage.getInstance().getString(TUsualKey.SELECT), null));
								r.getValue().forEach(c->{
									CostCenterTV cc = new CostCenterTV(c);
									cmb.getItems().add(cc);
									ICostCenterAccounting entity = (ICostCenterAccounting) mv.getModel();
									if(entity.getCostCenter()!=null 
											&& entity.getCostCenter().equals(c)) {
										cmb.getSelectionModel().select(cc);
									}
									if(cmb.getSelectionModel().isEmpty())
										((Property)mv.getProperty("costCenter")).setValue(null);
								});
							}
						}
					}
				}
			});
			CostCenter u = new CostCenter();
			LegalPerson lp = new LegalPerson();
			lp.setId(value.getId());
			u.setLegalPerson(lp);
			p.search(u);
			p.startProcess();
		}else {
			cmb.getItems().clear();
			cmb.getSelectionModel().clearSelection();
			
		}
		
	}

}
