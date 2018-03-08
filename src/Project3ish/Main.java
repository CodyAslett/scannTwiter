package Project3ish;

import com.google.gson.Gson;
import twitter4j.*;

import java.util.*;


public class Main {

    //private variables
    private static String hashtag = "#byui";
    private static Map<String, BYUITweet> map;
    private static TweetLoader tweetLoader = new TweetLoader();

    /***
     *
     * @param args
     * @throws TwitterException
     * Main makes an instance of Gson, calls a class that turns a that
     */
    public static void main(String[] args) throws TwitterException {
        Gson g = new Gson();

        map = tweetLoader.retrieveTweetsWithHashTag(hashtag);

        List<String> list;
        list = mapToList(map);
        display(map, list);
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


    public static void display(Map <String, BYUITweet> map, List<String> list) {

        BYUITweet tweet = new BYUITweet();
        List<BYUITweet> val = new ArrayList<>(map.values());
        Collections.sort(val, new Comparator<BYUITweet>() {
            @Override
            public int compare(BYUITweet o1, BYUITweet o2) {
                return 0;
            }
        });

        //for (BYUITweet value : map.values()) {
        //System.out.println(value.getUser().getName() + " (" + value.getUser().followers + " Followers) - "  + value.text);
        


        for (int i = 0; i <list.size(); i++) {
            //System.out.println(list.get(i)) + " ( " + map.get(list.get(i)).getUser().followers + " Followers - " + map.get(list.get(i)).text);
            System.out.println(list.get(i));
            //System.out.println("User :" + );
            try {
                List tweets = tweetLoader.retrieveUserTweets(list.get(i));

                for(Object tweetInfo: tweets) {
                    System.out.println("\t " + tweetInfo);
                }
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}


