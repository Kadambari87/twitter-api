package services;

import model.TwitterDataStore;
import model.TwitterResponseModel;
import repository.EventStoreImpl;
import utilities.DynamoDbClient;

import java.util.Date;
import java.util.List;

public class TwitterServices {

    private static EventStoreImpl twitterEventStore;

    static {
        twitterEventStore = new EventStoreImpl(DynamoDbClient.getStandardClient());
    }

    public String createTweet(String userId, String tweetMessage) {
        TwitterDataStore twitterDataStore = new TwitterDataStore();
        twitterDataStore.setTwitterMessage(tweetMessage);
        twitterDataStore.setUserId(userId);
        twitterDataStore.setPostedDate(new Date());
        twitterEventStore.save(twitterDataStore);
        return "Tweet is -> " + tweetMessage;
    }

    public List<TwitterResponseModel> getMyTweets(String userId) {
        TwitterDataStore twitterDataStore = new TwitterDataStore();
        twitterDataStore.setUserId(userId);
        return twitterEventStore.loadMyTweets(twitterDataStore);
    }

    public List<TwitterResponseModel> getAllTweets() {
        return twitterEventStore.getAllTweets();
    }
}
