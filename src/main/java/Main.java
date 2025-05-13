/*
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ChatBot chatBot = new ChatBot("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");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Chat con memoria. Scrivi 'esci' per uscire.");

        while (true) {
            System.out.print("\nTu: ");
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("esci")) {
                System.out.println("Chat terminata.");
                break;
            }

            String risposta = chatBot.chat(userInput);
            System.out.println("Modello: " + risposta);
        }
    }
}

class ChatBot {
    private final String apiKey;
    private final HttpClient client;
    private final List<String> memory;

    public ChatBot(String apiKey) {
        this.apiKey = "Bearer " + apiKey;
        this.client = HttpClient.newHttpClient();
        this.memory = new ArrayList<>();
    }

    public String chat(String userInput) throws IOException, InterruptedException {
        memory.add("{\"role\": \"user\", \"content\": \"" + escape(userInput) + "\"}");

        StringBuilder requestBody = new StringBuilder();
        requestBody.append("{\"model\": \"llama-3.3-70b-versatile\", \"messages\": [");
        for (int i = 0; i < memory.size(); i++) {
            requestBody.append(memory.get(i));
            if (i < memory.size() - 1) requestBody.append(",");
        }
        requestBody.append("]}");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.groq.com/openai/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String content = extractContent(response.body());

        if (content != null) {
            memory.add("{\"role\": \"assistant\", \"content\": \"" + escape(content) + "\"}");
            return content;
        } else {
            return "Nessuna risposta trovata.";
        }
    }

    private String extractContent(String json) {
        String marker = "\"content\":\"";
        int start = json.indexOf(marker);
        if (start == -1) return null;
        start += marker.length();
        StringBuilder content = new StringBuilder();
        boolean escaped = false;
        for (int i = start; i < json.length(); i++) {
            char c = json.charAt(i);
            if (escaped) {
                content.append(c);
                escaped = false;
            } else if (c == '\\') {
                escaped = true;
            } else if (c == '"') {
                break;
            } else {
                content.append(c);
            }
        }
        return content.toString().replace("\\n", "\n").replace("\\\"", "\"");
    }

    private String escape(String input) {
        return input.replace("\"", "\\\"").replace("\n", "\\n");
    }
}
*/
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String apiKey = "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";
        HttpClient client = HttpClient.newHttpClient();
        Scanner scanner = new Scanner(System.in);

        List<ChatBot> allSessions = new ArrayList<>();

        System.out.println("Chat con memoria. Scrivi 'esci' per terminare una sessione. Scrivi 'fine' per uscire completamente.");

        while (true) {
            ChatBot chatBot = new ChatBot(apiKey, client);
            allSessions.add(chatBot);

            System.out.println("\n--- Nuova chat ---");

            while (true) {
                chatBot.stampaStorico();
                System.out.print("\nTu: ");
                String userInput = scanner.nextLine();

                if (userInput.equalsIgnoreCase("esci")) {
                    System.out.println("Hai terminato questa sessione. Digita 'fine' per chiudere tutto o premi invio per iniziarne una nuova.");
                    break;
                }

                if (userInput.equalsIgnoreCase("fine")) {
                    System.out.println("Chatbot chiuso.");
                    scanner.close();
                    return;
                }

                String risposta = chatBot.chat(userInput);
                System.out.println("Modello: " + risposta);
            }
        }
    }
}
