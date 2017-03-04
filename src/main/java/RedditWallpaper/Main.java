package RedditWallpaper;

import java.util.*;
import net.dean.jraw.http.NetworkException;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthData;
import net.dean.jraw.http.oauth.OAuthException;
import net.dean.jraw.paginators.SubredditPaginator;
import net.dean.jraw.*;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import com.sun.jna.platform.win32.WinDef.UINT_PTR;

public class Main {
    
    public static void main(String[] args) {
        
        String configFile = "src/main/resources/config.properties";
        Properties prop = getConfigProperties(configFile);
        
        UserAgent myUserAgent = UserAgent.of(
                prop.getProperty("TARGET_PLATFORM"),
                prop.getProperty("UNIQUE_ID"),
                prop.getProperty("APP_VERSION"),
                prop.getProperty("REDDIT_USERNAME"));
                
        RedditClient redditClient = new RedditClient(myUserAgent);
        Credentials cred = Credentials.script(
                prop.getProperty("REDDIT_USERNAME"),
                prop.getProperty("REDDIT_PASSWORD"),
                prop.getProperty("CLIENT_ID"),
                prop.getProperty("CLIENT_SECRET"));
        
        OAuthData authData = null;
        try {
            authData = redditClient.getOAuthHelper().easyAuth(cred);
        } catch (OAuthException ex) {
            System.out.println("Could not validate user: " + ex);
            System.exit(1);
        }
        redditClient.authenticate(authData);
        
        System.out.println(redditClient.me());
        
        SubredditPaginator earthPorn = new SubredditPaginator(redditClient, "EarthPorn");
        Listing<Submission> submissions = earthPorn.next();
        
        Wallpaper w = new Wallpaper();
        w.chooseWallpaper(submissions);
        
        w.downloadFile();

        SPI.INSTANCE.SystemParametersInfo(
            new UINT_PTR(SPI.SPI_SETDESKWALLPAPER),
            new UINT_PTR(0),
            w.getFilePath(),
            new UINT_PTR(SPI.SPIF_UPDATEINIFILE | SPI.SPIF_SENDWININICHANGE));
    }
    
    public static Properties getConfigProperties(String configFilePath) {
        UseProperties useProperties = new UseProperties();
        useProperties.loadConfigFile(configFilePath);
        
        return useProperties.getProperties();
    }
}
