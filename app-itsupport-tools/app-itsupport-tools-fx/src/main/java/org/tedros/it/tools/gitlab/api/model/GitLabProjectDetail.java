package org.tedros.it.tools.gitlab.api.model;

import com.google.gson.annotations.SerializedName;

public record GitLabProjectDetail(
        Long id,
        String name,
        @SerializedName("name_with_namespace") String nameWithNamespace,
        String path,
        @SerializedName("path_with_namespace") String pathWithNamespace,

        // Datas importantes
        @SerializedName("created_at") String createdAt,
        @SerializedName("updated_at") String updatedAt,
        @SerializedName("last_activity_at") String lastActivityAt,
        @SerializedName("marked_for_deletion_at") String markedForDeletionAt,

        // Branches e acesso
        @SerializedName("default_branch") String defaultBranch,
        @SerializedName("ssh_url_to_repo") String sshUrlToRepo,
        @SerializedName("http_url_to_repo") String httpUrlToRepo,
        @SerializedName("web_url") String webUrl,
        @SerializedName("readme_url") String readmeUrl,
        @SerializedName("avatar_url") String avatarUrl,

        // Tags e Topics
        @SerializedName("tag_list") java.util.List<String> tagList,
        @SerializedName("topics") java.util.List<String> topics,

        // Container Registry
        @SerializedName("container_registry_image_prefix") String containerRegistryImagePrefix,

        // Status do projeto
        String description,
        Boolean archived,
        String visibility, // "private", "internal", "public"
        @SerializedName("open_issues_count") Integer openIssuesCount,
        @SerializedName("star_count") Integer starCount,
        @SerializedName("forks_count") Integer forksCount,

        // Recursos habilitados (úteis para gestor)
        @SerializedName("issues_enabled") Boolean issuesEnabled,
        @SerializedName("merge_requests_enabled") Boolean mergeRequestsEnabled,
        @SerializedName("wiki_enabled") Boolean wikiEnabled,
        @SerializedName("jobs_enabled") Boolean jobsEnabled,
        @SerializedName("container_registry_enabled") Boolean containerRegistryEnabled,
        @SerializedName("packages_enabled") Boolean packagesEnabled,

        // CI/CD
        @SerializedName("shared_runners_enabled") Boolean sharedRunnersEnabled,
        @SerializedName("ci_config_path") String ciConfigPath,

        // Segurança e governança
        @SerializedName("merge_method") String mergeMethod, // merge, rebase, ff
        @SerializedName("only_allow_merge_if_pipeline_succeeds") Boolean onlyAllowMergeIfPipelineSucceeds,
        @SerializedName("remove_source_branch_after_merge") Boolean removeSourceBranchAfterMerge,

        // Relacionamentos
        Namespace namespace,
        Permissions permissions,
        @SerializedName("_links") ProjectLinks links,

        // Flags importantes
        @SerializedName("empty_repo") Boolean emptyRepo) {
}
