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

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static <T> T createClient(Class<T> clientClass, String url, Supplier<String> tokenSupplier) {
        Gson gson = new GsonBuilder()
                .setDateFormat(DEFAULT_DATE_FORMAT)
                .create();

        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder(gson))
                .decoder(new GsonDecoder(gson))
                .requestInterceptor(new IFoodTokenInterceptor(tokenSupplier))
                .target(clientClass, url);
    }
}
