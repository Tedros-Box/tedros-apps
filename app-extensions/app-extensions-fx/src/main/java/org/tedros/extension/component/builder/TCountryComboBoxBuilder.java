/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.extension.component.builder;

import java.lang.annotation.Annotation;
import java.util.List;

import org.tedros.extension.component.annotation.TCountryComboBox;
import org.tedros.extension.ejb.controller.ICountryController;
import org.tedros.extension.model.Country;
import org.tedros.fx.builder.ITControlBuilder;
import org.tedros.fx.builder.TBuilder;
import org.tedros.fx.control.TComboBoxField;
import org.tedros.fx.control.TItem;
import org.tedros.fx.process.TOptionsProcess;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.result.TResult;

import javafx.beans.property.Property;
import javafx.concurrent.Worker.State;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;


/**
 * DESCRIÃ‡ÃƒO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TCountryComboBoxBuilder 
extends TBuilder
implements ITControlBuilder<org.tedros.fx.control.TComboBoxField, Property<Object>> {
	
	@SuppressWarnings({"unchecked"})
	public org.tedros.fx.control.TComboBoxField build(final Annotation annotation, final Property<Object> attrProperty) throws Exception {
	
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
