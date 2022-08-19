/**
 * 
 */
package com.tedros.extension.contact.model;

import java.util.ArrayList;
import java.util.List;

import com.tedros.core.TLanguage;
import com.tedros.extension.model.ContactType;
import com.tedros.fxapi.builder.TGenericBuilder;
import com.tedros.fxapi.control.TItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TypeItemBuiler extends TGenericBuilder<ObservableList> {

	@Override
	public ObservableList build() {
		TLanguage lan = TLanguage.getInstance();
		List<TItem> l = new ArrayList<>();
		for(ContactType t : ContactType.values()) {
			TItem i = new TItem(lan.getString(t.getValue()), t);
			l.add(i);
		}
		 
		return FXCollections.observableArrayList(l);
	}

}
