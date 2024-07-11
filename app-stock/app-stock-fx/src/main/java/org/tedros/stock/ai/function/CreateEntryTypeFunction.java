package org.tedros.stock.ai.function;

import java.util.ArrayList;
import java.util.List;

import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.stock.entity.EntryType;
import org.tedros.stock.module.inventory.InventoryModule;
import org.tedros.stock.module.inventory.model.EntryTypeMV;

import javafx.application.Platform;
import javafx.collections.FXCollections;

public class CreateEntryTypeFunction extends TFunction<EventsParam> {

	public CreateEntryTypeFunction() {
		super("create_entry_type", "Creates a list of entry types for inventory", EventsParam.class, 
		v->{
			
			Platform.runLater(()->{
				List<EntryTypeMV> l = new ArrayList<>();
				
				for(EventTypeParam p : v.getEvents()) {
					EntryType e = new EntryType();
					e.setCode(p.getCode());
					e.setName(p.getName());
					e.setDescription(p.getDescription());
					
					l.add(new EntryTypeMV(e));
				}
				TedrosAppManager.getInstance()
				.loadInModule(InventoryModule.class, FXCollections.observableArrayList(l));
			});
			
			
			
			return new Response("Successful");
			
		});
		
	}

}
