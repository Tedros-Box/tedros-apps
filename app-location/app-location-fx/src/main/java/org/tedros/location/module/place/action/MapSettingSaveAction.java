/**
 * 
 */
package org.tedros.location.module.place.action;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.tedros.location.LocatKey;
import org.tedros.location.module.place.model.MapSettingMV;
import org.tedros.location.module.place.model.MapSettingModel;
import org.tedros.location.resource.AppResource;

import org.tedros.core.TLanguage;
import org.tedros.fx.TFxKey;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.modal.TMessage;
import org.tedros.fx.modal.TMessageType;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.entity.behavior.TSaveViewBehavior;

/**
 * @author Davis Gordon
 *
 */
public class MapSettingSaveAction extends TPresenterAction {

	/**
	 * @param tActionType
	 */
	public MapSettingSaveAction() {
		super(TActionType.SAVE);
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.control.action.TPresenterAction#runBefore()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean runBefore() {
		TSaveViewBehavior bv = (TSaveViewBehavior) ((TDynaPresenter)super.getPresenter()).getBehavior();
		
		MapSettingMV mv = (MapSettingMV) bv.getModelView();
		String type = mv.getModel().getMapType();
		String key = mv.getModel().getMapquestKey();
		
		if(mv.getModel().isMapQuestType() && StringUtils.isBlank(key)) {
			bv.addMessage(new TMessage(TMessageType.WARNING,TLanguage.getInstance()
					.getString(LocatKey.MSG_MAPQUEST_KEY_REQUIRED)));
			return false;
		}
		Properties p = new Properties();
		p.setProperty(MapSettingModel.TYPE, type);
		p.setProperty(MapSettingModel.MAPQUEST_KEY, key);
		AppResource.saveSettings(p);
		
		bv.addMessage(new TMessage(TMessageType.INFO, TLanguage.getInstance()
				.getFormatedString(TFxKey.MESSAGE_SAVE, "Map Settings")));
		
		return false;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.control.action.TPresenterAction#runAfter()
	 */
	@Override
	public void runAfter() {
		// TODO Auto-generated method stub

	}

}
