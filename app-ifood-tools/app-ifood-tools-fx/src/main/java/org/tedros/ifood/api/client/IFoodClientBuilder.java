package org.tedros.ifood.api.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IFoodClientBuilder {

    public static <T> T createClient(Class<T> clientClass, String url, Supplier<String> tokenSupplier) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .create();

        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder(gson))
                .decoder((response, type) -> {
                    if (type == byte[].class) {
                        return feign.Util.toByteArray(response.body().asInputStream());
                    }
                    return new GsonDecoder(gson).decode(response, type);
                })
                .requestInterceptor(new IFoodTokenInterceptor(tokenSupplier))
                .target(clientClass, url);
    }
}
