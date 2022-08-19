/**
 * 
 */
package com.tedros.location.module.place.action;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.TLanguage;
import com.tedros.fxapi.TFxKey;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.modal.TMessage;
import com.tedros.fxapi.modal.TMessageType;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TSaveViewBehavior;
import com.tedros.location.LocatKey;
import com.tedros.location.module.place.model.MapSettingMV;
import com.tedros.location.module.place.model.MapSettingModel;
import com.tedros.location.resource.AppResource;

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
	 * @see com.tedros.fxapi.control.action.TPresenterAction#runBefore()
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
	 * @see com.tedros.fxapi.control.action.TPresenterAction#runAfter()
	 */
	@Override
	public void runAfter() {
		// TODO Auto-generated method stub

	}

}
