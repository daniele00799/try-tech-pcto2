import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


public class GroqService {
    private final String apiKey;
    private final HttpClient client;

    public GroqService(String apiKey, HttpClient client) {
        this.apiKey = "Bearer " + apiKey;
        this.client = client;
    }

    public String sendMessage(List<String> messages) throws IOException, InterruptedException {
        StringBuilder requestBody = new StringBuilder();
        requestBody.append("{\"model\": \"llama3-70b-8192\", \"messages\": [");
        for (int i = 0; i < messages.size(); i++) {
            requestBody.append(messages.get(i));
            if (i < messages.size() - 1) requestBody.append(",");
        }
        requestBody.append("]}");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.groq.com/openai/v1/chat/completions")).build();
        return "";
    }
    }