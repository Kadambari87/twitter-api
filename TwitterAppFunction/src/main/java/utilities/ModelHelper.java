package utilities;

import model.TwitterResponseModel;

import java.util.List;

public class ModelHelper {

    public static String transformModelToMessages(List<TwitterResponseModel> twitterResponseModelList) {
        int len = twitterResponseModelList.size();
        String allTweets = "";
        for(int i=0; i<len; i++) {
            allTweets = allTweets + "message:" + twitterResponseModelList.get(i).getMessage() + ","
            + "userid:" + twitterResponseModelList.get(i).getUserid() + ","
            + "posted-date:" + twitterResponseModelList.get(i).getPostedDate();
        }
        return allTweets;
    }
}
