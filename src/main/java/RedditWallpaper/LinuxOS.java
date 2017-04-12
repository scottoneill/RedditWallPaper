package RedditWallpaper;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class LinuxOS implements OSState {
    
    @Override
    public void setBackground(String filePath) {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        s = "file://" + s;
        filePath = "\"" + s + filePath.substring(1, filePath.length()) + "\"";
        String[] args = new String[] {
            "gsettings",
            "set",
            "org.cinnamon.desktop.background",
            "picture-uri",
            filePath
        };
        
        try {
            Process proc = new ProcessBuilder(args).start();
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    
}
