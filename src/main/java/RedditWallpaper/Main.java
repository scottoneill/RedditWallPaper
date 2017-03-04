package RedditWallpaper;

import java.util.*;
import net.dean.jraw.paginators.Paginator;
import net.dean.jraw.http.NetworkException;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthData;
import net.dean.jraw.http.oauth.OAuthException;
import net.dean.jraw.paginators.SubredditPaginator;
import net.dean.jraw.*;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import org.apache.commons.io.*;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.UINT_PTR;
import com.sun.jna.win32.*;
import com.sun.jna.win32.W32APIOptions;




public class Main {
    
    public static void main(String[] args) throws NetworkException {
        
        UseProperties useProperties = new UseProperties();
        
        useProperties.loadConfigFile("src/main/resources/config.properties");
        
        Properties prop = useProperties.getProperties();        
        
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
            System.out.println("Could not valid user: " + ex);
            System.exit(1);
        }
        
        redditClient.authenticate(authData);
        
        System.out.println(redditClient.me());
        
        SubredditPaginator earthPorn = new SubredditPaginator(redditClient, "EarthPorn");
        
        Listing<Submission> submissions = earthPorn.next();
               
        Random random = new Random();
        
        boolean isImage = false;
        int number;
        Submission s = null;
        
        while (!isImage) {
            number = random.nextInt(submissions.size()-1);
            s = submissions.get(number);
            isImage = s.getPostHint().equals(Submission.PostHint.IMAGE);
        }
        System.out.println("Chosen: " + s.getTitle());

        String path = getFilePath(s);

        downloadFile(path, s.getUrl());

        SPI.INSTANCE.SystemParametersInfo(
            new UINT_PTR(SPI.SPI_SETDESKWALLPAPER),
            new UINT_PTR(0),
            path,
            new UINT_PTR(SPI.SPIF_UPDATEINIFILE | SPI.SPIF_SENDWININICHANGE));
    }

    
    public interface SPI extends StdCallLibrary {

        //MSDN article
        long SPI_SETDESKWALLPAPER = 20;
        long SPIF_UPDATEINIFILE = 0x01;
        long SPIF_SENDWININICHANGE = 0x02;


        SPI INSTANCE = (SPI) Native.loadLibrary("user32", SPI.class, W32APIOptions.DEFAULT_OPTIONS);

        boolean SystemParametersInfo(
            UINT_PTR uiAction,
            UINT_PTR uiParam,
            String pvParam,
            UINT_PTR fWinIni        
        );
    }
    
    public static String getFilePath(Submission s) {
        String dir = "C:\\Users\\Scott\\Desktop\\Earth Porn";
        String path = dir + "\\" + s.getTitle() + ".jpg";
        
        return path;
    } 
    
    public static void downloadFile(String filePath, String downloadURL) {
        File file = new File(filePath);
        URL url = null;
        
        try {
            url = new URL(downloadURL);
        } catch (MalformedURLException ex) {
            System.out.println("Malformed URL: " + ex);
        }

        try {
            FileUtils.copyURLToFile(url, file, 1000, 1000);
        } catch (IOException ex) {
            System.out.println("IO Exception: " + ex);
        }
    }
}
