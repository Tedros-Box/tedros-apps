package org.tedros.tools.test;

import org.tedros.it.tools.gitlab.ai.function.SearchGitLabProjectFunction;
import org.tedros.it.tools.gitlab.ai.model.TGitLabProjectName;
import org.tedros.it.tools.gitlab.gateway.GitLabClient;

import feign.Feign;
import feign.RequestInterceptor;
import feign.gson.GsonDecoder;
import feign.okhttp.OkHttpClient;

public class GitLabPoc {
	
	private static final String TOKEN = "";
	private static final String GITLAB_URL = "https://gitlab.detran.go.gov.br/";

	public static void main(String[] args) {
		searchProjectFunctionPoc();
		searchProjectByNamePoc();
	}

	private static void searchProjectFunctionPoc() {
		SearchGitLabProjectFunction x = new SearchGitLabProjectFunction();
		Object result = x.getCallback().apply(TGitLabProjectName.builder().name("sgm-integracao").build());
		System.out.println(result);
	}
	
	private static void searchProjectByNamePoc() {
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
            var mrs = client.getOpenedMergeRequests(projectId);

            mrs.forEach(mr -> System.out.println("!%d %s → %s | %s".formatted(
                mr.iid(), mr.sourceBranch(), mr.targetBranch(), mr.title()
            )));
        }
	}

}
