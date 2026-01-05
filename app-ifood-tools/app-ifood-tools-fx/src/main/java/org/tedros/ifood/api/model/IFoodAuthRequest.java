package org.tedros.ifood.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class IFoodAuthRequest {

    @SerializedName("grant_type")
    private String grantType;

    @SerializedName("client_id")
    private String clientId;

    @SerializedName("client_secret")
    private String clientSecret;

    @SerializedName("authorizationCode")
    private String authorizationCode;

    @SerializedName("authorizationCodeVerifier")
    private String authorizationCodeVerifier;

    @SerializedName("refreshToken")
    private String refreshToken;
}
