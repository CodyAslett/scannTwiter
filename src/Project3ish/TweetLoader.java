package Project3ish;

import com.google.gson.Gson;
import twitter4j.*;
import twitter4j.User;
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
    public List<Status> scanHash(String hashtag) throws TwitterException {
        return twitter.search(new Query(hashtag)).getTweets();
    }

    public void retrieveLimit() throws TwitterException {
        //int	totalTweets = 0;
        //long maxID = -1;
        Map<String, RateLimitStatus> rateLimitStatus = twitter.getRateLimitStatus("search");
        RateLimitStatus searchTweetsRateLimit = rateLimitStatus.get("/search/tweets");
        System.out.printf("You have %d calls remaining out of %d, Limit resets in %d seconds\n",
                searchTweetsRateLimit.getRemaining(),
                searchTweetsRateLimit.getLimit(),
                searchTweetsRateLimit.getSecondsUntilReset());
    }


    public List<Status> getStatus(String name) throws TwitterException {
        Paging paging = new Paging(1, 100);
            List<Status> out = new LinkedList<>();

            IDs friendsIDs = twitter.getFriendsIDs(name, -1);
            System.out.println(twitter.showUser(name).getName() + "\n\t");
            do {
                for (long IdOfAFriend : friendsIDs.getIDs()) {
                    System.out.println(twitter.showUser(IdOfAFriend).getName());
                    out.add(twitter.showUser(IdOfAFriend).getStatus());
                }
            } while (friendsIDs.hasNext());

            return out;
        }

        public List<User> getFollowers(Long ID) throws TwitterException {
            List<User> out = new LinkedList<>();

            IDs friendsIDs = twitter.getFriendsIDs(ID, -1);
            System.out.println(twitter.showUser(ID).getName() + "\n\t");
            do {
                for(long IdOfAFriend: friendsIDs.getIDs()) {
                    System.out.println(twitter.showUser(IdOfAFriend).getName());
                    out.add(twitter.showUser(IdOfAFriend));
                }
            } while (friendsIDs.hasNext());

            return out;
        }

    public List<User> getFollowers(String userName) throws TwitterException {
        List<User> out = new LinkedList<>();

        IDs friendsIDs = twitter.getFriendsIDs(userName, -1);
        System.out.println(twitter.showUser(userName).getName() + "\n\t");
        do {
            for(long IdOfAFriend: friendsIDs.getIDs()) {
                System.out.println(twitter.showUser(IdOfAFriend).getName());
                out.add(twitter.showUser(IdOfAFriend));
            }
        } while (friendsIDs.hasNext());

        return out;
    }

    public List<User> getFollowers(User user) throws TwitterException {
        List<User> out = new LinkedList<>();

        IDs friendsIDs = twitter.getFriendsIDs(user.getId(), -1);
        System.out.println(twitter.showUser(user.getId()).getName() + "\n\t");
        do {
            for(long IdOfAFriend: friendsIDs.getIDs()) {
                System.out.println(twitter.showUser(IdOfAFriend).getName());
                out.add(twitter.showUser(IdOfAFriend));
            }
        } while (friendsIDs.hasNext());

        return out;
    }

    public User getUser(Long id) throws TwitterException {
        return twitter.showUser(id);
    }
    public User getUser(String name) throws TwitterException {
        return twitter.showUser(name);
    }

    public List<Status> getTimeline(User user) throws TwitterException {
        List<Status> out = new LinkedList<>();

        for(int i = 1; i< 10; i++) {
            Paging paging = new Paging(i, 100);
            List<Status> temp = twitter.getUserTimeline(user.getName(), paging);
            out.addAll(temp);
        }
        return out;
    }
}
