package it.cosenonjaviste.introtoretrofitrxjava;

import java.util.Map;

import it.cosenonjaviste.introtoretrofitrxjava.model.RepoResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.RepoStats;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Activity05RxMultipleCalls extends BaseActivity<RepoStats, GitHubService> {

    @Override protected Class<GitHubService> getServiceClass() {
        return GitHubService.class;
    }

    protected void loadItems() {
        service.listLastRepos()
                .flatMapIterable(RepoResponse::getItems)
                .flatMap(repo -> loadRepoStats(repo.getOwner().getLogin(), repo.getName()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::addAll, t -> showError());
    }

    private Observable<RepoStats> loadRepoStats(String login, String repoName) {
        return Observable.zip(
                service.listContributors(login, repoName),
                service.listLanguages(login, repoName).map(Map::keySet),
                (contributors, languages) -> new RepoStats(repoName, contributors, languages)
        );
    }
}