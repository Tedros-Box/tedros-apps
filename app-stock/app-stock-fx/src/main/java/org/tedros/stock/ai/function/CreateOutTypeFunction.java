package org.tedros.stock.ai.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.stock.entity.OutType;
import org.tedros.stock.module.inventory.InventoryModule;
import org.tedros.stock.module.inventory.model.OutTypeMV;
import org.tedros.util.TLoggerUtil;

import javafx.application.Platform;
import javafx.collections.FXCollections;

public class CreateOutTypeFunction extends TFunction<EventsParam> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(CreateOutTypeFunction.class);
	
	public static final String NAME = "create_out_type";
	public static final String DESCRIPTION = "Opens the Inventory Module to create new 'Out Types' (stock removal classifications). " +
            "Use this tool when the user wants to define reasons for stock leaving the inventory, " +
            "such as 'Sales', 'Internal Consumption', 'Loss/Waste', 'Theft', 'Donation', or 'Expiration'. " +
            "It generates a list for user validation.";

	public CreateOutTypeFunction() {
		super(NAME, DESCRIPTION, EventsParam.class, 
		v->{
			
			LOGGER.info("Creating Out Types: {}", v);
			
			Platform.runLater(()->{
				try {
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
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			});
			
			return ToolCallResult.builder()
					.message("Out Types created successfully.")
					.result(Map.of(
		                    STATUS, SUCCESS,
		                    "out_type_created_count", v.getEvents().size(),
		                    ACTION, "out_type_screen_opened",
		                    INFO_MESSAGE, CONTENT_LOADED_IN_VIEW_FOR_USER_REVIEW_DO_NOT_RETRY
		                ))
					.build();
			
		});
		
	}

}
