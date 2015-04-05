package it.cosenonjaviste.introtoretrofitrxjava;

import it.cosenonjaviste.introtoretrofitrxjava.model.Repo;
import it.cosenonjaviste.introtoretrofitrxjava.model.RepoResponse;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Activity04Rx extends BaseActivity<Repo, GitHubService> {

    @Override protected Class<GitHubService> getServiceClass() {
        return GitHubService.class;
    }

    protected void loadItems() {
        service.listLastRepos()
                .map(RepoResponse::getItems)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::addAll, t -> showError());
    }
}