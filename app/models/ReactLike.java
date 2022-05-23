package models;

import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;

import java.util.List;

@Entity
public class ReactLike extends Model {

    @Required
    public String author;

    @ManyToOne
    @Required
    public Post post;

    public ReactLike(Post post, String author) {
        this.post = post;
        this.author = author;
    }
    public static List<ReactLike> findLikedBy(String profile) {
        return Comment.find(
                "select p from Post p, ReactLike r where r.post = p and r.author = ?1", profile
        ).fetch();
    }

}