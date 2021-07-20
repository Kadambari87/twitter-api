package repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import model.UsersDataStore;

public class UsersStoreImpl implements EventStore<UsersDataStore> {
    private final AmazonDynamoDB client;
    private final DynamoDBMapper dynamoDBMapper;

    public UsersStoreImpl(AmazonDynamoDB client) {
        this.client = client;
        this.dynamoDBMapper = new DynamoDBMapper(this.client);
    }

    @Override
    public void save(UsersDataStore object) {
        this.dynamoDBMapper.save(object);
    }

    @Override
    public void delete(UsersDataStore object) {
        this.dynamoDBMapper.delete(object);
    }

    @Override
    public UsersDataStore load(UsersDataStore object) {
        return this.dynamoDBMapper.load(object);
    }
}
