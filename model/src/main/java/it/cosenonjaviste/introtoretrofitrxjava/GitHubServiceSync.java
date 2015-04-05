package it.cosenonjaviste.introtoretrofitrxjava;

import java.util.List;
import java.util.Map;

import it.cosenonjaviste.introtoretrofitrxjava.model.RepoResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.User;
import retrofit.http.GET;
import retrofit.http.Path;

public interface GitHubServiceSync {

    @GET("/search/repositories?q=language:Java&sort=updated&order=desc")
    RepoResponse listLastRepos();

    @GET("/repos/{owner}/{repo}/contributors")
    List<User> listContributors(@Path("owner") String ownerLoginName, @Path("repo") String repoName);

    @GET("/repos/{owner}/{repo}/languages")
    Map<String, String> listLanguages(@Path("owner") String ownerLoginName, @Path("repo") String repoName);
}