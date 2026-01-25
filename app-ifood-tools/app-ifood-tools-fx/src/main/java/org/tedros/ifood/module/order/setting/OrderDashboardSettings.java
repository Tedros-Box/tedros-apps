package org.tedros.ifood.module.order.setting;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.fx.form.TSetting;
import org.tedros.ifood.api.client.IFoodEventsClient;
import org.tedros.ifood.api.client.IFoodOrderClient;
import org.tedros.ifood.api.service.IFoodService;
import org.tedros.ifood.component.IFoodOrderDashboard;

import javafx.scene.layout.VBox;

public class OrderDashboardSettings extends TSetting {
	
	private IFoodOrderDashboard dashboard;

	public OrderDashboardSettings(ITComponentDescriptor descriptor) {
		super(descriptor);
	}

	@Override
	public void dispose() {
		if(dashboard!=null) {
			dashboard.stopPolling();
		}
	}

	@Override
	public void run() {

        IFoodEventsClient eventsClient = IFoodService.getInstance().getEventClient();
        IFoodOrderClient orderClient = IFoodService.getInstance().getOrderClient();
        
        dashboard = new IFoodOrderDashboard(eventsClient, orderClient);
		
		VBox pane = super.getLayout("webContent");
		
		pane.getChildren().add(dashboard);
	}
}
