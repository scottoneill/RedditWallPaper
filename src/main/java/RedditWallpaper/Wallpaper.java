package RedditWallpaper;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class Wallpaper {
    private Submission chosen;
    private final Logger logger;
    private String filePath;
    
    public Wallpaper() {
        this.chosen = null;
        this.logger = Logger.getLogger(Wallpaper.class);
        this.filePath = null;
    }
    
    public String getFilePath() {
        return this.filePath;
    }
    
    public String getURL() {
        return this.chosen.getUrl();
    }
    
    private void setFilePath() {
        String correctFilePath = this.sanitiseString(this.chosen.getTitle());
        
        String dir = "C:\\Applications\\RedditWallpaper\\Earth Porn";
        this.filePath =  dir + "\\" + correctFilePath + ".jpg";
    }
    
    public void chooseWallpaper(Listing<Submission> submissions) {
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
        this.chosen = s;
    }
    
    public void downloadFile() {
        this.setFilePath();
        File file = new File(this.getFilePath());
        URL url = null;
        
        try {
            url = new URL(this.getURL());
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
    
    private String sanitiseString(String unSanit) {
        String sanitString = unSanit;
        
        sanitString = sanitString.replace("\"", "'");
        sanitString = sanitString.replace("\\", "");
        sanitString = sanitString.replace("/", "");
        sanitString = sanitString.replace("?", "");
        sanitString = sanitString.replace("*", "");
        sanitString = sanitString.replace(":", "");
        sanitString = sanitString.replace("<", "");
        sanitString = sanitString.replace(">", "");
        sanitString = sanitString.replace("|", "");
        
        return sanitString;
    }
}
