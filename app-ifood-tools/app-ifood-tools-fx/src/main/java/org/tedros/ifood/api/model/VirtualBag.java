package org.tedros.ifood.api.model;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VirtualBag {

    private String id;
    private String shortCode;
    private Boolean test;
    private Date createdAt;
    @SerializedName("package")
    private Package pkg;
    private SalesChannel salesChannel;
    private Merchant merchant;
    private TaxPayer taxPayer;
    private Customer customer;
    private Bag bag;
    private OperationMode operationMode;
    private Payment payment;
    private Benefit benefit;
    private InvoiceInformation invoiceInformation;
    private List<Fee> fees;
    private List<VerificationCode> verificationCodes;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Package {
        private Integer drybag;
        private Integer coldbag;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalesChannel {
        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Merchant {
        private String id;
        private String name;
        private String cnpj;
        private Address address;
        private List<PhoneInfo> phones;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Address {
            private String streetName;
            private String city;
            private String complement;
            private String country;
            private String district;
            private Double latitude;
            private Double longitude;
            private String state;
            private String streetNumber;
            private String zipCode;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PhoneInfo {
            private String phone;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TaxPayer {
        private Identification identification;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Identification {
            private String value;
            private String type;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Customer {
        private String id;
        private String name;
        private List<PhoneInfo> phones;
        private String email;
        private Date birthDate;
        private Document document;
        private BillingAddress billingAddress;
        private String type;
        private Localizer localizer;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PhoneInfo {
            private String phone;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Document {
            private String type;
            private String value;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class BillingAddress {
            private String streetName;
            private String city;
            private String complement;
            private String country;
            private String district;
            private Double latitude;
            private Double longitude;
            private String state;
            private String streetNumber;
            private String zipCode;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Localizer {
            private String code;
            private String phone;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Bag {
        private List<BagItem> items;
        private Prices prices;
        private ReplacementOptions replacementOptions;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class BagItem {
            private String uniqueId;
            private Integer quantity;
            private String name;
            private String ean;
            private String note;
            private Weight weight;
            private ItemPrices prices;
            private Product product;
            private Boolean unavailable;
            private Replacement replacement;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Weight {
                private Double value;
            }

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class ItemPrices {
                private PriceValue unitValue;
                private PriceValue salePriceKg;
                private PriceValue grossValue;
            }

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Product {
                private String plu;
                private Boolean isVariableWeight;
            }

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Replacement {
                private String option;
                private List<ReplacementItem> list;

                @Data
                @Builder
                @NoArgsConstructor
                @AllArgsConstructor
                public static class ReplacementItem {
                    private String name;
                    private String description;
                    private String externalCode;
                    private String ean;
                    private Weight price;
                    private String productId;
                }
            }
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Prices {
            private PriceValue grossValue;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ReplacementOptions {
            private String mode;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OperationMode {
        private String type;
        private String schedulingType;
        private DeliveryInfo delivery;
        private TakeoutInfo takeout;
        private PreparationInfo preparation;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class DeliveryInfo {
            private String provider;
            private String type;
            private Destination destination;
            private Prices prices;
            private DeliveryTime deliveryTime;
            private String code;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Destination {
                private String streetName;
                private String city;
                private String complement;
                private String country;
                private String district;
                private Double latitude;
                private Double longitude;
                private String state;
                private String streetNumber;
                private String zipCode;
            }

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Prices {
                private PriceValue grossValue;
            }

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class DeliveryTime {
                private Window window;

                @Data
                @Builder
                @NoArgsConstructor
                @AllArgsConstructor
                public static class Window {
                    private Date from;
                    private Date to;
                }
            }
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class TakeoutInfo {
            private TakeoutTime takeoutTime;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class TakeoutTime {
                private Window window;

                @Data
                @Builder
                @NoArgsConstructor
                @AllArgsConstructor
                public static class Window {
                    private Date from;
                    private Date to;
                }
            }
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PreparationInfo {
            private Date start;
            private Date end;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payment {
        private List<Method> methods;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Method {
            private String name;
            private Boolean inPerson;
            private String liability;
            private PriceValue amount;
            private Cash cash;
            private Card card;
            private ProviderRef wallet;
            private Digital digital;
            private String uniqueId;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Cash {
                private PriceValue changeFor;
            }

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Card {
                private String brand;
                private String cardNumber;
                private String provider;
            }

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class ProviderRef {
                private String provider;
            }

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Digital {
                private String data;
            }
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Benefit {
        private List<BenefitItem> benefits;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class BenefitItem {
            private String targetId;
            private String target;
            private String benefitIdentifier;
            private String preAuthorizationToken;
            private String transactionIdentifier;
            private String authorizerId;
            private List<Sponsorship> sponsorships;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Sponsorship {
                private String liability;
                private PriceValue amount;
            }
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InvoiceInformation {
        private String stateRegistration;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fee {
        private String audience;
        private List<FeeValue> values;
        private PriceValue prices;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class FeeValue {
            private PriceValue amount;
            private String uniqueId;
            private String type;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerificationCode {
        private String name;
        private String value;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PriceValue {
        private String currency;
        private String value;
    }
}
