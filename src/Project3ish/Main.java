package Project3ish;

import com.google.gson.Gson;
import twitter4j.*;

import java.lang.reflect.Field;
import java.util.*;


public class Main {

    //private variables
    private static String hashtag = "#sick";
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
        Gson g = new Gson();

        map = tweetLoader.retrieveTweetsWithHashTag(hashtag);
        tweetLoader.retrieveLimit();
        List<String> list;
        list = mapToList(map);
        SaveUserStatus.display(map, list);
        SaveUserStatus.saveFile(map, list);

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


    public static void display(Map <String, BYUITweet> map, List<String> list) {

        BYUITweet tweet = new BYUITweet();
        List<BYUITweet> val = new ArrayList<>(map.values());
        Collections.sort(val, new Comparator<BYUITweet>() {
            @Override
            public int compare(BYUITweet o1, BYUITweet o2) {
                return 0;
            }
        });


        for (int i = 0; i <list.size(); i++) {
            //System.out.println(list.get(i)) + " ( " + map.get(list.get(i)).getUser().followers + " Followers - " + map.get(list.get(i)).text);
            System.out.println(list.get(i));
            //System.out.println("User :" + );
            try {
                List<Status> tweets = tweetLoader.getStatus(list.get(i));

                for(Status status: tweets) {
                    System.out.println("\t " + status.getFavoriteCount() );
                }
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}


