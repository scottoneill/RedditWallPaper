package RedditWallpaper;

public class Background {
    private static Background instance = null;
    private String filePath;
    
    private Background() {
        filePath = null;
    }
    
    public static Background getInstance() {
        if (instance == null) {
            instance = new Background();
        }
        return instance;
    }
    
    public String getFilePath() {
        return this.filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
