package org.tedros.redminetools.git.model;

import com.google.gson.annotations.SerializedName;

public record GitLabUser(
    Long id,
    String username,
    String name,
    @SerializedName("avatar_url") String avatarUrl,
    @SerializedName("web_url") String webUrl
) {}
