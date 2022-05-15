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
import com.tedros.location.annotation.TCityComboBox;
import com.tedros.location.model.AdminArea;
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
public final class TCityComboBoxBuilder 
extends TBuilder
implements ITControlBuilder<com.tedros.fxapi.control.TComboBoxField, Property<Object>> {
	
	@SuppressWarnings({"unchecked"})
	public com.tedros.fxapi.control.TComboBoxField build(final Annotation annotation, final Property<Object> attrProperty) throws Exception {
	
		final TCityComboBox tAnnotation = (TCityComboBox) annotation;
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
				String admName = tAnnotation.adminAreaName();
				
				final ComboBox countryCb = !"".equals(tAnnotation.countryField().trim()) 
						? (ComboBox) super.getComponentDescriptor().getFieldDescriptor(tAnnotation.countryField()).getControl() 
								: null;
						
				final ComboBox admCb = !"".equals(tAnnotation.adminAreaField().trim()) 
						? (ComboBox) super.getComponentDescriptor().getFieldDescriptor(tAnnotation.adminAreaField()).getControl()
								: null;
						
				if(admCb!=null) {
					admCb.getSelectionModel().selectedItemProperty().addListener((a, o, n) -> {
						control.getItems().clear();
						control.getSelectionModel().clearSelection();
						if(n instanceof AdminArea)
							filter(tAnnotation, control, countryIso2Code, countryCb!=null?(Country)countryCb.getSelectionModel().getSelectedItem():null, (AdminArea) n);
						else
							LocationUtils.addEmptyItem(control.getItems());
					});
					AdminArea n = admCb!=null ? (AdminArea) admCb.getSelectionModel().getSelectedItem() : null;
					if(n!=null)
						filter(null, control, countryIso2Code, countryCb!=null?(Country)countryCb.getSelectionModel().getSelectedItem():null, n);
				}else if (!"".equals(admName)) {
					AdminArea n = new AdminArea();
					n.setName(admName);
					filter(null, control, countryIso2Code, countryCb!=null?(Country)countryCb.getSelectionModel().getSelectedItem():null, n);
				}
			}
		});
		if(attrProperty!=null)
			control.setValue(attrProperty.getValue());
		
		return control;
	}

	@SuppressWarnings("unchecked")
	private void filter(Annotation ann, final TComboBoxField control, String countryIso2Code, Country country,
			AdminArea n) {
		Country c = null;
		if(country!=null)
			c = country;
		else if(!"".equals(countryIso2Code)) {
			c = new Country();
			c.setIso2Code(countryIso2Code);
		}
		LocationUtils.filterCity(ann, c, n, control.getItems());
	}
}
