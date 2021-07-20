package handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import model.UserResponseModel;
import services.UserServices;

import java.util.Map;

public class LoginToTwitterHandler {

    private static UserServices userServices;

    static {
        userServices = new UserServices();
    }

    public static UserServices getUserService() {
        return userServices;
    }

    public static String handleGetRequest(String userId) {
        UserResponseModel loadedDetails = userServices.loadUserDetails(userId);
        if(loadedDetails == null ||
                loadedDetails.getUserId() == null) {
            return "Invalid user, you are not registered on twitter!";
        }
        return loadedDetails.getUserId() + ":" +
                loadedDetails.getUserFirstName() + ":" +
                loadedDetails.getUserLastName() + ":" +
                loadedDetails.getEmail();
    }

    public static String handlePostRequest(Map<String, String> userInfo) {
        return userServices.createUser(userInfo.get("userId"),
                userInfo.get("firstName"),
                userInfo.get("lastName"),
                userInfo.get("email"),
                userInfo.get("password"));
    }

}
