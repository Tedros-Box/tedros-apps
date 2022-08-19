/**
 * 
 */
package org.tedros.extension.contact.model;

import java.util.ArrayList;
import java.util.List;

import org.tedros.extension.model.ContactType;

import org.tedros.core.TLanguage;
import org.tedros.fx.builder.TGenericBuilder;
import org.tedros.fx.control.TItem;

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
