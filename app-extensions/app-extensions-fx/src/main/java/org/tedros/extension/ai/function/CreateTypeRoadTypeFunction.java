package org.tedros.extension.ai.function;

import java.util.ArrayList;
import java.util.List;

import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.extension.model.StreetType;
import org.tedros.extension.model.StreetTypeMV;
import org.tedros.extension.module.place.PlaceModule;

import javafx.application.Platform;
import javafx.collections.FXCollections;

public class CreateTypeRoadTypeFunction extends TFunction<ListItemParam> {

	public CreateTypeRoadTypeFunction() {
		super("create_type_road", "Creates a list of types of roads", ListItemParam.class, 
			v->{				
				Platform.runLater(()->{
					List<StreetTypeMV> l = new ArrayList<>();
					
					for(CodeNameDescParam p : v.getItems()) {
						StreetType e = new StreetType();
						e.setCode(p.getCode());
						e.setName(p.getName());
						e.setDescription(p.getDescription());
						
						l.add(new StreetTypeMV(e));
					}
					
					TedrosAppManager.getInstance()
					.loadInModule(PlaceModule.class, FXCollections.observableArrayList(l));
				});
				
				return new Response("Successful");
			});
	}

}
