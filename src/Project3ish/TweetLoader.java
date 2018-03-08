package Project3ish;

import com.google.gson.Gson;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.*;


public class TweetLoader {

    // class variables
    private Twitter twitter;


    public TweetLoader() {
        configureKeys();
    }

    /***
     * Sets your authentication keys
     */
    private void configureKeys(){
        ConfigurationBuilder cb = new ConfigurationBuilder();

        //TwitterFactory tf = new TwitterFactory(cb.build());
        Keys key = new Keys();
        //this.twitter = tf.getInstance();
        this.twitter = key.instantuate();
    }

    /***
     *
     * @param hashtag
     * @return
     * @throws TwitterException
     * converts a
     */
    public Map<String, BYUITweet> retrieveTweetsWithHashTag(String hashtag) throws TwitterException {

        //create an instance of a map
        Map map = new TreeMap();

        // creates a query that passes in a string
        Query hasher = new Query(hashtag);

        // make a twitter search that tells us our hash results
        QueryResult result = twitter.search(hasher);


        // from http://twitter4j.org/en/code-examples.html

        // convert our json to our BYUITweet
        // for loop to put our tweet results into a map
        for (Status status : result.getTweets()) {
            String json = TwitterObjectFactory.getRawJSON(status);


            // deserialize the Query with a GSon
            Gson gson = new Gson();
            BYUITweet tweeter = gson.fromJson(json, BYUITweet.class);


            // put the values in the map
            map.put(status.getUser().getScreenName(), tweeter);

        }
            return map;
    }


    public List retrieveUserTweets(String name) throws TwitterException {
        List<String> out = new LinkedList<>();
        Paging paging = new Paging(1, 100);
        List<Status> statuses = this.twitter.getUserTimeline(name, paging);
        for(Status status : statuses) {
            out.add(status.getText());
        }
        return out;
    }
}
