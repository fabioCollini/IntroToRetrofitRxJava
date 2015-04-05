package it.cosenonjaviste.introtoretrofitrxjava;

import android.widget.Toast;

import java.util.List;

import it.cosenonjaviste.introtoretrofitrxjava.model.Repo;

public class Activity01SingleCall extends BaseActivity<Repo, GitHubServiceSync> {

    @Override protected Class<GitHubServiceSync> getServiceClass() {
        return GitHubServiceSync.class;
    }

    @Override protected void loadItems() {
        new Thread(() -> {
            try {
                List<Repo> items = loadItemsSync();
                runOnUiThread(() -> adapter.addAll(items));
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, R.string.error_loading, Toast.LENGTH_LONG).show());
            }
        }).start();
    }

    private List<Repo> loadItemsSync() {
        return service.listLastRepos().getItems();
    }
}