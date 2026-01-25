package org.tedros.it.tools.gitlab.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/**
 * Representa a estrutura 'commit' da API de Branches do GitLab.
 */
public record CommitModel(
    @SerializedName("id") String id,
    @SerializedName("short_id") String shortId,
    @SerializedName("created_at") String createdAt, // String para data/hora
    @SerializedName("parent_ids") List<String> parentIds,
    @SerializedName("title") String title,
    @SerializedName("message") String message,
    @SerializedName("author_name") String authorName,
    @SerializedName("author_email") String authorEmail,
    @SerializedName("authored_date") String authoredDate, // String para data/hora
    @SerializedName("committer_name") String committerName,
    @SerializedName("committer_email") String committerEmail,
    @SerializedName("committed_date") String committedDate, // String para data/hora
    @SerializedName("trailers") Map<String, Object> trailers,
    @SerializedName("extended_trailers") Map<String, Object> extendedTrailers,
    @SerializedName("web_url") String webUrl
) {
}
