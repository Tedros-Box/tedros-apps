package org.tedros.ifood.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Merchant extends IFoodBaseEntity {

    @SerializedName("corporateName")
    private String corporateName;

    private String description;

    @SerializedName("averageTicket")
    private Double averageTicket;

    private Boolean exclusive;

    private String type;

    private String status;

    @SerializedName("createdAt")
    private Date createdAt;

    private MerchantAddress address;

    private List<MerchantOperation> operations;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MerchantAddress {
        private String country;
        private String state;
        private String city;
        @SerializedName("postalCode")
        private String postalCode;
        private String district;
        private String street;
        private String number;
        private Double latitude;
        private Double longitude;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MerchantOperation {
        private String name;
        private String state;
        @SerializedName("salesChannel")
        private String salesChannel;
    }
}
