package org.tedros.it.tools.gitlab.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Representa o objeto principal (uma Branch) retornado pela API de Branches do GitLab.
 */
public record BranchModel(
    @SerializedName("name") String name,
    @SerializedName("commit") CommitModel commit,
    @SerializedName("merged") boolean merged,
    @SerializedName("protected") boolean protectedBranch, // Nome alterado para evitar a palavra reservada 'protected'
    @SerializedName("developers_can_push") boolean developersCanPush,
    @SerializedName("developers_can_merge") boolean developersCanMerge,
    @SerializedName("can_push") boolean canPush,
    @SerializedName("default") boolean isDefault, // Nome alterado para evitar a palavra reservada 'default'
    @SerializedName("web_url") String webUrl
) {
	
	
}
