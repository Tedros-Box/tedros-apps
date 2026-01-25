package org.tedros.ifood.api.client;

import java.util.List;

import org.tedros.ifood.api.model.CheckIn;
import org.tedros.ifood.api.model.Merchant;
import org.tedros.ifood.api.model.MerchantInterruption;
import org.tedros.ifood.api.model.MerchantStatus;
import org.tedros.ifood.api.model.SaveShiftsOpeningHours;
import org.tedros.ifood.api.model.ShiftsOpeningHours;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Headers({ "Content-Type: application/json", "Accept: application/json" })
public interface IFoodMerchantClient {

    @RequestLine("GET /merchants")
    List<Merchant> getAllMerchants();

    @RequestLine("GET /merchants/{id}")
    Merchant getMerchant(@Param("id") String id);

    @RequestLine("GET /merchants/{id}/status")
    List<MerchantStatus> getAllStatusDetails(@Param("id") String id);

    @RequestLine("GET /merchants/{id}/status/{operation}")
    List<MerchantStatus> getStatusDetails(@Param("id") String id, @Param("operation") String operation);

    @RequestLine("GET /merchants/{id}/interruptions")
    List<MerchantInterruption> getInterruption(@Param("id") String id);

    @RequestLine("POST /merchants/{id}/interruptions")
    MerchantInterruption createInterruption(@Param("id") String id, MerchantInterruption interruption);

    @RequestLine("DELETE /merchants/{id}/interruptions/{interruptionId}")
    void deleteInterruption(@Param("id") String id, @Param("interruptionId") String interruptionId);

    @RequestLine("GET /merchants/{id}/opening-hours")
    ShiftsOpeningHours getOpeningHours(@Param("id") String id);

    @RequestLine("PUT /merchants/{id}/opening-hours")
    void updateOpeningHours(@Param("id") String id, SaveShiftsOpeningHours openingHours);

    @RequestLine("POST /merchants/checkin-qrcode")
    @Headers("Accept: application/pdf")
    byte[] postCheckinQrcode(CheckIn request);
}
