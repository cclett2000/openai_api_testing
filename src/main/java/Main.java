import httpHandler.http;

public class Main {
    public static void main(String[] args) {
        // sends prompt to OpenAI and receives response
        // be as specific as possible
        http.touchGPTAPI("Create one hashmap with three values in java");
    }
}
