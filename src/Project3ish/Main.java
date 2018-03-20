package Project3ish;

import com.google.gson.Gson;
import twitter4j.*;
import twitter4j.User;

import java.lang.reflect.Field;
import java.util.*;


public class Main {

    //private variables
    private static String hashtag = "sickday";
    private static Map<String, BYUITweet> map;
    private static TweetLoader tweetLoader = new TweetLoader();
    private static SaveUserStatus saver = new SaveUserStatus();
    /***
     *
     * @param args
     * @throws TwitterException
     * Main makes an instance of Gson, calls a class that turns a that
     */
    public static void main(String[] args) throws TwitterException {
        String test = tweetLoader.scanHash(hashtag).get(0).getUser().getName();
        tweetEgo egoScan = new tweetEgo();
        egoScan.endlessSaveUserTweetsToFile("tweets.txt", test);
        //Gson g = new Gson();
        //Map tweetsWithHashtag = tweetLoader.retrieveTweetsWithHashTag(hashtag);
        //tweetLoader.retrieveLimit();
        //List<String> list;
        //list = mapToList(tweetsWithHashtag);
        //SaveUserStatus.display(tweetsWithHashtag, list);
        //scanUserAtHashtag(hashtag);
        //SaveUserStatus.saveFile(tweetsWithHashtag, list);

        //display(map, list);
    }


    public static List<String> mapToList(Map <String, BYUITweet> map) {
        List<String> list = new ArrayList<>(map.keySet());
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String s1 = (String) o1;
                String s2 = (String) o2;
                return s1.toLowerCase().compareTo(s2.toLowerCase());


            }
        });
        return list;
    }

    /********************************************************************
     * Scan for Users using a particular Hashtag
     * @param startingHashtag
     ********************************************************************/
    public static void scanUserAtHashtag(String startingHashtag) {

        System.out.println("Starting scan of " + startingHashtag);

        try {
            Map users = new HashMap<Long, String>();
            List<Long> toScanUsers = new LinkedList<>();

            // Scan tweets using hashtag
            List<Status> statuses = new LinkedList<>();
            try {
                statuses = tweetLoader.getStatus(startingHashtag);
            }
            catch (Exception e) {
                System.out.println("ERROR GET STATUS");
                System.out.println("ERROR : " + e);
                return;
            }
            for(Status status: statuses) {
                String userName = status.getUser().getName();
                System.out.println("user name : " + userName); // get users name
                Long userId = status.getUser().getId();
                System.out.println("user ID : " + userId);
                if(!users.containsKey(userId)) {
                    System.out.println("New user");
                    toScanUsers.add(userId);
                }
                System.out.println("adding to map of users");
                users.put(userId, userName);
            }
            System.out.println("toScanUsers");
            // toScanUsers
            for(Long userId: toScanUsers) {
                List<User> toScan = tweetLoader.getFollowers(userId);
                for(User u: toScan) {
                    if(!users.containsKey(u.getId()) ) {
                        toScanUsers.add(u.getId());
                    }
                }
                // list users
                List<Status> statuss = tweetLoader.getStatus((String)users.get(toScanUsers));
                for(Status status: statuss) {
                    System.out.println(status.getUser().getName());
                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println(e.getStackTrace());
        }
    }

    public static void display(Map <String, BYUITweet> map, List<String> tweetsWithHashTag) {
        Map users = new HashMap<String, Long>();
        List<String> toScanUsers = new LinkedList<>();

        for (int i = 0; i <tweetsWithHashTag.size(); i++) {
            //System.out.println(tweetsWithHashTag.get(i)) + " ( " + map.get(list.get(i)).getUser().followers + " Followers - " + map.get(list.get(i)).text);
            System.out.println(tweetsWithHashTag.get(i));
            //System.out.println("User :" + );
            try {
                List<Status> tweets = tweetLoader.getStatus(tweetsWithHashTag.get(i));

                for(Status status: tweets) {

                    System.out.println("\t " + status);
                    String userName = status.getUser().getName();
                    Long userId = status.getUser().getId();
                    if(!users.containsKey(userName)) {
                        System.out.println("New user");
                        toScanUsers.add(userName);
                    }
                    users.put(userName, userId);
                }

/////////////////// to Scan Users
                for(String userName: toScanUsers) {
                    System.out.println(" getting user " + userName);
                    List<Status> StatusToScan = tweetLoader.getStatus(userName);
                    // get Tweets
                    for(Status stat: StatusToScan) {
                        if(!users.containsKey(stat.getUser().getName() ) ) {
                            toScanUsers.add(stat.getUser().getName());
                        }
                    }
                    // list users
                }
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}


