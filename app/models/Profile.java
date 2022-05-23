package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;

@Entity
public class Profile extends Model implements Comparable<Profile> {

    @Required
    public String creator;

    private Profile(String name) {
        this.creator = name;
    }

    public static List<Map> getCloud() {
        List<Map> result = Profile.find(
                "select new map(pf.name as profile, count(pd.id) as pound) from Post p join p.profiles as p group by p.name order by pf.name"
        ).fetch();
        return result;
    }

    public String toString() {
        return creator;
    }

    public int compareTo(Profile otherProfile) {
        return creator.compareTo(otherProfile.creator);
    }

    public static Profile findOrCreateByName(String name) {
        Profile profile = Profile.find("byName", name).first();
        if(profile == null) {
            profile = new Profile(name);
        }
        return profile;
    }
}