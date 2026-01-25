package org.tedros.ifood.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.tedros.ifood.api.model.MerchantInterruption;

import java.util.Date;

public class GsonDateReproductionTest {

    @Test
    void testDateParsing() {
        // Original format causing the issue
        // IFoodClientBuilder had "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();

        String json = "{\"start\": \"2026-01-05T11:38:03.161\"}";

        try {
            gson.fromJson(json, MerchantInterruption.class);
            Assertions.fail("Should have failed with the original format");
        } catch (Exception e) {
            // Expected failure
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        // Proposed fix
        Gson fixedGson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .create();

        MerchantInterruption interruption = fixedGson.fromJson(json, MerchantInterruption.class);
        Assertions.assertNotNull(interruption.getStart());
        System.out.println("Successfully parsed date: " + interruption.getStart());
    }
}
