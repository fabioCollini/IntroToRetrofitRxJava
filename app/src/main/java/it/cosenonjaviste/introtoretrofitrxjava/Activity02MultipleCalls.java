package it.cosenonjaviste.introtoretrofitrxjava;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.cosenonjaviste.introtoretrofitrxjava.model.Repo;
import it.cosenonjaviste.introtoretrofitrxjava.model.RepoResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.RepoStats;
import it.cosenonjaviste.introtoretrofitrxjava.model.User;

public class Activity02MultipleCalls extends BaseActivity<RepoStats, GitHubServiceSync> {

    @Override protected Class<GitHubServiceSync> getServiceClass() {
        return GitHubServiceSync.class;
    }

    @Override protected void loadItems() {
        new Thread(() -> {
            try {
                List<RepoStats> items = loadItemsSync();
                runOnUiThread(() -> adapter.addAll(items));
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, R.string.error_loading, Toast.LENGTH_LONG).show());
            }
        }).start();
    }

    private List<RepoStats> loadItemsSync() {
        RepoResponse repoResponse = service.listLastRepos();
        List<Repo> items = repoResponse.getItems();
        List<RepoStats> statsList = new ArrayList<>();
        for (Repo repo : items) {
            String login = repo.getOwner().getLogin();
            String name = repo.getName();
            List<User> contributors =
                    service.listContributors(login, name);
            Set<String> languages =
                    service.listLanguages(login, name).keySet();
            statsList.add(new RepoStats(name, contributors, languages));
        }
        return statsList;
    }
}