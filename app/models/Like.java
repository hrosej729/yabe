package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;

@Entity
public class Like extends Model {

    @ManyToOne
    @Required
    public Post post;

    public Like(Post post) {
        this.post = post;
    }

}