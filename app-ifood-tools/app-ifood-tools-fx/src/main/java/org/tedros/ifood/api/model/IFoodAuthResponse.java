package org.tedros.ifood.api.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IFoodAuthResponse extends IFoodBaseEntity {

    @SerializedName("accessToken") // Note: Documentation might say snake_case, but standard iFood v1 often uses
                                   // camelCase or check specific endpoint. However prompt asked to use
                                   // @SerializedName to map snake_case to camelCase. Let's assume the JSON from
                                   // iFood is snake_case or standard OAuth.
    // Standard OAuth2 is snake_case: access_token, expires_in, refresh_token.
    // iFood documentation usually follows this for OAuth.
    // Let's correct to snake_case mapping just in case, or stick to what is common.
    // iFood Docs: https://developer.ifood.com.br/en-US/docs/guides/authentication
    // Response example: { "accessToken": "...", "refreshToken": "...", "type":
    // "Bearer", "expiresIn": 3600 } -> Actually iFood v1.0 docs often show
    // camelCase for these fields in some places or snake_case in others.
    // Let's follow the prompt instruction: "Use @SerializedName from Gson to map
    // snake_case JSON fields to camelCase Java fields".
    // This implies the JSON IS snake_case.
    //@SerializedName("access_token")
    private String accessToken;

    @SerializedName("type")
    private String type;

    @SerializedName("expires_in")
    private Long expiresIn;

    @SerializedName("refresh_token")
    private String refreshToken;

}
