package Project3ish;

import com.google.gson.Gson;
import twitter4j.*;
import twitter4j.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class SaveUserStatus {


    private static TweetLoader tweetLoader = new TweetLoader();

    public static void saveFile(Map<String, BYUITweet> map, List<String> list) throws TwitterException {
        BYUITweet tweet = new BYUITweet();
        List<BYUITweet> val = new ArrayList<>(map.values());
        Collections.sort(val, new Comparator<BYUITweet>() {
            @Override
            public int compare(BYUITweet o1, BYUITweet o2) {
                return 0;
            }
        });

        BufferedWriter bw = null;
        FileWriter fw = null;

        final String FILENAME = "C:\\Users\\Chad\\IdeaProjects\\Tweet Tweet\\scannTwiter\\Try3.txt";

        try {
            String data = "";

            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
                List<Status> tweets = tweetLoader.getStatus(list.get(i));
                for (Status status : tweets) {


                    data +=  status.getContributors()
                            + "~" + status.getText().trim().replaceAll("\n", "").trim().replaceAll("~", " ")
                            + "~" + status.getCreatedAt()
                            + "~" + status.getCurrentUserRetweetId()
                            + "~" + status.getFavoriteCount()
                            + "~" + status.getGeoLocation()
                            + "~" + status.getId()
                            + "~" + status.getInReplyToScreenName()
                            + "~" + status.getInReplyToUserId()
                            + "~" + status.getInReplyToStatusId()
                            + "~" + status.getLang()
                            + "~" + status.getPlace()
                            + "~" + status.getWithheldInCountries()
                            + "~" + status.isFavorited()
                            + "~" + status.isPossiblySensitive()
                            + "~" + status.isRetweet()
                            + "~" + status.isRetweeted()
                            + "~" + status.isRetweetedByMe()
                            + "~" + status.isTruncated()
                            + "~" + status.getUser().getDescription()
                            + "~" + status.getUser().getFavouritesCount()
                            + "~" + status.getUser().getFriendsCount()
                            + "~" + status.getUser().getFollowersCount()
                            + "~" + status.getUser().getProfileTextColor()
                            + "~" + status.getUser().isGeoEnabled()
                            + "~" + status.getUser().isFollowRequestSent()
                            + "\n" ;
                }
            }

            File file = new File(FILENAME);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // true = append file
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            bw.write(data);

            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }

    }


    public static void display(Map<String, BYUITweet> map, List<String> list) {
        BYUITweet tweet = new BYUITweet();
        List<BYUITweet> val = new ArrayList<>(map.values());
        Collections.sort(val, new Comparator<BYUITweet>() {
            @Override
            public int compare(BYUITweet o1, BYUITweet o2) {
                return 0;
            }
        });

        for (int i = 0; i < list.size(); i++) {
            //System.out.println(list.get(i)) + " ( " + map.get(list.get(i)).getUser().followers + " Followers - " + map.get(list.get(i)).text);
            System.out.println(list.get(i));
            //System.out.println("User :" + );

            try {
                List<Status> tweets = tweetLoader.getStatus(list.get(i));

                for (Status status : tweets) {
                    System.out.println(
                            //status.getContributors()
                            //status.getCreatedAt()
                            //status.getCurrentUserRetweetId() //maybe not worth having , it's a negative 1 for most if not all
                            //status.getFavoriteCount()
                            //status.getGeoLocation() //this is coming out as null // need to work on this more if we want it, known issue that is fixed if radius <100
                            //status.getId()
                            //status.getInReplyToScreenName() //-1 mostly
                            //status.getInReplyToStatusId() //-1 mostly
                            //status.getInReplyToUserId() //-1 mostly
                            //status.getLang()
                            //status.getPlace()  //samething as geolocation\
                            //status.getWithheldInCountries()   //mostly null
                            //status.isFavorited() //not sure how useful this is just a true or false
                            //status.isPossiblySensitive() //another true or false
                            //status.isRetweet()
                            //status.isRetweeted()
                            //status.isRetweetedByMe()
                            //status.isTruncated()
                            //status.getText()
                            //status.isRetweeted()
                            //status.getCurrentUserRetweetId() +
                            //status.getUser().getDescription() //talk to cody about this.
                            //status.getUser().getFavouritesCount()
                            //status.getUser().getFriendsCount()
                            //status.getUser().getProfileTextColor()
                            //status.getUser().isProtected()
                            //status.getUser().isVerified()
                            //status.getUser().isFollowRequestSent()
                            //status.getUser().isGeoEnabled()
                            //status.getUser().isTranslator()
                            status.getText()
                    );

                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }


    }
    // save user tweets ~Cody
    public void saveUser(User user) throws TwitterException {

        for(Status status: tweetLoader.getTimeline(user) ) {
            appendToFile("tweets.txt", status.toString());
        }
    }

    public void appendToFile(String fileName, String content) {
        try {
            // if file is non existant
            File f = new File(fileName);
            if (!f.exists() || f.isDirectory()) {
                f.createNewFile();
            }
            // append to file
            Files.write(Paths.get(fileName), content.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            System.out.println("Error Appending to File error was : " + e);
        }
    }

}