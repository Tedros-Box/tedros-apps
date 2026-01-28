package org.tedros.it.tools.gitlab.gateway;

import java.util.List;

import org.tedros.it.tools.gitlab.api.model.BranchModel;
import org.tedros.it.tools.gitlab.api.model.CommitDiffModel;
import org.tedros.it.tools.gitlab.api.model.CommitModel;
import org.tedros.it.tools.gitlab.api.model.GitLabMergeRequest;
import org.tedros.it.tools.gitlab.api.model.GitLabProject;
import org.tedros.it.tools.gitlab.api.model.GitLabProjectDetail;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

/**
 * Feign client for GitLab REST API v4.
 * <p>
 * All methods require a valid {@code PRIVATE-TOKEN} header (added via
 * interceptor).
 * Base URL must point to the GitLab instance (e.g. https://gitlab.com or
 * self-hosted).
 * </p>
 *
 * @see <a href="https://docs.gitlab.com/ee/api/rest/">GitLab API
 *      Documentation</a>
 */
@Headers("Accept: application/json")
public interface GitLabClient {

    /**
     * Searches for projects by name.
     *
     * @param name the project name (or part of it) to search for
     * @return list of projects matching the name (may be empty)
     * @see <a href=
     *      "https://docs.gitlab.com/ee/api/projects.html#list-all-projects">List
     *      all projects</a>
     */
    @RequestLine("GET /api/v4/projects?search={name}&simple=false")
    @Headers("Content-Type: application/json")
    List<GitLabProject> searchProjectsByName(@Param("name") String name);

    /**
     * Retrieves all projects visible to the authenticated user.
     *
     * @return list of all accessible projects
     * @see <a href=
     *      "https://docs.gitlab.com/ee/api/projects.html#list-all-projects">List
     *      all projects</a>
     */
    @RequestLine("GET /api/v4/projects")
    List<GitLabProject> getAllProjects();

    @RequestLine("GET /api/v4/projects/{projectId}")
    GitLabProjectDetail getProject(@Param("projectId") Long projectId);

    /**
     * Retrieves up to 100 merge requests for a specific project (all states).
     *
     * @param projectId the ID or URL-encoded path of the project
     * @return list of merge requests (up to 100)
     * @see <a href=
     *      "https://docs.gitlab.com/ee/api/merge_requests.html#list-project-merge-requests">List
     *      project merge requests</a>
     */
    @RequestLine("GET /api/v4/projects/{projectId}/merge_requests?per_page=100")
    List<GitLabMergeRequest> getMergeRequests(@Param("projectId") Long projectId);

    /**
     * Retrieves all <strong>opened</strong> merge requests for a project.
     *
     * @param projectId the ID or URL-encoded path of the project
     * @return list of opened merge requests
     * @see <a href=
     *      "https://docs.gitlab.com/ee/api/merge_requests.html#list-project-merge-requests">List
     *      project merge requests</a>
     */
    @RequestLine("GET /api/v4/projects/{projectId}/merge_requests?state=opened")
    List<GitLabMergeRequest> getOpenedMergeRequests(@Param("projectId") Long projectId);

    /**
     * Retrieves the 50 most recently <strong>merged</strong> merge requests for a
     * project.
     *
     * @param projectId the ID or URL-encoded path of the project
     * @return list of merged merge requests (max 50)
     * @see <a href=
     *      "https://docs.gitlab.com/ee/api/merge_requests.html#list-project-merge-requests">List
     *      project merge requests</a>
     */
    @RequestLine("GET /api/v4/projects/{projectId}/merge_requests?state=merged&per_page=50")
    List<GitLabMergeRequest> getMergedMergeRequests(@Param("projectId") Long projectId);

    /**
     * Retrieves a single merge request by its IID (Internal ID).
     *
     * @param projectId the ID or URL-encoded path of the project
     * @param iid       the internal ID of the merge request (displayed in GitLab
     *                  UI)
     * @return the merge request details
     * @see <a href=
     *      "https://docs.gitlab.com/ee/api/merge_requests.html#get-single-mr">Get
     *      single MR</a>
     */
    @RequestLine("GET /api/v4/projects/{projectId}/merge_requests/{iid}")
    GitLabMergeRequest getMergeRequest(@Param("projectId") Long projectId, @Param("iid") Long iid);

    /**
     * Retrieves the raw unified diff of a merge request as plain text.
     * <p>
     * Returns a {@link Response} because the content is a large text payload.
     * </p>
     *
     * @param projectId the ID or URL-encoded path of the project
     * @param iid       the internal ID of the merge request
     * @return Feign {@link Response} containing the raw diff
     * @see <a href=
     *      "https://docs.gitlab.com/ee/api/merge_requests.html#get-mr-diffs">MR
     *      diffs</a>
     */
    @RequestLine("GET /api/v4/projects/{projectId}/merge_requests/{iid}/raw_diffs")
    Response getMergeRequestRawDiffs(@Param("projectId") Long projectId, @Param("iid") Long iid);

    /**
     * Lists all branches in the repository.
     *
     * @param projectId the ID or URL-encoded path of the project
     * @return list of branches
     * @see <a href="https://docs.gitlab.com/ee/api/branches.html">Branches API</a>
     */
    @RequestLine("GET /api/v4/projects/{projectId}/repository/branches")
    List<BranchModel> getRepositoryBranches(@Param("projectId") Long projectId);

    /**
     * Retrieves details of a single branch.
     *
     * @param projectId the ID or URL-encoded path of the project
     * @param branch    name of the branch (URL-encoded if necessary)
     * @return branch details
     * @see <a href="https://docs.gitlab.com/ee/api/branches.html">Branches API</a>
     */
    @RequestLine("GET /api/v4/projects/{projectId}/repository/branches/{branch}")
    BranchModel getSingleRepositoryBranches(@Param("projectId") Long projectId, @Param("branch") String branch);

    /**
     * Lists commits on the default branch (usually {@code main} or {@code master}).
     *
     * @param projectId the ID or URL-encoded path of the project
     * @return list of recent commits
     * @see <a href=
     *      "https://docs.gitlab.com/ee/api/commits.html#list-repository-commits">List
     *      repository commits</a>
     */
    @RequestLine("GET /api/v4/projects/{projectId}/repository/commits")
    List<CommitModel> getRepositoryCommits(@Param("projectId") Long projectId);

    /**
     * Retrieves a single commit by its SHA.
     *
     * @param projectId the ID or URL-encoded path of the project
     * @param sha       the commit SHA (full or short)
     * @return commit details
     * @see <a href=
     *      "https://docs.gitlab.com/ee/api/commits.html#get-a-single-commit">Get a
     *      single commit</a>
     */
    @RequestLine("GET /api/v4/projects/{projectId}/repository/commits/{sha}")
    CommitModel getSingleRepositoryCommit(@Param("projectId") Long projectId, @Param("sha") String sha);

    /**
     * Retrieves the diff of a specific commit (list of changed files with their
     * patches).
     *
     * @param projectId the ID or URL-encoded path of the project
     * @param sha       the commit SHA
     * @return list of file diffs for the commit
     * @see <a href=
     *      "https://docs.gitlab.com/ee/api/commits.html#get-the-diff-of-a-commit">Get
     *      the diff of a commit</a>
     */
    @RequestLine("GET /api/v4/projects/{projectId}/repository/commits/{sha}/diff")
    List<CommitDiffModel> getRepositoryCommitDiff(@Param("projectId") Long projectId, @Param("sha") String sha);
}