package RedditWallpaper;

import java.util.Properties;
import net.dean.jraw.paginators.SubredditPaginator;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import com.sun.jna.platform.win32.WinDef.UINT_PTR;
import org.apache.log4j.Logger;

public class Main {
    
    public static void main(String[] args) {
        
        final Logger logger = Logger.getLogger(Main.class);
        
        String configFile = "config.properties";
        Properties prop = getConfigProperties(configFile);
        
        RedditConnection connection = new RedditConnection(prop);
                
        connection.authenticate();
        System.out.println(connection.getRedditClient().me());
        
        SubredditPaginator earthPorn = new SubredditPaginator(connection.getRedditClient(), "EarthPorn");
        Wallpaper w = new Wallpaper();
        
        Listing<Submission> submissions = earthPorn.next();
        
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
            
            w.chooseWallpaper(submissions);
            w.downloadFile();
            SPI.INSTANCE.SystemParametersInfo(
                new UINT_PTR(SPI.SPI_SETDESKWALLPAPER),
                new UINT_PTR(0),
                w.getFilePath(),
                new UINT_PTR(SPI.SPIF_UPDATEINIFILE | SPI.SPIF_SENDWININICHANGE));
            
            logger.info("chosen image: " + w.getFilePath());
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException ex) {
                logger.error(ex);
                Thread.currentThread().interrupt();
            }
            
            if (count == 5) {
                submissions = earthPorn.next();
                count = 0;
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
