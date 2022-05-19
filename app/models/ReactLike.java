package models;

import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;

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

}