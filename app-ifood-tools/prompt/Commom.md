You are an expert Java developer tasked with creating a complete client for consuming iFood APIs using the specified stack. Generate the Java code based on the iFood API documentation available at: https://developer.ifood.com.br/pt-BR/docs/references. Ensure all code is compatible with Java 17, follows clean code practices, and uses the provided dependencies: OpenFeign 13.6, OkHttp 4.12.0, Lombok 1.18.42. Do not use Java records.

First, create a base class IFoodBaseEntity in package org.tedros.ifood.api.model for common fields like ID and name to reduce redundancy. Use Lombok annotations: @Data, @Builder, @NoArgsConstructor, @AllArgsConstructor. Extend relevant models from this base class where applicable.

Next, create a configuration class IFoodClientBuilder in package org.tedros.ifood.api.client that uses Feign.builder() with GsonDecoder, GsonEncoder, and OkHttpClient. Configure the GsonBuilder with .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") to handle ISO 8601 dates properly.

Additionally, create a class IFoodTokenInterceptor in package org.tedros.ifood.api.client that implements feign.RequestInterceptor to automatically inject the Authorization: Bearer {token} header in all calls. This interceptor should accept a token supplier or similar mechanism, as the client will be used in a JavaFX application where token management might be dynamic.

Output the complete source code for these classes, organized by packages.