package handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Methods", "OPTIONS,POST,GET");
        headers.put("Access-Control-Allow-Headers", "Content-Type");
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        System.out.println();
        String responseBody = "";
        if( input != null) {
            String requestedHttpMethod = input.getHttpMethod();
            switch (requestedHttpMethod.toUpperCase()) {
                case "GET":
                    responseBody = TwitterRequestHandler.handleGetRequest(input);
                    break;
                case "POST":
                    responseBody = TwitterRequestHandler.handlePostRequest(input);
                    break;
                default:
                    responseBody = "Invalid request made!";
                    break;
            }
        }
        try {
            return response
                    .withStatusCode(200)
                    .withBody(responseBody);
        } catch (Exception e) {
            return response
                    .withBody("{}")
                    .withStatusCode(500);
        }
    }
}
