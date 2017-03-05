package RedditWallpaper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.lang.ClassLoader;


public class UseProperties {
    private Properties prop;
    private String configFile;
    private InputStream input;
    
    
    public UseProperties() {
        this.prop = new Properties();
        this.configFile = null;
        this.input = null;
    }
    
    public String getConfigFile() {
        return this.configFile;
    }
    
    public Properties getProperties() {
        return this.prop;
    }
    
    public void loadConfigFile(String configFile) {
        this.configFile = configFile;
        
        if (this.configFile == null) {
            System.out.println("prop is null");
            return;
        }
        try {
            this.input = UseProperties.class.getClassLoader().getResourceAsStream(this.configFile);
            this.prop.load(this.input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void closeProperties() {
        if (this.input != null) {
            try {
                this.input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
