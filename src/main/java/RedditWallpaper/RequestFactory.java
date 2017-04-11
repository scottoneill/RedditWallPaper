package RedditWallpaper;

import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;

public interface RequestFactory {
    public static final DefaultHttpClient client = new DefaultHttpClient();
    
    public Request createRequest(String url) throws URISyntaxException;
}
