package Project3ish;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    // variable names
    String name;
    List<String> tweets;

    @SerializedName("followers_count")
    int followers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }


}
