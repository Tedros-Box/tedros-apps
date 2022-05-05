/**
 * 
 */
package com.tedros.location.module.address.model;

import java.util.List;

import javax.naming.NamingException;

import com.tedros.core.context.TedrosContext;
import com.tedros.core.service.remote.ServiceLocator;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.controller.IAdminAreaController;
import com.tedros.ejb.controller.ICityController;
import com.tedros.fxapi.control.TComboBoxField;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.form.TSetting;
import com.tedros.location.model.AdminArea;
import com.tedros.location.model.City;
import com.tedros.location.model.Country;

import javafx.application.Platform;

/**
 * @author Davis Gordon
 *
 */
public class AddressSetting extends TSetting {

	/**
	 * @param descriptor
	 */
	public AddressSetting(TComponentDescriptor descriptor) {
		super(descriptor);
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.form.TSetting#run()
	 */
	@Override
	public void run() {
		AddressMV mv = super.getModelView();
		//SimpleObjectProperty<Country> country = super.getProperty("country");
		TComboBoxField<Country> countryCB = super.getControl("country");
		TComboBoxField<AdminArea> admAreaCB = super.getControl("adminArea");
		TComboBoxField<City> cityCB = super.getControl("city");
		
		countryCB.getSelectionModel().selectedItemProperty()
		.addListener((a,o,n)->{
			admAreaCB.getSelectionModel().clearSelection();
			admAreaCB.getItems().clear();
			if(n!=null){
				countryAction(admAreaCB, n);
			}
		});
		
		admAreaCB.getSelectionModel().selectedItemProperty()
		.addListener((a,o,n)->{
			cityCB.getSelectionModel().clearSelection();
			cityCB.getItems().clear();
			if(n!=null){
				admAreaAction(countryCB, cityCB, n);
			}
		});
		
		if(mv.getAdminArea().getValue()!=null) {
			countryAction(admAreaCB, mv.getCountry().getValue());
		}
		
		if(mv.getCity().getValue()!=null) {
			admAreaAction(countryCB, cityCB, mv.getAdminArea().getValue());
		}
		
	}

	private void admAreaAction(TComboBoxField<Country> countryCB, TComboBoxField<City> cityCB, AdminArea n) {
		Platform.runLater(()->{
			ICityController serv;
			try { 
				serv = ServiceLocator.getInstance().lookup("ICityControllerRemote");
				TResult<List<City>> r = serv.filter(TedrosContext.getLoggedUser().getAccessToken(), countryCB.getValue(), n);
				if(r.getResult().equals(EnumResult.SUCESS)) {
					List<City> lst = r.getValue();
					cityCB.getItems().addAll(lst);
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}finally {
				ServiceLocator.getInstance().close();
			}
		});
	}

	private void countryAction(TComboBoxField<AdminArea> admAreaCB, Country n) {
		Platform.runLater(()->{
			IAdminAreaController serv;
			try { 
				serv = ServiceLocator.getInstance().lookup("IAdminAreaControllerRemote");
				TResult<List<AdminArea>> r = serv.filter(TedrosContext.getLoggedUser().getAccessToken(), n);
				if(r.getResult().equals(EnumResult.SUCESS)) {
					List<AdminArea> lst = r.getValue();
					admAreaCB.getItems().addAll(lst);
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}finally {
				ServiceLocator.getInstance().close();
			}
		});
	}
	
	

}
