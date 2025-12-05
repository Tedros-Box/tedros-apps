package org.tedros.it.tools.gitlab.api.model;

import com.google.gson.annotations.SerializedName;

public record Links(
    String self,
    String issues,
    @SerializedName("merge_requests") String mergeRequests,
    @SerializedName("repo_branches") String repoBranches,
    String members
) {}
