package RedditWallpaper;

public class Submission {
    private final String title;
    private final String url;
    private final String postHint;
    
    public Submission(String title, String url, String postHint) {
        this.title = title;
        this.url = url;
        this.postHint = postHint;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getURL() {
        return this.url;
    }
    
    public String getPostHint() {
        return this.postHint;
    }
}