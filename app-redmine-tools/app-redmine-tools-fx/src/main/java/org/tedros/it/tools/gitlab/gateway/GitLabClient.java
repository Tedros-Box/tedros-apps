package org.tedros.it.tools.gitlab.gateway;

import java.util.List;

import org.tedros.it.tools.gitlab.api.model.GitLabMergeRequest;
import org.tedros.it.tools.gitlab.api.model.GitLabProject;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

@Headers("Accept: application/json")
public interface GitLabClient {

    @RequestLine("GET /api/v4/projects?search={name}&simple=false")
    @Headers("Content-Type: application/json")
    List<GitLabProject> searchProjectsByName(@Param("name") String name);

    // Caso queira buscar sem parâmetro (todos os projetos visíveis)
    @RequestLine("GET /api/v4/projects")
    List<GitLabProject> getAllProjects();
    
    // Todos os MRs de um projeto
    @RequestLine("GET /api/v4/projects/{projectId}/merge_requests?per_page=100")
    List<GitLabMergeRequest> getMergeRequests(@Param("projectId") Long projectId);

    // MRs abertos
    @RequestLine("GET /api/v4/projects/{projectId}/merge_requests?state=opened")
    List<GitLabMergeRequest> getOpenedMergeRequests(@Param("projectId") Long projectId);

    // MRs mesclados (últimos 50)
    @RequestLine("GET /api/v4/projects/{projectId}/merge_requests?state=merged&per_page=50")
    List<GitLabMergeRequest> getMergedMergeRequests(@Param("projectId") Long projectId);
    
    @RequestLine("GET /api/v4/projects/{projectId}/merge_requests/{iid}")
    GitLabMergeRequest getMergeRequest(@Param("projectId") Long projectId, @Param("iid") Long iid);
    
    @RequestLine("GET /api/v4/projects/{projectId}/merge_requests/{iid}/raw_diffs")
    Response getMergeRequestRawDiffs(@Param("projectId") Long projectId, @Param("iid") Long iid);
}