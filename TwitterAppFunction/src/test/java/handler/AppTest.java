package handler;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import model.TwitterResponseModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import services.TwitterServices;

import java.util.*;

import static org.junit.Assert.*;

public class AppTest {

  @Mock
  private Context context;
  @Mock
  private TwitterServices twitterServices;
  private App app;
  private APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    app = new App();
  }

  @Test
  public void successfulResponseForAllTweets() {
    apiGatewayProxyRequestEvent = new APIGatewayProxyRequestEvent();
    apiGatewayProxyRequestEvent.setResource("/tweets/");
    apiGatewayProxyRequestEvent.setHttpMethod("GET");
    apiGatewayProxyRequestEvent.setResource("/tweets");
    String[] userIds = new String[2];
    userIds[0] = "1234";
    userIds[1] = "9000";
    when(twitterServices.getAllTweets()).thenReturn(expectedTweets(userIds));
    APIGatewayProxyResponseEvent result = app.handleRequest(apiGatewayProxyRequestEvent, context);
    assertEquals(result.getStatusCode().intValue(), 200);
    assertEquals(result.getHeaders().get("Content-Type"), "application/json");
    String content = result.getBody();
    assertNotNull(content);
    assertTrue(content.contains("userid:9000"));
    assertTrue(content.contains("userid:1234"));
  }

  @Test
  public void successfulResponseForTweetOfAUser() {
    App app = new App();
    apiGatewayProxyRequestEvent = new APIGatewayProxyRequestEvent();
    apiGatewayProxyRequestEvent.setHttpMethod("GET");
    apiGatewayProxyRequestEvent.setResource("/tweets/{userId}");
    Map<String, String> pathParameters = new HashMap<>();
    pathParameters.put("userId", "9000");
    apiGatewayProxyRequestEvent.setPathParameters(pathParameters);
    String[] userIds = new String[2];
    userIds[0] = "9000";
    when(twitterServices.getMyTweets("9000")).thenReturn(expectedTweets(userIds));
    APIGatewayProxyResponseEvent result = app.handleRequest(apiGatewayProxyRequestEvent, context);
    assertEquals(result.getStatusCode().intValue(), 200);
    assertEquals(result.getHeaders().get("Content-Type"), "application/json");
    String content = result.getBody();
    assertNotNull(content);
    assertTrue(content.contains("userid:9000"));
  }

  @Test
  public void successfulResponseWhenTweetIsCreatedForAUser() {
    apiGatewayProxyRequestEvent = new APIGatewayProxyRequestEvent();
    String message = "message:This is being unit tested from twitter api!";
    apiGatewayProxyRequestEvent.setHttpMethod("POST");
    apiGatewayProxyRequestEvent.setResource("/tweets/create/{userId}");
    Map<String, String> pathParameters = new HashMap<>();
    pathParameters.put("userId", "9000");
    apiGatewayProxyRequestEvent.setPathParameters(pathParameters);
    apiGatewayProxyRequestEvent.setBody(message);
    String[] userid = new String[1];
    userid[0] = "9000";
    when(twitterServices.createTweet("9000", apiGatewayProxyRequestEvent.getBody())).thenReturn("Tweet is -> " + message);
    APIGatewayProxyResponseEvent result = app.handleRequest(apiGatewayProxyRequestEvent, context);
    assertEquals(result.getStatusCode().intValue(), 200);
    assertEquals(result.getHeaders().get("Content-Type"), "application/json");
    String content = result.getBody();
    assertNotNull(content);
    assertEquals(content, "Tweet is -> " + message);
  }

  private List<TwitterResponseModel> expectedTweets(String[] userIds) {
    List<TwitterResponseModel> responseModels = new ArrayList<>();
    for(String id: userIds) {
      TwitterResponseModel responseModel = new TwitterResponseModel();
      responseModel.setPostedDate(new Date());
      responseModel.setUserid(id);
      responseModel.setMessage("Tweeted from unit test");
      responseModels.add(responseModel);
    }
    return responseModels;
  }
}
