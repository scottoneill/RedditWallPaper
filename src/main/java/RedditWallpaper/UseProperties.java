package RedditWallpaper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class UseProperties {
    private Properties prop;
    private String configFile;
    private InputStream input;
    
    
    public UseProperties(String configFile) {
        this.prop = new Properties();
    }
    
    public String getConfigFile() {
        return this.configFile;
    }
    
    public Properties getProperties() {
        return this.prop;
    }
    
    public void loadConfigFile(String ConfigFile) {
        this.configFile = configFile;
        input = null;
        
        try {
            input = new FileInputStream(configFile);
            this.prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void closeProperties() {
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
