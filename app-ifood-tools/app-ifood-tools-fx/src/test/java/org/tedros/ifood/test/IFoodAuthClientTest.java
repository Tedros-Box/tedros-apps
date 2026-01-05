package org.tedros.ifood.test;

import org.junit.jupiter.api.Test;
import org.tedros.ifood.api.client.IFoodAuthClient;
import org.tedros.ifood.api.client.IFoodClientBuilder;
import org.tedros.ifood.api.model.IFoodAuthRequest;
import org.tedros.ifood.api.model.IFoodAuthResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class IFoodAuthClientTest {

        @Test
        public void testAuth() {
                IFoodAuthClient client = IFoodClientBuilder.createClient(
                                IFoodAuthClient.class,
                                "https://merchant-api.ifood.com.br",
                                () -> null // No token needed for auth
                );

                IFoodAuthRequest request = IFoodAuthRequest.builder()
                                .grantType(System.getenv().get("grantType"))
                                .clientId(System.getenv().get("clientId"))
                                .clientSecret(System.getenv().get("clientSecret"))
                                .authorizationCode(System.getenv().get("authorizationCode"))
                                .authorizationCodeVerifier(System.getenv().get("authorizationCodeVerifier"))
                                .refreshToken("")
                                .build();

                IFoodAuthResponse response = client.auth(
                                request.getGrantType(),
                                request.getClientId(),
                                request.getClientSecret(),
                                request.getAuthorizationCode(),
                                request.getAuthorizationCodeVerifier(),
                                request.getRefreshToken());
                System.out.println(response);
                assertNotNull(response);
                assertNotNull(response.getAccessToken());
        }
}
