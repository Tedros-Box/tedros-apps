package org.tedros.ifood.api.client;

import feign.Headers;
import feign.RequestLine;
import org.tedros.ifood.api.model.IFoodAuthRequest;
import org.tedros.ifood.api.model.IFoodAuthResponse;

public interface IFoodAuthClient {

    @RequestLine("POST /authentication/v1.0/oauth/token")
    @Headers("Content-Type: application/json")
    IFoodAuthResponse auth(IFoodAuthRequest request);

}
