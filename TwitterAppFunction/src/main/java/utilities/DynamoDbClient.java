package utilities;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class DynamoDbClient {

    private static AmazonDynamoDB amazonDynamoDBStandardClient;

    public static AmazonDynamoDB getStandardClient() {
        return amazonDynamoDBStandardClient;
    }

    private static AmazonDynamoDB create() {
        AmazonDynamoDBClientBuilder amazonDynamoDBClientBuilder = AmazonDynamoDBClientBuilder.standard();
        amazonDynamoDBClientBuilder.withRegion(Regions.EU_WEST_1);
        return amazonDynamoDBClientBuilder.build();
    }

    static {
        amazonDynamoDBStandardClient = create();
    }

}
