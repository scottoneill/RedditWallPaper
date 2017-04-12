package RedditWallpaper;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

public class PostRequest implements Request {
    private HttpPost post;
    private HashMap<String, String> headers;
    private ArrayList<NameValuePair> urlParameters;
    
    public PostRequest(String url) throws URISyntaxException {
        this.post = new HttpPost(url);
        this.headers = new HashMap<>();
        this.urlParameters = new ArrayList<>();
    }
    
    
    public HttpPost request() {
        return this.post;
    }

    @Override
    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    @Override
    public void addParams(String name, String value) {
        this.urlParameters.add(new BasicNameValuePair(name, value));
    }

    @Override
    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public ArrayList<NameValuePair> getParams() {
        return this.urlParameters;
    }
}
