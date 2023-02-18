package httpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class http {

    // public accessor method for API coms
    public static void touchGPTAPI() throws Exception {
        gptAPI();
    }

    private static Object gptAPI() throws Exception {
        StringBuilder response = new StringBuilder();
        URL url = new URL("https://api.openai.com/v1/completions");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // set request method prop
        connection.setRequestMethod("POST");

        // formatting
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        connection.setRequestProperty("Authorization","Bearer <API_KEY_HERE>");

        // connect ensure
        connection.setDoOutput(true);

        // JSON String -- Nasty ass formatting
        String jsonString = (
                "{\"model\":\"code-davinci-002\"," +    // model to use -- 'code-davinci-002' for code completion
                "\"prompt\":\"Create a hashmap in java\"," +
                "\"max_tokens\":700," +                // max tokens for 'code-davinci-002' = 8000
                "\"temperature\":0.8," +
                "\"top_p\":1," +
                "\"n\":1," +                            // completions to create -- leave at 1
                "\"stream\":false," +
                "\"logprobs\":null," +
                "\"stop\":\"\"}"                     // where API will stop generating sequences
        );

        // convert json string to bytes
        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response);
        }

        return response;
    }
}
