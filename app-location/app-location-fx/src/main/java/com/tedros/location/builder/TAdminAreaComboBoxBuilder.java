/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.location.builder;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.builder.ITControlBuilder;
import com.tedros.fxapi.builder.TBuilder;
import com.tedros.fxapi.control.TComboBoxField;
import com.tedros.fxapi.control.TItem;
import com.tedros.location.annotation.TAdminAreaComboBox;
import com.tedros.location.model.Country;

import javafx.beans.property.Property;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;


/**
 * DESCRIÃ‡ÃƒO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TAdminAreaComboBoxBuilder 
extends TBuilder
implements ITControlBuilder<com.tedros.fxapi.control.TComboBoxField, Property<Object>> {
	
	@SuppressWarnings({"unchecked", "unused"})
	public com.tedros.fxapi.control.TComboBoxField build(final Annotation annotation, final Property<Object> attrProperty) throws Exception {
	
		final TAdminAreaComboBox tAnnotation = (TAdminAreaComboBox) annotation;
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
                    String s = obj!=null ? obj.toString() : null;
                    setText(s);
                }
            };
            return cell;
        });
		
		callParser(tAnnotation, (ComboBox) control);
		
		super.getComponentDescriptor().getForm().tLoadedProperty().addListener((x,y,b)->{
		
			if(b) {
				String countryIso2Code = tAnnotation.countryIso2Code();
				
				final ComboBox cb = (ComboBox) super.getComponentDescriptor().getFieldDescriptor(tAnnotation.countryField()).getControl();
				
				if(cb!=null) {
					cb.getSelectionModel().selectedItemProperty().addListener((a, o, n) -> {
						control.getSelectionModel().clearSelection();
						control.getItems().clear();
						if(n instanceof Country) {
							LocationUtils.filterAdminArea(tAnnotation, (Country) n, control.getItems());
						}
					});
					Country c = (Country) cb.getSelectionModel().getSelectedItem();
					if(c!=null)
						LocationUtils.filterAdminArea(null, c, control.getItems());
				}else if (!"".equals(countryIso2Code)) {
					Country c = new Country();
					c.setIso2Code(countryIso2Code);
					LocationUtils.filterAdminArea(null, c, control.getItems());
				}
			}
		});
		
		if(attrProperty!=null)
			control.setValue(attrProperty.getValue());
		
		return control;
	}
}
