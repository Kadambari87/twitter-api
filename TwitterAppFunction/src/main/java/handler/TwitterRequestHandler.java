package handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import model.TwitterResponseModel;
import org.apache.commons.lang3.StringUtils;
import services.TwitterServices;
import utilities.ModelHelper;

import java.util.List;

public class TwitterRequestHandler {

    private static TwitterServices twitterServices;

    static {
        twitterServices = new TwitterServices();
    }

    public static String handleGetRequest(APIGatewayProxyRequestEvent input) {
        List<TwitterResponseModel> responseModel;
        if (input.getPathParameters() == null ||
                input.getPathParameters().isEmpty()) {
            responseModel = twitterServices.getAllTweets();
        } else {
            String userId = input.getPathParameters().get("userId");
            responseModel =  twitterServices.getMyTweets(userId);
        }
        return ModelHelper.transformModelToMessages(responseModel);
    }

    public static String handlePostRequest(APIGatewayProxyRequestEvent input) {
        String userId = input.getPathParameters().get("userId");
        String message = input.getBody();
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(message)) {
            if(StringUtils.length(message) < 160) {
                return twitterServices.createTweet(userId, message);
            } else {
                return "Tweet message cannot be more than 160 characters";
            }
        } else {
            return "Either user id or tweet created is blank!";
        }
    }
}
