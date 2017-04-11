package RedditWallpaper;


import com.google.common.net.HttpHeaders;
import java.util.Properties;
//import net.dean.jraw.paginators.SubredditPaginator;
//import net.dean.jraw.models.Listing;
//import net.dean.jraw.models.Submission;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Base64;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {
    
    public static void main(String[] args) throws Exception {
        
        final Logger logger = Logger.getLogger(Main.class);
        
        String configFile = "config.properties";
        Properties prop = getConfigProperties(configFile);
        
        String url = "https://www.reddit.com/api/v1/access_token";
        String userAgent = "RedditWallpaper v0.1 (by /u/ess_j)";        
        
        PostRequestFactory postFactory = new PostRequestFactory(userAgent);
        PostRequest postRequest = postFactory.createRequest(url);
        
        // setup Basic Authentication
        String auth = "DZVl2XiOzhjmFw" + ":" + "Eoa5W6Gcj5BrA6YDBIkRIpc3xfo";
        byte[] encodedAuth = Base64.encodeBase64(
                        auth.getBytes(Charset.forName("ISO-8859-1")));
        String authHeader = "Basic " + new String(encodedAuth);
        
        postRequest.addHeader(HttpHeaders.AUTHORIZATION, authHeader);
        postRequest.addParams("username", "ess_j");
        postRequest.addParams("password", "terrible");
        postRequest.addParams("grant_type", "password");
        postRequest.addParams("duration", "permanent");
        
        HttpResponse response = postFactory.executeRequest(postRequest);
        

       
        // Status code
        System.out.println("\nSending POST request to URL : " + url);
	System.out.println("Response Code : " +
                       response.getStatusLine().getStatusCode());

        
        
        //JSON printing for testing
        BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
                result.append(line);
        }
        System.out.println(result.toString());
        
        
        
        // GET REQUEST
        String requestURL = "https://oauth.reddit.com";
        String APIRequest = "/api/v1/me";
        
        GetRequestFactory getFactory = new GetRequestFactory(userAgent);
        GetRequest getRequest = getFactory.createRequest(requestURL + APIRequest);
        
        // JSON Parser
        JsonObject jsonObject = new JsonParser().parse(result.toString()).getAsJsonObject();
        String accessToken = jsonObject.get("access_token").getAsString();
        String tokenType = jsonObject.get("token_type").getAsString();

        getRequest.addHeader("Authorization", tokenType + " " + accessToken);
        
        response = getFactory.executeRequest(getRequest);
          
        // Status Code
        System.out.println("\nSending GET request to URL : " + requestURL + APIRequest);
	System.out.println("Response Code : " +
                                response.getStatusLine().getStatusCode());
        
        // JSON Printing
        rd.close();
        rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));
        result.delete(0, result.length());
        line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        String s = result.toString();
        s = s.replaceAll("(?<=[{;,])", "\t\n");
        System.out.println(s);
        
        
//        RedditConnection connection = new RedditConnection(prop);
//                
//        connection.authenticate();
//        System.out.println(connection.getRedditClient().me());
//        
//        SubredditPaginator earthPorn = new SubredditPaginator(connection.getRedditClient(), "EarthPorn");
//        Controller controller = new Controller();
//        
//        Listing<Submission> submissions = earthPorn.next();
//        
//        int hours;
//        int minutes;
//        int seconds;
//        
//        if (args.length != 0 && args[0].equals("testing")) {
//            hours = 0;
//            minutes = 0;
//            seconds = 10;
//        } else {
//            hours = 0;
//            minutes = 30;
//            seconds = 0;
//        }
//        long waitTime = getTimeinMS(hours, minutes, seconds);
//        
//        int count = 0;
//        while (true) {         
//            controller.chooseBackground(submissions);
//            
//            try {
//                Thread.sleep(waitTime);
//            } catch (InterruptedException ex) {
//                logger.error(ex);
//                Thread.currentThread().interrupt();
//            }
//            
//            if (count == 5) {
//                submissions = earthPorn.next();
//                count = 0;
//            } else {
//                count++;
//            }
//        }
    }
    
    public static Properties getConfigProperties(String configFilePath) {
        UseProperties useProperties = new UseProperties();
        useProperties.loadConfigFile(configFilePath);
        
        return useProperties.getProperties();
    }
    
    public static long getTimeinMS(int hours, int minutes, int seconds) {
        long time = 0;
        
        time += 1000 * seconds;
        time += 1000 * 60 * minutes;
        time += 1000 * 60 * 60 * hours;
        
        return time;
    }
}
