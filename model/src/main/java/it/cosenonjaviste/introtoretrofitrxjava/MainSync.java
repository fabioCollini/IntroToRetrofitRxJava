package it.cosenonjaviste.introtoretrofitrxjava;

import java.util.List;

import it.cosenonjaviste.introtoretrofitrxjava.model.Repo;
import it.cosenonjaviste.introtoretrofitrxjava.model.RepoResponse;
import retrofit.RestAdapter;

public class MainSync {
    public static void main(String[] args) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com/")
                .build();
        restAdapter.setLogLevel(RestAdapter.LogLevel.BASIC);
        GitHubServiceSync gitHubService = restAdapter.create(GitHubServiceSync.class);

        RepoResponse repoResponse = gitHubService.listLastRepos();
        List<Repo> items = repoResponse.getItems();
        System.out.println(items);
    }
}
