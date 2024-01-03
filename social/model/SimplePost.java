package social.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

public class SimplePost implements Post {

    private String text;
    private Instant date;
    private Set<User> likers;

    public SimplePost(String text) {
        if (text == null) {
            throw new NullPointerException();
        }
        this.text = text;
        this.date = Instant.now();
        this.likers = new HashSet<User>();
    }

    @Override
    public Instant getDate() {
        return this.date;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public int getLikeNumber() {
        return this.likers.size();
    }

    @Override
    public boolean hasLikeFrom(User u) {
        return this.likers.contains(u);
    }

    @Override
    public boolean addLikeFrom(User u) {
        if (u == null) {
            throw new NullPointerException();
        }
        return this.likers.add(u);
    }

    @Override
    public Set<User> getLikers() {
        return Collections.unmodifiableSet(this.likers);
    }

    @Override
    public ListIterator<User> iterator() {
        return Collections.unmodifiableList(new ArrayList<User>(this.likers)).listIterator();
    }

    @Override
    public int compareTo(Post p) {
        if (p == null) {
            throw new NullPointerException();
        }
        return this.date.compareTo(p.getDate());
    }

    @Override
    public boolean isAfter(Post p) {
        if (p == null) {
            throw new NullPointerException();
        }
        return this.date.isAfter(p.getDate());
    }

    @Override
    public boolean isBefore(Post p) {
        if (p == null) {
            throw new NullPointerException();
        }
        return this.date.isBefore(p.getDate());
    }
}
