package RedditWallpaper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;

public class ResponseParser {
    private HttpResponse response;
    private BufferedReader reader;
    private StringBuilder result;
    private JsonParser parser;
    private Gson gson;
    
    public ResponseParser(HttpResponse response) throws IOException {
        this.response = response;
        this.reader = new BufferedReader(
                new InputStreamReader(this.response.getEntity().getContent()));
        this.result = new StringBuilder();
        this.parser = new JsonParser();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
    
    public void setResponse(HttpResponse response) throws IOException {
        this.response = response;
        this.reader = new BufferedReader(
                new InputStreamReader(this.response.getEntity().getContent()));
    }
    
    public void parse() {
        if (this.result.length() > 0) {
            this.result.delete(0, this.result.length());
        }
        String line = "";
        try {
            while ((line = this.reader.readLine()) != null) {
                this.result.append(line);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    
    @Override
    public String toString() {
        return this.result.toString();
    }
    
    public JsonObject toJson() {
        return this.parser.parse(this.result.toString()).getAsJsonObject();
    }
    
    public String prettyPrintString() {
        return this.gson.toJson(this.toJson());
    }
    
    public void printToFile(String fileName) throws IOException {
        
        List<String> lines = Arrays.asList(this.prettyPrintString());
        Path file  = Paths.get(fileName);
        Files.write(file, lines, Charset.forName("UTF-8"));
    }
}
