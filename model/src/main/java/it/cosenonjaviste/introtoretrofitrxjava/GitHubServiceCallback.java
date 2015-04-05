package it.cosenonjaviste.introtoretrofitrxjava;

import java.util.List;
import java.util.Map;

import it.cosenonjaviste.introtoretrofitrxjava.model.RepoResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.User;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface GitHubServiceCallback {

    @GET("/search/repositories?q=language:Java&sort=updated&order=desc")
    void listLastRepos(Callback<RepoResponse> callback);

    @GET("/repos/{owner}/{repo}/contributors")
    void listContributors(@Path("owner") String ownerLoginName, @Path("repo") String repoName, Callback<List<User>> callback);

    @GET("/repos/{owner}/{repo}/languages")
    void listLanguages(@Path("owner") String ownerLoginName, @Path("repo") String repoName, Callback<Map<String, String>> callback);
}