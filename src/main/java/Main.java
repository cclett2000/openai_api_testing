import httpHandler.http;

public class Main {
    public static void main(String[] args) {
        // sends prompt to OpenAI and receives response
        http.touchGPTAPI("Create a hashmap in java with documentation");
    }
}
