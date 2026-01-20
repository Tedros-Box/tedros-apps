package org.tedros.stock.ai.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.stock.entity.EntryType;
import org.tedros.stock.module.inventory.InventoryModule;
import org.tedros.stock.module.inventory.model.EntryTypeMV;
import org.tedros.util.TLoggerUtil;

import javafx.application.Platform;
import javafx.collections.FXCollections;

public class CreateEntryTypeFunction extends TFunction<EventsParam> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(CreateEntryTypeFunction.class);
	
	public static final String NAME = "create_entry_type";
	public static final String DESCRIPTION = "Opens the Inventory Module to create new 'Entry Types' (stock movement classifications). " +
            "Use this when the user wants to define new reasons for stock entry, such as 'Purchase', 'Return', or 'Bonus'. " +
            "It prepares a list for the user to review in the application.";

	public CreateEntryTypeFunction() {
		super(NAME, DESCRIPTION, EventsParam.class, 
		v->{
						
			LOGGER.info("Creating Entry Types: {}", v);
			
			Platform.runLater(()->{
				try {
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
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			});
			
			Map<String, Object> resultData = Map.of(
                    "status", "success",
                    "entry_type_created_count", v.getEvents().size(),
                    "action", "entry_type_screen_opened",
                    "message", "Content loaded in view for user review. Do not retry."
                );
			
			return new ToolCallResult(SUSCESS_MESSAGE, resultData, true);
			
		});
		
	}

}
