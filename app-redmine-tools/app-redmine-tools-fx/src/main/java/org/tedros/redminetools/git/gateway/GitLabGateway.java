package org.tedros.redminetools.git.gateway;

import java.util.List;

import org.tedros.redminetools.git.model.GitLabMergeRequest;
import org.tedros.redminetools.git.model.GitLabProject;

import feign.Feign;
import feign.Param;
import feign.RequestInterceptor;
import feign.gson.GsonDecoder;
import feign.okhttp.OkHttpClient;

public class GitLabGateway {

    private final GitLabClient client;

    public GitLabGateway(String baseUrl, String privateToken) {
        RequestInterceptor authInterceptor = template -> 
            template.header("PRIVATE-TOKEN", privateToken)
                    .header("Accept", "application/json");

        this.client = Feign.builder()
                .client(new OkHttpClient())
                .decoder(new GsonDecoder())
                .requestInterceptor(authInterceptor)
                .target(GitLabClient.class, baseUrl);
    }

    public List<GitLabProject> searchProjectsByName(@Param("name") String name){
    	return client.searchProjectsByName(name);
    }

    public List<GitLabProject> getAllProjects(){
    	return client.getAllProjects();
    }
    
    public List<GitLabMergeRequest> getMergeRequests(@Param("projectId") Long projectId){
    	return client.getMergeRequests(projectId);
    }

    public List<GitLabMergeRequest> getOpenMergeRequests(@Param("projectId") Long projectId){
    	return client.getOpenMergeRequests(projectId);
    }

    public List<GitLabMergeRequest> getMergedMergeRequests(@Param("projectId") Long projectId){
    	return client.getMergeRequests(projectId);
    }

    
}
