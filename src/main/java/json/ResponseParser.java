// API response parser
package json;

import model.code_completion.Root;
import org.codehaus.jackson.map.ObjectMapper;

public class ResponseParser {
    public static Root parseJSON(String response){
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(response, Root.class);
        }
        catch (Exception ex){
            ex.printStackTrace();
            System.exit(500);
            return null;
        }
    }
}



