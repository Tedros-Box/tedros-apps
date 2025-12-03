package org.tedros.it.tools.gitlab.model;

import com.google.gson.annotations.SerializedName;

public record Permissions(
    @SerializedName("project_access") Object projectAccess,  // pode ser null
    @SerializedName("group_access") GroupAccess groupAccess
) {
    public record GroupAccess(
        @SerializedName("access_level") int accessLevel,
        @SerializedName("notification_level") int notificationLevel
    ) {}
}
