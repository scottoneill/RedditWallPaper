package RedditWallpaper;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

public interface Request {
    public void addHeader(String name, String value);
    public void addParams(String name, String value);
    public HashMap<String, String> getHeaders();
    public ArrayList<NameValuePair> getParams();
}
