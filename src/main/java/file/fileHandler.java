package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// reads API Key from file ***not included in repo***
// to use api key, create a file called 'API_KEY.txt' in 'main' and paste your API key on the very first line
public class fileHandler {
    public static String getKey(){
        return readAPIKey();
    }

    private static String readAPIKey(){
        String key = "";

        try {
            File file = new File("src/main/API_KEY.txt");
            Scanner fileReader = new Scanner(file);

            key = fileReader.nextLine();
            System.out.println("[FILE-HANDLER] >> API_KEY=" + key);
        }
        catch (FileNotFoundException fileEX){
            fileEX.printStackTrace();
            System.exit(401);
        }

        return key;
    }
}
