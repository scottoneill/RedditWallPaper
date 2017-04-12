package RedditWallpaper;

import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;

public class PostRequestFactory implements RequestFactory {
    private String userAgent;
    
    public PostRequestFactory(String userAgent) {
        this.userAgent = userAgent;
    }
    
    @Override
    public PostRequest createRequest(String url) throws URISyntaxException {
        return new PostRequest(url);
    }
    
    public HttpResponse executeRequest(PostRequest request) throws HttpException, IOException {
        request.request().setHeader("User-Agent", this.userAgent);
        for (String k : request.getHeaders().keySet()) {
            request.request().setHeader(k, request.getHeaders().get(k));
        }
        request.request().setEntity(new UrlEncodedFormEntity(request.getParams()));

        return client.execute(request.request());
    }
}
