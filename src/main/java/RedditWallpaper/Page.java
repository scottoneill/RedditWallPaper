package RedditWallpaper;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;


public class Page {
    private ArrayList<Submission> content;
    
    public Page() {
        this.content = new ArrayList<>();
    }
    
    public void updateContent(JsonObject json) {
        JsonObject data1 = json.get("data").getAsJsonObject();
        JsonArray contentArray = data1.getAsJsonArray("children");
        
        
        JsonObject index;
        JsonObject data;
        String URL;
        String title;
        String postHint;
        for (int i = 0; i < contentArray.size(); i++) {
            index = contentArray.get(i).getAsJsonObject();
            data = index.get("data").getAsJsonObject();
            URL = data.get("url").getAsString();
            title = data.get("title").getAsString();
            postHint = data.get("post_hint").getAsString();
            this.content.add(new Submission(title, URL, postHint));
        }
    }
    
    public ArrayList getPosts() {
        return this.content;
    }
}