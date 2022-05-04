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
import com.tedros.location.module.country.model.CountryMV;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;

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
		TComboBoxField<CountryMV> countryCB = super.getControl("country");
		TComboBoxField<AdminArea> admAreaCB = super.getControl("adminArea");
		TComboBoxField<City> cityCB = super.getControl("city");
		
		countryCB.getSelectionModel().selectedItemProperty()
		.addListener((a,o,n)->{
			admAreaCB.getSelectionModel().clearSelection();
			admAreaCB.getItems().clear();
			if(n!=null){
				Platform.runLater(()->{
					IAdminAreaController serv;
					try { 
						serv = ServiceLocator.getInstance().lookup("IAdminAreaControllerRemote");
						TResult<List<AdminArea>> r = serv.filter(TedrosContext.getLoggedUser().getAccessToken(), n.getEntity());
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
		});
		
		admAreaCB.getSelectionModel().selectedItemProperty()
		.addListener((a,o,n)->{
			cityCB.getSelectionModel().clearSelection();
			cityCB.getItems().clear();
			if(n!=null){
				Platform.runLater(()->{
					ICityController serv;
					try { 
						serv = ServiceLocator.getInstance().lookup("ICityControllerRemote");
						TResult<List<City>> r = serv.filter(TedrosContext.getLoggedUser().getAccessToken(), countryCB.getValue().getEntity(), n);
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
		});
		
	}
	
	

}
