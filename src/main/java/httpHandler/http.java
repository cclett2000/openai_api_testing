package httpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import file.fileHandler;
import json.ResponseParser;
import model.code_completion.Root;

public class http {

    // public accessor method for API comms
    public static void touchGPTAPI(String prompt)  {
        gptAPI(prompt);
    }

    // returns API response -- uses custom model, so it's easy to access each attribute
    private static Root gptAPI(String prompt)  {
        StringBuilder response = new StringBuilder();
        Root responseData = null;

        try {
            // set endpoint and open connection
            URL url = new URL("https://api.openai.com/v1/completions");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // set request method property
            connection.setRequestMethod("POST");

            // formatting
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            // to use api key, create a file called 'API_KEY.txt' in 'main' and paste your API key on the very first line
            // "fileHandler" should automatically pick up the file and extract the key.
            connection.setRequestProperty("Authorization", "Bearer " + fileHandler.getKey());

            // connect ensure -- can't write to connection if disabled or false
            connection.setDoOutput(true);

            /*
              Notes:
               - 'code-davinci-002' provides complex info but is not suited for realtime
               - 'code-cushman-001' only produces code (thus far) with documentation but is suitable for realtime
               - if response is longer than 'max_tokens' the response will be cut off
             */
            // json POST
            String jsonString = (
                    "{\"model\":\"code-cushman-001\"," +            // model to use -- 'code-davinci-002' or 'code-cushman-001' for code completion
                            "\"prompt\":\"" + prompt + "\"," +
                            "\"max_tokens\":2000," +                // max working value 3888 (code-davinci-002) || 2000 (code-cushman-001)
                            "\"temperature\":0.2," +                // lower == more deterministic || higher == more random -- 0.2 seems to be the sweet spot
                            "\"top_p\":1," +
                            "\"n\":1," +                            // completions to create -- leave at 1
                            "\"stream\":false," +
                            "\"logprobs\":null," +
                            "\"stop\":\"\"}"                        // where API will stop generating sequences -- leave blank to allow for full response
            );

            // convert json string to byte array
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // store response as string and print raw response
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String jsonResponseString;
                while ((jsonResponseString = br.readLine()) != null) {
                    response.append(jsonResponseString.trim());
                }
                //System.out.println("[HTTP-RESPONSE] >> " + response);
            }

            System.out.println("[RESPONSE-TEST-PROMPT] >> " + prompt);

            // parse raw json string and store easily accessible data structure
            responseData = ResponseParser.parseJSON(String.valueOf(response));

            // leave arrayList element at 0 || get text is the generated response from the API
            System.out.println("[RESPONSE-TEST-PROMPT-ANSWER] >> " + responseData.choices.get(0).getText());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return responseData;
    }
}
