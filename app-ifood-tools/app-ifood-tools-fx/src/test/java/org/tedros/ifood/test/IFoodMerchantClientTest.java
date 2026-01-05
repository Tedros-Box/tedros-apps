package org.tedros.ifood.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.tedros.ifood.api.client.IFoodClientBuilder;
import org.tedros.ifood.api.client.IFoodMerchantClient;
import org.tedros.ifood.api.model.CheckIn;
import org.tedros.ifood.api.model.Merchant;
import org.tedros.ifood.api.model.MerchantInterruption;
import org.tedros.ifood.api.model.MerchantStatus;
import org.tedros.ifood.api.model.SaveShiftsOpeningHours;
import org.tedros.ifood.api.model.ShiftsOpeningHours;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IFoodMerchantClientTest {

    private static final String BASE_URL = "https://merchant-api.ifood.com.br/merchant/v1.0";

    private IFoodMerchantClient client = IFoodClientBuilder.createClient(
            IFoodMerchantClient.class,
            BASE_URL,
            () -> System.getenv("IFOOD_TOKEN"));

    private static String merchantId;

    @Test
    @Order(1)
    void testGetAllMerchants() {
        List<Merchant> response = client.getAllMerchants();
        System.out.println(response);
        assertNotNull(response);
        if (!response.isEmpty()) {
            merchantId = response.get(0).getId();
        }
    }

    @Test
    @Order(2)
    void testGetMerchant() {
        if (merchantId == null)
            return;
        Merchant response = client.getMerchant(merchantId);
        System.out.println(response);
        assertNotNull(response);
    }

    @Test
    @Order(3)
    void testGetAllStatusDetails() {
        if (merchantId == null)
            return;
        List<MerchantStatus> response = client.getAllStatusDetails(merchantId);
        System.out.println(response);
        assertNotNull(response);
    }

    @Test
    @Order(4)
    void testGetStatusDetails() {
        if (merchantId == null)
            return;
        // Assuming "DELIVERY" as a standard operation, theoretically we should pick one
        // from getAllStatusDetails
        List<MerchantStatus> response = client.getStatusDetails(merchantId, "delivery");
        System.out.println(response);
        assertNotNull(response);
    }

    @Test
    @Order(5)
    void testGetOpeningHours() {
        if (merchantId == null)
            return;
        ShiftsOpeningHours response = client.getOpeningHours(merchantId);
        System.out.println(response);
        assertNotNull(response);
    }

    @Test
    @Order(6)
    void testUpdateOpeningHours() {
        if (merchantId == null)
            return;
        ShiftsOpeningHours currentHours = client.getOpeningHours(merchantId);
        // Just sending back the same hours to confirm it works without changing data
        // destructively
        if (currentHours != null && !currentHours.getShifts().isEmpty()) {
            client.updateOpeningHours(merchantId, SaveShiftsOpeningHours.builder()
            		.storeId(merchantId)
            		.shifts(currentHours.getShifts())
            		.build());
        }
    }

    private static String interruptionId;

    @Test
    @Order(7)
    void testCreateInterruption() {
        if (merchantId == null)
            return;
        
        MerchantInterruption interruption = MerchantInterruption.builder()
                .description("Teste de interrupcao automatizado")
                .start(new Date())
                .end(new Date(System.currentTimeMillis() + 3600000)) // +1 hour
                .build();

        MerchantInterruption response = client.createInterruption(merchantId, interruption);
        System.out.println(response);
        assertNotNull(response);
        interruptionId = response.getId();
    }

    @Test
    @Order(8)
    void testGetInterruption() {
        if (merchantId == null)
            return;
        List<MerchantInterruption> response = client.getInterruption(merchantId);
        System.out.println(response);
        assertNotNull(response);
    }

    @Test
    @Order(9)
    void testDeleteInterruption() {
        if (merchantId == null || interruptionId == null)
            return;
        assertDoesNotThrow(() -> {
			client.deleteInterruption(merchantId, interruptionId);
		});        
    }

    @Test
    @Order(10)
    void testPostCheckinQrcode() {
        if (merchantId == null)
            return;
        CheckIn checkIn = CheckIn.builder()
                .merchantIds(List.of(merchantId))
                .build();
        byte[] response = client.postCheckinQrcode(checkIn);
        assertNotNull(response);
        System.out.println("QRCode: " + response);
    }

}
