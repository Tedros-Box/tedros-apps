/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.extension.component.builder;

import java.lang.annotation.Annotation;

import org.tedros.extension.component.annotation.TAdminAreaComboBox;
import org.tedros.extension.model.Country;
import org.tedros.fx.builder.ITControlBuilder;
import org.tedros.fx.builder.TBuilder;
import org.tedros.fx.control.TComboBoxField;
import org.tedros.fx.control.TItem;

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
implements ITControlBuilder<org.tedros.fx.control.TComboBoxField, Property<Object>> {
	
	@SuppressWarnings({"unchecked", "unused"})
	public org.tedros.fx.control.TComboBoxField build(final Annotation annotation, final Property<Object> attrProperty) throws Exception {
	
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
				
				final ComboBox cb = !"".equals(tAnnotation.countryField().trim()) 
						? (ComboBox) super.getComponentDescriptor().getFieldDescriptor(tAnnotation.countryField()).getControl()
								: null;
				
				if(cb!=null) {
					cb.getSelectionModel().selectedItemProperty().addListener((a, o, n) -> {
						control.getSelectionModel().clearSelection();
						control.getItems().clear();
						if(n instanceof Country) 
							FilterHelper.filterAdminArea(tAnnotation, (Country) n, control.getItems());
						else
							FilterHelper.addEmptyItem(control.getItems());
					});
					Country c = (Country) cb.getSelectionModel().getSelectedItem();
					if(c!=null)
						FilterHelper.filterAdminArea(null, c, control.getItems());
				}else if (!"".equals(countryIso2Code)) {
					Country c = new Country();
					c.setIso2Code(countryIso2Code);
					FilterHelper.filterAdminArea(null, c, control.getItems());
				}
			}
		});
		
		if(attrProperty!=null)
			control.setValue(attrProperty.getValue());
		
		return control;
	}
}
