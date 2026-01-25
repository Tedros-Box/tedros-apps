package org.tedros.ifood.api.model;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderDetail extends IFoodBaseEntity {

    @SerializedName("displayId")
    private String displayId;

    @SerializedName("orderType")
    private String orderType;

    @SerializedName("orderTiming")
    private String orderTiming;

    @SerializedName("salesChannel")
    private String salesChannel;

    @SerializedName("category")
    private String category;

    @SerializedName("createdAt")
    private Date createdAt;

    @SerializedName("preparationStartDateTime")
    private Date preparationStartDateTime;

    @SerializedName("merchant")
    private OrderMerchant merchant;

    @SerializedName("customer")
    private Customer customer;

    @SerializedName("total")
    private OrderTotal total;

    @SerializedName("items")
    private List<OrderItem> items;

    @SerializedName("payments")
    private PaymentInfo payments;

    @SerializedName("benefits")
    private List<Benefit> benefits;

    @SerializedName("additionalFees")
    private List<AdditionalFee> additionalFees;

    @SerializedName("delivery")
    private Delivery delivery;

    @SerializedName("schedule")
    private Schedule schedule;

    @SerializedName("indoor")
    private Indoor indoor;

    @SerializedName("dineIn")
    private DineIn dineIn;

    @SerializedName("takeout")
    private Takeout takeout;

    @SerializedName("picking")
    private Picking picking;

    @SerializedName("extraInfo")
    private String extraInfo;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderMerchant {
        private String id;
        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Customer {
        private String id;
        private String name;
        @SerializedName("documentNumber")
        private String documentNumber;
        private String segmentation;
        @SerializedName("ordersCountOnMerchant")
        private Integer ordersCountOnMerchant;
        private Phone phone;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Phone {
            private String number;
            private String localizer;
            @SerializedName("localizerExpiration")
            private Date localizerExpiration;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderTotal {
        private Double subTotal;
        private Double deliveryFee;
        private Double benefits;
        private Double orderAmount;
        private Double additionalFees;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItem {
        private String id;
        private Integer index;
        private String name;
        private String externalCode;
        private String unit;
        private String ean;
        private Integer quantity;
        private Double unitPrice;
        private Double price;
        private Double totalPrice;
        private Double optionsPrice;
        private String observations;
        private String imageUrl;
        private String type;
        private ScalePrices scalePrices;
        private List<Option> options;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ScalePrices {
            private Double defaultPrice;
            private List<Scale> scales;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Scale {
                private Integer minQuantity;
                private Double price;
            }
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Option {
            private String id;
            private Integer index;
            private String name;
            private String externalCode;
            private String unit;
            private String ean;
            private Integer quantity;
            private Double unitPrice;
            private Double price;
            private Double addition;
            private String groupName;
            private String type;
            private List<Customization> customization;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Customization {
                private String id;
                private String name;
                private String externalCode;
                private String groupName;
                private String type;
                private Integer quantity;
                private Double unitPrice;
                private Double addition;
                private Double price;
            }
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentInfo {
        private List<PaymentMethod> methods;
        private Double pending;
        private Double prepaid;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PaymentMethod {
            private String method;
            private String type;
            private String currency;
            private Double value;
            private Boolean prepaid;
            private Card card;
            private Cash cash;
            private Wallet wallet;
            private Transaction transaction;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Card {
                private String brand;
            }

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Cash {
                private Double changeFor;
            }

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Wallet {
                private String name;
            }

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Transaction {
                private String authorizationCode;
                private String acquirerDocument;
            }
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Benefit {
        private String targetId;
        private String target;
        private Double value;
        private Campaign campaign;
        private List<SponsorshipValue> sponsorshipValues;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Campaign {
            private String id;
            private String name;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SponsorshipValue {
            private String name;
            private Double value;
            private String description;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdditionalFee {
        private String type;
        private Double value;
        private String description;
        private String fullDescription;
        private List<Liability> liabilities;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Liability {
            private String name;
            private Double percentage;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Delivery {
        private String mode;
        private String deliveredBy;
        private String pickupCode;
        private String description;
        private Address deliveryAddress;
        private Date deliveryDateTime;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Address {
            private String streetName;
            private String streetNumber;
            private String city;
            private String neighborhood;
            private String state;
            private String country;
            private String postalCode;
            private String reference;
            private String complement;
            private String formattedAddress;
            private Coordinates coordinates;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Coordinates {
                private Double latitude;
                private Double longitude;
            }
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Schedule {
        private Date deliveryDateTimeStart;
        private Date deliveryDateTimeEnd;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Indoor {
        private String mode;
        private String table;
        private Date deliveryDateTime;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DineIn {
        private Date deliveryDateTime;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Takeout {
        private String mode;
        private Date takeoutDateTime;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Picking {
        private String picker;
        private String replacementOptions;
    }
}
