package org.tedros.extension.ai.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.extension.ai.model.CodeNameDescParam;
import org.tedros.extension.ai.model.ListItemParam;
import org.tedros.extension.model.StreetType;
import org.tedros.extension.model.StreetTypeMV;
import org.tedros.extension.module.place.PlaceModule;
import org.tedros.util.TLoggerUtil;

import javafx.application.Platform;
import javafx.collections.FXCollections;

public class CreateTypeRoadTypeFunction extends TFunction<ListItemParam> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(CreateTypeRoadTypeFunction.class);
	
	public static final String NAME = "create_road_type";
	public static final String DESCRIPTION = "Opens the Place/Location Module to create new 'Street Types' (Thoroughfares). " +
            "Use this to define address prefixes such as 'Avenue', 'Street', 'Boulevard', 'Lane', 'Alley', or 'Plaza'. " +
            "Do NOT use this for road surface types (like asphalt or dirt). " +
            "It generates a list for user validation.";

	public CreateTypeRoadTypeFunction() {
		super(NAME, DESCRIPTION, ListItemParam.class, 
			v->{
				
				LOGGER.info("Creating Road Types: {}", v);
				
				Platform.runLater(()->{
					try {
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
					} catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
					}
				});
				
				return ToolCallResult.builder()
						.message("Road Types created successfully.")
						.result(Map.of(
			                    STATUS, SUCCESS,
			                    "road_type_created_count", v.getItems().size(),
			                    ACTION, "road_type_screen_opened",
			                    SYSTEM_INSTRUCTION, "The system has opened the Place/Location screen. "
				                    	+ "Do not retry again. Inform the user to check the opened screen."
			                ))
						.build();
			});
	}

}
