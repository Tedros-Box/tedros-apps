/**
 * 
 */
package org.tedros.extension.component.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.extension.ejb.controller.IAdminAreaController;
import org.tedros.extension.ejb.controller.ICityController;
import org.tedros.extension.model.AdminArea;
import org.tedros.extension.model.City;
import org.tedros.extension.model.Country;
import org.tedros.fx.control.TItem;
import org.tedros.fx.util.TReflectionUtil;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;

import javafx.application.Platform;
import javafx.collections.ObservableList;

/**
 * @author Davis Gordon
 *
 */
class FilterHelper {

	static void filterCity(Annotation ann, Country country, AdminArea adminArea, ObservableList<City> items ) {
		Platform.runLater(()->{
			ICityController serv;
			try { 
				serv = ServiceLocator.getInstance().lookup(ICityController.JNDI_NAME);
				TResult<List<City>> r = serv.filter(TedrosContext.getLoggedUser().getAccessToken(), country, adminArea);
				if(r.getState().equals(TState.SUCCESS)) {
					addFirstItem(ann,items);
					List<City> lst = r.getValue();
					items.addAll(lst);
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}finally {
				ServiceLocator.getInstance().close();
			}
		});
	}

	static void filterAdminArea(Annotation ann, Country country, final ObservableList<AdminArea> items) {
		Platform.runLater(()->{
			IAdminAreaController serv;
			try { 
				serv = ServiceLocator.getInstance().lookup(IAdminAreaController.JNDI_NAME);
				TResult<List<AdminArea>> r = serv.filter(TedrosContext.getLoggedUser().getAccessToken(), country);
				if(r.getState().equals(TState.SUCCESS)) {
					addFirstItem(ann,items);
					List<AdminArea> lst = r.getValue();
					items.addAll(lst);
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}finally {
				ServiceLocator.getInstance().close();
			}
		});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static void addEmptyItem(ObservableList items) {
		if(items==null)
			return;
		items.add(0, new TItem("-", null));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void addFirstItem(Annotation ann, ObservableList items) {
		if(ann==null || items==null)
			return;
		try {
			Method m = TReflectionUtil.getMethod(ann, "firstItemTex");
			if(m!=null) {
				final String firstItemText = (String) m.invoke(ann);
				if(StringUtils.isNotBlank(firstItemText)){
					items.add(0, new TItem(TLanguage.getInstance().getString(firstItemText), null));
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
