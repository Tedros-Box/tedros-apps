package org.tedros.ifood.api.client;

import java.util.function.Supplier;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IFoodTokenInterceptor implements RequestInterceptor {

    private final Supplier<String> tokenSupplier;

    @Override
    public void apply(RequestTemplate template) {
        String token = tokenSupplier.get();
        if (token != null && !token.isBlank()) {
            template.header("Authorization", "Bearer " + token);
        }
    }
}
