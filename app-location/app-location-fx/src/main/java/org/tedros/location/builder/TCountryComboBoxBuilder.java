/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.location.builder;

import java.lang.annotation.Annotation;
import java.util.List;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.controller.ICountryController;
import com.tedros.fxapi.builder.ITControlBuilder;
import com.tedros.fxapi.builder.TBuilder;
import com.tedros.fxapi.control.TComboBoxField;
import com.tedros.fxapi.control.TItem;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.process.TOptionsProcess;
import com.tedros.location.annotation.TCountryComboBox;
import com.tedros.location.model.Country;

import javafx.beans.property.Property;
import javafx.concurrent.Worker.State;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;


/**
 * DESCRIÃ‡ÃƒO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TCountryComboBoxBuilder 
extends TBuilder
implements ITControlBuilder<com.tedros.fxapi.control.TComboBoxField, Property<Object>> {
	
	@SuppressWarnings({"unchecked"})
	public com.tedros.fxapi.control.TComboBoxField build(final Annotation annotation, final Property<Object> attrProperty) throws Exception {
	
		final TCountryComboBox tAnnotation = (TCountryComboBox) annotation;
		final TComboBoxField control = new TComboBoxField();
		control.getSelectionModel().selectedItemProperty().addListener((a, o, n) -> {
			if(n instanceof TItem)
				attrProperty.setValue(((TItem)n).getValue());
			else
				attrProperty.setValue(n);
		});	
								
		control.setCellFactory(p -> {
            final ListCell<Object> cell = new ListCell<Object>(){
                @Override
                protected void updateItem(Object obj, boolean bln) {
                    super.updateItem(obj, bln);
                    if(obj != null){
    					setText(obj.toString());
    				}else{
                        setText(null);
                    }
                }
            };
            return cell;
        });
		
		callParser(tAnnotation, (ComboBox) control);
		
		final Class<? extends ITEntity> eClass = Country.class;
		final String serviceName = ICountryController.JNDI_NAME;
		
		final TOptionsProcess process = new TOptionsProcess(eClass, serviceName) {};
	
		process.stateProperty().addListener((arg0, arg1, arg2) -> {
				
			if(arg2.equals(State.SUCCEEDED)){
				List<TResult<Object>> resultados = (List<TResult<Object>>) process.getValue();
				if(resultados!=null && resultados.size()>0){
					TResult result = resultados.get(0);
					if(result.getValue()!=null && result.getValue() instanceof List<?>){
						List<Country> lst = (List<Country>) result.getValue();
						control.getItems().addAll(lst);
						
						//Country value = (Country) attrProperty.getValue();
						//control.setValue(value);
					}
				}
			}
		});
		
		process.list();
		process.startProcess();
		
		if(attrProperty!=null)
			control.setValue(attrProperty.getValue());
		
		return control;
	}
}
