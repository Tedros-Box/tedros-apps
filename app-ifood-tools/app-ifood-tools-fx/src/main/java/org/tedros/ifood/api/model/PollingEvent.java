package org.tedros.ifood.api.model;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PollingEvent {

    private String id;
    private String code;

    @SerializedName("fullCode")
    private String fullCode;

    @SerializedName("orderId")
    private String orderId;

    @SerializedName("merchantId")
    private String merchantId;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("salesChannel")
    private String salesChannel;

    private Map<String, Object> metadata;
}
