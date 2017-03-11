package RedditWallpaper;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import java.util.Random;
import org.apache.commons.io.FileUtils;

public class Wallpaper {
    private Submission chosen;
    
    public Wallpaper() {
        this.chosen = null;
    }
    
    public String getFilePath() {
        String dir = "C:\\Applications\\RedditWallpaper\\Earth Porn";
        return dir + "\\" + chosen.getTitle() + ".jpg";
    }
    
    public String getURL() {
        return chosen.getUrl();
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
        File file = new File(this.getFilePath());
        URL url = null;
        
        try {
            url = new URL(this.getURL());
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
