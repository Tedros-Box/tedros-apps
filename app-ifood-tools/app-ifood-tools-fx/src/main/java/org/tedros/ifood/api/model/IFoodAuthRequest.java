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

    private String grantType;
    
    private String clientId;
    
    private String clientSecret;

    private String authorizationCode;

    private String authorizationCodeVerifier;

    private String refreshToken;
}
