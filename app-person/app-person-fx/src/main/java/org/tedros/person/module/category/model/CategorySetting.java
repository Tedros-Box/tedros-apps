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
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.control.TableView;

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

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		repo = new TRepository();
		CategoryMV mv = super.getModelView();
		TAutoCompleteEntity ace = super.getControl("item");
		TButton addBtn = super.getControl("addBtn");
		TButton remBtn = super.getControl("remBtn");
		remBtn.setDisable(true);
		TableView<PersonTV> tbv = super.getControl("persons");
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
		
		EventHandler<ActionEvent> ev2 = ev ->{
			lst.removeAll(tbv.getSelectionModel().getSelectedItems());
		};
		repo.add("ev2", ev2);
		remBtn.setOnAction(new WeakEventHandler<>(ev2));
		
		ListChangeListener<PersonTV> lchl = ch ->{
			if(ch.next())
				remBtn.setDisable(ch.getList().size()==0);
		};
		repo.add("lchl", lchl);
		
		tbv.getSelectionModel().getSelectedItems()
		.addListener(new WeakListChangeListener<>(lchl));
		
		
	}

}
