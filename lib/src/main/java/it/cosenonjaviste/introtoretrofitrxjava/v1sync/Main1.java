package it.cosenonjaviste.introtoretrofitrxjava.v1sync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import it.cosenonjaviste.introtoretrofitrxjava.StackOverflowServiceFactory;
import it.cosenonjaviste.introtoretrofitrxjava.model.BadgeResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.TagResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.User;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserStats;

public class Main1 {
    public static void main(String[] args) {
        StackOverflowService service = StackOverflowServiceFactory
                .createService(StackOverflowService.class, Executors.newCachedThreadPool());

        List<User> users = service.getTopUsers().getItems();
        if (users.size() > 5) {
            users = users.subList(0, 5);
        }
        List<UserStats> statsList = new ArrayList<>();
        for (User user : users) {
            UserStats userStats = loadUserStats(service, user);
            statsList.add(userStats);
        }

        UserStats.printList(statsList);
    }

    private static UserStats loadUserStats(StackOverflowService service, User user) {
        TagResponse tags = service.getTags(user.getId());
        BadgeResponse badges = service.getBadges(user.getId());

        return new UserStats(user, tags.getItems(), badges.getItems());
    }
}
