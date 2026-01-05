package org.tedros.ifood.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpeningHours {

    @SerializedName("dayOfWeek")
    private String dayOfWeek;

    @SerializedName("salesChannel")
    private String salesChannel;

    @SerializedName("openingTime")
    private String openingTime;

    @SerializedName("closingTime")
    private String closingTime;
}
