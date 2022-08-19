/**
 * 
 */
package org.tedros.location.module.report.action;

import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.form.TDefaultForm;
import org.tedros.fx.form.TProgressIndicatorForm;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.report.behavior.TDataSetReportBehavior;

import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

/**
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class SearchAction extends TPresenterAction {
	
	/**
	 * @param tActionType
	 */
	public SearchAction() {
		super(TActionType.SEARCH);
	}
	
	@Override
	public boolean runBefore() {
		return true;
	}

	@Override
	public void runAfter() {
		TDynaPresenter p = super.getPresenter();
		TDataSetReportBehavior b =  (TDataSetReportBehavior) p.getBehavior();
		TDefaultForm f = (TDefaultForm) ((TProgressIndicatorForm) b.getForm()).gettForm();
		for(Node n : f.getChildren())
			if(n instanceof Accordion) {
				Accordion acc = (Accordion) n;
				TitledPane res = acc.getPanes().get(1);
				acc.setExpandedPane(res);
				break;
			}
	}

	

}
