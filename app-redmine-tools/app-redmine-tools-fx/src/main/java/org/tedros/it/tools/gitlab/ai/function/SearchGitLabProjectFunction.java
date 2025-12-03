package org.tedros.it.tools.gitlab.ai.function;

import java.util.List;
import java.util.logging.Level;

import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.it.tools.gitlab.gateway.GitLabClient;
import org.tedros.it.tools.gitlab.model.GitLabProject;
import org.tedros.it.tools.gitlab.model.TSearchGitProject;

import feign.Feign;
import feign.RequestInterceptor;
import feign.gson.GsonDecoder;
import feign.okhttp.OkHttpClient;
import lombok.extern.java.Log;

@Log
public class SearchGitLabProjectFunction extends TFunction<TSearchGitProject> {
	
	private static final String name = "search_gitlab_project_by_name"; 
	private static final String description = "Searches for a gitlab projects by name";
	private static String GITLAB_URL = null;
	private static String TOKEN = null;
	
	static {
		GitLabApiPropertyUtil instance = GitLabApiPropertyUtil.getInstance();
		GITLAB_URL = instance.getGitlabUrl();
		TOKEN = instance.getGitlabKey();
	}
	
	public SearchGitLabProjectFunction() {		
		super(name, description, TSearchGitProject.class, v->{
			
			try {
				RequestInterceptor auth = template -> 
	            template.header("PRIVATE-TOKEN", TOKEN);

	            GitLabClient client = Feign.builder()
	                .client(new OkHttpClient())
	                .decoder(new GsonDecoder())
	                .requestInterceptor(auth)
	                .target(GitLabClient.class, GITLAB_URL);
		        
		        return client.searchProjectsByName(v.getName());
		        
			} catch (Exception e) {
				e.printStackTrace();
				log.log(Level.SEVERE, e.getMessage());
				return new Response("Function error: " + e.getMessage());
				
			}
			
		});
	}
	
	/*public static void main(String[] args) {
		SearchGitLabProjectFunction x = new SearchGitLabProjectFunction();
		Object result = x.getCallback().apply(TSearchGitProject.builder().name("sgm-integracao").build());
		System.out.println(result);
	}
	
	public static void main(String[] args) {

        RequestInterceptor auth = template -> 
            template.header("PRIVATE-TOKEN", TOKEN);

        GitLabClient client = Feign.builder()
                .client(new OkHttpClient())
                .decoder(new GsonDecoder())
                .requestInterceptor(auth)
                .target(GitLabClient.class, GITLAB_URL);

        // Funciona perfeitamente!
        var projects = client.searchProjectsByName("sgm-integracao");
        if (!projects.isEmpty()) {
            Long projectId = projects.get(0).id();
            var mrs = client.getOpenMergeRequests(projectId);

            mrs.forEach(mr -> System.out.println("!%d %s → %s | %s".formatted(
                mr.iid(), mr.sourceBranch(), mr.targetBranch(), mr.title()
            )));
        }
    }*/

}
