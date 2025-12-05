package org.tedros.tools.test;

import org.tedros.it.tools.gitlab.ai.function.SearchGitLabProjectFunction;
import org.tedros.it.tools.gitlab.ai.model.TGitLabProjectName;
import org.tedros.it.tools.gitlab.gateway.GitLabClient;
import org.tedros.it.tools.gitlab.gateway.GitLabGateway;

import feign.Feign;
import feign.RequestInterceptor;
import feign.gson.GsonDecoder;
import feign.okhttp.OkHttpClient;

public class GitLabPoc {
	
	private static final String TOKEN = System.getenv("GITLAB_TOKEN");
	private static final String GITLAB_URL = "https://gitlab.detran.go.gov.br/";
	
	private static final GitLabGateway gateway;
	
	static {
		gateway = GitLabGateway.getInstance(GITLAB_URL, TOKEN); 
	}

	public static void main(String[] args) {
		//searchProjectFunctionPoc();
		//searchProjectByNamePoc();
		//getBranches();
		//getSingleBranches();
		//getCommits();
		//getSingleCommit();
		getCommitDiff();
	}
	
	private static void getCommitDiff() {
		gateway.getRepositoryCommitDiff(273L, "8eca03ff0cedb99b1828306a3861ed447913e7cb").stream()
		.forEach(System.out::println);
	}
	
	private static void getSingleCommit() {
		System.out.println(gateway.getSingleRepositoryCommit(273L, "8eca03ff0cedb99b1828306a3861ed447913e7cb"));
	}
	
	private static void getCommits() {
		gateway.getRepositoryCommits(273L).stream()
		.forEach(System.out::println);
	}
	
	private static void getSingleBranches() {
		System.out.println(gateway.getSingleRepositoryBranches(273L, "develop"));
	}
	
	private static void getBranches() {
		gateway.getRepositoryBranches(273L).stream()
		.forEach(System.out::println);
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
