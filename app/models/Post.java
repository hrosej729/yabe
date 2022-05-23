package models;

import java.util.*;
import java.util.Set;
import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;
@Entity
public class Post extends Model {

    @Required
    public String title;

    @Required
    public Date postedAt;

    @Lob
    @Required
    @MaxSize(10000)
    public String content;

    @Required
    @ManyToOne
    public User author;

    @OneToMany(mappedBy="post", cascade=CascadeType.ALL)
    public List<Comment> comments;
    @ManyToMany(cascade=CascadeType.PERSIST)
    public Set<Tag> tags;
    @ManyToMany(cascade=CascadeType.PERSIST)
    public Set<Profile> profiles;
    @OneToMany(mappedBy="post", cascade=CascadeType.ALL)
    public List<ReactLike> likes;
    public Post(User author, String title, String content) {
        this.comments = new ArrayList<Comment>();
        this.tags = new TreeSet<Tag>();
        this.likes = new ArrayList<ReactLike>();
        this.profiles = new TreeSet<Profile>();
        this.author = author;
        this.title = title;
        this.content = content;
        this.postedAt = new Date();
    }
    public Post tagItWith(String name) {
        tags.add(Tag.findOrCreateByName(name));
        return this;
    }

    public static List<Post> findTaggedWith(String... tags) {
        return Post.find(
                "select distinct p from Post p join p.tags as t where t.name in (:tags) group by p.id, p.author, p.title, p.content,p.postedAt having count(t.id) = :size"
        ).bind("tags", tags).bind("size", tags.length).fetch();
    }

    public Post profileIs(String creators) {
        profiles.add(Profile.findOrCreateByName(creators));
        return this;
    }

    public static List<Post> findMadeBy(String profile) {
        return Post.find(
                "select distinct p from Post p join p.profiles as pf where pf.creator = ?1", profile
        ).fetch();
    }
    public Post addComment(String author, String content) {
        Comment newComment = new Comment(this, author, content).save();
        this.comments.add(newComment);
        this.save();
        return this;
    }
    public Post addLike(String author) {
        ReactLike newLike = new ReactLike(this, author).save();
        this.likes.add(newLike);
        this.save();
        return this;
    }
    public Post previous() {
        return Post.find("postedAt < ?1 order by postedAt desc", postedAt).first();
    }
    public Post next() {
        return Post.find("postedAt > ?1 order by postedAt asc", postedAt).first();
    }

}