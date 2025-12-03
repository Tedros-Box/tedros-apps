package org.tedros.it.tools.gitlab.model;

import com.google.gson.annotations.SerializedName;

public record GitLabMergeRequest(
    Long id,
    Integer iid,                    // número interno do projeto (!13)
    @SerializedName("project_id") Long projectId,

    String title,
    String description,
    String state,                   // "opened", "merged", "closed"
    String draft,                   // boolean como String ("true"/"false") ou null

    // Datas como String (evita problema com ZonedDateTime no Gson)
    @SerializedName("created_at") String createdAt,
    @SerializedName("updated_at") String updatedAt,
    @SerializedName("merged_at") String mergedAt,
    @SerializedName("closed_at") String closedAt,

    // Branches
    @SerializedName("source_branch") String sourceBranch,
    @SerializedName("target_branch") String targetBranch,

    // Pessoas envolvidas
    GitLabUser author,
    GitLabUser assignee,            // pode ser null
    @SerializedName("merged_by") GitLabUser mergedBy,     // pode ser null
    @SerializedName("closed_by") GitLabUser closedBy,     // pode ser null

    // Status e métricas
    @SerializedName("user_notes_count") Integer commentsCount,
    Integer upvotes,
    Integer downvotes,
    @SerializedName("has_conflicts") Boolean hasConflicts,
    @SerializedName("merge_status") String mergeStatus,           // "can_be_merged", etc.
    @SerializedName("detailed_merge_status") String detailedMergeStatus,

    // URLs e referência
    @SerializedName("web_url") String webUrl,
    String reference,               // "!13"
    MergeRequestReferences references,

    // Informações úteis para análise
    @SerializedName("should_remove_source_branch") Boolean removeSourceBranch,
    @SerializedName("force_remove_source_branch") Boolean forceRemoveSourceBranch,
    @SerializedName("squash") Boolean squash,
    @SerializedName("squash_on_merge") Boolean squashOnMerge
) {

    // Métodos utilitários (super úteis!)
    public boolean isOpen() {
        return "opened".equalsIgnoreCase(state);
    }

    public boolean isMerged() {
        return "merged".equalsIgnoreCase(state);
    }

    public boolean isClosed() {
        return "closed".equalsIgnoreCase(state);
    }

    public boolean isDraft() {
        return Boolean.parseBoolean(draft);
    }

    public String getStatusEmoji() {
        return switch (state) {
            case "opened" -> "Open";
            case "merged" -> "Merged";
            case "closed" -> "Closed";
            default -> "Unknown";
        };
    }

    public String getShortReference() {
        return references != null ? references.shortReference() : reference;
    }

    public String getCreatedAtFormatted() {
        return formatDate(createdAt);
    }

    public String getMergedAtFormatted() {
        return formatDate(mergedAt);
    }

    private String formatDate(String isoDate) {
        if (isoDate == null || isoDate.isBlank()) return "Nunca";
        try {
            return java.time.ZonedDateTime.parse(isoDate)
                    .withZoneSameInstant(java.time.ZoneId.of("America/Sao_Paulo"))
                    .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        } catch (Exception e) {
            return isoDate.substring(0, 10); // fallback
        }
    }
}
