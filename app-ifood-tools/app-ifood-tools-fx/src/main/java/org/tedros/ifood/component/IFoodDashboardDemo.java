package org.tedros.ifood.component;

import java.util.Arrays;
import java.util.List;

import org.tedros.ifood.api.client.IFoodClientBuilder;
import org.tedros.ifood.api.client.IFoodEventsClient;
import org.tedros.ifood.api.client.IFoodOrderClient;
import org.tedros.ifood.api.model.OrderDetail;
import org.tedros.ifood.api.model.OrderDetail.Customer;
import org.tedros.ifood.api.model.PollingEvent;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Demo application for iFood Order Dashboard.
 */
public class IFoodDashboardDemo extends Application {

    private static final String EVENTS_URL = "https://merchant-api.ifood.com.br/events/v1.0";
    private static final String ORDER_URL = "https://merchant-api.ifood.com.br/order/v1.0";

    @Override
    public void start(Stage primaryStage) {
        String token = System.getenv("IFOOD_TOKEN");

        IFoodEventsClient eventsClient;
        IFoodOrderClient orderClient;

        if (token != null && !token.isEmpty()) {
            eventsClient = IFoodClientBuilder.createClient(
                    IFoodEventsClient.class, EVENTS_URL, () -> token);
            orderClient = IFoodClientBuilder.createClient(
                    IFoodOrderClient.class, ORDER_URL, () -> token);
        } else {
            // Mock clients for demonstration if no token is provided
            System.out.println("No IFOOD_TOKEN found in environment. Using mock data for demonstration.");
            eventsClient = createMockEventsClient();
            orderClient = createMockOrderClient();
        }

        IFoodOrderDashboard dashboard = new IFoodOrderDashboard(eventsClient, orderClient);

        primaryStage.setTitle("iFood Order Monitoring Dashboard");
        primaryStage.setScene(new Scene(dashboard, 1200, 800));
        primaryStage.setOnCloseRequest(e -> dashboard.stopPolling());
        primaryStage.show();
    }

    private IFoodEventsClient createMockEventsClient() {
        return new IFoodEventsClient() {
            @Override
            public List<PollingEvent> getOrderEvents() {
                return Arrays.asList(
                        PollingEvent.builder().orderId("1").fullCode("PLACED").salesChannel("IFOOD").build(),
                        PollingEvent.builder().orderId("2").fullCode("CONCLUDED").salesChannel("IFOOD").build(),
                        PollingEvent.builder().orderId("3").fullCode("CANCELLED").salesChannel("IFOOD").build());
            }

            @Override
            public void acknowledgeEvents(List<PollingEvent> events) {
                System.out.println("Acknowledged mock events: " + events.size());
            }
        };
    }

    private IFoodOrderClient createMockOrderClient() {
        return new IFoodOrderClient() {
            @Override
            public OrderDetail getOrderDetails(String id) {
                return OrderDetail.builder()
                        .displayId("ORD-" + id)
                        .orderType("DELIVERY")
                        .customer(Customer.builder().name("Customer " + id).build())
                        .build();
            }

            @Override
            public org.tedros.ifood.api.model.VirtualBag getOrderVirtualBag(String id) {
                return null;
            }

            @Override
            public void confirmOrder(String id) {
            }

            @Override
            public void startPreparation(String id) {
            }
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}
