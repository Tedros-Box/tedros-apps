package org.tedros.stock.ai.function;

import java.util.ArrayList;
import java.util.List;

import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.stock.entity.OutType;
import org.tedros.stock.module.inventory.InventoryModule;
import org.tedros.stock.module.inventory.model.OutTypeMV;

import javafx.application.Platform;
import javafx.collections.FXCollections;

public class CreateOutTypeFunction extends TFunction<EventsParam> {

	public CreateOutTypeFunction() {
		super("create_out_type", "Creates a list of out types for inventory", EventsParam.class, 
		v->{
			
			Platform.runLater(()->{
				List<OutTypeMV> l = new ArrayList<>();
				
				for(EventTypeParam p : v.getEvents()) {
					OutType e = new OutType();
					e.setCode(p.getCode());
					e.setName(p.getName());
					e.setDescription(p.getDescription());
					
					l.add(new OutTypeMV(e));
				}
				TedrosAppManager.getInstance()
				.loadInModule(InventoryModule.class, FXCollections.observableArrayList(l));
			});
			
			return new Response("Successful");
			
		});
		
	}

}
