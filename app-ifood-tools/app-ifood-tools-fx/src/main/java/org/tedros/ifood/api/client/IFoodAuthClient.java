package org.tedros.ifood.api.client;

import feign.Headers;
import feign.RequestLine;
import feign.Body;
import feign.Param;

import org.tedros.ifood.api.model.IFoodAuthResponse;

public interface IFoodAuthClient {

    @RequestLine("POST /authentication/v1.0/oauth/token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @Body("grantType={grantType}&clientId={clientId}&clientSecret={clientSecret}&authorizationCode={authorizationCode}&authorizationCodeVerifier={authorizationCodeVerifier}&refreshToken={refreshToken}")
    IFoodAuthResponse auth(@Param("grantType") String grantType,
            @Param("clientId") String clientId,
            @Param("clientSecret") String clientSecret,
            @Param("authorizationCode") String authorizationCode,
            @Param("authorizationCodeVerifier") String authorizationCodeVerifier,
            @Param("refreshToken") String refreshToken);

}
