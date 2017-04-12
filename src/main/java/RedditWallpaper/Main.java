package RedditWallpaper;


import org.apache.log4j.Logger;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;

import com.google.common.net.HttpHeaders;
import com.google.gson.JsonObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Properties;

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
        String auth = prop.getProperty("CLIENT_ID") + ":" + prop.getProperty("CLIENT_SECRET");
        byte[] encodedAuth = Base64.encodeBase64(
                        auth.getBytes(Charset.forName("ISO-8859-1")));
        String authHeader = "Basic " + new String(encodedAuth);
        
        postRequest.addHeader(HttpHeaders.AUTHORIZATION, authHeader);
        postRequest.addParams("username", prop.getProperty("REDDIT_USERNAME"));
        postRequest.addParams("password", prop.getProperty("REDDIT_PASSWORD"));
        postRequest.addParams("grant_type", "password");
        postRequest.addParams("duration", "permanent");
        
        HttpResponse response = postFactory.executeRequest(postRequest);
        

       
        // Status code
        System.out.println("\nSending POST request to URL : " + url);
	System.out.println("Response Code : " +
                       response.getStatusLine().getStatusCode());

        ResponseParser parser = new ResponseParser(response);
        parser.parse();
        System.out.println(parser.prettyPrintString());
        
        // GET REQUEST
        String requestURL = "https://oauth.reddit.com";
        String APIRequest = "/r/earthporn/hot";
        
        GetRequestFactory getFactory = new GetRequestFactory(userAgent);
        GetRequest getRequest = getFactory.createRequest(requestURL + APIRequest);
        
        // JSON Parser
        JsonObject jsonObject = parser.toJson();
        String accessToken = jsonObject.get("access_token").getAsString();
        String tokenType = jsonObject.get("token_type").getAsString();

        getRequest.addHeader("Authorization", tokenType + " " + accessToken);
        
        response = getFactory.executeRequest(getRequest);
          
        // Status Code
        System.out.println("\nSending GET request to URL : " + requestURL + APIRequest);
	System.out.println("Response Code : " +
                                response.getStatusLine().getStatusCode());
        

        
        parser.setResponse(response);
        parser.parse();
        JsonObject content = parser.toJson();

        Page page = new Page();
        page.updateContent(content);
        
        ArrayList<Submission> posts = page.getPosts();
        BackgroundController controller = new BackgroundController();
        
        int hours;
        int minutes;
        int seconds;
        
        if (args.length != 0 && args[0].equals("testing")) {
            hours = 0;
            minutes = 0;
            seconds = 10;
        } else {
            hours = 0;
            minutes = 30;
            seconds = 0;
        }
        long waitTime = getTimeinMS(hours, minutes, seconds);
        
        int count = 0;
        while (true) {         
            controller.chooseBackground(posts);
            
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException ex) {
                logger.error(ex);
                Thread.currentThread().interrupt();
            }
            
            if (count == 5) {
//                submissions = earthPorn.next();
//                count = 0;
                break;
            } else {
                count++;
            }
        }
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
