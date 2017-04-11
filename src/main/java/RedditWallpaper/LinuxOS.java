package RedditWallpaper;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LinuxOS implements OSState {
    
    @Override
    public void setBackground(String filePath) {
        String fullPath = "File://" + filePath;
        
        String[] args = new String[] {
            "/bin/bash",
            "-c",
            "gsettings",
            "org.cinnamon.desktop.background",
            "picture-uri",
            "fullPath"
        };
        
        try {
            Process proc = new ProcessBuilder(args).start();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
    }
    
    
}
