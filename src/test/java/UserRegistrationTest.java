import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import Controller.SocialMediaController;
import Model.Account;
import Util.ConnectionUtil;
import io.javalin.Javalin;

public class UserRegistrationTest {
    SocialMediaController socialMediaController;
    HttpClient webClient;
    ObjectMapper objectMapper;
    Javalin app;

    /**
     * Before every test, reset the database, restart the Javalin app, and create a new webClient and ObjectMapper
     * for interacting locally on the web.
     * @throws InterruptedException
     */
    @Before
    public void setUp() throws InterruptedException {
        ConnectionUtil.resetTestDatabase();
        socialMediaController = new SocialMediaController();
        app = socialMediaController.startAPI();
        webClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
        app.start(8080);
        Thread.sleep(1000);
    }

    @After
    public void tearDown() {
        app.stop();
    }

    /**
     * Sending an http request to POST localhost:8080/register when username does not exist in the system
     * 
     * Expected Response:
     *  Status Code: 200
     *  Response Body: JSON representation of user object
     */
    @Test
    public void registerUserSuccessful() throws IOException, InterruptedException {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/register"))
                .POST(HttpRequest.BodyPublishers.ofString("{" +
                        "\"username\": \"user\", " +
                        "\"password\": \"password\" }"))
                .header("Content-Type", "application/json")
                .build();
                HttpResponse<String> response = webClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
                int status = response.statusCode();
                Assert.assertEquals(200, status);
                
                // Deserialize response to Account object
                Account actualAccount = objectMapper.readValue(response.body(), Account.class);
                
                // Validate the response fields
                Assert.assertEquals("user", actualAccount.getUsername());
                Assert.assertEquals("password", actualAccount.getPassword());
                Assert.assertTrue(actualAccount.getId() > 0); // Ensure id is valid
                

    }


     /**
     * Sending an http request to POST localhost:8080/register when username already exists in system
     * 
     * Expected Response:
     *  Status Code: 400
     *  Response Body: 
     */
    @Test
    public void registerUserDuplicateUsername() throws IOException, InterruptedException {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/register"))
                .POST(HttpRequest.BodyPublishers.ofString("{" +
                        "\"username\": \"user\", " +
                        "\"password\": \"password\" }"))
                .header("Content-Type", "application/json")
                .build();
    
        // First request (should succeed)
        HttpResponse<String> response1 = webClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(200, response1.statusCode());
    
        // Second request (should fail)
        HttpResponse<String> response2 = webClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(400, response2.statusCode());
    
        // Validate response body (check if empty or contains a specific message)
        System.out.println("Response Body for duplicate username: " + response2.body());
        Assert.assertEquals("Username already exists", response2.body());
    }
    

    /**
     * Sending an http request to POST localhost:8080/register when no username provided
     * 
     * Expected Response:
     *  Status Code: 400
     *  Response Body: 
     */
    @Test
    public void registerUserUsernameBlank() throws IOException, InterruptedException {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/register"))
                .POST(HttpRequest.BodyPublishers.ofString("{" +
                        "\"username\": \"\", " +
                        "\"password\": \"password\" }"))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assert.assertEquals(400, status);
        Assert.assertEquals("", response.body().toString());

    }


    /**
     * Sending an http request to POST localhost:8080/register when no password is less than 4 characters
     * 
     * Expected Response:
     *  Status Code: 400
     *  Response Body: 
     */
    @Test
    public void registeUserPasswordLengthLessThanFour() throws IOException, InterruptedException {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/register"))
                .POST(HttpRequest.BodyPublishers.ofString("{" +
                        "\"username\": \"username\", " +
                        "\"password\": \"pas\" }"))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assert.assertEquals(400, status);
        Assert.assertEquals("", response.body().toString());

    }
}
