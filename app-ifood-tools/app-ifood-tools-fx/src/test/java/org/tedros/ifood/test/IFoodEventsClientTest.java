package org.tedros.ifood.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.tedros.ifood.api.client.IFoodClientBuilder;
import org.tedros.ifood.api.client.IFoodEventsClient;
import org.tedros.ifood.api.client.IFoodMerchantClient;
import org.tedros.ifood.api.model.PollingEvent;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IFoodEventsClientTest {

    private static final String BASE_URL = "https://merchant-api.ifood.com.br/events/v1.0";

    private IFoodEventsClient client = IFoodClientBuilder.createClient(
            IFoodEventsClient.class,
            BASE_URL,
            () -> System.getenv("IFOOD_TOKEN"));

    private static List<PollingEvent> events;

    @Test
    @Order(1)
    void testPolling() {
        events = client.getOrderEvents();
        System.out.println(events);
        assertNotNull(events);
    }

    @Test
    @Order(2)
    void testAcknowledgment() {
        if (events == null || events.isEmpty()) {
            return;
        }

        assertDoesNotThrow(() -> {
            client.acknowledgeEvents(events);
        });
    }

}
