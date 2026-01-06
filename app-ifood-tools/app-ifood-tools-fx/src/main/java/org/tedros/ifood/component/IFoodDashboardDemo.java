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
                        .createdAt(new java.util.Date())
                        .salesChannel("IFOOD")
                        .orderTiming("IMMEDIATE")
                        .merchant(OrderDetail.OrderMerchant.builder().name("Test Merchant").id("MRCH-001").build())
                        .customer(Customer.builder()
                                .name("Customer " + id)
                                .segmentation("VIP")
                                .documentNumber("123.456.789-00")
                                .ordersCountOnMerchant(5)
                                .phone(Customer.Phone.builder().number("+55 11 99999-9999").build())
                                .build())
                        .delivery(OrderDetail.Delivery.builder()
                                .mode("NORMAL")
                                .deliveredBy("IFOOD")
                                .pickupCode("1234")
                                .deliveryAddress(OrderDetail.Delivery.Address.builder()
                                        .formattedAddress("Rua dos Bobos, 0, São Paulo - SP")
                                        .complement("Apto 123")
                                        .build())
                                .build())
                        .items(Arrays.asList(
                                OrderDetail.OrderItem.builder()
                                        .name("Burger Classic")
                                        .quantity(1)
                                        .unitPrice(25.0)
                                        .totalPrice(25.0)
                                        .type("DISH")
                                        .observations("No onions please")
                                        .options(Arrays.asList(
                                                OrderDetail.OrderItem.Option.builder()
                                                        .name("Extra Bacon")
                                                        .quantity(2)
                                                        .price(5.0)
                                                        .customization(Arrays.asList(
                                                                OrderDetail.OrderItem.Option.Customization.builder()
                                                                        .name("Crispy")
                                                                        .quantity(1)
                                                                        .build()))
                                                        .build()))
                                        .build(),
                                OrderDetail.OrderItem.builder()
                                        .name("Coke 350ml")
                                        .quantity(2)
                                        .unitPrice(6.0)
                                        .totalPrice(12.0)
                                        .type("DRINK")
                                        .build()))
                        .total(OrderDetail.OrderTotal.builder()
                                .subTotal(37.0)
                                .deliveryFee(5.0)
                                .benefits(5.0)
                                .orderAmount(37.0)
                                .build())
                        .benefits(Arrays.asList(
                                OrderDetail.Benefit.builder()
                                        .target("ITEM")
                                        .value(5.0)
                                        .campaign(OrderDetail.Benefit.Campaign.builder().name("Lunch Coupon").build())
                                        .build()))
                        .payments(OrderDetail.PaymentInfo.builder()
                                .methods(Arrays.asList(
                                        OrderDetail.PaymentInfo.PaymentMethod.builder()
                                                .method("CREDIT")
                                                .type("ONLINE")
                                                .value(37.0)
                                                .card(OrderDetail.PaymentInfo.PaymentMethod.Card.builder().brand("VISA")
                                                        .build())
                                                .prepaid(true)
                                                .build()))
                                .prepaid(37.0)
                                .pending(0.0)
                                .build())
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
