package RedditWallpaper;

public class OSUtils {
    private static String OS = null;
    
    private OSUtils() {
    }
    
    public static String getOSName() {
        if (OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS;
    }
    
    public static boolean isWindows() {
        return getOSName().startsWith("Windows");
    }
    
    public static boolean isUnix() {
        return getOSName().startsWith("Linux");
    }
}
