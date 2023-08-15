/**
 * 
 */
package org.tedros.person.module.category.model;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.control.TAutoCompleteEntity;
import org.tedros.fx.control.TButton;
import org.tedros.fx.form.TSetting;
import org.tedros.person.table.PersonTV;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;

/**
 * @author Davis Gordon
 *
 */
public class CategorySetting extends TSetting {

	private TRepository repo;
	/**
	 * @param descriptor
	 */
	public CategorySetting(ITComponentDescriptor descriptor) {
		super(descriptor);
	}

	@Override
	public void dispose() {
		repo.clear();
	}

	@Override
	public void run() {
		repo = new TRepository();
		CategoryMV mv = super.getModelView();
		TAutoCompleteEntity ace = super.getControl("item");
		TButton addBtn = super.getControl("addBtn");
		
		ObservableList<PersonTV> lst = mv.getPersons();
		SimpleObjectProperty<PersonTV> item = mv.getItem();
		
		ChangeListener<PersonTV> chl1 = (a,o,n)->{
			addBtn.setDisable(n==null);
		};
		repo.add("chl1", chl1);
		item.addListener(new WeakChangeListener<>(chl1));
		
		EventHandler<ActionEvent> ev1 = ev ->{
			if(!lst.contains(item.getValue())) {
				lst.add(item.getValue());
				ace.setText(null);
			}
		};
		repo.add("ev1", ev1);
		addBtn.setOnAction(new WeakEventHandler<>(ev1));
	}

}
