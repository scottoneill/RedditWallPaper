package RedditWallpaper;

import static RedditWallpaper.RequestFactory.client;
import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;

public class GetRequestFactory implements RequestFactory {
    private String userAgent;
    
    public GetRequestFactory(String userAgent) {
        this.userAgent = userAgent;
    }
    
    @Override
    public GetRequest createRequest(String url) throws URISyntaxException {
        return new GetRequest(url);
    }
    
    public HttpResponse executeRequest(GetRequest request) throws HttpException, IOException {
        request.request().setHeader("User-Agent", this.userAgent);
        for (String k : request.getHeaders().keySet()) {
            request.request().setHeader(k, request.getHeaders().get(k));
        }

        return client.execute(request.request());
    }

}
