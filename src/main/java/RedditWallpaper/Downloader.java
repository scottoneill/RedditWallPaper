package RedditWallpaper;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class Downloader {
    private final Logger logger;
    
    public Downloader() {
        this.logger = Logger.getLogger(Downloader.class);
    }
    
    public void download(String URL, String destination) {
        File file = new File(destination);
        URL url = null;
        
        try {
            url = new URL(URL);
        } catch (MalformedURLException ex) {
            this.logger.error(ex);
            System.out.println("Malformed URL: " + ex);
        }

        try {
            FileUtils.copyURLToFile(url, file, 1000, 1000);
        } catch (IOException ex) {
            this.logger.error(ex);
            System.out.println("IO Exception: " + ex);
        }
    }
}
