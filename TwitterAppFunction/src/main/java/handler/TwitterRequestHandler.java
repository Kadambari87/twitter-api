package handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.sun.org.apache.xpath.internal.operations.Bool;
import model.TwitterResponseModel;
import model.UserResponseModel;
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
            if(isUserValid(userId)) {
                responseModel = twitterServices.getMyTweets(userId);
            } else {
                return "Invalid user! Cannot load tweets!";
            }
        }
        return ModelHelper.transformModelToMessages(responseModel);
    }

    public static Boolean isUserValid(String userId) {
        UserResponseModel loadedDetails = LoginToTwitterHandler.getUserService().loadUserDetails(userId);
        if(loadedDetails != null && loadedDetails.getUserId().equalsIgnoreCase(userId)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public static String handlePostRequest(APIGatewayProxyRequestEvent input) {
        String userId = input.getPathParameters().get("userId");
        String message = input.getBody();
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(message)
        && isUserValid(userId)) {
            if(StringUtils.length(message) < 160) {
                return twitterServices.createTweet(userId, message);
            } else {
                return "Tweet message cannot be more than 160 characters";
            }
        } else {
            return "Either user id invalid or tweet created is blank!";
        }
    }
}
