package org.tedros.ifood.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantStatus {

    private String operation;

    @SerializedName("salesChannel")
    private String salesChannel;

    private Boolean available;

    private String state;

    private List<Reason> reasons;

    private Reopenable reopenable;

    private List<Validation> validations;

    private StatusMessage message;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Reason {
        private String code;
        private String message;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Reopenable {
        private Boolean isReopenable;
        private String identifier;
        private String type;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Validation {
        private String id;
        private String code;
        private String state;
        private String message;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatusMessage {
        private String title;
        private String subtitle;
        private String description;
        private Integer priority;
    }
}
