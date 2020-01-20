import bot.Bot;
import fileManagement.FileLoader;
import fileManagement.FileStringReader;
import org.json.JSONObject;
import twitch_api.StreamData;
import twitch_api.TwitchApiEndpoints;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {


    public static void main(String[] args) throws IOException {
        Bot.getInstance();
        FileLoader.getInstance().loadFileFromClasspath("data/token.txt");
        StreamData streamData =  TwitchApiEndpoints.getLiveStreamByUser(TwitchApiEndpoints.getClientID("coonh"));
        System.out.println(streamData);

        setupScanner();
    }



    private static void setupScanner(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() ->{
            String line;
            Scanner in = new Scanner(System.in);
            while ((line = in.next())!= null){
                switch (line){
                    case "quit":
                    case "stop":
                    case "exit":
                        Bot.getInstance().getMyJDA().shutdownNow();
                        System.exit(0);
                    default:
                        System.out.println("Don't know: " + line);
                }
            }
        });
    }

}
