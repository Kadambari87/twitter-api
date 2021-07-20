package repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import model.TwitterDataStore;
import model.TwitterResponseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventStoreImpl implements EventStore<TwitterDataStore> {
    private final AmazonDynamoDB client;
    private final DynamoDBMapper dynamoDBMapper;
    private final TwitterDataStore.DateConverter converter = new TwitterDataStore.DateConverter();

    public EventStoreImpl(AmazonDynamoDB client) {
        this.client = client;
        this.dynamoDBMapper = new DynamoDBMapper(this.client);
    }

    @Override
    public void save(TwitterDataStore object) {
        this.dynamoDBMapper.save(object);
    }

    @Override
    public void delete(TwitterDataStore object) {
        this.dynamoDBMapper.delete(object);
    }

    @Override
    public TwitterDataStore load(TwitterDataStore object) {
        return this.dynamoDBMapper.load(object);
    }

    /**
     * Loads tweets of a specific user
     * @param object storing details of requested user
     * @return list of tweets of a user
     */
    public List<TwitterResponseModel> loadMyTweets(TwitterDataStore object) {
        Map<String, AttributeValue> expressionAttributeValues =
                new HashMap<String, AttributeValue>();
        expressionAttributeValues.put(":userIdFromRequest",
                new AttributeValue().withS(object.getUserId()));
        ScanRequest scanRequest = new ScanRequest().withTableName("twitter_feeds")
                .withFilterExpression("userid = :userIdFromRequest")
                .withExpressionAttributeValues(expressionAttributeValues);
        List<TwitterResponseModel> tweetsListForAUser = new ArrayList<>();
        ScanResult result = client.scan(scanRequest);
        for (Map<String, AttributeValue> item : result.getItems()) {
            TwitterResponseModel twitterResponseModel = new TwitterResponseModel();
            twitterResponseModel.setMessage(item.get("message").getS());
            twitterResponseModel.setUserid(item.get("userid").getS());
            twitterResponseModel.setPostedDate(converter.unconvert(Long.parseLong(item.get("posted-date").getN())));
            tweetsListForAUser.add(twitterResponseModel);
        }
        return tweetsListForAUser;
    }

    /**
     * Loads all tweets from users
     * @return list of all tweets
     */
    public List<TwitterResponseModel> getAllTweets() {
        List<TwitterResponseModel> tweetsListForAUser = new ArrayList<>();
        ScanRequest scanRequest = new ScanRequest().withTableName("twitter_feeds");
        ScanResult result = client.scan(scanRequest);
        for (Map<String, AttributeValue> item : result.getItems()) {
            TwitterResponseModel twitterResponseModel = new TwitterResponseModel();
            twitterResponseModel.setMessage(item.get("message").getS());
            twitterResponseModel.setUserid(item.get("userid").getS());
            twitterResponseModel.setPostedDate(converter.unconvert(Long.parseLong(item.get("posted-date").getN())));
            tweetsListForAUser.add(twitterResponseModel);
        }
        return tweetsListForAUser;
    }
}
