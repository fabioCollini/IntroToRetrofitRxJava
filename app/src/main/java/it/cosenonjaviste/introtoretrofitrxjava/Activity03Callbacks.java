package it.cosenonjaviste.introtoretrofitrxjava;

import java.util.List;

import it.cosenonjaviste.introtoretrofitrxjava.model.Repo;
import it.cosenonjaviste.introtoretrofitrxjava.model.RepoResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Activity03Callbacks extends BaseActivity<Repo, GitHubServiceCallback> {

    @Override protected Class<GitHubServiceCallback> getServiceClass() {
        return GitHubServiceCallback.class;
    }

    protected void loadItems() {
        service.listLastRepos(new Callback<RepoResponse>() {
            @Override public void success(RepoResponse repoResponse, Response response) {
                List<Repo> items = repoResponse.getItems();
                adapter.addAll(items);
            }

            @Override public void failure(RetrofitError error) {
                showError();
            }
        });
    }
}