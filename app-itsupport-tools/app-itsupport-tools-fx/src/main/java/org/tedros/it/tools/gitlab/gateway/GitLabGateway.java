package org.tedros.it.tools.gitlab.gateway;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.tedros.it.tools.gitlab.api.model.BranchModel;
import org.tedros.it.tools.gitlab.api.model.CommitDiffModel;
import org.tedros.it.tools.gitlab.api.model.CommitModel;
import org.tedros.it.tools.gitlab.api.model.GitLabMergeRequest;
import org.tedros.it.tools.gitlab.api.model.GitLabProject;
import org.tedros.it.tools.gitlab.api.model.GitLabProjectDetail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import feign.Feign;
import feign.Param;
import feign.RequestInterceptor;
import feign.Response;
import feign.Util;
import feign.gson.GsonDecoder;
import feign.okhttp.OkHttpClient;


public class GitLabGateway {
	
	// Instância do Gson para decodificar o Response manualmente
    private final Gson gson;
	
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
        
        this.gson = new Gson();
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
    public List<GitLabProject> getAllProjects() {
        // Chama o método genérico passando a primeira requisição
        return executePagination(client.getAllProjects());
    }

    public List<GitLabProject> getAllProjectsInSimpleMode() {
        // Chama o método genérico passando a primeira requisição (com simple=true)
        return executePagination(client.getAllProjectsInSimpleMode());
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
    
    // --- LÓGICA DE PAGINAÇÃO CENTRALIZADA ---

    /**
     * Executa o loop de paginação a partir de uma resposta inicial.
     */
    private List<GitLabProject> executePagination(Response initialResponse) {
        List<GitLabProject> allProjects = new ArrayList<>();
        String nextUrl = null;

        // 1. Processa a primeira página (vinda do parâmetro)
        try (Response response = initialResponse) {
            processResponse(response, allProjects);
            nextUrl = extractNextPageUrl(response);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao buscar página inicial de projetos", e);
        }

        // 2. Loop enquanto houver próxima página
        while (nextUrl != null && !nextUrl.isEmpty()) {
            try (Response response = client.getNextPage(URI.create(nextUrl))) {
                processResponse(response, allProjects);
                nextUrl = extractNextPageUrl(response);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao buscar próxima página: " + nextUrl, e);
            }
        }

        return allProjects;
    }

    private void processResponse(Response response, List<GitLabProject> targetList) throws IOException {
        if (response.status() == 200 && response.body() != null) {
            try (Reader reader = response.body().asReader(Util.UTF_8)) {
                Type listType = new TypeToken<List<GitLabProject>>(){}.getType();
                List<GitLabProject> pageProjects = gson.fromJson(reader, listType);
                if (pageProjects != null) {
                    targetList.addAll(pageProjects);
                }
            }
        }
    }

    private String extractNextPageUrl(Response response) {
        if (response.headers().containsKey("Link")) {
            String linkHeader = response.headers().get("Link").iterator().next();
            // Regex para capturar a URL dentro de <> onde o rel="next"
            Pattern pattern = Pattern.compile("<([^>]+)>; rel=\"next\"");
            Matcher matcher = pattern.matcher(linkHeader);
            
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    
}
