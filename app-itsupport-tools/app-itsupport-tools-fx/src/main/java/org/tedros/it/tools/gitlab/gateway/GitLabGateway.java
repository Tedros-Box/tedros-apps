package org.tedros.it.tools.gitlab.gateway;

import java.io.IOException;
import java.util.List;

import org.tedros.it.tools.gitlab.api.model.BranchModel;
import org.tedros.it.tools.gitlab.api.model.CommitDiffModel;
import org.tedros.it.tools.gitlab.api.model.CommitModel;
import org.tedros.it.tools.gitlab.api.model.GitLabMergeRequest;
import org.tedros.it.tools.gitlab.api.model.GitLabProject;
import org.tedros.it.tools.gitlab.api.model.GitLabProjectDetail;

import feign.Feign;
import feign.Param;
import feign.RequestInterceptor;
import feign.Response;
import feign.Util;
import feign.gson.GsonDecoder;
import feign.okhttp.OkHttpClient;


public class GitLabGateway {
	
	private static GitLabGateway instance;
	
    private final GitLabClient client;

    private GitLabGateway(String baseUrl, String privateToken) {
        RequestInterceptor authInterceptor = template -> 
            template.header("PRIVATE-TOKEN", privateToken)
                    .header("Accept", "application/json");

        this.client = Feign.builder()
                .client(new OkHttpClient())
                .decoder(new GsonDecoder())
                .requestInterceptor(authInterceptor)
                .target(GitLabClient.class, baseUrl);
    }
    
    public static synchronized GitLabGateway getInstance(String baseUrl, String privateToken) {
		if (instance == null) {
			instance = new GitLabGateway(baseUrl, privateToken);
		}
		return instance;
	}
    // Search projects by name
    public List<GitLabProject> searchProjectsByName(String name){
    	return client.searchProjectsByName(name);
    }
    // Get all projects
    public List<GitLabProject> getAllProjects(){
    	return client.getAllProjects();
    }
    
    public GitLabProjectDetail getProject(Long projectId){
    	return client.getProject(projectId);
    }
    
    public List<GitLabMergeRequest> getMergeRequests(Long projectId){
    	return client.getMergeRequests(projectId);
    }
    // Get all opened merge requests
    public List<GitLabMergeRequest> getOpenedMergeRequests(Long projectId){
    	return client.getOpenedMergeRequests(projectId);
    }

    public List<GitLabMergeRequest> getMergedMergeRequests(Long projectId){
    	return client.getMergeRequests(projectId);
    }
    
    
    public GitLabMergeRequest getMergeRequest(Long projectId, Long iid) {
    	return client.getMergeRequest(projectId, iid);
    }
    
    // Get merge request raw diffs
    public String getMergeRequestRawDiffs(Long projectId, Long iid) throws IOException {
    	try (Response response = client.getMergeRequestRawDiffs(projectId, iid)) {
            return Util.toString(response.body().asReader(Util.UTF_8));
        }
    }
    
    public List<BranchModel> getRepositoryBranches(Long projectId){
    	return client.getRepositoryBranches(projectId);
    }
    
    // Get single branch
    public BranchModel getSingleRepositoryBranches(Long projectId, String branch){
    	return client.getSingleRepositoryBranches(projectId, branch);
    }
    
    public List<CommitModel> getRepositoryCommits(Long projectId){
    	return client.getRepositoryCommits(projectId);
    }
    
    public CommitModel getSingleRepositoryCommit(Long projectId, String sha){
    	return client.getSingleRepositoryCommit(projectId, sha);
    }
    
    public List<CommitDiffModel> getRepositoryCommitDiff(@Param("projectId") Long projectId, @Param("sha")  String sha) {
    	return client.getRepositoryCommitDiff(projectId, sha);
    }

    
}
