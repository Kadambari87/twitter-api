package handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@Ignore
public class AppTest {
  @Test
  public void successfulResponseForAllTweets() {
    App app = new App();
    APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent = new APIGatewayProxyRequestEvent();
    apiGatewayProxyRequestEvent.setHttpMethod("GET");
    apiGatewayProxyRequestEvent.setResource("/tweets/");
    APIGatewayProxyResponseEvent result = app.handleRequest(apiGatewayProxyRequestEvent, null);
    assertEquals(result.getStatusCode().intValue(), 200);
    assertEquals(result.getHeaders().get("Content-Type"), "application/json");
    String content = result.getBody();
    assertNotNull(content);
    assertTrue(content.contains("Tweet is about random checks!"));
  }

  @Test
  public void successfulResponseForTweetOfAUser() {
    App app = new App();
    APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent = new APIGatewayProxyRequestEvent();
    apiGatewayProxyRequestEvent.setHttpMethod("GET");
    apiGatewayProxyRequestEvent.setResource("/tweets/{userId}");
    Map<String, String> pathParameters = new HashMap<>();
    pathParameters.put("userId", "56661");
    apiGatewayProxyRequestEvent.setPathParameters(pathParameters);
    APIGatewayProxyResponseEvent result = app.handleRequest(apiGatewayProxyRequestEvent, null);
    assertEquals(result.getStatusCode().intValue(), 200);
    assertEquals(result.getHeaders().get("Content-Type"), "application/json");
    String content = result.getBody();
    assertNotNull(content);
    assertTrue(content.contains("userid:56661"));
  }

  @Test
  public void successfulResponseWhenTweetIsCreatedForAUser() {
    App app = new App();
    APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent = new APIGatewayProxyRequestEvent();
    apiGatewayProxyRequestEvent.setHttpMethod("POST");
    apiGatewayProxyRequestEvent.setResource("/tweets/create/{userId}");
    Map<String, String> pathParameters = new HashMap<>();
    pathParameters.put("userId", "9000");
    apiGatewayProxyRequestEvent.setPathParameters(pathParameters);
    apiGatewayProxyRequestEvent.setBody("message:This is being unit tested from twitter api!");
    APIGatewayProxyResponseEvent result = app.handleRequest(apiGatewayProxyRequestEvent, null);
    assertEquals(result.getStatusCode().intValue(), 200);
    assertEquals(result.getHeaders().get("Content-Type"), "application/json");
    String content = result.getBody();
    assertNotNull(content);
    System.out.println(content);
    assertEquals(content, "Tweet is -> message:This is being unit tested from twitter api!");
  }
}
