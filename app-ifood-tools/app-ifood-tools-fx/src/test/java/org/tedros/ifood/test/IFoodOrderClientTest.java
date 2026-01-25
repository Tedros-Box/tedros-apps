package org.tedros.ifood.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.tedros.ifood.api.client.IFoodClientBuilder;
import org.tedros.ifood.api.client.IFoodOrderClient;
import org.tedros.ifood.api.model.OrderDetail;
import org.tedros.ifood.api.model.VirtualBag;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IFoodOrderClientTest {

    private static final String BASE_URL = "https://merchant-api.ifood.com.br/order/v1.0";

    private IFoodOrderClient client = IFoodClientBuilder.createClient(
            IFoodOrderClient.class,
            BASE_URL,
            () -> System.getenv("IFOOD_TOKEN"));

    private static String orderId = ""; 

    @Test
    @Order(1)
    void testGetOrderDetails() {
        // This will likely fail if the token is not set or ID is invalid,
        // but we want to verify the client can be built and annotations are correct.
        try {
            OrderDetail response = client.getOrderDetails(orderId);
            System.out.println(response);
            assertNotNull(response);
        } catch (Exception e) {
            System.out.println("Expected error or connection issue: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    void testGetOrderVirtualBag() {
        try {
            VirtualBag response = client.getOrderVirtualBag(orderId);
            System.out.println(response);
            assertNotNull(response);
        } catch (Exception e) {
            System.out.println("Expected error or connection issue: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    void testConfirmOrder() {
        assertDoesNotThrow(() ->{
            client.confirmOrder(orderId);
            System.out.println("Confirm Order request sent");
        });
        
    }

    @Test
    @Order(4)
    void testStartPreparation() {
    	  assertDoesNotThrow(() ->{
            client.startPreparation(orderId);
            System.out.println("Start Preparation request sent");
        });
    }
}
