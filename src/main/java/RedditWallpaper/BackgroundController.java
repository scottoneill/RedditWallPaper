package RedditWallpaper;

import java.util.Random;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import org.apache.log4j.Logger;

public class BackgroundController {
    private Background background;
    private final Downloader downloader;
    private final OSState state;
    private final Logger logger;
    
    public BackgroundController() {
        this.downloader = new Downloader();
        this.logger = Logger.getLogger(BackgroundController.class);
        
        if (OSUtils.isUnix()) {
            this.state = new LinuxOS();
        } else if (OSUtils.isWindows()) {
            this.state = new WindowsOS();
        } else {
            this.state = null;
        }
    }
    
    public void chooseBackground(Listing<Submission> submissions) {
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
        
        String filePath = generateFilePath(s.getTitle());
        this.logger.info("Chosen image: " + filePath);
        this.downloader.download(s.getUrl(), filePath);
        this.background.setFilePath(filePath);
        this.state.setBackground(filePath);
    }
    
    private String generateFilePath(String name) {
        String sanitisedFilePath = this.sanitiseString(name);
        
        String dir = "./Downloaded";
        return  dir + "\\" + sanitisedFilePath + ".jpg";
    }
    
    private String sanitiseString(String s) {
        s = s.replace("\"", "'");
        s = s.replace("\\", "");
        s = s.replace("/", "");
        s = s.replace("?", "");
        s = s.replace("*", "");
        s = s.replace(":", "");
        s = s.replace("<", "");
        s = s.replace(">", "");
        s = s.replace("|", "");
        
        return s;
    }
}
