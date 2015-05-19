package it.cosenonjaviste.introtoretrofitrxjava.model;

import java.text.MessageFormat;
import java.util.List;

public class UserStats {
    private User user;

    private List<Tag> tags;
    private List<Badge> badges;

    public UserStats(User user, List<Tag> tags, List<Badge> badges) {
        this.user = user;
        this.tags = tags.size() > 5 ? tags.subList(0, 5) : tags;
        this.badges = badges.size() > 5 ? badges.subList(0, 5) : badges;
    }

    public User getUser() {
        return user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<Badge> getBadges() {
        return badges;
    }

    @Override public String toString() {
        return MessageFormat.format("{0}\n\t{1}\n\t{2}", user.toString(), listToString(tags), listToString(badges));
    }

    private String listToString(List<?> l) {
        String s = l.toString();
        return s.substring(1, s.length() - 1);
    }

    public static void printList(List<UserStats> statsList) {
        for (UserStats userStats : statsList) {
            System.out.println(userStats);
        }
    }
}
