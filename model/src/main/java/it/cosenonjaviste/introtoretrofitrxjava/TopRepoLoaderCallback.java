package it.cosenonjaviste.introtoretrofitrxjava;

import java.util.List;
import java.util.Map;

import it.cosenonjaviste.introtoretrofitrxjava.model.RepoResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.RepoStats;
import it.cosenonjaviste.introtoretrofitrxjava.model.User;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TopRepoLoaderCallback {

    private GitHubServiceCallback service;

    public TopRepoLoaderCallback(GitHubServiceCallback service) {
        this.service = service;
    }

//    public List<RepoStats> load(int limit) {
//        RepoResponse repoResponse = service.listLastRepos();
//        List<Repo> items = repoResponse.getItems();
//        List<RepoStats> statsList = new ArrayList<>();
//        for (Repo repo : items) {
//            statsList.add(loadRepoStats(repo.getOwner().getLogin(), repo.getName()));
//        }
//        return statsList;
//    }

    private void loadRepoStats(String login, String repoName, Callback<RepoStats> callback) {
service.listLastRepos(new Callback<RepoResponse>() {
    @Override public void success(RepoResponse repoResponse, Response response) {
        System.out.println(repoResponse.getItems());
    }

    @Override public void failure(RetrofitError error) {
        error.printStackTrace();
    }
});
        service.listContributors(login, repoName, new Callback<List<User>>() {
            @Override public void success(List<User> contributors, Response response) {
                service.listLanguages(login, repoName, new Callback<Map<String, String>>() {
                    @Override public void success(Map<String, String> languages, Response response) {
                        callback.success(new RepoStats(repoName, contributors, languages.keySet()), response);
                    }

                    @Override public void failure(RetrofitError error) {
                        callback.failure(error);
                    }
                });
            }

            @Override public void failure(RetrofitError error) {
                callback.failure(error);
            }
        });
    }
}
