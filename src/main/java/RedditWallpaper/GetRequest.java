package RedditWallpaper;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class GetRequest implements Request {
    private HttpGet get;
    private HashMap<String, String> headers;
    private ArrayList<NameValuePair> urlParameters;
    
    public GetRequest(String url) throws URISyntaxException {
        this.get = new HttpGet(url);
        this.headers = new HashMap<String, String>();
        this.urlParameters = new ArrayList<NameValuePair>();
    }
    
    public HttpGet request() {
        return this.get;
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
