package org.tedros.redminetools.git.model;

import com.google.gson.annotations.SerializedName;

public record Namespace(
    Long id,
    String name,
    String path,
    String kind,           // "group" ou "user"
    @SerializedName("full_path") String fullPath,
    @SerializedName("parent_id") Long parentId,
    @SerializedName("web_url") String webUrl
) {}