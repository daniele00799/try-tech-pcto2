import java.io.*;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

class Chatbot {
    private String fileName;
    private List<String> storico;

    public Chatbot(String fileName, HttpClient client) throws IOException {
        this.fileName = fileName;
        this.storico = new ArrayList<>();

        // Carica lo storico dalla memoria (file) se esiste
        caricaStorico();
    }

    // Metodo per simulare la conversazione con il chatbot
    public String chat(String userInput) throws IOException {
        // Risposta simulata (puoi integrarla con un'API reale)
        String risposta = "Risposta simulata per: " + userInput;

        // Aggiungi la conversazione allo storico
        storico.add("Utente: " + userInput);
        storico.add("Modello: " + risposta);

        // Salva lo storico su file ogni volta che viene aggiunto un messaggio
        salvaStorico();

        return risposta;
    }

    // Metodo per stampare lo storico della chat
    public void stampaStorico() {
        if (storico.isEmpty()) {
            System.out.println("Non ci sono messaggi nello storico.");
        } else {
            for (String entry : storico) {
                System.out.println(entry);
            }
        }
    }

    // Metodo per caricare lo storico da un file
    private void caricaStorico() throws IOException {
        File file = new File(fileName);

        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                storico.add(line);  // Aggiungi ogni linea (messaggio) allo storico
            }
            reader.close();
        }
    }

    // Metodo per salvare lo storico in un file
    private void salvaStorico() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        // Scrivi ogni elemento dello storico nel file
        for (String entry : storico) {
            writer.write(entry);
            writer.newLine();
        }
        writer.close();
    }
}


