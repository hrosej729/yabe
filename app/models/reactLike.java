package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;

@Entity
public class reactLike extends Model {

    @Required
    public String author;

    @ManyToOne
    @Required
    public Post post;

    public reactLike(Post post, String author) {
        this.post = post;
        this.author = author;

    }

}