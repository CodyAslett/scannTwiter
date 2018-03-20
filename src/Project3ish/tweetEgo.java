package Project3ish;

import com.google.gson.Gson;
import twitter4j.*;
import twitter4j.User;

// NOTES:
//      // "Friends list of a friend using Twitter4J" : https://stackoverflow.com/questions/8891984/friends-list-of-a-friend-using-twitter4j

import java.util.*;

public class tweetEgo {
    private User userToScan;

    Map users = new HashMap<String, Long>();
    List<User> toScanUsers = new LinkedList<>();

    private static TweetLoader tweetLoader = new TweetLoader();
    private String fileSaveTweets;

    SaveUserStatus saver = new SaveUserStatus();


    /****************************************************************
     * Constructor Tweet Ego get of friends
     ****************************************************************/
    public tweetEgo() {

        return;
    }

    /****************************************************************
     * Non default constructor
     * IN: user to scan
     * @param user
     ****************************************************************/
    public tweetEgo(String user) throws TwitterException {
        userToScan = tweetLoader.getUser(user);
        return;
    }

    /********************************************************************
     * Save tweets to file
     * @param fileName
     */
    public void endlessSaveUserTweetsToFile(String fileName) {
        fileSaveTweets = fileName;
        ego();
        return;
    }
    public void endlessSaveUserTweetsToFile(String fileName, String userName) {
        try {
            fileSaveTweets = fileName;
            userToScan = tweetLoader.getUser(userName);
            ego();
        } catch (Exception e) {
            System.out.println("Error in enlessSaveUserTweetsToFile error was : " + e);
        }
        return;
    }

    private void ego() {
        try {
            // add user to scan list
            toScanUsers.add(userToScan);
            do {
                // get current user from list
                User currentUser = toScanUsers.get(0);
                // remove uer from list
                toScanUsers.remove(0);
                System.out.println("Getting " + currentUser.getName());

                //save tweets of user
                saver.saveUser(currentUser);

                // get user Freinds and add them to list to look at
                toScanUsers.addAll(tweetLoader.getFollowers(currentUser));
            } while (!toScanUsers.isEmpty());
            return;
        } catch (Exception e) {
            System.out.println("ego Failed with error : " + e + " : " + e.toString());
        }
    }

    /****************************************************************
     * Setters
     * @param user
     */
    public void setUserToScan(String user) throws TwitterException {
        userToScan = tweetLoader.getUser(user);
        return;
    }

    public void setUserToScan(User user) {
        userToScan = user;
        return;
    }

    public void setUserToScan(Long user) {
        try {
            userToScan = tweetLoader.getUser(user);
        } catch (Exception e) {
            System.out.println("TweetLoader threw exeption in function setUserToScan in class tweetEgo the error was : " + e);
        }
        return;
    }
}