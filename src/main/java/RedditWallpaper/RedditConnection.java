package RedditWallpaper;

import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthData;
import net.dean.jraw.http.oauth.OAuthException;
import net.dean.jraw.RedditClient;
import java.util.Properties;

public class RedditConnection {
    private RedditClient redditClient;
    private UserAgent myUserAgent;
    private Credentials cred;
    private Properties props;
    
    public RedditConnection(Properties props) {
        this.props = props;
        
        this.myUserAgent = UserAgent.of(
                props.getProperty("TARGET_PLATFORM"),
                props.getProperty("UNIQUE_ID"),
                props.getProperty("APP_VERSION"),
                props.getProperty("REDDIT_USERNAME"));
        
        this.redditClient = new RedditClient(myUserAgent);
    }
    
    public RedditClient getRedditClient() {
        return this.redditClient;
    }
    
    public void authenticate() {
        this.cred = Credentials.script(
                props.getProperty("REDDIT_USERNAME"),
                props.getProperty("REDDIT_PASSWORD"),
                props.getProperty("CLIENT_ID"),
                props.getProperty("CLIENT_SECRET"));
        
        OAuthData authData = null;
        try {
            authData = this.redditClient.getOAuthHelper().easyAuth(this.cred);
        } catch (OAuthException ex) {
            System.out.println("Could not validate user: " + ex);
            System.exit(1);
        }
        this.redditClient.authenticate(authData);
    }
    
    
    
}
