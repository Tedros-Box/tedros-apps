package org.tedros.ifood.module.order.model;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.fx.form.TSetting;
import org.tedros.ifood.api.client.IFoodClientBuilder;
import org.tedros.ifood.api.client.IFoodEventsClient;
import org.tedros.ifood.api.client.IFoodOrderClient;
import org.tedros.ifood.component.IFoodOrderDashboard;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class OrderDashboardSettings extends TSetting {
	
	private static final String EVENTS_URL = "https://merchant-api.ifood.com.br/events/v1.0";
    private static final String ORDER_URL = "https://merchant-api.ifood.com.br/order/v1.0";
    
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
		String token = System.getenv("IFOOD_TOKEN");

        IFoodEventsClient eventsClient;
        IFoodOrderClient orderClient;
        
        eventsClient = IFoodClientBuilder.createClient(
                IFoodEventsClient.class, EVENTS_URL, () -> token);
        orderClient = IFoodClientBuilder.createClient(
                IFoodOrderClient.class, ORDER_URL, () -> token);
        
        dashboard = new IFoodOrderDashboard(eventsClient, orderClient);
		
		VBox pane = super.getLayout("webContent");
		
		pane.getChildren().add(dashboard);
	}
}
