package org.tedros.it.tools.gitlab.gateway;

import java.io.IOException;
import java.util.List;

import org.tedros.it.tools.gitlab.api.model.GitLabMergeRequest;
import org.tedros.it.tools.gitlab.api.model.GitLabProject;

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

    public List<GitLabProject> searchProjectsByName(String name){
    	return client.searchProjectsByName(name);
    }

    public List<GitLabProject> getAllProjects(){
    	return client.getAllProjects();
    }
    
    public List<GitLabMergeRequest> getMergeRequests(Long projectId){
    	return client.getMergeRequests(projectId);
    }

    public List<GitLabMergeRequest> getOpenedMergeRequests(Long projectId){
    	return client.getOpenedMergeRequests(projectId);
    }

    public List<GitLabMergeRequest> getMergedMergeRequests(Long projectId){
    	return client.getMergeRequests(projectId);
    }
    
    
    public GitLabMergeRequest getMergeRequest(Long projectId, Long iid) {
    	return client.getMergeRequest(projectId, iid);
    }
    
    public String getMergeRequestRawDiffs(@Param("projectId") Long projectId, @Param("iid") Long iid) throws IOException {
    	try (Response response = client.getMergeRequestRawDiffs(projectId, iid)) {
            return Util.toString(response.body().asReader(Util.UTF_8));
        }
    }

    
}
